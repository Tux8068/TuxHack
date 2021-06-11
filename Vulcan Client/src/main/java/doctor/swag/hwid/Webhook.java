package doctor.swag.hwid;

import org.json.simple.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

public class Webhook {

    final String url;

    public Webhook(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void send(Message message) throws IOException {
        if (message.getContent() == null){
            throw new IllegalArgumentException("Please add content :(");
        }

        JSONObject json = new JSONObject();
        json.put("content", message.getContent());
        json.put("username", message.getUsername());
        json.put("avatar_url", message.getAvatarURL());
        json.put("tts", message.isTts());

        URL url = new URL(this.url);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        OutputStream stream = connection.getOutputStream();
        stream.write(json.toString().getBytes());
        stream.flush();
        stream.close();

        connection.getInputStream().close();
        connection.disconnect();
    }
}
