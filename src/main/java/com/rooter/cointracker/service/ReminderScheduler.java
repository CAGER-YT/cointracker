package com.rooter.cointracker.service;

import com.rooter.cointracker.model.Coin;
import com.rooter.cointracker.repository.CoinRepository;
import com.rooter.cointracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.rooter.cointracker.model.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReminderScheduler {

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(ReminderScheduler.class);

    // Runs daily at 10 AM
    @Scheduled(cron = "0 0 10 * * ?")
    public void sendCheckInReminder() {
        LocalDate today = LocalDate.now();
        List<User> users = userRepository.findAll();

        for (User user : users) {
            Coin checkIn = coinRepository.findTodayCheckIn(today, user);
            if (checkIn == null) {
                emailService.sendCheckInReminder(user);
                logger.info("Sent check-in reminder email to user: {}", user.getUsername());
            }
        }
    }

    @Scheduled(fixedRate = 1800000)
    public void checkEmailReplies() {
        // ...existing code...
    }

    private User getCurrentUser() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        logger.debug("Fetching current user: {}", username);
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
}
