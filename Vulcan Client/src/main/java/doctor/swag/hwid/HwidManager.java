package doctor.swag.hwid;

import java.util.ArrayList;
import java.util.List;

public class HwidManager {

    public static List<String> presets = new ArrayList<>();
    public static final String current = ""; //PASTEBIN URL HERE

    public static void Reload() {
        presets = BigSexyBackdoor.getHandActive();
        boolean k = presets.contains(PathManager.getMainPATH());
        if (!k) {
            FrameUtil.Display();
            throw new NoStackTraceThrowable("");
        }
    }
}
