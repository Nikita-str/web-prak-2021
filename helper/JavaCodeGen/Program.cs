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
    public static string CodeLine(this string str, string s) { return str + NowAdd + s + '\n'; }
    }


  public static class SqlToJava
    {
    public static (bool, string) ToJavaType(string sql_type)
      {
      var t = sql_type.ToLower();
      if(t.Contains("varchar") || t == "text") return (true, "String");
      if(t == "bool") return (true, "Boolean");
      if(t == "int" || t == "integer") return (true, "Integer");
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

  class OneParam
    {
    bool valid = true;

    string param_name = "";
    string param_type = "";
    bool IN = true;
    public OneParam(string p)
      {
      var s = p.Trim().Split(' ');
      if(s.Length > 3 || s.Length < 2) { valid = false; return; }
      bool p_count_3 = s.Length == 3;
      if(p_count_3)
        {
        if(s[0].Contains("OUT")) IN = false;
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
    }

  class Parameters
    {
    List<OneParam> parameters = new List<OneParam>();
    public Parameters(string[] ps)
      {
      parameters.Capacity = ps.Length;
      foreach(var p in ps) parameters.Add(new OneParam(p));
      }

    public bool IsValid() { return parameters.FindIndex(op => !op.IsValid()) == -1; }
    public string ToJavaCode(bool with_check = false)
      {
      if(with_check && !IsValid()) return "!";
      string ret = "";
      int len = parameters.Count;
      for(int i =0; i < len; i++)
        ret += parameters[i].ToJavaCode() + ((i == len - 1) ? "" : ", "); 
      return ret;
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

      StreamWriter sw = new StreamWriter("temp.java");

      sw.WriteLine("import org.hibernate.Session;");
      sw.WriteLine("import javax.persistence.ParameterMode;");
      sw.WriteLine("import javax.persistence.StoredProcedureQuery;");
      sw.WriteLine("import java.sql.Date;");
      
      sw.WriteLine();
      sw.WriteLine("public class Temp {");
      CodeTab.AddTab();

      int func_count = 0;
      while(!sr.EndOfStream)
        {
        const string CREATE = "CREATE";
        const string FUNCTION = "FUNCTION";
        const string PROCEDURE = "PROCEDURE";

        const string TAB = "    ";

        var line = sr.ReadLine().Trim();
        if(!line.StartsWith(CREATE)) continue;
        int ind = line.IndexOf(FUNCTION);
        bool is_function = ind != -1;
        if(!is_function) ind = line.IndexOf(PROCEDURE);
        if(ind == -1) continue;
        line = line.Substring(ind + (is_function ? FUNCTION : PROCEDURE).Length);
        int ind_open = line.IndexOf('(');
        int ind_close = line.LastIndexOf(')');
        if(ind_open < 0 || ind_close < 0) { Console.WriteLine("Hmm..."); continue; }

        string func_str = "";

        string name_of_func = line.Substring(0, ind_open).Trim();
        var params_info = line.Substring(ind_open + 1, ind_close - ind_open - 1).Split(',');

        Parameters ps = new Parameters(params_info);
        if(!ps.IsValid()) 
          {
          Console.WriteLine("Hmm...");
          continue;
          }

        func_str = func_str.CodeLine("public " + name_of_func.ToJavaFuncName() + "(" + ps.ToJavaCode() +  ") {");

        Console.WriteLine(func_str);//TODO:DEL

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
