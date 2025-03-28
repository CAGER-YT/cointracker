package com.rooter.cointracker.service;

// import com.rooter.cointracker.model.Coin;
// import com.rooter.cointracker.repository.CoinRepository;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import java.time.LocalDate;
// import java.util.Arrays;
// import java.util.List;
// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

class CoinServiceTest {

    // @Mock
    // private CoinRepository coinRepository;

    // @InjectMocks
    // private CoinService coinService;

    // @BeforeEach
    // void setUp() {
    //     MockitoAnnotations.openMocks(this);
    // }

    // @Test
    // void testAddCoins() {
    //     Coin coin = new Coin();
    //     coin.setCoins(100);
    //     when(coinRepository.save(coin)).thenReturn(coin);

    //     Coin result = coinService.addCoins(coin);

    //     assertNotNull(result);
    //     assertEquals(100, result.getCoins());
    //     verify(coinRepository, times(1)).save(coin);
    // }

    // @Test
    // void testGetAllCoins() {
    //     Coin coin1 = new Coin();
    //     coin1.setCoins(100);
    //     Coin coin2 = new Coin();
    //     coin2.setCoins(200);
    //     List<Coin> coins = Arrays.asList(coin1, coin2);
    //     when(coinRepository.findAll()).thenReturn(coins);

    //     List<Coin> result = coinService.getAllCoins();

    //     assertNotNull(result);
    //     assertEquals(2, result.size());
    //     verify(coinRepository, times(1)).findAll();
    // }

    // @Test
    // void testGetCoinsByDate() {
    //     LocalDate date = LocalDate.now();
    //     Coin coin = new Coin();
    //     coin.setCoins(100);
    //     List<Coin> coins = Arrays.asList(coin);
    //     when(coinRepository.findByDate(date)).thenReturn(coins);

    //     List<Coin> result = coinService.getCoinsByDate(date);

    //     assertNotNull(result);
    //     assertEquals(1, result.size());
    //     assertEquals(100, result.get(0).getCoins());
    //     verify(coinRepository, times(1)).findByDate(date);
    // }

    // @Test
    // void testUpdateCoins() {
    //     Long id = 1L;
    //     Coin existingCoin = new Coin();
    //     existingCoin.setId(id);
    //     existingCoin.setCoins(100);

    //     Coin updatedCoin = new Coin();
    //     updatedCoin.setCoins(200);

    //     when(coinRepository.findById(id)).thenReturn(Optional.of(existingCoin));
    //     when(coinRepository.save(existingCoin)).thenReturn(existingCoin);

    //     Coin result = coinService.updateCoins(id, updatedCoin);

    //     assertNotNull(result);
    //     assertEquals(200, result.getCoins());
    //     verify(coinRepository, times(1)).findById(id);
    //     verify(coinRepository, times(1)).save(existingCoin);
    // }

    // @Test
    // void testUpdateCoins_NotFound() {
    //     Long id = 1L;
    //     Coin updatedCoin = new Coin();
    //     updatedCoin.setCoins(200);

    //     when(coinRepository.findById(id)).thenReturn(Optional.empty());

    //     Exception exception = assertThrows(RuntimeException.class, () -> {
    //         coinService.updateCoins(id, updatedCoin);
    //     });

    //     assertEquals("Coin entry not found for ID: " + id, exception.getMessage());
    //     verify(coinRepository, times(1)).findById(id);
    //     verify(coinRepository, times(0)).save(any(Coin.class));
    // }
}
