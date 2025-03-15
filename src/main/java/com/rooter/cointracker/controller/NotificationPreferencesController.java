package com.rooter.cointracker.controller;

import com.rooter.cointracker.model.NotificationPreferences;
import com.rooter.cointracker.model.User;
import com.rooter.cointracker.repository.NotificationPreferencesRepository;
import com.rooter.cointracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationPreferencesController {

    @Autowired
    private NotificationPreferencesRepository notificationPreferencesRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<NotificationPreferences> getNotificationPreferences() {
        User user = getCurrentUser();
        NotificationPreferences preferences = notificationPreferencesRepository.findByUser(user);
        return ResponseEntity.ok(preferences);
    }

    @PostMapping
    public ResponseEntity<NotificationPreferences> updateNotificationPreferences(@RequestBody NotificationPreferences preferences) {
        User user = getCurrentUser();
        preferences.setUser(user);
        NotificationPreferences updatedPreferences = notificationPreferencesRepository.save(preferences);
        return ResponseEntity.ok(updatedPreferences);
    }

    private User getCurrentUser() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
}