package DAO.StdImpl;
import DAO.Interfaces.I_AuthorsDAO;
import DAO.Interfaces.StdImpl_AuthorDAO;
import com.sun.xml.internal.ws.handler.HandlerException;
import models.Authors;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import models.Books;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;
import utils.SQL_FuncCall;
import utils.SessionHelper;

import javax.persistence.StoredProcedureQuery;

public class StdDAO_Authors extends StdImpl_AuthorDAO implements I_AuthorsDAO {

    @Override
    public Authors AddAuthor(String f_name, String s_name, String patr) throws SQLException {
        return SessionHelper.InSessionActWithR(ses -> ses.load(Authors.class, SQL_FuncCall.AddAuthor(ses, f_name, s_name, patr)));
    }

    @Override
    public Authors GetAuthor(String f_name, String s_name, String patr) throws SQLException {
        return GetAuthorById(GetAuthorId(f_name, s_name, patr));
    }

    @Override
    public Integer GetAuthorId(String f_name, String s_name, String patr) throws SQLException {
        return SessionHelper.InSessionActWithR(ses -> SQL_FuncCall.GetAuthorId(ses, f_name, s_name, patr));
    }

    @Override
    public Authors GetAuthorById(Integer id) throws SQLException {
        return SessionHelper.InSessionActWithR(ses -> ses.load(Authors.class, id));
    }

    @Override
    public List<Authors> GetAuthorOfBook(Integer book_id) throws SQLException {
        return SessionHelper.InSessionActWithL(ses -> SQL_FuncCall.GetAuthorsOfBook(ses, book_id), Authors.class);
    }

    @Override
    public void AddAuthorToBook(Integer author_id, Integer book_id) throws SQLException {
        SessionHelper.InSessionAct(ses -> SQL_FuncCall.AddAuthorToBook(ses, author_id, book_id));
    }

    @Override
    public List<Authors> FindAuthor(String f_name, String s_name, String patr, Boolean complete_match) throws SQLException {
        return SessionHelper.InSessionActWithL(ses -> SQL_FuncCall.FindAuthor(ses, f_name, s_name, patr, complete_match), Authors.class);
    }

    @Override
    public List<Authors> FindAuthor(String s0, String s1, Boolean complete_match) throws SQLException {
        return SessionHelper.InSessionActWithL(ses -> SQL_FuncCall.FindAuthor(ses, s0, s1, complete_match), Authors.class);
    }

    @Override
    public List<Authors> FindAuthor(String surname, Boolean complete_match) throws SQLException {
        return SessionHelper.InSessionActWithL(ses -> SQL_FuncCall.FindAuthor(ses, surname, complete_match), Authors.class);
    }
}
