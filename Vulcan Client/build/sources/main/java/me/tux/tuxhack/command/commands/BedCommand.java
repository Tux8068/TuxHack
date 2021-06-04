package me.tux.tuxhack.command.commands;

import me.tux.tuxhack.command.Command;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class BedCommand extends Command {

    @Override
    public String[] getAlias() {
        return new String[]{
                "bed", "bedcoords"
        };
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        try
        {
            PlayerEvent mc = null;
            Command.sendRawMessage(mc.player.getBedLocation().toString());
        }
        catch(Exception e)
        {
            Command.sendRawMessage("Usage: " + getSyntax());
        }
    }
    }
