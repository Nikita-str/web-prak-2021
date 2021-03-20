package DAO.Interfaces;

import models.Authors;
import models.Books;

import java.sql.SQLException;
import java.util.List;

public interface I_AuthorsDAO {
    public Authors AddAuthor(String f_name, String s_name, String patr) throws SQLException;
    public Authors GetAuthor(String f_name, String s_name, String patr) throws SQLException;
    public Integer GetAuthorId(String f_name, String s_name, String patr) throws SQLException;
    public Authors GetAuthorById(Integer id) throws SQLException;
    public List<Authors> GetAuthorOfBook(Books book) throws SQLException;
    public List<Authors> GetAuthorOfBook(Integer book_id) throws SQLException;

    public void AddAuthorToBook(Authors aut, Books book) throws SQLException;
    public void AddAuthorToBook(Authors aut, Integer book_id) throws SQLException;
    public void AddAuthorToBook(Integer author_id, Books book) throws SQLException;
    public void AddAuthorToBook(Integer author_id, Integer book_id) throws SQLException;

    public List<Authors> FindAuthor(String f_name, String s_name, String patr, Boolean complete_match) throws SQLException;
    public List<Authors> FindAuthor(String s0, String s1, Boolean complete_match) throws SQLException;
    public List<Authors> FindAuthor(String surname, Boolean complete_match) throws SQLException;
}