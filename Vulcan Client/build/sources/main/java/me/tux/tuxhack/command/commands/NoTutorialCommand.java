package me.tux.tuxhack.command.commands;
import me.tux.tuxhack.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.client.tutorial.TutorialSteps;

public class NoTutorialCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{
                "tut", "tutorial"
        };
    }
    String PREFIX;
    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        Minecraft.getMinecraft().gameSettings.tutorialStep = TutorialSteps.NONE;
        Minecraft.getMinecraft().getTutorial().setStep(TutorialSteps.NONE);
        Command.sendRawMessage("Set tutorial step to none!");
    }

}
