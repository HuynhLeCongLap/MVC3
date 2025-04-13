package com.abc.dao;

import com.abc.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    // Lấy người dùng theo tên đăng nhập
    public User getUserByUserName(String userName) {
        try (Session session = sessionFactory.getCurrentSession()) {
            return session.createQuery("FROM User WHERE username = :userName", User.class)
                          .setParameter("userName", userName)
                          .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Đăng ký người dùng
    public boolean registerUser(User user) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.saveOrUpdate(user); // Lưu hoặc cập nhật đối tượng User
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy người dùng theo email
    public User getUserByEmail(String email) {
        try (Session session = sessionFactory.getCurrentSession()) {
            return session.createQuery("FROM User WHERE email = :email", User.class)
                          .setParameter("email", email)
                          .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
