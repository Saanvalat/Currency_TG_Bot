package settings.bot;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static settings.bot.AplicationConstants.BOT_NAME;
import static settings.bot.AplicationConstants.BOT_TOKEN;

public class TelegramBot extends TelegramLongPollingBot {


//    @Override
//    public void onUpdateReceived(Update update) {
////        if (update.hasMessage() && update.getMessage().hasText()) {
////            SendMessage message = new SendMessage();
////            message.setChatId(update.getMessage().getChatId().toString());
////            message.setText(update.getMessage().getText());
////
////            try {
////                execute(message);
////            } catch (TelegramApiException e) {
////                e.printStackTrace();
////            }
////        }
//
//
//    }


    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = getChatId(update);

        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            SendMessage msg = createMessage("*Ласкаво просимо! *\n" +
                    "Цей бот допоможе Вам відстежувати актуальні курси валют."
            );
            msg.setChatId(chatId);

            attachButtons(msg, Map.of(
                    "Отримати інфу", "get_Info",
                    "Налаштування","get_Setting"
            ));
            sendApiMethodAsync(msg);
        }
        if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().equals("get_Info")) {
                SendMessage message = createMessage("вибачте функционал \"Отримати інфу\" у розробці");
                message.setChatId(chatId);
                sendApiMethodAsync(message);
            }
            if (update.getCallbackQuery().getData().equals("get_Setting")) {
                SendMessage message = createMessage("*Налаштування*");
                message.setChatId(chatId);

                attachButtons(message,Map.of(
                        "Кількість знаків після коми", "get_DecimalPlaces",
                        "Банк","get_Bank",
                        "Валюти","get_Currency",
                        "Час оповіщень","get_Time"
                ));
                sendApiMethodAsync(message);
            }
            //-------------------------------
            if (update.getCallbackQuery().getData().equals("get_DecimalPlaces")) {
                System.out.println("кнопка ЗНАК");
                decimalPlaces(chatId);
            }

            if (update.getCallbackQuery().getData().equals("get_DecimalPlaces2")) {
                SettingDefault.settingSavedDecimal(2);
                System.out.println("кнопка 2");
                decimalPlaces(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_DecimalPlaces3")) {
                SettingDefault.settingSavedDecimal(3);
                System.out.println("кнопка 3");
                decimalPlaces(chatId);

            }
            if (update.getCallbackQuery().getData().equals("get_DecimalPlaces4")) {
                SettingDefault.settingSavedDecimal(4);
                System.out.println("кнопка 4");
                decimalPlaces(chatId);
            }
            //-------------------------------
            if (update.getCallbackQuery().getData().equals("get_Bank")) {
                System.out.println("кнопка БАНК");
                bank(chatId);
            }

            if (update.getCallbackQuery().getData().equals("get_bank1")) {
                SettingDefault.settingSavedBank("НБУ");
                System.out.println("кнопка НБУ");
                bank(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_bank2")) {
                SettingDefault.settingSavedBank("ПриватБанк");
                System.out.println("кнопка ПриватБанк");
                bank(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_bank3")) {
                SettingDefault.settingSavedBank("Монобанк");
                System.out.println("кнопка Монобанк");
                bank(chatId);
            }
            //-------------------------------
            if (update.getCallbackQuery().getData().equals("get_Currency")) {
                System.out.println("кнопка ВАЛЮТА");
                currency(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_СurrencyUSD")) {
                SettingDefault.settingSavedCurrency("USD");
                System.out.println("кнопка USD");
                currency(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_СurrencyEUR")) {
                SettingDefault.settingSavedCurrency("EUR");
                System.out.println("кнопка EUR");
                currency(chatId);
            }
            //-------------------------------
            if (update.getCallbackQuery().getData().equals("get_Time")) {
                System.out.println("кнопка ВРЕМЯ");
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_time9")) {
                SettingDefault.settingSaveTime(9);
                System.out.println("кнопка 9");
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_time10")) {
                SettingDefault.settingSaveTime(10);
                System.out.println("кнопка 10");
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_time11")) {
                SettingDefault.settingSaveTime(11);
                System.out.println("кнопка 11");
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_time12")) {
                SettingDefault.settingSaveTime(12);
                System.out.println("кнопка 12");
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_time13")) {
                SettingDefault.settingSaveTime(13);
                System.out.println("кнопка 13");
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_time14")) {
                SettingDefault.settingSaveTime(14);
                System.out.println("кнопка 14");
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_time15")) {
                SettingDefault.settingSaveTime(15);
                System.out.println("кнопка 15");
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_time16")) {
                SettingDefault.settingSaveTime(16);
                System.out.println("кнопка 16");
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_time17")) {
                SettingDefault.settingSaveTime(17);
                System.out.println("кнопка 17");
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_time18")) {
                SettingDefault.settingSaveTime(18);
                System.out.println("кнопка 18");
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_time0")) {
                SettingDefault.settingSaveTime(0);
                System.out.println("кнопка 0");
                time(chatId);
            }
        }
    }

    public Long getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom().getId();

        }
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        }
        return null;
    }

    public SendMessage createMessage(String text) {
        SendMessage message = new SendMessage();
        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
        message.setParseMode("markdown");
        return message;
    }

    public void attachButtons(SendMessage message, Map<String, String> buttons) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (String buttonName : buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);
            InlineKeyboardButton button = new InlineKeyboardButton();
            //      button.setText(buttonName);
            button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
            button.setCallbackData(buttonValue);
            keyboard.add(Arrays.asList(button));
        }
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }
    public void attachButtonsEmodji(SendMessage message, Map<String, String> buttons) {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (String buttonName : buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);
            InlineKeyboardButton button = new InlineKeyboardButton();

            System.out.println("==="+buttonName);

            if (Integer.parseInt (buttonName)>=2&&Integer.parseInt (buttonName)<=4){
                buttonEm(buttonName,button,SettingDefault.decimalPlacesSaved);
            }

//            if ((Integer.parseInt (buttonName)>=9&&Integer.parseInt (buttonName)<=18)||buttonName=="Вимкнути повідомлення" ){
//                System.out.println("!!!!СРАВН "+buttonName);
//            //    buttonEm(buttonName,button,SettingDefault.timeSaved);
//               if (buttonName.equals(String.valueOf(SettingDefault.timeSaved))) {
//                  button.setText(EmojiParser.parseToUnicode(":white_check_mark:" + buttonName));
//                   System.out.println("!!!!ДА "+buttonName);
//                 }
//                else {
//                button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
//                   System.out.println("!!!!НЕТ "+buttonName);
//                }
//            }
//            if (buttonName=="НБУ"||buttonName=="ПриватБанк"||buttonName=="Монобанк"){
//                System.out.println("!!!!СРАВНЕН "+buttonName);
//               // buttonEmString(buttonName,button,SettingDefault.bankSaved);
//                if (buttonName.equals(SettingDefault.bankSaved)) {
//                    button.setText(EmojiParser.parseToUnicode(":white_check_mark:" + buttonName));
//                }
//                else {
//                    button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
//                }
//            }
//            if (buttonName=="USD"||buttonName=="EUR"){
//          //      System.out.println("!!!!СРАВНЕ "+buttonName);
//                // buttonEmString(buttonName,button,SettingDefault.currencySaved);
//                if (buttonName.equals(SettingDefault.currencySaved)) {
//                    button.setText(EmojiParser.parseToUnicode(":white_check_mark:" + buttonName));
//                }
//                else {
//                    button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
//                }
//            }
//            if (buttonName.equals(String.valueOf(SettingDefault.decimalPlacesSaved))) {
//                button.setText(EmojiParser.parseToUnicode(":white_check_mark:" + buttonName));

//            }
//            else {
//                button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
//            }


            button.setCallbackData(buttonValue);
            keyboard.add(Arrays.asList(button));
        }
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }
    public void decimalPlaces(long chatId){
        SendMessage message = createMessage("*Оберить кількість знаків після коми*");
        message.setChatId(chatId);
        attachButtonsEmodji(message,Map.of(
                "2","get_DecimalPlaces2",
                "3","get_DecimalPlaces3",
                "4","get_DecimalPlaces4"
        ));
        sendApiMethodAsync(message);
    }
    public void currency(long chatId){
        System.out.println("!!!!*Оберить валюту*");
        SendMessage message = createMessage("*Оберить валюту*");
        message.setChatId(chatId);
        attachButtonsEmodjiCurr(message,Map.of(
                "USD","get_СurrencyUSD",
                "EUR","get_СurrencyEUR"

        ));
        sendApiMethodAsync(message);
    }
    public void bank(long chatId){
        System.out.println("!!!!*Оберить банк*");
        SendMessage message = createMessage("*Оберить банк*");

        message.setChatId(chatId);
        attachButtonsEmodjiBank(message,Map.of(
                "НБУ","get_bank1",
                "ПриватБанк","get_bank2",
                "Монобанк","get_bank3"
        ));
        sendApiMethodAsync(message);
    }
    public void time(long chatId){
        System.out.println("!!!!*Оберить час оповіщень*");
        SendMessage message = createMessage("*Оберить час оповіщень*");
        message.setChatId(chatId);//attachButtonsEmodjiTime   attachButtonsEmodji
        attachButtonsEmodjiTime(message,Map.of(

                "9","get_time9",
                "10","get_time10",
                "11","get_time11",
                "12","get_time12",
                "13","get_time13",
                "14","get_time14",
                "15","get_time15",
                "16","get_time16",
                "17","get_time17",
                //              "18","get_time18",
                "Вимкнути повідомлення","get_time0"
        ));
        sendApiMethodAsync(message);
    }
    public void attachButtonsEmodjiTime(SendMessage message, Map<String, String> buttons) {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (String buttonName : buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);
            InlineKeyboardButton button = new InlineKeyboardButton();

            //  System.out.println("==="+buttonName);
            //           if ((Integer.parseInt (buttonName)>=9&&Integer.parseInt (buttonName)<=18)||buttonName=="Вимкнути повідомлення" ){
            //  System.out.println("!!!!СРАВН "+buttonName);
            //    buttonEm(buttonName,button,SettingDefault.timeSaved);
            if (buttonName.equals(String.valueOf(SettingDefault.timeSaved))) {
                button.setText(EmojiParser.parseToUnicode(":white_check_mark:" + buttonName));
                //System.out.println("!!!!ДА "+buttonName);
            }
            else {
                button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
                //  System.out.println("!!!!НЕТ "+buttonName);
            }
            button.setCallbackData(buttonValue);
            keyboard.add(Arrays.asList(button));
        }
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }
    public void attachButtonsEmodjiBank(SendMessage message, Map<String, String> buttons) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (String buttonName : buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);
            InlineKeyboardButton button = new InlineKeyboardButton();

            //  System.out.println("==="+buttonName);

            //           if (buttonName=="НБУ"||buttonName=="ПриватБанк"||buttonName=="Монобанк"){
            System.out.println("!!!!СРАВНЕН "+buttonName);
            // buttonEmString(buttonName,button,SettingDefault.bankSaved);
            if (buttonName.equals(SettingDefault.bankSaved)) {
                //      button.setText(EmojiParser.parseToUnicode(":white_check_mark:" + buttonName));
                button.setText(EmojiParser.parseToUnicode(":white_check_mark:" + new String(buttonName.getBytes(), StandardCharsets.UTF_8)));
            }
            else {
                button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
            }
//            }
            button.setCallbackData(buttonValue);
            keyboard.add(Arrays.asList(button));
        }
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }
    public void attachButtonsEmodjiCurr(SendMessage message, Map<String, String> buttons) {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (String buttonName : buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);
            InlineKeyboardButton button = new InlineKeyboardButton();
//            System.out.println("==="+buttonName);

            // buttonEmString(buttonName,button,SettingDefault.currencySaved);
            if (buttonName.equals(SettingDefault.currencySaved)) {
                button.setText(EmojiParser.parseToUnicode(":white_check_mark:" + buttonName));
            }
            else {
                button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
            }
//            }
            button.setCallbackData(buttonValue);
            keyboard.add(Arrays.asList(button));
        }
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }
    public void buttonEm(String buttonName,InlineKeyboardButton button, int value ){
        if (buttonName.equals(String.valueOf( value ))) {
            button.setText(EmojiParser.parseToUnicode(":white_check_mark:" + buttonName));
        }
        else {
            button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
        }
    }
//    public void buttonEmString(String buttonName,InlineKeyboardButton button, String value ){
//        if (buttonName.equals( value )) {
//            button.setText(EmojiParser.parseToUnicode(":white_check_mark:" + buttonName));
//        }
//        else {
//            button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
//        }
//    }
}

