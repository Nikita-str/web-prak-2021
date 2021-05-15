package Web;

import DAO.Interfaces.*;
import DAO.StdImpl.StdDAO_Factory;
import models.BookExHistory;
import models.BookExamples;
import models.Books;
import models.Readers;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
        map.addAttribute("taked_book_all", reader_dao.GetReaderHistory(r).size());
        map.addAttribute("taked_book_now", reader_dao.GetReaderCurBook(r).size());
        map.addAttribute("overdue_all", reader_dao.GetReaderOverdueBook(r, false).size());
        map.addAttribute("overdue_now", reader_dao.GetReaderOverdueBook(r, true).size());
        map.addAttribute("cur_bs", reader_dao.GetReaderCurBook(r));
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
    public String FindReader(
            @RequestParam(name="find_by_id", required = false) String find_by_id,
            @RequestParam(name="find_name", required = false) String name,
            @RequestParam(name="snake", required = false) String snake,
            @RequestParam(name="pat", required = false) String pat,
            @RequestParam(name="phone", required = false) String phone,
            @RequestParam(name="from_take_ex_id", required = false) String str_from_take_ex_id,
            ModelMap map)
    {
        I_ReadersDAO reader_dao = StdDAO_Factory.getInstance().getReaderDao();
        if(find_by_id != null && find_by_id.length()!=0){
            List<Readers> rs0 = new ArrayList<>();
            rs0.add(reader_dao.GetReadersById(Integer.parseInt(find_by_id)));
            map.addAttribute("rs", rs0);
            if(str_from_take_ex_id != null) map.addAttribute("from_take_ex_id", Integer.parseInt(str_from_take_ex_id));
            return "found_reader";
        }
        if(name != null && name.length()==0)name=null;
        if(snake != null && snake.length()==0)snake=null;
        if(pat != null && pat.length()==0)pat=null;
        if(phone != null && phone.length()==0)phone=null;
        if(str_from_take_ex_id != null && str_from_take_ex_id.length()==0)str_from_take_ex_id=null;

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
        if(str_from_take_ex_id != null) map.addAttribute("from_take_ex_id", Integer.parseInt(str_from_take_ex_id));
        return "found_reader";
    }


    @RequestMapping(value = "/find_book", method = RequestMethod.GET)
    public String FindBook(@RequestParam(name="find_title", required = false) String title,
                             @RequestParam(name="find_pub", required = false) String pub,
                             @RequestParam(name="find_year", required = false) String year,
                             @RequestParam(name="find_ISBN", required = false) String ISBN,
                             @RequestParam(name="from_take_rid", required = false) String str_from_take_rid,
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
        //Books b;
        //book_dao.GetBookEx(b, true, true).get(0).getBookExId();
        if(str_from_take_rid != null) map.addAttribute("from_take_rid", Integer.parseInt(str_from_take_rid));
        return "found_book";
    }

    @RequestMapping(value = "/found_reader", method = RequestMethod.GET)
    public String FoundReader(ModelMap map){ return "found_reader"; }


    @RequestMapping(value = "/book_exs", method = RequestMethod.GET)
    public String BookExs(@RequestParam(name="id") String book_id, ModelMap map)
    {
        I_BooksDAO book_dao = StdDAO_Factory.getInstance().getBookDao();
        I_BookExDAO ex_dao = StdDAO_Factory.getInstance().getBookExDao();
        int id = Integer.parseInt(book_id);
        Books book = book_dao.GetBookById(id);
        ArrayList<ArrayList<Object>> exs = new ArrayList<>();
        List<BookExamples> real_exs = book_dao.GetBookEx(id, false, false);
        real_exs.forEach(ex -> {
            ArrayList<Object> temp = new ArrayList<>();
            temp.add(ex.getBookExId());
            AtomicInteger temp_str = new AtomicInteger(-1);
            if(!ex.getDecommissioned() && !ex.getSpare()){
                ex_dao.GetExBookHistory(ex).forEach(not_ret -> {if(not_ret.getRealRetDate()==null){ temp_str.set(not_ret.getReader().getLibraryCardId());}});
            }
            temp.add(ex.getDecommissioned() ? "снята с учета" : (ex.getSpare() ? "в наличии" : ("выдана: #" + temp_str.get())));
            exs.add(temp);
        });
        map.addAttribute("exs", exs);
        map.addAttribute("book_id", id);
        map.addAttribute("title", book.getTitle());
        map.addAttribute("can_take", (book_dao.GetBookEx(id, true, true).size()>0));
        return "book_exs";
    }

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
    public String BookRet(@RequestParam(name="ex_id") String str_ex_id, @RequestParam(name="to_r_id", required = false) String str_to_r_id, ModelMap map)
    {
        I_BookExDAO ex_dao = StdDAO_Factory.getInstance().getBookExDao();
        I_ReadersDAO r_dao = StdDAO_Factory.getInstance().getReaderDao();
        int ex_id = Integer.parseInt(str_ex_id);
        BookExamples ex = ex_dao.GetBookExById(ex_id);
        r_dao.BookRet(ex.getBookExId());
        if(str_to_r_id!=null && str_to_r_id.length()>0){
            map.addAttribute("id", Integer.parseInt(str_to_r_id));
            return "redirect:reader";
        }
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

    @RequestMapping(value = "/book_take", method = RequestMethod.GET)
    public String BookTake(
            @RequestParam(name="ex_id", required = false) String str_ex_id,
            @RequestParam(name="r_id", required = false) String str_r_id,
            ModelMap map)
    {
        boolean can_take = true;
        if(str_ex_id != null) {
            I_BookExDAO ex_dao = StdDAO_Factory.getInstance().getBookExDao();
            int ex_id = Integer.parseInt(str_ex_id);
            BookExamples ex = ex_dao.GetBookExById(ex_id);
            map.addAttribute("book_ex_id", ex_id);
            map.addAttribute("title", ex.getBook().getTitle());
            map.addAttribute("not_book", false);
            if(str_r_id != null){
                if(ex_dao.ReaderHasBookEx(ex_id, Integer.parseInt(str_r_id)))can_take=false;
            }
        } else {
            map.addAttribute("not_book", true);
            can_take = false;
        }
        if(str_r_id != null) {
            I_ReadersDAO r_dao = StdDAO_Factory.getInstance().getReaderDao();
            int r_id = Integer.parseInt(str_r_id);
            map.addAttribute("reader_id", r_id);
            map.addAttribute("not_reader", false);

        } else {
            map.addAttribute("not_reader", true);
            can_take = false;
        }
        map.addAttribute("can_take", can_take);
        return "book_take";
    }


    @RequestMapping(value = "/book_take_any", method = RequestMethod.GET)
    public String BookTakeAny(@RequestParam(name="b_id") String str_b_id, ModelMap map)
    {
        I_BooksDAO b_dao = StdDAO_Factory.getInstance().getBookDao();
        int b_id = Integer.parseInt(str_b_id);
        Books book = b_dao.GetBookById(b_id);
        List<BookExamples> exs = b_dao.GetBookEx(book, true, true);
        if(exs.size() == 0){
            map.addAttribute("id", b_id);
            return "redirect:book";
        }
        map.addAttribute("ex_id", exs.get(0).getBookExId());
        return "redirect:book_take";
    }


    @RequestMapping(value = "/give_out", method = RequestMethod.POST)
    public String GiveOut(
            @RequestParam(name="r_id") String str_r_id,
            @RequestParam(name="b_ex_id") String str_b_ex_id,
            @RequestParam(name="date_take", required = false) String str_date_take,
            @RequestParam(name="date_sh_ret", required = false) String str_date_sh_ret,
            @RequestParam(name="delta_day", required = false) String str_delta_day,
            ModelMap map)
    {
        I_ReadersDAO r_dao = StdDAO_Factory.getInstance().getReaderDao();
        int r_id = Integer.parseInt(str_r_id);
        int b_ex_id = Integer.parseInt(str_b_ex_id);
        if(str_delta_day!=null && str_delta_day.length() != 0){
            int dd = Integer.parseInt(str_delta_day);
            r_dao.BookTake(b_ex_id, r_id, dd);
            return "redirect:success";
        }

        if(str_date_take!=null && str_date_take.length() != 0){
            java.sql.Date data_take = java.sql.Date.valueOf(str_date_take);
            java.sql.Date data_sh_ret = java.sql.Date.valueOf(str_date_sh_ret);
            r_dao.BookTake(b_ex_id, r_id, data_take, data_sh_ret);
            return "redirect:success";
        }

        java.sql.Date data_sh_ret_1 = java.sql.Date.valueOf(str_date_sh_ret);
        r_dao.BookTake(b_ex_id, r_id, data_sh_ret_1);
        return "redirect:success";
    }


    @RequestMapping(value = "/add_exs_to_book", method = RequestMethod.POST)
    public String AddExsToBook(@RequestParam(name="b_id") String s_b_id, @RequestParam(name="amount") String s_amount, ModelMap map)
    {
        int b_id = Integer.parseInt(s_b_id);
        int amount = Integer.parseInt(s_amount);
        StdDAO_Factory.getInstance().getBookDao().AddBookEx(b_id, amount);
        map.addAttribute("id", b_id);
        return "redirect:book";
    }

    @GetMapping("/success") public String Success(ModelMap map) { return "success"; }

    @GetMapping("/showMsgX2")
    public String passParametersWithModelMap(@RequestParam(name="msg") String msg, ModelMap map) {
        map.addAttribute("message", msg + " " + msg);
        return "empty";
    }


}
