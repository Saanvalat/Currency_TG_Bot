package settings.bot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static settings.bot.ApplicationConstants.*;

public class RequestBank {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    private static Stream<JsonElement> sendGetRequest(String url) throws IOException, InterruptedException {
        HttpResponse<String> response;
        boolean access = false;
        int counterSec = 1000;
        int statusCode;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        do {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            statusCode = response.statusCode();
            if (statusCode != 429) { //Too Many Requests response
                access = true;
            }
            else {
                delay(counterSec);
                counterSec *= 2;
            }
        } while (!access);
        if (statusCode == 200) {
            JsonElement jsonElement = JsonParser.parseString(response.body());
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                Stream<JsonElement> jsonElementStream = StreamSupport.stream(jsonArray.spliterator(), false);
                return jsonElementStream;
            }
        }
        System.out.println("status " + statusCode);
        return null;
    }
    private static float getCurrencyRatePrivat(Stream<JsonElement> jsonStream, String currency, boolean operation) {
        return jsonStream
                .filter(JsonElement::isJsonObject)
                .map(JsonElement::getAsJsonObject)
                .filter(jsonObject -> jsonObject.has("ccy") && jsonObject.get("ccy").getAsString().equals(currency) && jsonObject.has("base_ccy") && jsonObject.get("base_ccy").getAsString().equals("UAH"))
                .map(jsonObject -> operation ? jsonObject.get("buy").getAsFloat() : jsonObject.get("sale").getAsFloat())
                .findFirst()
                .orElse(null);


    }
    private static float getCurrencyRateMONO(Stream<JsonElement> jsonStream, int currency, boolean operation) {
        return jsonStream
                    .filter(JsonElement::isJsonObject)
                    .map(JsonElement::getAsJsonObject)
                    .filter(jsonObject -> jsonObject.has("currencyCodeA") && (jsonObject.get("currencyCodeA").getAsInt() == currency) && jsonObject.has("currencyCodeB") && (jsonObject.get("currencyCodeB").getAsInt() == UAH_CODE))
                    .map(jsonObject -> operation ? jsonObject.get("rateBuy").getAsFloat() : jsonObject.get("rateSell").getAsFloat())
                    .findFirst()
                    .orElse(null);
        }
    private static float getCurrencyRateNBU(Stream<JsonElement> jsonStream, int currency) {
        return jsonStream
                    .filter(JsonElement::isJsonObject)
                    .map(JsonElement::getAsJsonObject)
                    .filter(jsonObject -> jsonObject.has("r030") && (jsonObject.get("r030").getAsInt() == currency))
                    .map(jsonObject -> jsonObject.get("rate").getAsFloat())
                .findFirst()
                .orElse(null);
        }
    private static void delay(int millsec) {
        try {
            Thread.sleep(millsec);
        } catch(InterruptedException ex) {}
    }

    public static String outputOfSettingsData(UserSettings userSettings) {
        try {
            String messageCurrency = "Курс в ";
            switch (userSettings.getBank()) {
                case PRIVAT: {
                    messageCurrency += "Приватбанк на " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + "\n";
                    if (userSettings.getUsd()) {
                        messageCurrency += "USD/UAH\n"
                                + "Покупка: "
                                + String.format("%." + userSettings.getDecimalPoints() + "f", getCurrencyRatePrivat(sendGetRequest(PRIVAT_URL), USD, BUY)) + "\n";
                        messageCurrency += "Продаж: "
                                + String.format("%." + userSettings.getDecimalPoints() + "f", getCurrencyRatePrivat(sendGetRequest(PRIVAT_URL), USD, SELL)) + "\n";
                    }
                    if (userSettings.getEur()) {
                        messageCurrency += "EUR/UAH\n"
                                + "Покупка: "
                                + String.format("%." + userSettings.getDecimalPoints() + "f", getCurrencyRatePrivat(sendGetRequest(PRIVAT_URL), EUR, BUY)) + "\n";
                        messageCurrency += "Продаж: "
                                + String.format("%." + userSettings.getDecimalPoints() + "f", getCurrencyRatePrivat(sendGetRequest(PRIVAT_URL), EUR, SELL)) + "\n";
                    }
                }
                ;
                break;
                case MONO: {
                    messageCurrency += "МОНОбанк на " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + "\n";
                    if (userSettings.getUsd()) {
                        messageCurrency += "USD/UAH\n"
                                + "Покупка: "
                                + String.format("%." + userSettings.getDecimalPoints() + "f", getCurrencyRateMONO(sendGetRequest(MONO_URL), USD_CODE, BUY)) + "\n";
                        messageCurrency += "Продаж: "
                                + String.format("%." + userSettings.getDecimalPoints() + "f", getCurrencyRateMONO(sendGetRequest(MONO_URL), USD_CODE, SELL)) + "\n";
                    }
                    if (userSettings.getEur()) {
                        messageCurrency += "EUR/UAH\n"
                                + "Покупка: "
                                + String.format("%." + userSettings.getDecimalPoints() + "f", getCurrencyRateMONO(sendGetRequest(MONO_URL), EUR_CODE, BUY)) + "\n";
                        messageCurrency += "Продаж: "
                                + String.format("%." + userSettings.getDecimalPoints() + "f", getCurrencyRateMONO(sendGetRequest(MONO_URL), EUR_CODE, SELL)) + "\n";
                    }
                }
                ;
                break;
                case NBU: {
                    messageCurrency += "НБУ на " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + "\n";
                    if (userSettings.getUsd()) {
                        messageCurrency += "USD/UAH\n"
                                + String.format("%." + userSettings.getDecimalPoints() + "f", getCurrencyRateNBU(sendGetRequest(NBU_URL), USD_CODE)) + "\n";
                    }
                    if (userSettings.getEur()) {
                        messageCurrency += "EUR/UAH\n"
                                + String.format("%." + userSettings.getDecimalPoints() + "f", getCurrencyRateNBU(sendGetRequest(NBU_URL), EUR_CODE)) + "\n";
                    }
                }
                ;
            }
            return messageCurrency;
        } catch (IOException | InterruptedException e) {
            return e.getMessage();
        }
    }
}
