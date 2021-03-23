package DAO.StdImpl;
import DAO.Interfaces.I_AuthorsDAO;
import DAO.Interfaces.StdImpl_AuthorDAO;
import models.Authors;

import java.util.List;

import models.Readers;
import utils.SQL_FuncCall;
import utils.SessionHelper;

import javax.persistence.TypedQuery;


public class StdDAO_Authors extends StdImpl_AuthorDAO implements I_AuthorsDAO {

    @Override
    public Authors AddAuthor(String f_name, String s_name, String patr) {
        if(f_name == null || s_name == null)return null;
        return SessionHelper.InSessionActWithR(ses -> ses.load(Authors.class, SQL_FuncCall.AddAuthor(ses, f_name, s_name, patr)));
    }

    @Override
    public Authors GetAuthor(String f_name, String s_name, String patr) {
        if(f_name == null || s_name == null)return null;
        return GetAuthorById(GetAuthorId(f_name, s_name, patr));
    }

    @Override
    public Integer GetAuthorId(String f_name, String s_name, String patr) {
        if(f_name == null || s_name == null)return null;
        return SessionHelper.InSessionActWithR(ses -> SQL_FuncCall.GetAuthorId(ses, f_name, s_name, patr));
    }

    @Override
    public Authors GetAuthorById(Integer id) {
        return SessionHelper.InSessionActWithR(ses -> ses.load(Authors.class, id));
    }

    @Override
    public List<Authors> GetAllAuthor() {
        return  SessionHelper.InSessionActWithR(ses -> {
                TypedQuery<Authors> q = ses.createQuery("FROM Authors", Authors.class);
                return q.getResultList();
        });
    }

    @Override
    public List<Authors> GetAuthorOfBook(Integer book_id) {
        return SessionHelper.InSessionActWithL(ses -> SQL_FuncCall.GetAuthorsOfBook(ses, book_id), Authors.class);
    }

    @Override
    public void AddAuthorToBook(Integer author_id, Integer book_id) {
        SessionHelper.InSessionAct(ses -> SQL_FuncCall.AddAuthorToBook(ses, author_id, book_id));
    }

    @Override
    public List<Authors> FindAuthor(String f_name, String s_name, String patr, Boolean complete_match) {
        return SessionHelper.InSessionActWithL(ses -> SQL_FuncCall.FindAuthor(ses, f_name, s_name, patr, complete_match), Authors.class);
    }

    @Override
    public List<Authors> FindAuthor(String s0, String s1, Boolean complete_match) {
        return SessionHelper.InSessionActWithL(ses -> SQL_FuncCall.FindAuthor(ses, s0, s1, complete_match), Authors.class);
    }

    @Override
    public List<Authors> FindAuthor(String surname, Boolean complete_match) {
        return SessionHelper.InSessionActWithL(ses -> SQL_FuncCall.FindAuthor(ses, surname, complete_match), Authors.class);
    }
}
