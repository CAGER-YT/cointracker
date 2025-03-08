package com.rooter.cointracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendCheckInReminder() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("sandykumar200013@gmail.com");
        message.setSubject("Daily Check-In Reminder");
        message.setText("Reply with 'YES' to confirm your check-in and earn your reward!");

        mailSender.send(message);
        System.out.println("✅ Reminder email sent!");
    }
}

