package service;

import dao.UserJdbcDao;
import model.User;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class UserServiceJdbc {
    //синглтон
    private UserServiceJdbc(){
    }
    private static class UserHolder{
        private final static UserServiceJdbc instance = new UserServiceJdbc();
    }
    public static UserServiceJdbc getInstance(){
        return UserHolder.instance;
    }
    // \синглтон

    public List<User> getAllUser() throws SQLException{
        return getUserJdbcDAO().getAllUsers();
    }
    public void AddUser(User user) throws SQLException{
        getUserJdbcDAO().createUser(user);
    }

    public void updateUser(User user) throws SQLException {
        getUserJdbcDAO().updateUser(user);
    }

    public void removeUserById(long id) throws SQLException{
        User user = findUserById(id);
        getUserJdbcDAO().deleteUser(user);
    }


    public User findUserById(Long id) throws SQLException{
        return getUserJdbcDAO().findUserById(id);
    }

    public User findUserByLogin(String login) throws SQLException {
        return getUserJdbcDAO().findUserByLogin(login);
    }



    //соединение с БД
    private static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("db_example?").          //db name
                    append("user=root&").          //login
                    append("password=jav@MySQ1");       //password

            System.out.println("URL: " + url + "\n");

            return DriverManager.getConnection(url.toString());
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }
    private static UserJdbcDao getUserJdbcDAO() {
        return new UserJdbcDao(getMysqlConnection());
    }
}
