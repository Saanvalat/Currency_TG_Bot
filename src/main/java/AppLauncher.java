import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import settings.bot.AplicationConstants;
import settings.bot.TelegramBot;

public class AppLauncher {

        public static void main(String[] args) throws TelegramApiException {
            AplicationConstants aplicationConstants = new AplicationConstants();

            TelegramBot telegramBot = new TelegramBot(aplicationConstants);

            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(telegramBot);

            String botToken = aplicationConstants.getBotToken();
            String botName = aplicationConstants.getBotName();

        }
}