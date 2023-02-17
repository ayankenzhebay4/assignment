import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection implements database{
    @Override
    public Connection getConnection(){
        String url="jdbc:postgresql://localhost:5432/db";
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url,"postgres","kenzhebay");
            return conn;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}
