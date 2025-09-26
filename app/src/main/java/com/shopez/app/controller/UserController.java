package com.shopez.app.controller;

import com.shopez.app.entities.User;
import com.shopez.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/registerNewUser")
    public User registerNewUser(@RequestBody User user) {
        System.out.println("Received registration request: " + user);
        User saved = userService.registerUser(user);
        System.out.println("User saved: " + saved);
        return saved;
    }

    @GetMapping("/forAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String forAdmin(){
        return "This URL only available for Admin";
    }

    @GetMapping("/forUser")
    @PreAuthorize("hasRole('USER')")
    public String forUser(){
        return "This URL only available for User";
    }
}
