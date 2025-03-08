package com.rooter.cointracker.service;

import com.rooter.cointracker.model.Coin;
import com.rooter.cointracker.repository.CoinRepository;
import jakarta.mail.*;
import jakarta.mail.search.FlagTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Properties;

@Service
public class EmailReplyListener {

    @Autowired
    private CoinRepository coinsRepository;

    // Runs every 30 minutes to check for replies
    @Scheduled(fixedRate = 1800000)
    public void checkEmailReplies() {
        String host = "imap.gmail.com";
        String username = "sandykumar200013@gmail.com";
        String password = "avln smno tchq zbug";

        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");

        try {
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("imaps");
            store.connect(host, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            for (Message message : messages) {
                String content = message.getContent().toString().trim();

                if (content.equalsIgnoreCase("YES")) {
                    Coin coins = coinsRepository.findAll().stream().findFirst().orElse(null);

                    if (coins != null) {
                        coins.setResponse("YES");
                        coins.setDate(LocalDate.now());
                        coinsRepository.save(coins);
                    }
                }
                message.setFlag(Flags.Flag.SEEN, true);
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
