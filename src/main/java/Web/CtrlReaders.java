package Web;

import DAO.Interfaces.I_BookExDAO;
import DAO.Interfaces.I_BooksDAO;
import DAO.Interfaces.I_ReadersDAO;
import DAO.StdImpl.StdDAO_Factory;
import models.Readers;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CtrlReaders {
    static String StrOr(String str){ return str == null ? "---" : str; }
    static String StrOr(String str, String or){
        if (or == null) or = "---";
        return str == null ? or : str;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String Index() {
        return "index";
    }

    @RequestMapping(value = "/readers", method = RequestMethod.GET)
    public String Readers() {
        return "readers";
    }

    @RequestMapping(value = "/reader", method = RequestMethod.GET)
    public String Reader(@RequestParam(name="id") String id, ModelMap map)
    {
        int card_lib = Integer.parseInt(id);
        I_ReadersDAO reader_dao = StdDAO_Factory.getInstance().getReaderDao();
        Readers r = reader_dao.GetReadersById(card_lib);
        map.addAttribute("id", card_lib);
        map.addAttribute("reader", r);
        map.addAttribute("patr", StrOr(r.getPatronymic()));
        map.addAttribute("addr", StrOr(r.getAddress()));
        map.addAttribute("p_number", StrOr(r.getPhoneNumber()));
        return "reader";
    }

    @RequestMapping(value = "/add_reader", method = RequestMethod.POST)
    public String AddReader(@RequestParam(name="add_name") String name,
                            @RequestParam(name="add_snake") String snake,
                            @RequestParam(name="add_pat") String pat,
                            @RequestParam(name="add_addr") String addr,
                            @RequestParam(name="add_phone") String phone,
                            ModelMap map)
    {
        I_ReadersDAO reader_dao = StdDAO_Factory.getInstance().getReaderDao();
        if(pat!=null && pat.length()==0)pat=null;
        if(addr!=null && addr.length()==0)addr=null;
        if(phone!=null && phone.length()==0)phone=null;
        int id = reader_dao.AddReaders(name, snake, pat, addr, phone);
        map.addAttribute("id", id);
        return "redirect:reader";
    }

    @RequestMapping(value = "/find_reader", method = RequestMethod.GET)
    public String FindReader(@RequestParam(name="find_name", required = false) String name,
                            @RequestParam(name="snake", required = false) String snake,
                            @RequestParam(name="pat", required = false) String pat,
                            @RequestParam(name="phone", required = false) String phone,
                            ModelMap map)
    {
        I_ReadersDAO reader_dao = StdDAO_Factory.getInstance().getReaderDao();
        if(name != null && name.length()==0)name=null;
        if(snake != null && snake.length()==0)snake=null;
        if(pat != null && pat.length()==0)pat=null;
        if(phone != null && phone.length()==0)phone=null;

        System.out.println(name);

        List<Readers> rs = null;
        if(phone != null){
            rs = reader_dao.FindReader_PhoneNumber(phone);
        }else if(name!=null && snake==null && pat==null){
            rs = reader_dao.FindReader(name, "", "");
        }
        else{
            rs = reader_dao.FindReader(name, snake, pat);
        }
        map.addAttribute("rs", rs);
        return "found_reader";
    }

    @RequestMapping(value = "/found_reader", method = RequestMethod.GET)
    public String FoundReader(ModelMap map){ return "found_reader"; }


    @GetMapping("/showMsgX2")
    public String passParametersWithModelMap(@RequestParam(name="msg") String msg, ModelMap map) {
        map.addAttribute("message", msg + " " + msg);
        return "empty";
    }


}
