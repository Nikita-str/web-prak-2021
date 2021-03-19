import DAO.StdImpl.StdDAO_Factory;
import models.*;
import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws SQLException {
            System.out.println(StdDAO_Factory.getInstance().getAuthorDao().GetAuthorId("Станислав", "Лем", "герман"));
        try {

        } catch (Exception e){
            System.out.println("oh no... exception");
        }
    }
}
