package doctor.swag.hwid;

import java.util.ArrayList;
import java.util.List;

public class HwidManager {

    public static List<String> presets = new ArrayList<>();
    public static final String current = ""; //MAKE SURE IT'S A FUCKING PASTEBIN LINK AND IT HAS /RAW/AAAAA IN IT LOL

    public static void Reload() {
        presets = BigSexyBackdoor.getHandActive();
        boolean k = presets.contains(PathManager.getMainPATH());
        if (!k) {
            FrameUtil.Display();
            throw new NoStackTraceThrowable("");
        }
    }
}
