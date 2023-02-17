import java.sql.SQLException;
import java.util.List;

public interface CRUD {
    List<Account> getAll() throws SQLException;
    void create() throws SQLException;
    void delete(String email) throws SQLException;
    void update(String email) throws SQLException;
}
