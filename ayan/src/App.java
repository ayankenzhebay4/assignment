import java.sql.SQLException;

public interface App {
    void start() throws SQLException;
    default void exit(){
        System.out.println("app closing");
        System.exit(0);
    }
}
