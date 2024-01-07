package me.ballmc.ChatHeads.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentText;
import me.ballmc.ChatHeads.Main;
import me.ballmc.ChatHeads.Main.ChatLineHook;

import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import me.ballmc.ChatHeads.Main;

@Debug(export = true)
@Mixin(value = GuiNewChat.class, priority = 50)
public class GuiNewChatMixin_TextRendering {
    @Inject(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ChatLine;getChatComponent()Lnet/minecraft/util/IChatComponent;"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void captureChatLine(int updateCounter, CallbackInfo ci, int i, boolean bl, int j, int k, float f, float g, int l, int m, ChatLine chatLine, int n, double d, int o, int p, int q) {
      System.out.println("Chatline id: " + chatLine.getChatComponent().getFormattedText());
      Main.chatting$drawingLine = chatLine;
    }

    // @Redirect(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    // private void redirectDrawString(String text, float x, float y, int color) {
    //     System.out.println("In drawStringWithShadow");
    //     Main.redirectDrawString(text, x, y, color, Main.chatting$drawingLine);
    // }

    // @WrapOperation(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    // private int wrapfunc(FontRenderer instance, String text, float x, float y, int color, Operation<Integer> original) {
    //   System.out.println("In wrapfunc");
    //   if (Main.enabled) {
    //     float actualX = x;
    //     System.out.println("In redirectDrawString");
    //     ChatLineHook hook = (ChatLineHook) chatting$drawingLine;
    //     if (hook.chatting$hasDetected()) {
    //         actualX += 10f;
    //     }
    //     NetworkPlayerInfo networkPlayerInfo = hook.chatting$getPlayerInfo();
    //     if (networkPlayerInfo != null) {
    //         GlStateManager.enableBlend();
    //         GlStateManager.enableAlpha();
    //         GlStateManager.enableTexture2D();
    //         Minecraft.getMinecraft().getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
    //         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    //         GlStateManager.color(1.0f, 1.0f, 1.0f, color / 255f);
    //         Gui.drawScaledCustomSizeModalRect((int) x, (int) (y - 1f), 8.0f, 8.0f, 8, 8, 8, 8, 64.0f, 64.0f);
    //         Gui.drawScaledCustomSizeModalRect((int) x, (int) (y - 1f), 40.0f, 8.0f, 8, 8, 8, 8, 64.0f, 64.0f);
    //         GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    //     }
    //     return original.call(instance, text, actualX, y, color, true);
    //   } 
    //   else {
    //     return original.call(instance, text, x, y, color);
    //   }
    // }
}