package com.rooter.cointracker.service;

import com.rooter.cointracker.model.Coin;
import com.rooter.cointracker.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class ReminderScheduler {

    @Autowired
    private CoinRepository coinsRepository;

    @Autowired
    private EmailService emailService;

    // Runs daily at 10 AM
    @Scheduled(cron = "0 0 10 * * ?")
    public void sendCheckInReminder() {
        LocalDate today = LocalDate.now();

        // Check if you've already replied "YES" today
        Coin checkIn = coinsRepository.findTodayCheckIn(today);

        if (checkIn == null) {
            emailService.sendCheckInReminder();
        }
    }
}
