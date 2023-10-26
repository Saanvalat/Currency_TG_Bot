package settings.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



public class TelegramBot extends TelegramLongPollingBot {
    private final AplicationConstants aplicationConstants;

    public TelegramBot(AplicationConstants aplicationConstants) {
        this.aplicationConstants = aplicationConstants;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
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
        return aplicationConstants.getBotName();
    }

    @Override
    public String getBotToken() {
        return aplicationConstants.getBotToken();
    }
}

