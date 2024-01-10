package me.ballmc.ChatHeads.mixins;

import com.google.common.collect.Lists;
import java.util.List;
import me.ballmc.ChatHeads.Main;
import me.ballmc.ChatHeads.Main.ChatLineHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
//import unique
import org.spongepowered.asm.mixin.Unique;

@Mixin(GuiNewChat.class)
public class DrawStringMixin {

  @Unique
  private ChatLine chatting$drawingLine;

  @Unique
  private static int l1_var;

  @Unique
  private static float y_var;

  @Inject(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ChatLine;getChatComponent()Lnet/minecraft/util/IChatComponent;"), locals = LocalCapture.CAPTURE_FAILSOFT)
  private void captureChatLine(int updateCounter, CallbackInfo ci, int i, boolean bl, int j, int k, float f, float g, int l, int m, ChatLine chatLine, int n, double d, int o, int p, int q) {
    // check if chatLine is nunll
    if (chatLine == null) {
      System.out.println("Chatline is null");
    } else {
      // System.out.println("Chatline text: " + chatLine.getChatComponent().getFormattedText());
        chatting$drawingLine = chatLine;
    }
  }

  @ModifyVariable(method = "drawChat", at = @At(value = "STORE"), require = 1, ordinal = 7)
  private int setl1(int l1) {
      // System.out.println("set l1: " + l1);
      l1_var = l1;
      return l1;
  }

  @ModifyVariable(method = "drawChat", at = @At(value = "STORE"), require = 1, ordinal = 8)
    private int setxrender(int x) {
      if (Main.enabled == false) {
        return x;
      }
        float temp = (int)x;
        ChatLineHook hook = (ChatLineHook) chatting$drawingLine;
        if (hook.chatting$hasDetected()) {
            temp += 10f;
            x = (int)temp;
        }
        NetworkPlayerInfo networkPlayerInfo = hook.chatting$getPlayerInfo();
        if (networkPlayerInfo != null) {
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
            Minecraft.getMinecraft().getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 16777215 + (l1_var << 24) / 255f);
            Gui.drawScaledCustomSizeModalRect((int) x, (int) (y_var - 1f), 8.0f, 8.0f, 8, 8, 8, 8, 64.0f, 64.0f);
            Gui.drawScaledCustomSizeModalRect((int) x, (int) (y_var - 1f), 40.0f, 8.0f, 8, 8, 8, 8, 64.0f, 64.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
        return x;
    }

    @ModifyVariable(method = "drawChat", at = @At(value = "STORE"), require = 1, ordinal = 9)
    private int setj2andY(int j2) {
        // System.out.println("set j2: " + j2);
        float y = (float)(j2 - 8);
        y_var = y;
        return j2;
    }
}
