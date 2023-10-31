package settings.bot;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.IOException;

public class ApplicationConstants extends TelegramBot{
    private JSONObject jsonObject;
    static final int PRIVAT = 1;
    static final int MONO = 2;
    static final int NBU = 3;
    static final String MONO_URL = "https://api.monobank.ua/bank/currency";
    static final String NBU_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    static final String PRIVAT_URL = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
    static final String USD = "USD";
    static final String EUR = "EUR";
    static final boolean BUY = true;
    static final boolean SELL = false;
    static final int USD_CODE = 840;
    static final int EUR_CODE = 978;
    static final int UAH_CODE = 980;

    public ApplicationConstants() {
        try {
            JSONTokener tokener = new JSONTokener(new FileInputStream("config.json"));
            jsonObject = new JSONObject(tokener);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TelegramBot bot = new TelegramBot(this);
        bot.setApplicationConstants(this);
    }

    public String getBotToken() {
        return jsonObject.getString("BOT_TOKEN");
    }

    public String getBotName() {
        return jsonObject.getString("BOT_NAME");
    }

}

