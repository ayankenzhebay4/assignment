import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Application extends Input implements App {
    private accountsDAO accountsDAO = new accountsDAO();
    private DBConnection psql = new DBConnection();

    @Override
    public void start() throws SQLException {
        while (true) {
            System.out.println("Hello, Welcome to the Bank");
            System.out.println(("\t1) Create Account"));
            System.out.println(("\t2) Login, Logout"));
            System.out.println(("\t3) Exit"));
            int o = in.nextInt();
            switch (o) {
                case 1:
                    createAccount();
                case 2:
                    login_logout();
                case 3:
                    exit();
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }

    public void createAccount() throws SQLException {
        accountsDAO.create();
        start();
    }

    public void login_logout() throws SQLException {
        Connection connection = psql.getConnection();
        int t = 0;
        Statement statement = null;
        ResultSet s = null;
        System.out.println("enter your email");
        String email = in.next();
        System.out.println("enter your password");
        String password = in.next();
        try {
            String sql = "select * from accounts where email ='" + email + "'";
            statement = connection.createStatement();
            s = statement.executeQuery(sql);
            while (s.next()) {
                if (s.getString(5).equals(password)) {
                    System.out.println("Здраствуйте " + s.getString(2) + ", Вы успешно зашли на аккаунт.");
                    t = 1;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (t == 1) {
            methods(email);
                }
            }


    public void manage(String email) throws SQLException{
        accountsDAO.update(email);
        methods(email);
    }
    public void deposit(String email)throws SQLException{
        accountsDAO.deposit(email);
         methods(email);
    }
    public void withdraw(String email) throws SQLException{
        accountsDAO.withdraw(email);
        methods(email);
    }
    public void view(String email)throws SQLException{
        System.out.println("Your balance is:" + accountsDAO.view(email));
        methods(email);
    }
    public void transfer(String email) throws SQLException{
        accountsDAO.transfer(email);
        methods(email);
    }
    public void delete(String email)throws SQLException{
        accountsDAO.delete(email);
    }
    public void methods(String email)throws SQLException{
        while (true) {
            System.out.println("You have following options:");
            System.out.println(("\t1) Manage Profile and Password"));
            System.out.println(("\t2) View the balance of the account"));
            System.out.println(("\t3) Deposit the amount"));
            System.out.println(("\t4) Withdraw amount"));
            System.out.println("\t5) Transfer the amount");
            System.out.println("\t6) Delete the account");
            System.out.println("\t7) Logout");
            int o = in.nextInt();
            switch (o) {
                case 1:
                    manage(email);
                    break;
                case 2:
                    view(email);
                    break;
                case 3:
                    deposit(email);
                    break;
                case 4:
                    withdraw(email);
                    break;
                case 5:
                    transfer(email);
                case 6:
                    delete(email);
                case 7:
                    exit();
            }
        }
    }

}