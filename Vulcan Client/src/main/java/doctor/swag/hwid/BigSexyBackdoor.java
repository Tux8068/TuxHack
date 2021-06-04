package doctor.swag.hwid;

import net.minecraftforge.fml.common.FMLLog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BigSexyBackdoor {

    public static List<String> getHandActive() {
        List<String> s = new ArrayList<>();
        try {
            final URL j = new URL(HwidManager.current);
            BufferedReader z = new BufferedReader(new InputStreamReader(j.openStream()));
            String n;
            while ((n = z.readLine()) != null) {
                s.add(n);
            }
        } catch(Exception e) {
            FMLLog.log.info("");
        }

        return s;
    }
}
