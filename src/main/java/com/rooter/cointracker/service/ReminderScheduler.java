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
import java.time.LocalDate;

@Service
public class ReminderScheduler {

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    // Runs daily at 10 AM
    @Scheduled(cron = "0 0 10 * * ?")
    public void sendCheckInReminder() {
        LocalDate today = LocalDate.now();

        // Check if you've already replied "YES" today
        User user = getCurrentUser();
        Coin checkIn = coinRepository.findTodayCheckIn(today, user);

        if (checkIn == null) {
            emailService.sendCheckInReminder();
        }
    }

    @Scheduled(fixedRate = 1800000)
    public void checkEmailReplies() {
        // ...existing code...
    }

    private User getCurrentUser() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userRepository.findByUsername(username);
    }
}
