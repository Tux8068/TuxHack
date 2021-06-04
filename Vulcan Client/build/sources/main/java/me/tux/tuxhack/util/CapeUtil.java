package me.tux.tuxhack.util;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

public class CapeUtil
{
    List<UUID> uuids;

    public CapeUtil() {
        this.uuids = new ArrayList<UUID>();
        try {
            final URL pastebin = new URL("https://pastebin.com/raw/Z8rufv3Y");
            final BufferedReader in = new BufferedReader(new InputStreamReader(pastebin.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                this.uuids.add(UUID.fromString(inputLine));
            }
        }
        catch (Exception ex) {}
    }

    public boolean hasCape(final UUID id) {
        return this.uuids.contains(id);
    }
}