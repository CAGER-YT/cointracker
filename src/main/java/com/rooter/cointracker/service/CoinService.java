package com.rooter.cointracker.service;

import com.rooter.cointracker.model.Coin;
import com.rooter.cointracker.repository.CoinRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CoinService {
    private final CoinRepository coinRepository;

    public CoinService(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    public Coin addCoins(Coin coin) {
        return coinRepository.save(coin);
    }

    public List<Coin> getAllCoins() {
        return coinRepository.findAll();
    }

    public List<Coin> getCoinsByDate(LocalDate date) {
        return coinRepository.findByDate(date);
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

}

