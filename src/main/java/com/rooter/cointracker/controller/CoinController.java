package com.rooter.cointracker.controller;

import com.rooter.cointracker.model.Coin;
import com.rooter.cointracker.service.CoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/coins")
@CrossOrigin(origins = "*")
public class CoinController {
    private final CoinService coinService;
    private static final Logger logger = LoggerFactory.getLogger(CoinController.class);

    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @PostMapping
    public Coin addCoins(@RequestBody Coin coin) {
        logger.info("Adding coins: {}", coin);
        return coinService.addCoins(coin);
    }

    @GetMapping
    public List<Coin> getAllCoins() {
        logger.info("Fetching all coins");
        return coinService.getAllCoins();
    }

    @GetMapping("/{date}")
    public List<Coin> getCoinsByDate(@PathVariable String date) {
        logger.info("Fetching coins by date: {}", date);
        return coinService.getCoinsByDate(LocalDate.parse(date));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coin> updateCoin(@PathVariable Long id, @RequestBody Coin coin) {
        logger.info("Updating coin with id: {}", id);
        Coin updatedCoin = coinService.updateCoins(id, coin);
        return ResponseEntity.ok(updatedCoin);
    }
}