package com.abc.services;

import com.abc.entities.User;

public interface UserService {
    boolean registerUser(User user);
    User getUserByUserName(String username);
}
