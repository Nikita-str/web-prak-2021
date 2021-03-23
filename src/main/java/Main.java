import DAO.StdImpl.StdDAO_Factory;
import models.*;
import org.hibernate.Session;
import utils.DatabaseHelper;
import utils.HibernateSessionFactoryUtil;
import utils.SQL_FuncCall;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String args[]) throws SQLException {
        //DatabaseHelper.DataBaseClear();
        //DatabaseHelper.DataBaseStdInit();

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

        StdDAO_Factory inst = StdDAO_Factory.getInstance();
        System.out.println("\n");
        System.out.println("BOOKS:");
        List<Books> l_bs = StdDAO_Factory.getInstance().getBookDao().BookFind_Isbn("978-51");
        l_bs.forEach(o -> System.out.println(o));
        System.out.println("next -->");
        l_bs = StdDAO_Factory.getInstance().getBookDao().BookFind_Title("суМма");
        l_bs.forEach(o -> System.out.println(o));
        System.out.println("next -->");
        l_bs = StdDAO_Factory.getInstance().getBookDao().BookFind_Year(1964);
        l_bs.forEach(o -> System.out.println(o));
        System.out.println("next -->");
        l_bs = StdDAO_Factory.getInstance().getBookDao().BookFind_Publisher(4);
        l_bs.forEach(o -> System.out.println(o));
        System.out.println("next -->");
        l_bs = StdDAO_Factory.getInstance().getBookDao().BookFind("ИСП", null, 1964, null, false);
        l_bs.forEach(System.out::println);
        System.out.println("next -->");
        l_bs = inst.getBookDao().BookFind(null, inst.getPublisherDao().GetPublisherId("АСТ"), 1964, null, false);
        l_bs.forEach(System.out::println);

        System.out.println("\n");
        System.out.println("BOOKS EXAMPLES:");
        List<BookExamples> l_bes = StdDAO_Factory.getInstance().getBookDao().GetBookEx(2);
        l_bes.forEach(o -> System.out.println(o));

        System.out.println("\n");
        System.out.println("READERS:");
        List<Readers> list_rs = inst.getReaderDao().FindReader_Surname("второй");
        list_rs.forEach(System.out::println);
        System.out.println("next -->");
        list_rs = inst.getReaderDao().FindReader_PhoneNumber("8(16)326");
        list_rs.forEach(System.out::println);

        return;
    }
}
