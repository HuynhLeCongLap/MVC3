package com.abc.dao;

import com.abc.entities.Location;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class LocationDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Location> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Location", Location.class)
                .list();
    }
}
