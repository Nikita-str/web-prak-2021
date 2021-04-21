package Web;

import DAO.Interfaces.I_ReadersDAO;
import DAO.StdImpl.StdDAO_Factory;
import models.Readers;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class CtrlReaders {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() { return "Hello, creature!"; }

    @RequestMapping(value = "/che", method = RequestMethod.GET)
    public String che() {
        return "che";
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String check() {
        return "check";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/readers")
    public String Readers(@RequestParam(name="id", required=false, defaultValue = "None") String name, Model model) {

        StdDAO_Factory factory = StdDAO_Factory.getInstance();
        I_ReadersDAO reader_dao = factory.getReaderDao();
        List<Readers> rs = reader_dao.FindReader_Surname("sur");


        model.addAttribute("rs_amount", rs.size());
        return "readers";
    }

}
