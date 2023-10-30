package settings.bot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Privat {
    private static final String PRIVAT_URL = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
    private static final String USD = "USD";
    private static final String EUR = "EUR";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    private static String getCurrencyRatePrivat(String jsonString, String currency, String operation) {
        JsonElement jsonElement = JsonParser.parseString(jsonString);
        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            Stream<JsonElement> jsonElementStream = StreamSupport.stream(jsonArray.spliterator(), false);
            return jsonElementStream
                    .filter(JsonElement::isJsonObject)
                    .map(JsonElement::getAsJsonObject)
                    .filter(jsonObject -> jsonObject.has("ccy") && jsonObject.get("ccy").getAsString().equals(currency) && jsonObject.has("base_ccy") && jsonObject.get("base_ccy").getAsString().equals("UAH"))
                    .map(jsonObject -> operation.equalsIgnoreCase("buy") ? jsonObject.get("buy").getAsString() : jsonObject.get("sale").getAsString())
                    .findFirst()
                    .orElse("Currency not found");
        }

        return "Something went wrong";
    }

    public static String getCurrencyRates() throws IOException, InterruptedException {
        String usdBuy = getCurrencyRatePrivat(sendGetRequest(PRIVAT_URL), USD, "buy");
        String eurBuy = getCurrencyRatePrivat(sendGetRequest(PRIVAT_URL), EUR, "buy");
        String usdSale = getCurrencyRatePrivat(sendGetRequest(PRIVAT_URL), USD,"sale");
        String eurSale = getCurrencyRatePrivat(sendGetRequest(PRIVAT_URL), EUR,"sale");

        return "Продаж USD: " + usdBuy + "\n" +
                "Кулівля USD: " + usdSale + "\n" +
                "Продаж EUR: " + eurBuy + "\n" +
                "Купівля EUR: " + eurSale;
    }
//    public static void main(String[] args) throws IOException, InterruptedException {
//        String usdBuy = getCurrencyRatePrivat(sendGetRequest(PRIVAT_URL), USD, "buy");
//        String eurBuy = getCurrencyRatePrivat(sendGetRequest(PRIVAT_URL), EUR, "buy");
//        String usdSale = getCurrencyRatePrivat(sendGetRequest(PRIVAT_URL), USD,"sale");
//        String eurSale = getCurrencyRatePrivat(sendGetRequest(PRIVAT_URL), EUR,"sale");
//
//        System.out.println("Продаж USD: " + usdBuy);
//        System.out.println("Кулівля USD: " + usdSale);
//        System.out.println("Продаж EUR: " + eurBuy);
//        System.out.println("Купівля EUR: " + eurSale);
//    }

    private static String sendGetRequest(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            return "Something went wrong";
        }
    }
}