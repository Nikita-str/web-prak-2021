package DAO.Interfaces;

import models.Authors;
import java.sql.SQLException;

public interface I_AuthorsDAO {
    public Authors AddAuthor(String f_name, String s_name, String patr) throws SQLException;
    public Authors GetAuthor(String f_name, String s_name, String patr) throws SQLException;
    public Long GetAuthorId(String f_name, String s_name, String patr) throws SQLException;
    public Authors GetAuthorById(long id) throws SQLException;
}
