package DAO.StdImpl;
import DAO.Interfaces.I_AuthorsDAO;
import models.Authors;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.type.StandardBasicTypes;
import utils.HibernateSessionFactoryUtil;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TemporalType;

public class StdDAO_Authors implements I_AuthorsDAO {

    @Override
    public Authors AddAuthor(String f_name, String s_name, String patr) throws SQLException {
        Authors aut = null;
        Session ses = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        ses.beginTransaction();

        ses.getTransaction().commit();
        ses.close();
        return aut;
    }

    @Override
    public Authors GetAuthor(String f_name, String s_name, String patr) throws SQLException {
        return GetAuthorById(GetAuthorId(f_name, s_name, patr));
    }

    //public Tuple<Long, Long> heh(){ return (1, 2);}
    public List heh() {
        Session ses = null;
        StoredProcedureQuery query = ses
                .createStoredProcedureQuery("get_author_id");
        return query.getResultList();
    }

    public Integer kek() {
        Session ses = null;
        StoredProcedureQuery query = ses
                .createStoredProcedureQuery("get_author_id");
        return (Integer) query.getOutputParameterValue("ret_author_id");
    }

    public Integer kekheh() {
        Session ses = null;
        StoredProcedureQuery query = ses
                .createStoredProcedureQuery("get_author_id");
        return (Integer) query.getSingleResult();
    }

    @Override
    public Long GetAuthorId(String f_name, String s_name, String patr) throws SQLException {
        Long aut_id = null;
        Session ses = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        /*
        StoredProcedureQuery query = ses
                .createStoredProcedureQuery("get_author_id")
                .registerStoredProcedureParameter("f_name", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("s_name", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("patr", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ret_author_id", Integer.class, ParameterMode.OUT)
                .setParameter("f_name", f_name)
                .setParameter("s_name", s_name)
                .setParameter("patr", patr);
        */
        ProcedureCall query = ses.createStoredProcedureCall("get_author_id");
        query.registerParameter("f_name", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("s_name", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("patr", String.class, ParameterMode.IN).enablePassingNulls(true);
        query.registerParameter("ret_author_id", Integer.class, ParameterMode.OUT);
        query.setParameter("f_name", f_name)
                .setParameter("s_name", s_name)
                .setParameter("patr", patr);

        query.execute();
        aut_id = Long.valueOf((Integer) query.getOutputParameterValue("ret_author_id"));

        ses.getTransaction().commit();
        ses.close();
        return aut_id;
    }

    @Override
    public Authors GetAuthorById(long id) throws SQLException {
        Authors aut = null;
        Session ses = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        aut = ses.load(Authors.class, id);
        ses.getTransaction().commit();
        ses.close();
        return aut;
    }
}
