package com.abc.dao;

import com.abc.entities.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public User getUserByUserName(String userName) {
        String hql = "FROM User WHERE username = :username";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, User.class)
                .setParameter("username", userName)
                .uniqueResult();
    }

    public boolean registerUser(User user) {
        try {
            sessionFactory.getCurrentSession().save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> findUsersByFollowerAndFollowing(int minFollowers, int minFollowing) {
        String sql = """
            SELECT u.* FROM users u
            WHERE 
                (SELECT COUNT(*) FROM follows f1 WHERE f1.followed_user_id = u.id) >= :minFollowers
                AND
                (SELECT COUNT(*) FROM follows f2 WHERE f2.following_user_id = u.id) >= :minFollowing
        """;

        return sessionFactory.getCurrentSession()
                .createNativeQuery(sql, User.class)
                .setParameter("minFollowers", minFollowers)
                .setParameter("minFollowing", minFollowing)
                .getResultList();
    }

    public boolean isEmailExists(String email) {
        String hql = "SELECT COUNT(u) FROM User u WHERE u.email = :email";
        Long count = sessionFactory.getCurrentSession()
                .createQuery(hql, Long.class)
                .setParameter("email", email)
                .uniqueResult();
        return count != null && count > 0;
    }

    public User findByEmail(String email) {
        String hql = "FROM User WHERE email = :email";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, User.class)
                .setParameter("email", email)
                .uniqueResult();
    }

    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }
}
