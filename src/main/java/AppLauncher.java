import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import settings.bot.ApplicationConstants;
import settings.bot.TelegramBot;

public class AppLauncher {

    public static void main(String[] args) throws TelegramApiException {
        ApplicationConstants applicationConstants = new ApplicationConstants();

        TelegramBot telegramBot = new TelegramBot(applicationConstants);

        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(telegramBot);

        String botToken = applicationConstants.getBotToken();
        String botName = applicationConstants.getBotName();
    }
}