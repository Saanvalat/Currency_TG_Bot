package settings.bot;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.module.Configuration;
import org.json.JSONObject;





public class AplicationConstants {


    private JSONObject jsonObject;

    public AplicationConstants() {
        try {
            JSONTokener tokener = new JSONTokener(new FileInputStream("config.json"));
            jsonObject = new JSONObject(tokener);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBotToken() {
        return jsonObject.getString("BOT_TOKEN");
    }

    public String getBotName() {
        return jsonObject.getString("BOT_NAME");
    }
}







