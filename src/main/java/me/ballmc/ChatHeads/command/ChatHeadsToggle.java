package me.ballmc.ChatHeads.command;

import net.weavemc.loader.api.command.Command;
import net.minecraft.client.Minecraft;
import static me.ballmc.ChatHeads.Main.*;

public class ChatHeadsToggle extends Command {
    public ChatHeadsToggle() {
        super("chatheads");
    }

    @Override
    public void handle(String[] args) {
        setEnabled(!enabled);
    }
}
