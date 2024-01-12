package me.ballmc.ChatHeads;

import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.command.CommandBus;
import me.ballmc.ChatHeads.command.ChatHeadsToggle;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.network.NetworkPlayerInfo;

import java.lang.ref.WeakReference;
import java.util.HashSet;

public class Main implements ModInitializer {
    public static boolean enabled = true;

    public static void setEnabled(boolean value) {
        enabled = value;
        EnumChatFormatting statusColor = enabled ? EnumChatFormatting.GREEN : EnumChatFormatting.RED;
        String message = EnumChatFormatting.BLUE + "ChatHeads" + EnumChatFormatting.RESET +
                " has been " + statusColor + (enabled ? "enabled" : "disabled") + EnumChatFormatting.RESET + ".";
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }

    public interface ChatLineHook {
        HashSet<WeakReference<ChatLine>> chatting$chatLines = new HashSet<>();
        boolean chatting$hasDetected();
        NetworkPlayerInfo chatting$getPlayerInfo();
        void chatting$updatePlayerInfo();
        long chatting$getUniqueId();
    }

    @Override
    public void preInit() {
        System.out.println("Initializing ChatHeads!");
        CommandBus.register(new ChatHeadsToggle());
    }
}