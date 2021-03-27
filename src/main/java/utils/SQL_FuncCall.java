package utils;

import org.hibernate.Session;
import javax.persistence.ParameterMode;
import org.hibernate.procedure.ProcedureCall;
import java.sql.Date;
import java.util.List;

public class SQL_FuncCall {

    public static List FindPublisher(Session session, String publ_name, Boolean complete_match) {
        ProcedureCall query = session.createStoredProcedureCall("find_publisher");
        query.registerParameter("publ_name", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("complete_match", Boolean.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("publ_name", publ_name);
        query.setParameter("complete_match", complete_match);
        query.execute();
        return query.getResultList();
    }


    public static Integer GetPublisherId(Session session, String publ_name) {
        ProcedureCall query = session.createStoredProcedureCall("get_publisher_id");
        query.registerParameter("publ_name", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("ret_p_id", Integer.class, ParameterMode.OUT);
        query.setParameter("publ_name", publ_name);
        query.execute();
        return (Integer) query.getOutputParameterValue("ret_p_id");
    }


    public static List FindAuthor(Session session, String f_name, String s_name, String patr, Boolean complete_match) {
        ProcedureCall query = session.createStoredProcedureCall("find_author");
        query.registerParameter("f_name", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("s_name", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("patr", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("complete_match", Boolean.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("f_name", f_name);
        query.setParameter("s_name", s_name);
        query.setParameter("patr", patr);
        query.setParameter("complete_match", complete_match);
        query.execute();
        return query.getResultList();
    }


    public static List FindAuthor(Session session, String s0, String s1, Boolean complete_match) {
        ProcedureCall query = session.createStoredProcedureCall("find_author");
        query.registerParameter("s0", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("s1", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("complete_match", Boolean.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("s0", s0);
        query.setParameter("s1", s1);
        query.setParameter("complete_match", complete_match);
        query.execute();
        return query.getResultList();
    }


    public static List FindAuthor(Session session, String surname, Boolean complete_match) {
        ProcedureCall query = session.createStoredProcedureCall("find_author");
        query.registerParameter("surname", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("complete_match", Boolean.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("surname", surname);
        query.setParameter("complete_match", complete_match);
        query.execute();
        return query.getResultList();
    }


    public static Integer AddAuthor(Session session, String f_name, String s_name, String patr) {
        ProcedureCall query = session.createStoredProcedureCall("add_author");
        query.registerParameter("f_name", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("s_name", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("patr", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("ret_author_id", Integer.class, ParameterMode.OUT);
        query.setParameter("f_name", f_name);
        query.setParameter("s_name", s_name);
        query.setParameter("patr", patr);
        query.execute();
        return (Integer) query.getOutputParameterValue("ret_author_id");
    }


    public static void AddAuthorToBook(Session session, Integer author_id, Integer book_id) {
        ProcedureCall query = session.createStoredProcedureCall("add_author_to_book");
        query.registerParameter("author_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("book_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("author_id", author_id);
        query.setParameter("book_id", book_id);
        query.execute();
        return;
    }


    public static List GetAuthorsOfBook(Session session, Integer _book_id) {
        ProcedureCall query = session.createStoredProcedureCall("get_authors_of_book");
        query.registerParameter("_book_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("_book_id", _book_id);
        query.execute();
        return query.getResultList();
    }


    public static Integer GetAuthorId(Session session, String f_name, String s_name, String patr) {
        ProcedureCall query = session.createStoredProcedureCall("get_author_id");
        query.registerParameter("f_name", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("s_name", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("patr", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("ret_author_id", Integer.class, ParameterMode.OUT);
        query.setParameter("f_name", f_name);
        query.setParameter("s_name", s_name);
        query.setParameter("patr", patr);
        query.execute();
        return (Integer) query.getOutputParameterValue("ret_author_id");
    }


    public static void AddBookEx(Session session, Integer bk_id, Integer amount) {
        ProcedureCall query = session.createStoredProcedureCall("add_book_ex");
        query.registerParameter("bk_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("amount", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("bk_id", bk_id);
        query.setParameter("amount", amount);
        query.execute();
        return;
    }


    public static Integer AddBook(Session session, String title) {
        ProcedureCall query = session.createStoredProcedureCall("add_book");
        query.registerParameter("title", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("ret_book_id", Integer.class, ParameterMode.OUT);
        query.setParameter("title", title);
        query.execute();
        return (Integer) query.getOutputParameterValue("ret_book_id");
    }


    public static Integer AddBook(Session session, String title, String about, Integer publ_id, Integer pub_year, String ISBN, Integer amount) {
        ProcedureCall query = session.createStoredProcedureCall("add_book");
        query.registerParameter("title", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("about", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("publ_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("pub_year", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("ISBN", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("amount", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("ret_book_id", Integer.class, ParameterMode.OUT);
        query.setParameter("title", title);
        query.setParameter("about", about);
        query.setParameter("publ_id", publ_id);
        query.setParameter("pub_year", pub_year);
        query.setParameter("ISBN", ISBN);
        query.setParameter("amount", amount);
        query.execute();
        return (Integer) query.getOutputParameterValue("ret_book_id");
    }


    public static Integer AddBook(Session session, String title, String about, String publ_name, Integer pub_year, String ISBN, Integer amount) {
        ProcedureCall query = session.createStoredProcedureCall("add_book");
        query.registerParameter("title", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("about", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("publ_name", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("pub_year", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("ISBN", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("amount", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("ret_book_id", Integer.class, ParameterMode.OUT);
        query.setParameter("title", title);
        query.setParameter("about", about);
        query.setParameter("publ_name", publ_name);
        query.setParameter("pub_year", pub_year);
        query.setParameter("ISBN", ISBN);
        query.setParameter("amount", amount);
        query.execute();
        return (Integer) query.getOutputParameterValue("ret_book_id");
    }


    public static Integer AddBook(Session session, String title, String about, Integer pub_year, String ISBN, Integer amount) {
        ProcedureCall query = session.createStoredProcedureCall("add_book");
        query.registerParameter("title", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("about", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("pub_year", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("ISBN", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("amount", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("ret_book_id", Integer.class, ParameterMode.OUT);
        query.setParameter("title", title);
        query.setParameter("about", about);
        query.setParameter("pub_year", pub_year);
        query.setParameter("ISBN", ISBN);
        query.setParameter("amount", amount);
        query.execute();
        return (Integer) query.getOutputParameterValue("ret_book_id");
    }


    public static Integer AddReader(Session session, String f_name, String s_name, String patr, String address, String phone_number) {
        ProcedureCall query = session.createStoredProcedureCall("add_reader");
        query.registerParameter("f_name", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("s_name", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("patr", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("address", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("phone_number", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("ret_reader_id", Integer.class, ParameterMode.OUT);
        query.setParameter("f_name", f_name);
        query.setParameter("s_name", s_name);
        query.setParameter("patr", patr);
        query.setParameter("address", address);
        query.setParameter("phone_number", phone_number);
        query.execute();
        return (Integer) query.getOutputParameterValue("ret_reader_id");
    }


    public static Boolean TheSameBook(Session session, Integer ex_id_1, Integer ex_id_2) {
        ProcedureCall query = session.createStoredProcedureCall("the_same_book");
        query.registerParameter("ex_id_1", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("ex_id_2", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("ex_id_1", ex_id_1);
        query.setParameter("ex_id_2", ex_id_2);
        query.execute();
        return (Boolean) query.getSingleResult();
    }


    public static Boolean BookAlreadyTaked(Session session, Integer bk_ex_id) {
        ProcedureCall query = session.createStoredProcedureCall("book_already_taked");
        query.registerParameter("bk_ex_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("bk_ex_id", bk_ex_id);
        query.execute();
        return (Boolean) query.getSingleResult();
    }


    public static Boolean BookIsDereg(Session session, Integer bk_ex_id) {
        ProcedureCall query = session.createStoredProcedureCall("book_is_dereg");
        query.registerParameter("bk_ex_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("bk_ex_id", bk_ex_id);
        query.execute();
        return (Boolean) query.getSingleResult();
    }


    public static Boolean ReaderHasBookEx(Session session, Integer bk_ex_id, Integer _lib_card_id) {
        ProcedureCall query = session.createStoredProcedureCall("reader_has_book_ex");
        query.registerParameter("bk_ex_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("_lib_card_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("bk_ex_id", bk_ex_id);
        query.setParameter("_lib_card_id", _lib_card_id);
        query.execute();
        return (Boolean) query.getSingleResult();
    }


    public static void BookTake(Session session, Integer bk_ex_id, Integer lib_card_id, Date date_issue, Date schedule_ret_date) {
        ProcedureCall query = session.createStoredProcedureCall("book_take");
        query.registerParameter("bk_ex_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("lib_card_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("date_issue", Date.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("schedule_ret_date", Date.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("bk_ex_id", bk_ex_id);
        query.setParameter("lib_card_id", lib_card_id);
        query.setParameter("date_issue", date_issue);
        query.setParameter("schedule_ret_date", schedule_ret_date);
        query.execute();
        return;
    }


    public static void BookTake(Session session, Integer bk_ex_id, Integer lib_card_id, Date schedule_ret_date) {
        ProcedureCall query = session.createStoredProcedureCall("book_take_with_ret_date");
        query.registerParameter("bk_ex_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("lib_card_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("schedule_ret_date", Date.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("bk_ex_id", bk_ex_id);
        query.setParameter("lib_card_id", lib_card_id);
        query.setParameter("schedule_ret_date", schedule_ret_date);
        query.execute();
        return;
    }


    public static void BookTake(Session session, Integer book_ex_id, Integer lib_card_id, Integer day_for_ret) {
        ProcedureCall query = session.createStoredProcedureCall("book_take");
        query.registerParameter("book_ex_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("lib_card_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("day_for_ret", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("book_ex_id", book_ex_id);
        query.setParameter("lib_card_id", lib_card_id);
        query.setParameter("day_for_ret", day_for_ret);
        query.execute();
        return;
    }


    public static void BookRet(Session session, Integer bk_ex_id) {
        ProcedureCall query = session.createStoredProcedureCall("book_ret");
        query.registerParameter("bk_ex_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("bk_ex_id", bk_ex_id);
        query.execute();
        return;
    }


    public static List GetExBookHistory(Session session, Integer bk_ex_id) {
        ProcedureCall query = session.createStoredProcedureCall("get_ex_book_history");
        query.registerParameter("bk_ex_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("bk_ex_id", bk_ex_id);
        query.execute();
        return query.getResultList();
    }


    public static List GetReaderHistory(Session session, Integer _lib_card_id) {
        ProcedureCall query = session.createStoredProcedureCall("get_reader_history");
        query.registerParameter("_lib_card_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("_lib_card_id", _lib_card_id);
        query.execute();
        return query.getResultList();
    }


    public static List GetReaderCurBook(Session session, Integer _lib_card_id) {
        ProcedureCall query = session.createStoredProcedureCall("get_reader_cur_book");
        query.registerParameter("_lib_card_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("_lib_card_id", _lib_card_id);
        query.execute();
        return query.getResultList();
    }


    public static List GetReaderOverdueBook(Session session, Integer _lib_card_id, Boolean only_cur) {
        ProcedureCall query = session.createStoredProcedureCall("get_reader_overdue_book");
        query.registerParameter("_lib_card_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("only_cur", Boolean.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("_lib_card_id", _lib_card_id);
        query.setParameter("only_cur", only_cur);
        query.execute();
        return query.getResultList();
    }


    public static Boolean ReaderCanPassLibCard(Session session, Integer _lib_card_id) {
        ProcedureCall query = session.createStoredProcedureCall("reader_can_pass_lib_card");
        query.registerParameter("_lib_card_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("_lib_card_id", _lib_card_id);
        query.execute();
        return (Boolean) query.getSingleResult();
    }


    public static void BookExDereg(Session session, Integer bk_ex_id, Boolean need_to_ret) {
        ProcedureCall query = session.createStoredProcedureCall("book_ex_dereg");
        query.registerParameter("bk_ex_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("need_to_ret", Boolean.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("bk_ex_id", bk_ex_id);
        query.setParameter("need_to_ret", need_to_ret);
        query.execute();
        return;
    }


    public static void BookDereg(Session session, Integer _book_id, Boolean need_to_ret) {
        ProcedureCall query = session.createStoredProcedureCall("book_dereg");
        query.registerParameter("_book_id", Integer.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("need_to_ret", Boolean.class, ParameterMode.IN).enablePassingNulls(true);
        query.setParameter("_book_id", _book_id);
        query.setParameter("need_to_ret", need_to_ret);
        query.execute();
        return;
    }

}
