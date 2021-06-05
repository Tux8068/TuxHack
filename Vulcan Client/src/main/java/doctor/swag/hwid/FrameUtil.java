package doctor.swag.hwid;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class FrameUtil {

    public static void Display() {
        Frame frame = new Frame();
        frame.setVisible(false);
        throw new NoStackTraceThrowable("verification failed");
    }

    public static class Frame extends JFrame {

        public Frame() {
            this.setTitle("Verify Failed");
            this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.setLocationRelativeTo(null);
            copyToClipboard();
            String message = "You're not on the hwid list. " + "\n" + "HWID: " + PathManager.getMainPATH() + "\n(Copied to clipboard)" + "\n" + "DM Tux your HWID (Tuxiscool#6456)";
            JOptionPane.showMessageDialog(this, message, "=> Nice, T3xH3ck Prevention sysem running :^)", JOptionPane.PLAIN_MESSAGE, UIManager.getIcon("OptionPane.errorIcon"));
        }

        public static void copyToClipboard() {
            StringSelection selection = new StringSelection(PathManager.getMainPATH());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }
    }
}