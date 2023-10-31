package settings.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.nio.charset.StandardCharsets;
import java.util.*;

import com.vdurmont.emoji.EmojiParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static settings.bot.ApplicationConstants.*;

public class TelegramBot extends TelegramLongPollingBot {
    private ApplicationConstants applicationConstants;

    public TelegramBot(ApplicationConstants applicationConstants) {
        this.applicationConstants = applicationConstants;
    }

    public TelegramBot() {
    }

    public void setApplicationConstants(ApplicationConstants applicationConstants) {
        this.applicationConstants = applicationConstants;
    }

    public ApplicationConstants getApplicationConstants() {
        return applicationConstants;
    }
    public UserSettings userSettings = new UserSettings(NBU,false,true,4, 10);
    Integer lastMessageID;




    @Override
    public String getBotUsername() {
        return applicationConstants.getBotName();
    }

    @Override
    public String getBotToken() {
        return applicationConstants.getBotToken();
    }
    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = getChatId(update);

        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            SendMessage message = createMessage("*Ласкаво просимо! *\n" +
                    "Цей бот допоможе Вам відстежувати актуальні курси валют."
            );
            message.setChatId(chatId);

            attachButtons(message, Map.of(
                    "Отримати інфу", "get_Info",
                    "Налаштування","get_Setting"
            ),"");
            sendApiMethodAsync(message);
        }
        if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().equals("get_Info")) {
                SendMessage message = createMessage(RequestBank.outputOfSettingsData(userSettings));
                attachButtons(message, Map.of(
                        "Отримати інфу", "get_Info",
                        "Налаштування","get_Setting"
                ),"");
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

                ),"");
                sendApiMethodAsync(message);
            }
            if (update.getCallbackQuery().getData().equals("get_DecimalPlaces")) {
                //DeleteMessage(chatId,(int) lastMessageID);
                decimalPlaces(chatId);

            }
            if (update.getCallbackQuery().getData().equals("get_DecimalPlaces2")) {
                userSettings.setDecimalPoints(2);
                //DeleteMessage(chatId,(int) lastMessageID);
                decimalPlaces(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_DecimalPlaces3")) {
                userSettings.setDecimalPoints(3);
                //DeleteMessage(chatId,(int) lastMessageID);
                decimalPlaces(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_DecimalPlaces4")) {
                userSettings.setDecimalPoints(4);
                //DeleteMessage(chatId,(int) lastMessageID);
                decimalPlaces(chatId);
            }

            if (update.getCallbackQuery().getData().equals("get_Bank")) {
                bank(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_Bank" + NBU)) {
                userSettings.setBank(NBU);
                bank(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_Bank" + PRIVAT)) {
                userSettings.setBank(PRIVAT);
                bank(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_Bank" + MONO)) {
                userSettings.setBank(MONO);
                bank(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_Currency")) {
                currency(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_Currency" + USD)) {
                userSettings.setUsd(!userSettings.getUsd());
                currency(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_Currency" + EUR)) {
                userSettings.setEur(!userSettings.getEur());
                currency(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_Time")) {
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_Time10")) {
                userSettings.setNotificationTime(10);
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_Time11")) {
                userSettings.setNotificationTime(11);
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_Time12")) {
                userSettings.setNotificationTime(12);
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_Time13")) {
                userSettings.setNotificationTime(13);
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_Time14")) {
                userSettings.setNotificationTime(14);
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_Time15")) {
                userSettings.setNotificationTime(15);
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_Time16")) {
                userSettings.setNotificationTime(16);
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_Time17")) {
                userSettings.setNotificationTime(17);
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_Time18")) {
                userSettings.setNotificationTime(18);
                time(chatId);
            }
            if (update.getCallbackQuery().getData().equals("get_Time0")) {
                userSettings.setNotificationTime(0);
                time(chatId);
            }
        }
    }

    public void decimalPlaces(long chatId){
        SendMessage message = createMessage("*Оберить кількість знаків після коми*");
        message.setChatId(chatId);
 /*       try {
            lastMessageID = execute(message).getMessageId();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/
        attachButtons(message,Map.of(
                        "2","get_DecimalPlaces2",
                        "3","get_DecimalPlaces3",
                        "4","get_DecimalPlaces4"
                ),
                "get_DecimalPlaces" + userSettings.getDecimalPoints()
        );
        sendApiMethodAsync(message);
    }
    public void currency(long chatId){

        SendMessage message = createMessage("*Оберить валюту*");
        message.setChatId(chatId);
/*       try {
            lastMessageID = execute(message).getMessageId();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/
        attachButtonsCurrency(message,Map.of(
                        "USD","get_CurrencyUSD",
                        "EUR","get_CurrencyEUR"),
                userSettings.getUsd(), userSettings.getEur()
        );
        sendApiMethodAsync(message);
    }
    public void bank(long chatId){
        SendMessage message = createMessage("*Оберить банк*");
        message.setChatId(chatId);
 /*       try {
            lastMessageID = execute(message).getMessageId();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/
        attachButtons(message,Map.of(
                        "NBU","get_Bank" + NBU,
                        "PrivatBank","get_Bank" + PRIVAT,
                        "MONOBank","get_Bank" + MONO),
                "get_Bank" + userSettings.getBank()
        );
        sendApiMethodAsync(message);
    }
    public void time(long chatId){
        SendMessage message = createMessage("*Оберить час оповіщень*");
        message.setChatId(chatId);
/*       try {
            lastMessageID = execute(message).getMessageId();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/
        attachButtons(message,Map.of(
                        "18","get_Time18",
                        "10","get_Time10",
                        "11","get_Time11",
                        "12","get_Time12",
                        "13","get_Time13",
                        "14","get_Time14",
                        "15","get_Time15",
                        "16","get_Time16",
                        "17","get_Time17",
                        "No_notification","get_Time0"),
                "get_Time" + userSettings.getNotificationTime()
        );
        sendApiMethodAsync(message);
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

    public void attachButtons(SendMessage message, Map<String, String> buttons, String pressedButton) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (String buttonName : buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);
            InlineKeyboardButton button = new InlineKeyboardButton();
            if (buttonValue.equals(pressedButton)) {
                button.setText(EmojiParser.parseToUnicode(":white_check_mark:" + buttonName));
            } else {
                button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
            }

            button.setCallbackData(buttonValue);
            keyboard.add(Arrays.asList(button));
        }
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }
    public void attachButtonsCurrency(SendMessage message, Map<String, String> buttons, boolean usd, boolean eur) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (String buttonName : buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);
            InlineKeyboardButton button = new InlineKeyboardButton();
            if ((usd && buttonName.equals("USD")) || (eur && buttonName.equals("EUR"))) {
                button.setText(EmojiParser.parseToUnicode(":white_check_mark:" + buttonName));
            } else {
                button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
            }

            button.setCallbackData(buttonValue);
            keyboard.add(Arrays.asList(button));
        }
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }
   /* public void DeleteMessage(long chatId, int messageId)  {
        try {
            DeleteMessage deleteMessage = new DeleteMessage();
            deleteMessage.setChatId(chatId);
            deleteMessage.setMessageId(messageId);
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }*/

}