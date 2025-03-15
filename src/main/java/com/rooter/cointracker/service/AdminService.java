package com.rooter.cointracker.service;

import com.rooter.cointracker.model.User;
import com.rooter.cointracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public String exportData() {
        List<User> users = userRepository.findAll();
        StringBuilder csvData = new StringBuilder("ID,Username,Role\n");

        for (User user : users) {
            csvData.append(user.getId())
                   .append(",")
                   .append(user.getUsername())
                   .append(",")
                   .append(user.getRole())
                   .append("\n");
        }

        return csvData.toString();
    }

    public String monitorSystem() {
        StringBuilder status = new StringBuilder("System status:\n");

        // Check database connectivity
        try {
            long userCount = userRepository.count();
            status.append("Database: OK (").append(userCount).append(" users)\n");
        } catch (Exception e) {
            status.append("Database: ERROR (").append(e.getMessage()).append(")\n");
        }

        // Check memory usage
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        status.append("Memory: ").append(freeMemory / (1024 * 1024)).append(" MB free of ")
              .append(totalMemory / (1024 * 1024)).append(" MB total\n");

        // Add other system checks as needed

        return status.toString();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        return userRepository.save(existingUser);
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }
}