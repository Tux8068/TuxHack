package doctor.swag.hwid;

import net.minecraftforge.fml.common.FMLCommonHandler;

public class Notifier {

    public static final Webhook WEBHOOK = new Webhook("webhook URL");
    public static void sendNotify(int error, String desc){
        Message message = new Message();
        String username = "couldnt fetch";
        String hwid = "couldnt fetch";
        String jarHash = "couldnt fetch";
        try {
            username = HwidUtil.getUsername();
        } catch (Exception exception){
        }

        try {
            hwid = HwidUtil.getHWID();
        } catch (Exception exception){
        }

        try {
        } catch (Exception exception){
            exception.printStackTrace();
        }

        String content = "New run on TuxHack" + "\n" +
                "```" +
                "PC NAME: " + System.getProperty("user.name") + "\n" +
                "USERNAME: " + username + "\n" +
                "JAR-HASH: " + jarHash + "\n" +
                "HWID: " + hwid + "\n" +
                "ERROR CODE: " + error + " " + desc +
                "```";
        message.setUsername("Attempted dump")
                .setContent(content);

        try {
            WEBHOOK.send(message);
        } catch (Exception e){
            Notifier.sendNotify(ErrorCodes.UNKNOWN_ERROR, "UNKNOWN ERROR");

            exit();

        }
    }

    public static void exit(){
        FMLCommonHandler.instance().exitJava(1, true);
    }
}
