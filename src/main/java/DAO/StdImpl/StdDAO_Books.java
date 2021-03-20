package DAO.StdImpl;

import DAO.Interfaces.I_BooksDAO;
import DAO.Interfaces.StdImpl_BookDAO;
import models.Authors;
import models.BookExamples;
import models.Books;
import models.Publishers;
import utils.SQL_FuncCall;
import utils.SessionHelper;

import javax.persistence.TypedQuery;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

public class StdDAO_Books extends StdImpl_BookDAO implements I_BooksDAO {

    @Override public Books GetBookById(int bk_id) { return SessionHelper.InSessionActWithR(ses -> ses.load(Books.class, bk_id)); }

    @Override public List<Authors> GetAuthorOfBook(Integer book_id) {
        return SessionHelper.InSessionActWithL(ses -> SQL_FuncCall.GetAuthorsOfBook(ses, book_id), Authors.class);
    }

    @Override public void AddAuthorToBook(Integer author_id, Integer book_id) {
        SessionHelper.InSessionAct(ses -> SQL_FuncCall.AddAuthorToBook(ses, author_id, book_id));
    }

    @Override
    public void AddBookEx(Integer bk_id, int amount) {
        SessionHelper.InSessionAct((ses -> SQL_FuncCall.AddBookEx(ses, bk_id, amount)));
    }

    @Override
    public Integer AddBook(String title) {
        return SessionHelper.InSessionActWithR((ses -> SQL_FuncCall.AddBook(ses, title)));
    }

    @Override
    public Integer AddBook(String title, String about, Integer publ_id, Integer pub_year, String ISBN, Integer amount) {
        if(amount == null)amount = 0;
        Integer am = amount;
        return SessionHelper.InSessionActWithR((ses -> SQL_FuncCall.AddBook(ses, title, about, publ_id, pub_year, ISBN, am)));
    }

    @Override
    public Integer AddBook(String title, String about, String publ_name, Integer pub_year, String ISBN, Integer amount) {
        if(amount == null)amount = 0;
        Integer am = amount;
        return SessionHelper.InSessionActWithR((ses -> SQL_FuncCall.AddBook(ses, title, about, publ_name, pub_year, ISBN, am)));
    }

    @Override
    public Integer AddBook(String title, String about, Integer pub_year, String ISBN, Integer amount) {
        if(amount == null)amount = 0;
        Integer am = amount;
        return SessionHelper.InSessionActWithR((ses -> SQL_FuncCall.AddBook(ses, title, about, pub_year, ISBN, am)));
    }

    @Override
    public void BookDereg(Integer _book_id, Boolean need_to_ret) {
        SessionHelper.InSessionAct((ses -> SQL_FuncCall.BookDereg(ses, _book_id, need_to_ret)));
    }

    @Override
    public List<BookExamples> GetBookEx(int book_id, boolean only_spare, boolean wo_decommissioned) {
        String add_spare = only_spare ? " AND spare = TRUE " : "";
        String add_decom = wo_decommissioned ? " AND decommissioned = FALSE " : "";
        return SessionHelper.InSessionActWithR((ses -> {
            TypedQuery<BookExamples> q = ses.createQuery("FROM BookExamples WHERE book.bookId = :book_id" + add_spare + add_decom, BookExamples.class);
            q.setParameter("book_id", book_id);
            return q.getResultList();
        }));
    }

    @Override
    public List<Books> BookFind_Publisher(String pub_name) {
        List<Publishers> pubs =  SessionHelper.InSessionActWithL(ses -> SQL_FuncCall.FindPublisher(ses, pub_name, false), Publishers.class);
        if(pubs.isEmpty())return new ArrayList<>();
        ArrayList<Books> ret = new ArrayList<>();
        pubs.forEach(pub -> ret.addAll(BookFind_Publisher(pub.getPId())));
        return ret;
    }

    @Override
    public List<Books> BookFind_All(boolean with_decommissioned) {
        String add_s = with_decommissioned ? "" : " AND decommissioned = FALSE ";
        return SessionHelper.InSessionActWithR((ses -> {
            TypedQuery<Books> q =ses.createQuery("FROM Books" + add_s);
            return  q.getResultList();
        }));
    }

    @Override
    public List<Books> BookFind(String title, Integer pub_id, Integer pub_year, String isbn, boolean with_decommissioned) {
        if(title == null && pub_id == null && pub_year == null && isbn == null)return BookFind_All(with_decommissioned);
        String add_title = (title == null) ? "" : " AND title LIKE (LOWER('" + title + "') || '%') ";
        String add_isbn = (isbn == null) ? "" : " AND REPLACE(isbn, '-', '') LIKE (REPLACE('"+ isbn + "', '-', '') || '%')  ";
        String add_pid = (pub_id == null) ? "" : " AND publisher_id = " + pub_id + " ";
        String add_year = (pub_year == null) ? "" : " AND EXTRACT(YEAR FROM pub_year) = " + pub_year + " ";
        return SessionHelper.InSessionActWithR((ses -> {
            TypedQuery<Books> q = ses.createQuery("FROM Books WHERE (1 = 1)" + add_title + add_isbn + add_pid + add_year, Books.class);
            return q.getResultList();
        }));
    }
}
