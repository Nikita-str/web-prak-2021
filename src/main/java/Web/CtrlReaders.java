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

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String Index() {
        return "index";
    }

    @RequestMapping(value = "/readers", method = RequestMethod.GET)
    public String Readers() {
        return "readers";
    }

}
