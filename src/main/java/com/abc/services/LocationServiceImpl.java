package com.abc.services;

import com.abc.dao.LocationDAO;
import com.abc.entities.Location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDAO locationDAO;

    @Override
    public List<Location> getAllLocations() {
        return locationDAO.findAll();
    }
}
