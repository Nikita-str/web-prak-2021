using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JavaCodeGen
  {

  public static class CodeTab
    {
    static int TabSize = 4;
    static int Tabs = 0;
    static string NowAdd = "";
    public static void SetTabSize(int tab_sz) { TabSize = tab_sz; } 
    public static void AddTab() { Tabs++; NowAdd = new string(' ', TabSize * Tabs); }
    public static void DecTab() { Tabs--; NowAdd = new string(' ', (Tabs > 0) ? TabSize * Tabs : 0); }

    public static void CodeLine(this StreamWriter sw, string s) { sw.WriteLine(NowAdd + s); }
    public static string AddCodeLine(this string str, string s) { return str + NowAdd + s + '\n'; }
    }


  public static class SqlToJava
    {
    public static (bool, string) ToJavaType(string sql_type)
      {
      var t = sql_type.ToLower();
      if(t.Contains("varchar") || t == "text") return (true, "String");
      if(t == "bool") return (true, "Boolean");
      if(t == "int" || t == "integer") return (true, "Integer");
      if(t.Contains("setof")) return (true, "List");
      if(t == "date") return (true, "Date"); 
      return (false, "!");
      }

    public static string ToJavaFuncName(this string s)
      {
      string ret = "";
      bool big = true;
      foreach(char c in s)
        {
        if(c == '_') { big = true; continue; }
        if(big) ret += c.ToString().ToUpper();
        else ret += c;
        big = false;
        }
      return ret;
      }
    }

  enum ParamCallType
    {
    IN,
    OUT,
    RET
    }

  class OneParam
    {
    bool valid = true;

    string param_name = "";
    string param_type = "";
    public ParamCallType pct { get; private set; } = ParamCallType.IN;

    public OneParam(string _param_name, string _param_type, ParamCallType _pct)
      {
      param_name = _param_name;
      param_type = _param_type;
      pct = _pct;
      }

    public OneParam(string p)
      {
      var s = p.Trim().Split(' ');
      if(s.Length > 3 || s.Length < 2) { valid = false; return; }
      bool p_count_3 = s.Length == 3;
      if(p_count_3)
        {
        if(s[0].Contains("OUT")) pct = ParamCallType.OUT;
        else { valid = false; return; }
        }
      param_name = (p_count_3 ? s[1] : s[0]).Trim();
      var type = SqlToJava.ToJavaType((p_count_3 ? s[2] : s[1]).Trim()); 
      if(type.Item1 == false) { valid = false; return; }
      param_type = type.Item2;
      }

    public bool IsValid() { return valid; }
    public string ToJavaCode(bool with_check = false)
      {
      if(with_check && !valid) return "!";
      return param_type + " " + param_name;
      }

    public string GetParamType() { return param_type; }

    public string RegParam()
      {
      if(pct == ParamCallType.RET) return null;
      return"query.registerParameter(\"" + param_name + "\", " + param_type + ".class, ParameterMode." + 
        (pct == ParamCallType.IN ? "IN).enablePassingNulls(true)" : "OUT)") + ";"; 
      }

    public string SetParam()
      {
      if(pct != ParamCallType.IN) return "";
      return "query.setParameter(\"" + param_name + "\", " + param_name + ");";
      }

    public string GetRetFunc()
      {
      if(pct == ParamCallType.IN) return null;
      if(pct == ParamCallType.OUT) return "("+param_type+") query.getOutputParameterValue(\""+param_name+"\");";
      if(param_type == "List") return "query.getResultList();";
      return "(" + param_type + ") query.getSingleResult();";
      }
    }

  class Parameters
    {
    private int InParam = 0;
    List<OneParam> parameters = new List<OneParam>();
    public Parameters(string[] ps)
      {
      parameters.Capacity = ps.Length;
      foreach(var p in ps)AddParameter(new OneParam(p));
      }

    public void AddParameter(OneParam op)
      {
      parameters.Add(op);
      if(op.pct == ParamCallType.IN) InParam++;
      }

    public bool IsValid() { return parameters.FindIndex(op => !op.IsValid()) == -1; }
    private List<int> RetVal()
      {
      var ret = new List<int>();
      for(int i = 0; i < parameters.Count; i++)
        if(parameters[i].pct != ParamCallType.IN) ret.Add(i);
      return ret;
      }

    public (bool, string) GetRetValue()
      {
      var rv = RetVal();
      if(rv.Count > 1) return (false, "!");
      if(rv.Count == 0) return (true, "void");
      return (true, parameters[rv[0]].GetParamType());
      }

    public string ToJavaCode(bool with_check = false)
      {
      if(with_check && !IsValid()) return "!";
      string ret = "";
      int len = parameters.Count;
      bool first = true;
      for(int i = 0; i < len; i++)
        {
        if(parameters[i].pct != ParamCallType.IN) continue;
        if(!first) ret += ", ";
        ret += parameters[i].ToJavaCode();
        first = false;
        }
      return ret;
      }

    public string RegParam()
      {
      string ret = "";
      foreach(var p in parameters)
        if(p.pct != ParamCallType.RET) ret = ret.AddCodeLine(p.RegParam());
      return ret;
      }

    public string SetParam()
      {
      if(InParam == 0) return "";
      string ret = "";
      foreach(var p in parameters)
        if(p.pct == ParamCallType.IN) ret = ret.AddCodeLine(p.SetParam());
      return ret;
      }

    public string ReturnLine()
      {
      var rv = RetVal();
      if(rv.Count == 0) return "".AddCodeLine("return;");
      return "".AddCodeLine("return " + parameters[rv[0]].GetRetFunc());
      }
    }

  class Program
    {
    static void Main(string[] args)
      {
      bool open = false;
      StreamReader sr = null;
      while(!open)
        {
        try
          {
          Console.WriteLine("path to file with SQL functions");
          sr = new StreamReader(Console.ReadLine());
          open = true;
          }
        catch{Console.WriteLine("cant open file, try again");}
        }
      
      StreamWriter sw = new StreamWriter("SQL_FuncCall.java");

      sw.WriteLine("import org.hibernate.Session;");
      sw.WriteLine("import javax.persistence.ParameterMode;");
      sw.WriteLine("import org.hibernate.procedure.ProcedureCall;");
      sw.WriteLine("import java.sql.Date;");
      sw.WriteLine("import java.util.List;");
      
      sw.WriteLine();
      sw.WriteLine("public class SQL_FuncCall {");
      CodeTab.AddTab();

      int func_count = 0;
      while(!sr.EndOfStream)
        {
        const string CREATE = "CREATE";
        const string FUNCTION = "FUNCTION";
        const string PROCEDURE = "PROCEDURE";

        var line = sr.ReadLine().Trim();
        if(!line.StartsWith(CREATE)) continue;
        int ind = line.IndexOf(FUNCTION);
        bool is_function = ind != -1;
        if(!is_function) ind = line.IndexOf(PROCEDURE);
        if(ind == -1) continue;
        line = line.Substring(ind + (is_function ? FUNCTION : PROCEDURE).Length);
        int ind_open = line.IndexOf('(');
        int ind_close = line.LastIndexOf(')');
        if(ind_open < 0 || ind_close < 0) { Console.WriteLine("Hmm... [1]"); continue; }

        string func_str = "\n";

        string name_of_func = line.Substring(0, ind_open).Trim();
        var params_info = line.Substring(ind_open + 1, ind_close - ind_open - 1).Split(',');

        Parameters ps = new Parameters(params_info);
        if(!ps.IsValid()) 
          {
          Console.WriteLine("Hmm... [2]");
          continue;
          }

        if(is_function)
          {
          line = sr.ReadLine().Trim();
          if(line.Contains("RETURNS"))
            {
            var temp0 = SqlToJava.ToJavaType(line.Split(' ')[1]);
            if(!temp0.Item1) { Console.WriteLine("Hmm... [3]"); continue; }
            ps.AddParameter(new OneParam("", temp0.Item2, ParamCallType.RET));
            }
          }

        var temp1 = ps.GetRetValue();
        if(!temp1.Item1) { Console.WriteLine("Hmm... [4]"); continue; }
        func_str = func_str.AddCodeLine("public " + temp1.Item2 + " " + name_of_func.ToJavaFuncName() + "(Session session, " + ps.ToJavaCode() +  ") {");
        CodeTab.AddTab();
        func_str = func_str.AddCodeLine("ProcedureCall query = session.createStoredProcedureCall(\"" + name_of_func + "\");");
        func_str += ps.RegParam();
        func_str += ps.SetParam();
        func_str = func_str.AddCodeLine("query.execute();");
        func_str += ps.ReturnLine();
        CodeTab.DecTab();
        func_str = func_str.AddCodeLine("}");

        sw.WriteLine(func_str);
        func_count++;
        }

      CodeTab.DecTab();
      sw.WriteLine("}");
      
      sw.Close();
      sr.Close();

      Console.WriteLine("F amount:" + func_count);

      Console.WriteLine("Press any key (except for power off, win ...)");
      Console.ReadKey();
      }
    }
  }
