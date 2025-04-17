package com.abc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abc.entities.User;
import com.abc.services.UserService;

@Controller
public class SearchController {

    private final UserService userService;

    @Autowired
    public SearchController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/search")
    public String searchUsers(@RequestParam(name = "minFollowers", defaultValue = "0") int minFollowers,
                              @RequestParam(name = "minFollowing", defaultValue = "0") int minFollowing,
                              Model model) {
        List<User> users = userService.findUsersByFollowerAndFollowing(minFollowers, minFollowing);
        model.addAttribute("users", users);
        model.addAttribute("minFollowers", minFollowers);
        model.addAttribute("minFollowing", minFollowing);
        return "user_search";
    }

}
