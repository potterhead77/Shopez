    package com.shopez.app.service;

    import com.shopez.app.dao.UserDao;
    import com.shopez.app.entities.JwtRequest;
    import com.shopez.app.entities.JwtResponse;
    import com.shopez.app.entities.User;
    import com.shopez.app.util.JwtUtil;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Lazy;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.BadCredentialsException;
    import org.springframework.security.authentication.DisabledException;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;

    import java.util.HashSet;
    import java.util.Set;

    @Service
    public class JwtService   {

        private final UserDao userDao;

        private final UserDetailsService userDetailsService;
        private final JwtUtil jwtUtil;

        private final AuthenticationManager authenticationManager;

        public JwtService(
                @Lazy AuthenticationManager authenticationManager,
                UserDao userDao,
                JwtUtil jwtUtil,
                UserDetailsService userDetailsService) {
            this.authenticationManager = authenticationManager;
            this.userDao = userDao;
            this.jwtUtil = jwtUtil;
            this.userDetailsService = userDetailsService;
        }

        public JwtResponse createJwtToken(JwtRequest jwtRequest) {
            String userName = jwtRequest.getUserName();
            String userPassword = jwtRequest.getUserPassword();

            authenticate(userName, userPassword);

            final UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            String newGeneratedToken = jwtUtil.generateToken(userDetails);

            User user = userDao.findByUserName(userName).orElseThrow(() -> new RuntimeException("User not found"));

            return new JwtResponse(user, newGeneratedToken);
        }

        private Set getAuthorities(User user){
            Set authorities = new HashSet();
            user.getRole().forEach(role ->  {
                authorities.add(new SimpleGrantedAuthority("ROLE"+role.getRoleName()));
            });
            return authorities;
        }

        private void authenticate(String userName,String userPassword) {
            try{
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,userPassword));
            }catch (DisabledException e){
                throw new RuntimeException("User is disabled");
            }catch (BadCredentialsException e){
                throw new RuntimeException("Bad credentials from user");
            }

        }
    }
