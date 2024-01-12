package me.ballmc.ChatHeads.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.Unique;

import me.ballmc.ChatHeads.Main;
import me.ballmc.ChatHeads.Main.ChatLineHook;

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
    chatting$drawingLine = chatLine;
  }

  /**
   * Author: guru
   * 
   * Reason: I couldnt get it to work with a redirect since lunar client redirects the same method beforehand and I cannot bypass that with mixin priorities.
   * Even with a callback cancel or overwrite it would remove lunar's prexisting chat settings which would not be nice for the user. 
   * So I just edited the local variables before they got passed to the drawStringWithShadow method instead.
   * The ordinals 7,8,9 are variables l1, i2, j2 respectively.
   */

  @ModifyVariable(method = "drawChat", at = @At(value = "STORE"), require = 1, ordinal = 7)
  private int capturel1(int l1) {
      l1_var = l1;
      return l1;
  }

  @ModifyVariable(method = "drawChat", at = @At(value = "STORE"), require = 1, ordinal = 8)
  private int setXRender(int x) {
      if (!Main.enabled) return x;
      ChatLineHook hook = (ChatLineHook) chatting$drawingLine;
      if (hook == null || !hook.chatting$hasDetected()) return x;

      float temp = x + 10f;
      int temp2 = (int) temp;

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
      return temp2;
  }

    @ModifyVariable(method = "drawChat", at = @At(value = "STORE"), require = 1, ordinal = 9)
    private int capturej2AndSetY(int j2) {
        float y = (float)(j2 - 8);
        y_var = y;
        return j2;
    }
}
