package me.ballmc.ChatHeads;

import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.command.CommandBus;
import me.ballmc.ChatHeads.command.ChatHeadsToggle;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;

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

    public static int redirectDrawString(String text, float x, float y, int color, ChatLine chatLine) {
    float actualX = x;
    if (enabled) {
        System.out.println("In redirectDrawString");
        ChatLineHook hook = (ChatLineHook) chatLine;
        if (hook.chatting$hasDetected()) {
            actualX += 10f;
        }
        NetworkPlayerInfo networkPlayerInfo = hook.chatting$getPlayerInfo();
        if (networkPlayerInfo != null) {
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
            Minecraft.getMinecraft().getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.color(1.0f, 1.0f, 1.0f, color / 255f);
            Gui.drawScaledCustomSizeModalRect(
                (int) x,
                (int) (y - 1f),
                8.0f,
                8.0f,
                8,
                8,
                8,
                8,
                64.0f,
                64.0f
            );
            Gui.drawScaledCustomSizeModalRect(
                (int) x,
                (int) (y - 1f),
                40.0f,
                8.0f,
                8,
                8,
                8,
                8,
                64.0f,
                64.0f
            );
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    return Minecraft.getMinecraft().fontRendererObj.drawString(text, actualX, y, color, true);
}

    @Override
    public void preInit() {
        System.out.println("Initializing ChatHeads!");
        CommandBus.register(new ChatHeadsToggle());
        // EventBus.subscribe(new RenderGameOverlayListener());
    }
}