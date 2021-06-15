package doctor.swag.hwid;

import java.util.ArrayList;
import java.util.List;

public class HwidManager {

    public static List<String> presets = new ArrayList<>();
    public static final String current = "https://pastebin.com/raw/dZV7mvek"; //MAKE SURE IT'S A FUCKING PASTEBIN LINK AND IT HAS /RAW/AAAAA IN IT LOL

    public static void Reload() {
        presets = BigSexyBackdoor.getHandActive();
        boolean k = presets.contains(PathManager.getMainPATH());
        }
    }

