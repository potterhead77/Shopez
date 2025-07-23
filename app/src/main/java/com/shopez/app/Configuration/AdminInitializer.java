package com.shopez.app.Configuration;

import com.shopez.app.entities.User;
import com.shopez.app.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initAdminUser() {
        String username = "admin1";

        if (userService.userExists(username)) {
            System.out.println("👮 Admin user already exists.");
            return;
        }

        User admin = new User();
        admin.setUserName(username);
        admin.setUserFirstName("Alice");
        admin.setUserLastName("Admin");
        admin.setUserPassword("admin123"); // plain password, will be encoded

        userService.registerAdmin(admin); // handles encoding + role
        System.out.println("✅ Admin user created.");
    }
}
