package DAO.StdImpl;
import DAO.Interfaces.I_AuthorsDAO;
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

import javax.persistence.StoredProcedureQuery;


public class StdDAO_Authors implements I_AuthorsDAO {

    @Override
    public Authors AddAuthor(String f_name, String s_name, String patr) throws SQLException {
        Authors aut = null;
        Session ses = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        ses.beginTransaction();

        aut = GetAuthorById(SQL_FuncCall.GetAuthorId(ses, f_name, s_name, patr));

        ses.getTransaction().commit();
        ses.close();
        return aut;
    }

    @Override
    public Authors GetAuthor(String f_name, String s_name, String patr) throws SQLException {
        return GetAuthorById(GetAuthorId(f_name, s_name, patr));
    }

    @Override
    public Integer GetAuthorId(String f_name, String s_name, String patr) throws SQLException {
        Session ses = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        ses.beginTransaction();

        Integer aut_id = SQL_FuncCall.GetAuthorId(ses, f_name, s_name, patr);

        ses.getTransaction().commit();
        ses.close();
        return aut_id;
    }

    @Override
    public Authors GetAuthorById(Integer id) throws SQLException {
        System.out.println(id);
        Authors aut = null;
        Session ses = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        aut = ses.load(Authors.class, id);
        ses.getTransaction().commit();
        ses.close();
        return aut;
    }

    public List<Authors> GetAuthorOfBook(Books book) throws SQLException {
        Long id_val = book.getBookId();
        return GetAuthorOfBook(id_val.intValue());
    }

    public List<Authors> GetAuthorOfBook(Integer book_id) throws SQLException {
        Session ses = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        ses.beginTransaction();

        //List ret = new ArrayList<Authors>();
        //ret = (List<Authors>)SQL_FuncCall.GetAuthorsOfBook(ses, book_id.intValue());
        //List<Object[]> pre_ret = SQL_FuncCall.GetAuthorsOfBook(ses, book_id.intValue());
        //StoredProcedureQuery qq = ses.createNamedStoredProcedureQuery("get_authors_of_book");
        /*Query qq = ses.createSQLQuery("SELECT * FROM get_authors_of_book(:_book_id)");
        qq.setParameter("_book_id", book_id);
        qq.getResultList();
        List ret = qq.list();*/

        List ret = new ArrayList<Authors>();
        List<Object[]> pre_ret = (List<Object[]>) SQL_FuncCall.GetAuthorsOfBook(ses, book_id);
        pre_ret.forEach(o -> ret.add(ses.load(Authors.class, (Integer)o[0])));

        ses.getTransaction().commit();
        ses.close();
        return ret;
    }


}
