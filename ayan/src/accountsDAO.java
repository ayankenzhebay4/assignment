import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class accountsDAO extends Input implements CRUD{
    //static Scanner in = new Scanner(System.in);
    private DBConnection psql = new DBConnection();
    @Override
    public List<Account> getAll() throws SQLException{
     List<Account> accounts = new ArrayList<>();
        return accounts;
    }

    @Override
    public void create() throws SQLException{
        Connection connection = psql.getConnection();
        System.out.println("enter name");
        String name = in.next();
        System.out.println("enter last name");
        String lname=in.next();
        System.out.println("enter email");
        String email = in.next();
        System.out.println("enter password");
        String password = in.next();
        int balance = 0;
        PreparedStatement preparedStatement = connection.prepareStatement("insert into accounts(name, lastname,email,password,balance) values (?,?,?,?,?)");
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,lname);
        preparedStatement.setString(3,email);
        preparedStatement.setString(4,password);
        preparedStatement.setInt(5,balance);

        preparedStatement.executeUpdate();
        preparedStatement.close();

        connection.close();

        System.out.println("user added");
    }




    @Override
    public void delete(String email) throws SQLException{
        Connection connection = psql.getConnection();
        Statement statement = connection.createStatement();
        ResultSet s =getCertain(email);
        if(!s.next()){
            System.out.println("user does not founded");
            return;
        }
        int userId = s.getInt("id");
        System.out.println("Enter 1 for confirm action");
        String one = in.next();
        if(!one.equals("1")){
            System.out.println("Action cancelled");
            return;
        }
        statement.executeUpdate("delete from accounts where id="+userId);
        s.close();
        statement.close();
        connection.close();
        System.out.println("User was deleted");
    }




     public void update(String email) throws SQLException{
        Connection connection = psql.getConnection();
         Statement statement = connection.createStatement();
         ResultSet s = getCertain(email);
         if(!s.next()){
             System.out.println("user does not founded");
             return;
         }
         int userId = s.getInt("id");
         System.out.println("enter new name");
         String newname = in.next();
         System.out.println("enter new last name");
         String newlastname = in.next();
         System.out.println("enter new password");
         String newpassword = in.next();
         Statement update = connection.createStatement();
         update.executeUpdate("update accounts set name = '"+newname+"'"+" where id = "+ userId);
         update.executeUpdate("update accounts set lastname = '"+newlastname+"'"+" where id = "+ userId);
         update.executeUpdate("update accounts set password = '"+newpassword+"'"+" where id = "+ userId);
         update.close();
         statement.close();
         s.close();
         connection.close();
         System.out.println("user updated");
     }




      public ResultSet getCertain(String email) throws SQLException{
        Connection connection = psql.getConnection();
        String searchUser = "select * from accounts where email = '"+email+"'";
        Statement statement = connection.createStatement();
        ResultSet s = statement.executeQuery(searchUser);
        return s;
    }




    public int view(String email) throws SQLException{
        Connection connection = psql.getConnection();
        Statement statement = null;
        ResultSet s = null;
        try{
            String sql = "select * from accounts where email ='" + email + "'";
            statement = connection.createStatement();
            s=statement.executeQuery(sql);
            while(s.next()){
                return s.getInt(6);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }




    public void deposit(String email) throws SQLException {
        Connection connection = psql.getConnection();
        Statement statement = connection.createStatement();
        ResultSet s = getCertain(email);
        if (!s.next()) {
            System.out.println("user does not founded");
            return;
        }
        System.out.println("Deposit the amount you want.");
        int dep = in.nextInt();
        int all = view(email);
        int sum = dep + all;
        try {
            String sql = "update accounts set balance = '" + sum + "' where email='" + email + "'";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Вы пополнили депозит на: " + dep);

        } catch (Exception e) {
            System.out.println(e);
        }
    }




    public void withdraw(String email)throws SQLException{
        Connection connection = psql.getConnection();
        Statement statement = connection.createStatement();
        ResultSet s = getCertain(email);
        if (!s.next()) {
            System.out.println("user does not founded");
            return;
        }
        System.out.println("Ваш баланс - " + view(email));
        System.out.println("Введите сумму которую хотите вывести");
        Statement update = connection.createStatement();
        int wd = in.nextInt();
        int all = view(email);
        int sum = all-wd;
        try {
            String sql = "update accounts set balance = '" + sum + "' where email='" + email + "'";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Вы вывели: " + wd);

        } catch (Exception e) {
            System.out.println(e);
        }

    }




    public void transfer(String email)throws SQLException{
        Connection connection = psql.getConnection();
        Statement statement = connection.createStatement();
        ResultSet s = getCertain(email);
        if (!s.next()) {
            System.out.println("user does not founded");
            return;
        }
        System.out.println("Введите email того человека которому хотите перевести деньги");
        String em = in.next();
        System.out.println("Ваш баланс: " + view(email));
        System.out.println("Введите сумму которую хотите перевести: ");
        int sa= in.nextInt();
        int sum = view(em)+sa;
        try {
            String sql = "update accounts set balance = '" + sum + "' where email='" + em + "'";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Вы перевели: " + sa);

        } catch (Exception e) {
            System.out.println(e);
        }
        int remainder = view(email)-sa;
        try {
            String sql = "update accounts set balance = '" + remainder + "' where email='" + email + "'";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Теперь ваш баланс: " + view(email));


    }




}
