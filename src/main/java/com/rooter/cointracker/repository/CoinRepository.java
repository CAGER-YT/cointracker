package com.rooter.cointracker.repository;

import com.rooter.cointracker.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.*;

public interface CoinRepository extends JpaRepository<Coin, Long> {

    List<Coin> findByDate(LocalDate date);

    @Query("SELECT c FROM Coin c WHERE c.date = :date AND c.response = 'YES'")
    Coin findTodayCheckIn(LocalDate date);
}