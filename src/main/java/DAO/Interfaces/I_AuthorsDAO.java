package DAO.Interfaces;

import models.Authors;
import models.Books;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface I_AuthorsDAO {
    public Authors AddAuthor(String f_name, String s_name, String patr) throws SQLException;
    public Authors GetAuthor(String f_name, String s_name, String patr) throws SQLException;
    public Integer GetAuthorId(String f_name, String s_name, String patr) throws SQLException;
    public Authors GetAuthorById(Integer id) throws SQLException;
    public List<Authors> GetAuthorOfBook(Books book) throws SQLException;
    public List<Authors> GetAuthorOfBook(Integer book_id) throws SQLException;
}
