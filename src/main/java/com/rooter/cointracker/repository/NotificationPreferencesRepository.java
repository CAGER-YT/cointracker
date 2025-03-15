package com.rooter.cointracker.repository;

import com.rooter.cointracker.model.NotificationPreferences;
import com.rooter.cointracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationPreferencesRepository extends JpaRepository<NotificationPreferences, Long> {
    NotificationPreferences findByUser(User user);
}