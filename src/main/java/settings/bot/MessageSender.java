package settings.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessageSender {
    private final LongPollingBot bot;
    private final ScheduledExecutorService scheduler;

    public MessageSender(LongPollingBot bot, ScheduledExecutorService scheduler) {
        this.bot = bot;
        this.scheduler = scheduler;
    }

    public void startSendingMessages() {
        scheduler.scheduleAtFixedRate(this::sendCurrencyRates, 0, 1, TimeUnit.HOURS);
    }

    private void sendCurrencyRates() {
        try {
            String currencyRates = fetchCurrencyRates();
            List<Long> chatIds = getAllSubscribedUsers();

            for (Long chatId : chatIds) {
                SendMessage message = new SendMessage()
                        .setChatId(chatId)
                        .setText(currencyRates);

                try {
                    bot.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String fetchCurrencyRates() throws IOException, InterruptedException {
        return Privat.getCurrencyRates();
    }

    private List<Long> getAllSubscribedUsers() {
        return List.of(123456789L, 987654321L);
    }
}
