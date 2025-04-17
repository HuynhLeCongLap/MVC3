package com.abc.services;

import com.abc.entities.User;
import java.util.List;

public interface UserService {
    User getUserByUserName(String userName);
    boolean registerUser(User user);
   
    List<User> findUsersByFollowerAndFollowing(int minFollowers, int minFollowing);
}
