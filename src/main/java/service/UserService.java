package service;


import dao.UserHibernateDao;
import model.User;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private static UserService userService;

    private SessionFactory sessionFactory;

    private UserService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static UserService getInstance(){
        if (userService == null){
            userService = new UserService(DBHelper.getSessionFactory());
        }
        return userService;
    }

    private  UserHibernateDao getUserHibernateDao() {
        return new UserHibernateDao(sessionFactory.openSession());
    }
    public List<User> getAllUser() {
        return getUserHibernateDao().getAllUsers();
    }

    public void AddUser(User user)  {
        getUserHibernateDao().createUser(user);
    }

    public void updateUser(User user)  {
        getUserHibernateDao().updateUser(user);
    }

    public void removeUser(User user)  {
        getUserHibernateDao().deleteUser(user);
    }
    public User findUserById(long id)  {
        return getUserHibernateDao().findUserById(id);
    }

    public User findUserByLogin(String login)  {
        return getUserHibernateDao().findUserByLogin(login);
    }


}
