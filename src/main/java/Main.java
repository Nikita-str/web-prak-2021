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
        //System.out.println(StdDAO_Factory.getInstance().getAuthorDao().GetAuthorId("Станислав", "Лем", "герман"));
        System.out.println(StdDAO_Factory.getInstance().getAuthorDao().GetAuthor("Братья", "Стругацкие", null));
        System.out.println(StdDAO_Factory.getInstance().getAuthorDao().GetAuthor("Братья", "Стругацкие", null));

        try {

        } catch (Exception e){
            System.out.println("oh no... exception");
        }

        List<Authors> l_as = StdDAO_Factory.getInstance().getAuthorDao().GetAuthorOfBook(1);
        l_as.forEach(o -> System.out.println(o));

        return;
    }
}
