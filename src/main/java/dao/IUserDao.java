package dao;

import model.User;

import java.sql.SQLException;
import java.util.List;


public interface IUserDao<T> {

    List<T> getAllUsers() throws Exception;
    void createUser(T t) throws Exception;
    void updateUser(T t) throws Exception;
    void deleteUser(T t) throws Exception;
    T findUserById(long id) throws Exception;
    T findUserByLogin(String login) throws Exception;
}
