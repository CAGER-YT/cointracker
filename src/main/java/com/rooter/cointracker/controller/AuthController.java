package com.rooter.cointracker.controller;

import com.rooter.cointracker.model.User;
import com.rooter.cointracker.service.UserService;
import com.rooter.cointracker.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        logger.info("Registering user: {}", user.getUsername());
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) throws Exception {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            logger.error("Invalid login request: missing username or password");
            throw new Exception("Invalid login request: missing username or password");
        }

        logger.info("Attempting to log in user: {}", user.getUsername());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (Exception e) {
            logger.error("Invalid username or password for user: {}", user.getUsername(), e);
            throw new Exception("Invalid username or password");
        }
        final UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        logger.info("Generated JWT token for user: {}", user.getUsername());
        return token;
    }
}
