package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserJdbcDao implements IUserDao<User>{
    private Connection connection;

    public UserJdbcDao(Connection connection) {
        this.connection = connection;
    }

    public List<User> getAllUsers() throws SQLException {
       List<User> userList = new ArrayList<>();
       Statement stmt = connection.createStatement();
       stmt.execute("select * from users2");
       ResultSet rs = stmt.getResultSet();
       while(rs.next()){
           long id = rs.getLong(1);
           User user = new User(id, findUserById(id).getLogin(), findUserById(id).getAge(), findUserById(id).getEmail(), findUserById(id).getPassword());
           userList.add(user);
       }
       rs.close();
       stmt.close();
       return userList;
    }

    public void createUser(User user) throws SQLException {
        String login = user.getLogin();
        int age = user.getAge();
        String email = user.getEmail();
        String password = user.getPassword();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("insert into users2(login, age, email,password) VALUES ('" +login + "', '" +age + "', '" +email + "','" +password + "')");
        stmt.close();
    }

    public void deleteUser(User user) throws SQLException {
        long id = user.getId();
        PreparedStatement stmt = connection.prepareStatement("delete from users2 where id=?");
        stmt.setLong(1,id);
        stmt.executeUpdate();
        stmt.close();
    }



    public void updateUser(User user) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("update users2 SET login=? , age=? , email=?, password=? where id= ?");
        stmt.setString(1, user.getLogin());
        stmt.setInt(2, user.getAge());
        stmt.setString(3,user.getEmail());
        stmt.setString(4, user.getPassword());
        stmt.setLong(5, user.getId());
        stmt.executeUpdate();
        stmt.close();
    }

    public User findUserById(long id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("select * from users2 where id=?");
        stmt.setLong(1,id);
        ResultSet rs = stmt.executeQuery();
        String login = null;
        int age = 0;
        String email = null;
        String password = null;
        while (rs.next()){
            login = rs.getString(2);
            age = rs.getInt(3);
            email = rs.getString(4);
            password = rs.getString(5);
        }
        User userById = new User(id, login, age, email, password);
        rs.close();
        stmt.close();
        return userById;
    }

    public long getUserIdByLogin(String login) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("select * from users2 where login=?");
        stmt.setString(1,login);
        ResultSet rs = stmt.executeQuery();
        long id = 0;
        if (rs.first()) {
            id = rs.getLong(1);
        }
        rs.close();
        stmt.close();
        return id;
    }

    public User findUserByLogin(String login) throws SQLException {
        return findUserById(getUserIdByLogin(login));
    }

    public void createTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("create table if not exists users2 (id bigint auto_increment, login varchar(256), age integer, email varchar(256), password varchar(256), primary key (id))");
        stmt.close();
    }

    public void dropTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS users2");
        stmt.close();
    }
}
