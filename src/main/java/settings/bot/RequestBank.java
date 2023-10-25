package settings.bot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class RequestBank {
    private static final String MONO_URL = "https://api.monobank.ua/bank/currency";
    private static final String NBU_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private static final int USD = 840;
    private static final int EUR = 978;
    private static final int UAH = 980;
    private static final int NBU = 1;
    private static final int MONO_BANK = 2;
    private static final int TIME = 0;
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static String getCurrencyRateBank(int bank, String bankUrl, int currency) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(bankUrl))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            switch(bank) {
                case NBU :
                    return getCurrencyRateNBU(response.body(), currency);
                case MONO_BANK :
                    return getCurrencyRateMONO(response.body(), currency);
            }
        }
        return "UPS, Something wrong";
    }
    private static String getCurrencyRateNBU(String jsonString, int currency) {
        JsonElement jsonElement = JsonParser.parseString(jsonString);
        // parsing a json string
        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            Stream<JsonElement> jsonElementStream = StreamSupport.stream(jsonArray.spliterator(), false);
            return jsonElementStream
                    .filter(JsonElement::isJsonObject)
                    .map(JsonElement::getAsJsonObject)
                    .filter(jsonObject -> jsonObject.has("r030") && (jsonObject.get("r030").getAsInt() == currency))
                    .map(jsonObject -> jsonObject.get("rate").getAsString())
                    .collect(Collectors.joining());
        }
        return "UPS, Something wrong";
    }
    private static String getCurrencyRateMONO(String jsonString, int currency) {
        JsonElement jsonElement = JsonParser.parseString(jsonString);
        // parsing a json string
        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            Stream<JsonElement> jsonElementStream = StreamSupport.stream(jsonArray.spliterator(), false);
            return jsonElementStream
                    .filter(JsonElement::isJsonObject)
                    .map(JsonElement::getAsJsonObject)
                    .filter(jsonObject -> jsonObject.has("currencyCodeA") && (jsonObject.get("currencyCodeA").getAsInt() == currency) && jsonObject.has("currencyCodeB") && (jsonObject.get("currencyCodeB").getAsInt() == UAH))
                    .map(jsonObject -> jsonObject.get("rateSell").getAsString())
                    .collect(Collectors.joining());
        }
        return "UPS, Something wrong";
    }
    public static void main (String[] args) throws IOException, InterruptedException { //test requests

        System.out.println(getCurrencyRateBank(NBU,NBU_URL,USD));
        System.out.println(getCurrencyRateBank(NBU,NBU_URL,EUR));
        System.out.println(getCurrencyRateBank(MONO_BANK,MONO_URL,USD));
        System.out.println(getCurrencyRateBank(MONO_BANK,MONO_URL,EUR));
        System.out.println("STOP");
    }

}
