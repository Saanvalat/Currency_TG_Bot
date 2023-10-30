package settings.bot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class RequestBank {
    private static final String MONO_URL = "https://api.monobank.ua/bank/currency";
    private static final String NBU_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private static final String PRIVAT_URL = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
    private static final String USD = "USD";
    private static final String EUR = "EUR";
    private static final boolean BUY = true;
    private static final boolean SELL = false;
    private static final int USD_CODE = 840;
    private static final int EUR_CODE = 978;
    private static final int UAH_CODE = 980;
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
            } else {
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
                .orElse(0.0f);
    }

    private static float getCurrencyRateMONO(Stream<JsonElement> jsonStream, int currency, boolean operation) {
        return jsonStream
                .filter(JsonElement::isJsonObject)
                .map(JsonElement::getAsJsonObject)
                .filter(jsonObject -> jsonObject.has("currencyCodeA") && (jsonObject.get("currencyCodeA").getAsInt() == currency) && jsonObject.has("currencyCodeB") && (jsonObject.get("currencyCodeB").getAsInt() == UAH_CODE))
                .map(jsonObject -> operation ? jsonObject.get("rateBuy").getAsFloat() : jsonObject.get("rateSell").getAsFloat())
                .findFirst()
                .orElse(0.0f); //
    }


    private static float getCurrencyRateNBU(Stream<JsonElement> jsonStream, int currency) {
        return jsonStream
                .filter(JsonElement::isJsonObject)
                .map(JsonElement::getAsJsonObject)
                .filter(jsonObject -> jsonObject.has("r030") && (jsonObject.get("r030").getAsInt() == currency))
                .map(jsonObject -> jsonObject.get("rate").getAsFloat())
                .findFirst()
                .orElse(0.0f);
    }


    private static void delay(int millsec) {
        try {
            Thread.sleep(millsec);
        } catch (InterruptedException ex) {
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Курси продажу та купівлі валюти Приватбанку");
        System.out.println("Купівля USD: " + getCurrencyRatePrivat(sendGetRequest(PRIVAT_URL), USD, BUY));
        System.out.println("Купівля EUR: " + getCurrencyRatePrivat(sendGetRequest(PRIVAT_URL), EUR, BUY));
        System.out.println("Продаж USD: " + getCurrencyRatePrivat(sendGetRequest(PRIVAT_URL), USD, SELL));
        System.out.println("Продаж EUR: " + getCurrencyRatePrivat(sendGetRequest(PRIVAT_URL), EUR, SELL));

        System.out.println("Курси продажу та купівлі валюти Монобанку");
        System.out.println("Купівля USD: " + getCurrencyRateMONO(sendGetRequest(MONO_URL), USD_CODE, BUY));
        System.out.println("Купівля EUR: " + getCurrencyRateMONO(sendGetRequest(MONO_URL), EUR_CODE, BUY));
        System.out.println("Продаж USD: " + getCurrencyRateMONO(sendGetRequest(MONO_URL), USD_CODE, SELL));
        System.out.println("Продаж EUR: " + getCurrencyRateMONO(sendGetRequest(MONO_URL), EUR_CODE, SELL));

        System.out.println("Курси валюти НБУ");
        System.out.println("Продаж USD: " + getCurrencyRateNBU(sendGetRequest(NBU_URL), USD_CODE));
        System.out.println("Продаж EUR: " + getCurrencyRateNBU(sendGetRequest(NBU_URL), EUR_CODE));
    }
}
