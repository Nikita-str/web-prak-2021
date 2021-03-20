import DAO.StdImpl.StdDAO_Factory;
import models.*;
import org.hibernate.Session;
import utils.HibernateSessionFactoryUtil;
import utils.SQL_FuncCall;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String args[]) throws SQLException {
        System.out.println("AUTHORS:");
        System.out.println(StdDAO_Factory.getInstance().getAuthorDao().GetAuthor("Братья", "Стругацкие", null));
        System.out.println(StdDAO_Factory.getInstance().getAuthorDao().GetAuthor("Братья", "Стругацкие", null));

        try {

        } catch (Exception e){
            System.out.println("oh no... exception");
        }

        System.out.println();
        System.out.println("  | GetAuthorOfBook |  ");
        List<Authors> l_as = StdDAO_Factory.getInstance().getAuthorDao().GetAuthorOfBook(1);
        l_as.forEach(o -> System.out.println(o));

        System.out.println();
        System.out.println("  | GetAuthorOfBook |  ");
        l_as =  StdDAO_Factory.getInstance().getAuthorDao().FindAuthor("Бр", "сТр", false);
        l_as.forEach(o -> System.out.println(o));

        System.out.println("\n");
        System.out.println("PUBLISHERS:");
        List<Publishers> l_pubs =  StdDAO_Factory.getInstance().getPublisherDao().FindPublisher("fi", false);
        l_pubs.forEach(o -> System.out.println(o));

        l_pubs =  StdDAO_Factory.getInstance().getPublisherDao().FindPublisher("abra-kadabra", false);
        l_pubs.forEach(o -> System.out.println(o));

        System.out.println("\n");
        System.out.println("READERS:");
        List<BookExHistory> l_bks = StdDAO_Factory.getInstance().getReaderDao().GetReaderCurBook(1);
        l_bks.forEach(o -> System.out.println(o));

        l_bks = StdDAO_Factory.getInstance().getReaderDao().GetReaderCurBook(777);
        l_bks.forEach(o -> System.out.println(o));

        return;
    }
}
