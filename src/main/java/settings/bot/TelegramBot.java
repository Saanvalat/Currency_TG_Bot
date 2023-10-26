package settings.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class TelegramBot extends TelegramLongPollingBot {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public TelegramBot() {
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setChatId(Long.toString(chatId));
            message.setText(update.getMessage().getText());

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public String getBotUsername() {
        return AplicationConstants.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return AplicationConstants.BOT_TOKEN;
    }





}