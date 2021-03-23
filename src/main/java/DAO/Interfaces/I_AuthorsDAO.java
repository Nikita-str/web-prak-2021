package DAO.Interfaces;

import models.Authors;
import models.Books;

import java.sql.SQLException;
import java.util.List;

public interface I_AuthorsDAO {
    public Authors AddAuthor(String f_name, String s_name, String patr);
    public Authors GetAuthor(String f_name, String s_name, String patr);
    public Integer GetAuthorId(String f_name, String s_name, String patr);
    public Authors GetAuthorById(Integer id);

    public List<Authors> GetAllAuthor();

    public List<Authors> GetAuthorOfBook(Books book);
    public List<Authors> GetAuthorOfBook(Integer book_id);

    public void AddAuthorToBook(Authors aut, Books book);
    public void AddAuthorToBook(Authors aut, Integer book_id);
    public void AddAuthorToBook(Integer author_id, Books book);
    public void AddAuthorToBook(Integer author_id, Integer book_id);

    public List<Authors> FindAuthor(String f_name, String s_name, String patr, Boolean complete_match);
    public List<Authors> FindAuthor(String s0, String s1, Boolean complete_match);
    public List<Authors> FindAuthor(String surname, Boolean complete_match);
}