    package com.shopez.app.service;

    import com.shopez.app.dao.RoleDao;
    import com.shopez.app.dao.UserDao;
    import com.shopez.app.entities.Role;
    import com.shopez.app.entities.User;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.util.HashSet;
    import java.util.Optional;
    import java.util.Set;

    @Service
    public class UserService {

        @Autowired
        private UserDao userDao;

        @Autowired
        private RoleDao roleDao;

        @Autowired
        private PasswordEncoder passwordEncoder;

        public User registerUser(User user) {
            System.out.println("➡️ Received user registration request: " + user.getUserName());
            // Encode password
            user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            System.out.println("🔐 Encoded password set for user");

            // Set default role if none is provided
            if (user.getRole() == null || user.getRole().isEmpty()) {
                Optional<Role> defaultRoleOpt = roleDao.findByRoleName("USER");

                Role defaultRole = defaultRoleOpt.orElseGet(() -> {
                    // Create role if it doesn’t exist
                    Role newRole = new Role();
                    newRole.setRoleName("USER");
                    return roleDao.save(newRole);
                });


                System.out.println("✅ Role being assigned: " + defaultRole.getRoleName());

                Set<Role> roles = new HashSet<>();
                roles.add(defaultRole);
                user.setRole(roles);
            }

            User savedUser = userDao.save(user);
            System.out.println("✅ User saved to DB with ID: " + savedUser.getUserName());
            return savedUser;
        }

        public String getEncodedPassword(String password){
            return passwordEncoder.encode(password);
        }



        public boolean userExists(String username) {
            return userDao.findByUserName(username).isPresent();
        }

        public void registerAdmin(User admin) {
            System.out.println("➡️ Received admin registration request: " + admin.getUserName());

            // Encode the password
            admin.setUserPassword(passwordEncoder.encode(admin.getUserPassword()));
            System.out.println("🔐 Encoded password set for admin");

            // Set ADMIN role
            Optional<Role> adminRoleOpt = roleDao.findByRoleName("ADMIN");

            Role adminRole = adminRoleOpt.orElseGet(() -> {
                Role newRole = new Role();
                newRole.setRoleName("ADMIN");
                return roleDao.save(newRole);
            });

            System.out.println("👑 Role being assigned: " + adminRole.getRoleName());

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            admin.setRole(roles);

            // Save the admin user
            User savedAdmin = userDao.save(admin);
            System.out.println("✅ Admin user saved to DB with username: " + savedAdmin.getUserName());
        }

    }
