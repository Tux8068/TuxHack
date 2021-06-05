package doctor.swag.hwid;


import me.tux.tuxhack.util.Wrapper;
import net.minecraft.client.Minecraft;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class PathManager extends Wrapper {
    public static String getMainPATH() {
        String mainPath = "Tuxhack/";
        return DigestUtils.sha256Hex(DigestUtils.sha256Hex(System.getenv("os")
                + System.getProperty("os.name")
                + System.getProperty("os.arch")
                + System.getProperty("user.name")
                + System.getenv("SystemRoot")
                + System.getenv("HOMEDRIVE")
                + System.getenv("PROCESSOR_LEVEL")
                + System.getenv("PROCESSOR_REVISION")
                + System.getenv("PROCESSOR_IDENTIFIER")
                + System.getenv("PROCESSOR_ARCHITECTURE")
                + System.getenv("PROCESSOR_ARCHITEW6432")
                + System.getenv("NUMBER_OF_PROCESSORS")
                + System.getenv("USERNAME")
        )) + Minecraft.getMinecraft().session.getUsername();


    }
}