package utils;

import org.postgresql.core.Tuple;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class DatabaseHelper {

    private static String getPropertyNamed(String xml, String prop_name){
        return xml.split("<property name=\""+prop_name+"\">")[1].split("</property>")[0];
    }

    private static boolean ExecuteSql(String sql_path){
        try {
            String xml = new String ( Files.readAllBytes( Paths.get("src/main/resources/hibernate.cfg.xml") ) );
            String url = getPropertyNamed(xml, "connection.url");
            String user = getPropertyNamed(xml, "connection.username");
            String password = getPropertyNamed(xml, "connection.password");
            DriverManager.getConnection(url, user, password)
                    .createStatement()
                    .executeUpdate(new String ( Files.readAllBytes(Paths.get(sql_path))));
        } catch(Exception e){ return false; }
        return true;
    }

    public static boolean DataBaseClear(){ return ExecuteSql("sql/tables_clear.sql"); }
    public static boolean DataBaseStdInit(){ return ExecuteSql("sql/insert.sql"); }
}
