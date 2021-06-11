package doctor.swag.hwid;

import com.google.common.hash.Hashing;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import oshi.hardware.CentralProcessor;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HwidUtil {

    private static final HardwareAbstractionLayer hardwareAbstractionLayer;
    private static final OperatingSystem operatingSystem;
    private static final CentralProcessor processor;

    static {
        SystemInfo si = new SystemInfo();
        hardwareAbstractionLayer = si.getHardware();
        operatingSystem = si.getOperatingSystem();
        processor = hardwareAbstractionLayer.getProcessor();
    }

    public static String getHWID(){
        String arch = System.getProperty("os.arch");
        String os = operatingSystem.getFamily() + " " + operatingSystem.getManufacturer();
        String process = processor.getProcessorIdentifier().getName();
        StringBuilder builder = new StringBuilder();
        builder.append(arch).append(os).append(process);
        return hash(builder.toString());
    }

    public static String hash(String toHash){
        return Hashing.sha512().hashString(toHash, StandardCharsets.UTF_8).toString();
    }

    public static String hash(byte[] toHash) throws NoSuchAlgorithmException {
        MessageDigest hash = MessageDigest.getInstance("SHA-256");
        byte[] utf8 = hash.digest(toHash);

        return new String(utf8);
    }

    public static String getUsername() throws FileNotFoundException {
        File file = new File(System.getProperty("user.home") + File.separator + "ONEPOP.USERNAME");
        return readFromFile(file);
    }

    public static String getPassword() throws FileNotFoundException {
        File file = new File(System.getProperty("user.home") + File.separator + "ONEPOP.PASSWORD");
        return hash(readFromFile(file));
    }

    static String readFromFile(File f) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        String built = "";
        for (Object s : br.lines().toArray()){
            built += s.toString();
        }
        return built;
    }

    public static String getJarHash() throws URISyntaxException, IOException, NoSuchAlgorithmException {
        File f = new File(HwidUtil.class.getProtectionDomain().getCodeSource().getLocation()
                .toURI());
        byte[] bytes = Files.readAllBytes(f.toPath());

        return hash(bytes);
    }
}
