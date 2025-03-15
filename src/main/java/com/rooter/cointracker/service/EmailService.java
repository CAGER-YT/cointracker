package com.rooter.cointracker.service;

import com.rooter.cointracker.model.NotificationPreferences;
import com.rooter.cointracker.model.User;
import com.rooter.cointracker.repository.NotificationPreferencesRepository;
import com.rooter.cointracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationPreferencesRepository notificationPreferencesRepository;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendCheckInReminder(User user) {
        NotificationPreferences preferences = notificationPreferencesRepository.findByUser(user);
        if (preferences != null && preferences.isReminders()) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Daily Check-In Reminder");
            message.setText("Reply with 'YES' to confirm your check-in and earn your reward!");

            mailSender.send(message);
            logger.info("Reminder email sent to {}", user.getEmail());
        }
    }

    public void sendCoinUpdateNotification(User user, String updateMessage) {
        NotificationPreferences preferences = notificationPreferencesRepository.findByUser(user);
        if (preferences != null && preferences.isCoinUpdates()) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Coin Update Notification");
            message.setText(updateMessage);

            mailSender.send(message);
            logger.info("Coin update email sent to {}", user.getEmail());
        }
    }
}

