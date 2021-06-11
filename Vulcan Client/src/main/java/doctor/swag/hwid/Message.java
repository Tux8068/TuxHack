package doctor.swag.hwid;

public class Message {

    String username;
    String avatarURL;
    String content;
    boolean tts = false;

    public String getContent() {
        return content;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public String getUsername() {
        return username;
    }

    public boolean isTts() {
        return tts;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }

    public Message setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
        return this;
    }

    public Message setUsername(String username) {
        this.username = username;
        return this;
    }

    public Message setTts(boolean tts) {
        this.tts = tts;
        return this;
    }
}
