package settings.bot;

public class SettingDefault {
    public static int decimalPlacesSaved;
    public static String currencySaved;
    public static String bankSaved;
    public static int timeSaved;
    public static void settingDefaultSaved(){
        decimalPlacesSaved=2;
        currencySaved ="USD";
        bankSaved ="ПриватБанк";
        timeSaved =9;
    }
    public static void settingSaved(int decimalPlaces, String currency,String bank,int time){
        decimalPlacesSaved=decimalPlaces;
        currencySaved =currency;
        bankSaved =bank;
        timeSaved =time;
    }
    public static void settingSavedDecimal(int decimalPlaces){
        decimalPlacesSaved=decimalPlaces;
        System.out.println("выбрана кнопка "+decimalPlaces);
    }
    public static void settingSavedCurrency( String currency){
        currencySaved =currency;
        System.out.println("выбрана кнопка "+currency);
    }
    public static void settingSavedBank(String bank){
        bankSaved =bank;
        System.out.println("выбрана кнопка "+bank);
    }
    public static void settingSaveTime(int time ){
        timeSaved  =time;
        System.out.println("выбрана кнопка "+time);
    }
}
