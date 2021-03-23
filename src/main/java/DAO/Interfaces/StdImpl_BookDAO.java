package DAO.Interfaces;

import models.Authors;
import models.BookExamples;
import models.Books;

import java.util.ArrayList;
import java.util.List;

public abstract class StdImpl_BookDAO implements I_BooksDAO {
    @Override public List<Authors> GetAuthorOfBook(Books book) { return GetAuthorOfBook(book.getBookId()); }

    @Override public void AddAuthorToBook(Authors aut, Books book) { AddAuthorToBook(aut.getAuthorId(), book.getBookId()); }
    @Override public void AddAuthorToBook(Authors aut, Integer book_id) { AddAuthorToBook(aut.getAuthorId(), book_id); }
    @Override public void AddAuthorToBook(Integer author_id, Books book) { AddAuthorToBook(author_id, book.getBookId()); }

    @Override public void BookDereg(Books book, Boolean need_to_ret) { BookDereg(book.getBookId(), need_to_ret);}

    @Override public void AddBookEx(Books book, int amount) { AddBookEx(book.getBookId(), amount);}

    @Override public List<BookExamples> GetBookEx(Books book) { return GetBookEx(book.getBookId());}
    @Override public List<BookExamples> GetBookEx(int book_id) { return GetBookEx(book_id, true, true);}
    @Override public List<BookExamples> GetBookEx(Books book, boolean only_spare, boolean wo_decom)
            { return GetBookEx(book.getBookId(), only_spare, wo_decom);}

    @Override public List<Books> BookFind_All(){return BookFind_All(false);}
    @Override public List<Books> BookFind_Title(String title) {
        if(title == null) return new ArrayList<>();
        return BookFind(title, null, null, null, false);
    }
    @Override public List<Books> BookFind_Publisher(int pub_id) {
        return BookFind(null, pub_id, null, null, false);
    }
    @Override public List<Books> BookFind_Year(int pub_year) {
        return BookFind(null, null, pub_year, null, false);
    }
    @Override public List<Books> BookFind_Isbn(String isbn) {
        if(isbn == null) return new ArrayList<>();
        return BookFind(null, null, null, isbn, false);
    }
}
