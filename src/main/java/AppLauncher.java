import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import settings.bot.SettingDefault;
import settings.bot.TelegramBot;

public class AppLauncher {

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        SettingDefault.settingDefaultSaved();
        try {
            api.registerBot(new TelegramBot());
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }
}