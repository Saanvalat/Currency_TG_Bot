import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AppLauncher extends TelegramLongPollingBot {
    public static void main (String[] args) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);

        try {
            api.registerBot(new  AppLauncher());
        }catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public String getBotUsername() {
        return "ExchangeCurrencyTGbot";
    }
    @Override
    public String getBotToken() {
        return "6888449176:AAFoGMCJZLZzoGJFDhrwAisJT3_xxX1Ivs8" ;
    }
    @Override
    public void onUpdateReceived(Update update) {
        Long chatId=getChatId(update);
        if (update.hasMessage()&& update.getMessage().getText().equals("/start")) {
            SendMessage msg= createMessage("*Ласкаво просимо! *\n" +
                    "Цей бот допоможе Вам відстежувати актуальні курси валют."
            );
            msg.setChatId(chatId);
            attachButtons(msg,Map.of(
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
        }
    }
    public Long getChatId(Update update){
        if (update.hasMessage()){
            return update.getMessage().getFrom().getId();
        }
        if(update.hasCallbackQuery()){
            return update.getCallbackQuery().getFrom().getId();
        }
        return null;
    }
    public SendMessage createMessage(String text){
        SendMessage message= new SendMessage();
        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
        message.setParseMode("markdown");
        return message;
    }
    public void attachButtons(SendMessage message, Map<String, String> buttons){
        InlineKeyboardMarkup markup=new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for(String buttonName: buttons.keySet()){
            String buttonValue=buttons.get(buttonName);
            InlineKeyboardButton button=new InlineKeyboardButton();

            button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
            button.setCallbackData(buttonValue);
            keyboard.add(Arrays.asList(button));
        }
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }
}

