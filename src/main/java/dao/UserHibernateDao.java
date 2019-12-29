package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserHibernateDao implements IUserDao<User>{

    private Session session;

    public UserHibernateDao(Session session) {
        this.session = session;
    }
    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Transaction transaction = session.beginTransaction();
        List<User> allUsers = (List<User>) session.createQuery("from User").list();
        transaction.commit();
        session.close();
        return allUsers;
    }

    @Override
    public void createUser(User user) {
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
    }

    @Override
    public void updateUser(User user) {
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();

    }

    @Override
    public void deleteUser(User user)  {
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
    }

    @Override
    public User findUserById(long id)  {
        Transaction transaction = session.beginTransaction();
//        User user= session.get(User.class, id);
        User user = (User) session.createQuery("FROM User WHERE id = '" +id+ "'").uniqueResult();
        transaction.commit();
        return user;
    }


    public User findUserByLogin(String login){
        Transaction transaction = session.beginTransaction();
//        User user= session.get(User.class, login).;
        User user = (User) session.createQuery("FROM User WHERE login = '" +login+ "' ").uniqueResult();
        transaction.commit();
        return user;
    }



}
