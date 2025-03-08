package com.rooter.cointracker.controller;

import com.rooter.cointracker.model.Coin;
import com.rooter.cointracker.service.CoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/coins")
@CrossOrigin(origins = "http://localhost:3000")
public class CoinController {
    private final CoinService coinService;

    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @PostMapping
    public Coin addCoins(@RequestBody Coin coin) {
        return coinService.addCoins(coin);
    }

    @GetMapping
    public List<Coin> getAllCoins() {
        return coinService.getAllCoins();
    }

    @GetMapping("/{date}")
    public List<Coin> getCoinsByDate(@PathVariable String date) {
        return coinService.getCoinsByDate(LocalDate.parse(date));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coin> updateCoin(@PathVariable Long id, @RequestBody Coin coin) {
        Coin updatedCoin = coinService.updateCoins(id, coin);
        return ResponseEntity.ok(updatedCoin);
    }
}