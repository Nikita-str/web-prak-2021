package utils;

import models.Authors;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class SessionHelper {
    public static void InSessionAct(Consumer<Session> action){
        Session ses = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        //try {action.accept(ses);} catch(Exception e) {}
        action.accept(ses);
        ses.getTransaction().commit();
        ses.close();
    }

    public static<R> R InSessionActWithR(Function<Session, R> action){
        Session ses = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        R result = action.apply(ses);
        ses.getTransaction().commit();
        ses.close();
        return result;
    }

    public static<LT> List<LT> InSessionActWithL(Function<Session, List> action, Class<LT> list_type){
        List<LT> ret = new ArrayList<LT>();
        //try {
            SessionHelper.InSessionAct(ses -> {
                List<Object[]> pre_ret = action.apply(ses);
                pre_ret.forEach(o -> ret.add(ses.load(list_type, (Integer) o[0])));
            });
        //} catch(Exception e) {}
        return ret;
    }
}
