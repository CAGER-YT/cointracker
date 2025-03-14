package com.rooter.cointracker.repository;

import com.rooter.cointracker.model.Coin;
import com.rooter.cointracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CoinRepository extends JpaRepository<Coin, Long> {
    List<Coin> findByDateAndUser(LocalDate date, User user);
    List<Coin> findByUser(User user);
    List<Coin> findByDate(LocalDate date);

    @Query("SELECT c FROM Coin c WHERE c.date = :date AND c.response = 'YES' AND c.user = :user")
    Coin findTodayCheckIn(LocalDate date, User user);
}