package settings.bot;

public class ApplicationConstants extends TelegramBot{
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
    public static final String BOT_TOKEN = "6602578220:AAGPWbC6OOfwIoSoQPVZ5ZNNQSclwTGzfSg";
    public static final String BOT_NAME = "ExchangeCurrencyTG2bot";
/*    public static final String BOT_TOKEN = "6888449176:AAFoGMCJZLZzoGJFDhrwAisJT3_xxX1Ivs8";
    public static final String BOT_NAME = "ExchangeCurrencyTGbot";*/


}
