package Web;

import DAO.Interfaces.*;
import DAO.StdImpl.StdDAO_Factory;
import models.BookExamples;
import models.Books;
import models.Readers;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Calendar;
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

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public String Books() {
        return "books";
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String Book(@RequestParam(name="id") String b_id, ModelMap map)
    {
        int id = Integer.parseInt(b_id);
        I_BooksDAO book_dao = StdDAO_Factory.getInstance().getBookDao();
        Books book = book_dao.GetBookById(id);
        map.addAttribute("id", id);
        map.addAttribute("book", book);

        if(book.getPubYear() == null)
            map.addAttribute("pub_year", "неизвестен");
        else {
            Calendar c = Calendar.getInstance();
            c.setTime(book.getPubYear());
            int year = c.get(Calendar.YEAR);
            map.addAttribute("pub_year", year);
        }
        map.addAttribute("book_pub", book.getPublisher()==null?"---":book.getPublisher().getPName());
        map.addAttribute("auths", book_dao.GetAuthorOfBook(book));
        map.addAttribute("can_take", (book_dao.GetBookEx(id, true, true).size()>0));

        return "book";
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

    @RequestMapping(value = "/add_auth", method = RequestMethod.POST)
    public String AddAuth(@RequestParam(name="add_name") String name,
                            @RequestParam(name="add_snake") String snake,
                            @RequestParam(name="add_pat") String pat,
                          @RequestParam(name="book_id") String b_id,
                            ModelMap map)
    {
        I_BooksDAO book_dao = StdDAO_Factory.getInstance().getBookDao();
        I_AuthorsDAO a_dao = StdDAO_Factory.getInstance().getAuthorDao();
        if(pat!=null && pat.length()==0)pat=null;
        int id = Integer.parseInt(b_id);
        book_dao.AddAuthorToBook(a_dao.GetAuthorId(name, snake, pat), id);
        map.addAttribute("id", id);
        return "redirect:book";
    }

    @RequestMapping(value = "/add_book", method = RequestMethod.POST)
    public String AddBook(@RequestParam(name="add_title") String title,
                            @RequestParam(name="add_about") String about,
                            @RequestParam(name="add_pub") String pub,
                            @RequestParam(name="add_year") String year,
                            @RequestParam(name="add_ISBN") String ISBN,
                          @RequestParam(name="amount") String amount,
                            ModelMap map)
    {
        I_BooksDAO book_dao = StdDAO_Factory.getInstance().getBookDao();
        if(title!=null && title.length()==0)title=null;
        if(about!=null && about.length()==0)about=null;
        if(pub!=null && pub.length()==0)pub=null;
        if(year!=null && year.length()==0)year=null;
        if(ISBN!=null && ISBN.length()==0)ISBN=null;
        if(amount!=null && amount.length()==0)amount=null;
        int id = book_dao.AddBook(title, about, pub, year == null ? null : Integer.parseInt(year), ISBN, amount == null ? 0 : Integer.parseInt(amount));
        map.addAttribute("id", id);
        return "redirect:book";
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


    @RequestMapping(value = "/find_book", method = RequestMethod.GET)
    public String FindBook(@RequestParam(name="find_title", required = false) String title,
                             @RequestParam(name="find_pub", required = false) String pub,
                             @RequestParam(name="find_year", required = false) String year,
                             @RequestParam(name="find_ISBN", required = false) String ISBN,
                             ModelMap map)
    {
        I_BooksDAO book_dao = StdDAO_Factory.getInstance().getBookDao();
        I_PublishersDAO p_dao = StdDAO_Factory.getInstance().getPublisherDao();
        if(title != null && title.length()==0)title=null;
        if(pub != null && pub.length()==0)pub=null;
        if(year != null && year.length()==0)year=null;
        if(ISBN != null && ISBN.length()==0)ISBN=null;
        List<Books> bs = book_dao.BookFind(title, pub==null?null:p_dao.GetPublisherId(pub),year==null?null:Integer.parseInt(year), ISBN, false);
        map.addAttribute("bs", bs);
        return "found_book";
    }

    @RequestMapping(value = "/found_reader", method = RequestMethod.GET)
    public String FoundReader(ModelMap map){ return "found_reader"; }

    @RequestMapping(value = "/book_ex", method = RequestMethod.GET)
    public String BookEx(@RequestParam(name="ex_id") String str_ex_id, ModelMap map)
    {
        I_BookExDAO ex_dao = StdDAO_Factory.getInstance().getBookExDao();
        int ex_id = Integer.parseInt(str_ex_id);
        BookExamples ex = ex_dao.GetBookExById(ex_id);
        map.addAttribute("ex_id", ex_id);
        map.addAttribute("title", ex.getBook().getTitle());
        map.addAttribute("book_id", ex.getBook().getBookId());
        map.addAttribute("can_take", ex.getSpare());
        map.addAttribute("dereg", ex.getDecommissioned());
        return "book_ex";
    }

    @RequestMapping(value = "/book_ret", method = RequestMethod.GET)
    public String BookRet(@RequestParam(name="ex_id") String str_ex_id, ModelMap map)
    {
        I_BookExDAO ex_dao = StdDAO_Factory.getInstance().getBookExDao();
        I_ReadersDAO r_dao = StdDAO_Factory.getInstance().getReaderDao();
        int ex_id = Integer.parseInt(str_ex_id);
        BookExamples ex = ex_dao.GetBookExById(ex_id);
        r_dao.BookRet(ex.getBookExId());
        map.addAttribute("id", ex.getBook().getBookId());
        return "redirect:book";
    }

    @RequestMapping(value = "/book_lost", method = RequestMethod.GET)
    public String BookLost(@RequestParam(name="ex_id") String str_ex_id, ModelMap map)
    {
        I_BookExDAO ex_dao = StdDAO_Factory.getInstance().getBookExDao();
        int ex_id = Integer.parseInt(str_ex_id);
        BookExamples ex = ex_dao.GetBookExById(ex_id);
        ex_dao.BookExDereg(ex_id, false);
        map.addAttribute("id", ex.getBook().getBookId());
        return "redirect:book";
    }

    @GetMapping("/showMsgX2")
    public String passParametersWithModelMap(@RequestParam(name="msg") String msg, ModelMap map) {
        map.addAttribute("message", msg + " " + msg);
        return "empty";
    }


}
