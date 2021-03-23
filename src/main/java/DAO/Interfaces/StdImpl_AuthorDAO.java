package DAO.Interfaces;

import models.Authors;
import models.Books;

import java.sql.SQLException;
import java.util.List;

public abstract class StdImpl_AuthorDAO implements I_AuthorsDAO{
    @Override
    public List<Authors> GetAuthorOfBook(Books book) {
        return GetAuthorOfBook(book.getBookId());
    }

    @Override
    public void AddAuthorToBook(Authors aut, Books book) {
        this.AddAuthorToBook(aut.getAuthorId(), book.getBookId());
    }
    @Override
    public void AddAuthorToBook(Authors aut, Integer book_id) {
        this.AddAuthorToBook(aut.getAuthorId(), book_id);
    }
    @Override
    public void AddAuthorToBook(Integer author_id, Books book) {
        this.AddAuthorToBook(author_id, book.getBookId());
    }
}
