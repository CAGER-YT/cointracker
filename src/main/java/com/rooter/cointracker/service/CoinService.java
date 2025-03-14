package com.rooter.cointracker.service;

import com.rooter.cointracker.model.Coin;
import com.rooter.cointracker.model.User;
import com.rooter.cointracker.repository.CoinRepository;
import com.rooter.cointracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CoinService {
    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private UserRepository userRepository;

    public Coin addCoins(Coin coin) {
        User user = getCurrentUser();
        coin.setUser(user);
        return coinRepository.save(coin);
    }

    public List<Coin> getAllCoins() {
        User user = getCurrentUser();
        return coinRepository.findByUser(user);
    }

    public List<Coin> getCoinsByDate(LocalDate date) {
        User user = getCurrentUser();
        return coinRepository.findByDateAndUser(date, user);
    }

    public Coin updateCoins(Long id, Coin updatedCoin) {
        Optional<Coin> existingCoin = coinRepository.findById(id);
        if (existingCoin.isPresent()) {
            Coin coinToUpdate = existingCoin.get();
            coinToUpdate.setCoins(updatedCoin.getCoins());
            return coinRepository.save(coinToUpdate);
        } else {
            throw new RuntimeException("Coin entry not found for ID: " + id);
        }
    }

    private User getCurrentUser() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userRepository.findByUsername(username);
    }
}

