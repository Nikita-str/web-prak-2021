package DAO.Interfaces;

import models.Authors;
import models.BookExamples;
import models.Books;
import models.Publishers;
import org.hibernate.Session;

import java.util.List;

public interface I_BooksDAO {
    public Books GetBookById(int bk_id);

    public List<Authors> GetAuthorOfBook(Books book);
    public List<Authors> GetAuthorOfBook(Integer book_id);

    public void AddAuthorToBook(Authors aut, Books book);
    public void AddAuthorToBook(Authors aut, Integer book_id);
    public void AddAuthorToBook(Integer author_id, Books book);
    public void AddAuthorToBook(Integer author_id, Integer book_id);

    public void AddBookEx(Books book, int amount);
    public void AddBookEx(Integer bk_id, int amount);

    public Integer AddBook(String title);
    public Integer AddBook(String title, String about, Integer publ_id, Integer pub_year, String ISBN, Integer amount);
    public Integer AddBook(String title, String about, String publ_name, Integer pub_year, String ISBN, Integer amount);
    public Integer AddBook(String title, String about, Integer pub_year, String ISBN, Integer amount);

    public void BookDereg(Books book, Boolean need_to_ret);
    public void BookDereg(Integer _book_id, Boolean need_to_ret);

    public List<BookExamples> GetBookEx(Books book);
    public List<BookExamples> GetBookEx(int book_id);
    public List<BookExamples> GetBookEx(Books book, boolean only_spare, boolean wo_decommissioned);
    public List<BookExamples> GetBookEx(int book_id, boolean only_spare, boolean wo_decommissioned);

    public List<Books> BookFind_Title(String title);
    public List<Books> BookFind_Publisher(String pub_name);
    public List<Books> BookFind_Publisher(int pub_id);
    public List<Books> BookFind_Year(int pub_year);
    public List<Books> BookFind_Isbn(String isbn);
    public List<Books> BookFind_All();
    public List<Books> BookFind_All(boolean with_decommissioned);
    public List<Books> BookFind(String title, Integer pub_id, Integer pub_year, String isbn, boolean with_decommissioned);
}
