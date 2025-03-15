package com.rooter.cointracker.service;

import com.rooter.cointracker.model.Coin;
import com.rooter.cointracker.model.User;
import com.rooter.cointracker.repository.CoinRepository;
import com.rooter.cointracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CoinService {
    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(CoinService.class);

    public Coin addCoins(Coin coin) {
        User user = getCurrentUser();
        coin.setUser(user);
        logger.info("Adding coins for user: {}", user.getUsername());
        return coinRepository.save(coin);
    }

    public List<Coin> getAllCoins() {
        User user = getCurrentUser();
        logger.info("Fetching all coins for user: {}", user.getUsername());
        return coinRepository.findByUser(user);
    }

    public List<Coin> getCoinsByDate(LocalDate date) {
        User user = getCurrentUser();
        logger.info("Fetching coins by date: {} for user: {}", date, user.getUsername());
        return coinRepository.findByDateAndUser(date, user);
    }

    public Coin updateCoins(Long id, Coin updatedCoin) {
        Optional<Coin> existingCoin = coinRepository.findById(id);
        if (existingCoin.isPresent()) {
            Coin coinToUpdate = existingCoin.get();
            coinToUpdate.setCoins(updatedCoin.getCoins());
            logger.info("Updating coins for id: {}", id);
            return coinRepository.save(coinToUpdate);
        } else {
            logger.error("Coin entry not found for ID: {}", id);
            throw new RuntimeException("Coin entry not found for ID: " + id);
        }
    }

    private User getCurrentUser() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        logger.debug("Fetching current user: {}", username);
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
}

