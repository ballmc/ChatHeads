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

  // @Shadow
  // public abstract int getLineCount();

  // @Shadow
  // @Final
  // private Minecraft mc;

  // @Final
  // @Shadow
  // private final List<ChatLine> drawnChatLines = Lists.newArrayList();

  // @Shadow
  // public abstract boolean getChatOpen();

  // @Shadow
  // public abstract float getChatScale();

  // @Shadow
  // public abstract int getChatWidth();

  // @Shadow
  // private int scrollPos;

  // @Shadow
  // private boolean isScrolled;

  // @Overwrite
  // public void drawChat(int updateCounter) {
  //   if (
  //     this.mc.gameSettings.chatVisibility !=
  //     EntityPlayer.EnumChatVisibility.HIDDEN
  //   ) {
  //     int i = this.getLineCount();
  //     boolean flag = false;
  //     int j = 0;
  //     int k = this.drawnChatLines.size();
  //     float f = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;

  //     if (k > 0) {
  //       if (this.getChatOpen()) {
  //         flag = true;
  //       }

  //       float f1 = this.getChatScale();
  //       int l = MathHelper.ceiling_float_int((float) this.getChatWidth() / f1);
  //       GlStateManager.pushMatrix();
  //       GlStateManager.translate(2.0F, 20.0F, 0.0F);
  //       GlStateManager.scale(f1, f1, 1.0F);

  //       for (
  //         int i1 = 0;
  //         i1 + this.scrollPos < this.drawnChatLines.size() && i1 < i;
  //         ++i1
  //       ) {
  //         ChatLine chatline = (ChatLine) this.drawnChatLines.get(
  //             i1 + this.scrollPos
  //           );

  //         if (chatline != null) {
  //           int j1 = updateCounter - chatline.getUpdatedCounter();

  //           if (j1 < 200 || flag) {
  //             double d0 = (double) j1 / 200.0D;
  //             d0 = 1.0D - d0;
  //             d0 = d0 * 10.0D;
  //             d0 = MathHelper.clamp_double(d0, 0.0D, 1.0D);
  //             d0 = d0 * d0;
  //             int l1 = (int) (255.0D * d0);

  //             if (flag) {
  //               l1 = 255;
  //             }

  //             l1 = (int) ((float) l1 * f);
  //             ++j;

  //             if (l1 > 3) {
  //               int i2 = 0;
  //               int j2 = -i1 * 9;
  //               Gui.drawRect(i2, j2 - 9, i2 + l + 4, j2, l1 / 2 << 24);
  //               String s = chatline.getChatComponent().getFormattedText();
  //               GlStateManager.enableBlend();
  //               // System.out.println("WE ARE IN DRAWCHAT");

  //               if (Main.enabled) {
  //                 float x = (float) i2;
  //                 float y = (float) (j2 - 8);
  //                 String text = s;
  //                 int color = 16777215 + (l1 << 24);

  //                 // Include the logic from redirectDrawString here
  //                 float actualX = x;
  //                 ChatLineHook hook = (ChatLineHook) chatline;
  //                 if (hook.chatting$hasDetected()) {
  //                   System.out.println("Chatting has been detected.");
  //                   actualX += 10f;
  //                 }
  //                 NetworkPlayerInfo networkPlayerInfo = hook.chatting$getPlayerInfo();
  //                 if (networkPlayerInfo != null) {
  //                   System.out.println("NetworkPlayerInfo is not null.");
  //                   GlStateManager.enableBlend();
  //                   GlStateManager.enableAlpha();
  //                   GlStateManager.enableTexture2D();
  //                   Minecraft
  //                     .getMinecraft()
  //                     .getTextureManager()
  //                     .bindTexture(networkPlayerInfo.getLocationSkin());
  //                   GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
  //                   GlStateManager.color(1.0f, 1.0f, 1.0f, color / 255f);
  //                   Gui.drawScaledCustomSizeModalRect(
  //                     (int) x,
  //                     (int) (y - 1f),
  //                     8.0f,
  //                     8.0f,
  //                     8,
  //                     8,
  //                     8,
  //                     8,
  //                     64.0f,
  //                     64.0f
  //                   );
  //                   Gui.drawScaledCustomSizeModalRect(
  //                     (int) x,
  //                     (int) (y - 1f),
  //                     40.0f,
  //                     8.0f,
  //                     8,
  //                     8,
  //                     8,
  //                     8,
  //                     64.0f,
  //                     64.0f
  //                   );
  //                   GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
  //                 }
  //                 Minecraft.getMinecraft().fontRendererObj.drawString(text, actualX, y, color, true);
  //               }

  //               // end of overwrite logic
  //               this.mc.fontRendererObj.drawStringWithShadow(
  //                   s,
  //                   (float) i2,
  //                   (float) (j2 - 8),
  //                   16777215 + (l1 << 24)
  //                 );
  //               GlStateManager.disableAlpha();
  //               GlStateManager.disableBlend();
  //             }
  //           }
  //         }
  //       }

  //       if (flag) {
  //         int k2 = this.mc.fontRendererObj.FONT_HEIGHT;
  //         GlStateManager.translate(-3.0F, 0.0F, 0.0F);
  //         int l2 = k * k2 + k;
  //         int i3 = j * k2 + j;
  //         int j3 = this.scrollPos * i3 / k;
  //         int k1 = i3 * i3 / l2;

  //         if (l2 != i3) {
  //           int k3 = j3 > 0 ? 170 : 96;
  //           int l3 = this.isScrolled ? 13382451 : 3355562;
  //           Gui.drawRect(0, -j3, 2, -j3 - k1, l3 + (k3 << 24));
  //           Gui.drawRect(2, -j3, 1, -j3 - k1, 13421772 + (k3 << 24));
  //         }
  //       }

  //       GlStateManager.popMatrix();
  //     }
  //   }
  // }

  // @Inject(method = "drawChat", at = @At(value = "INVOKE"), cancellable = true)
  // private void drawChat(int updateCounter, CallbackInfo ci) {
  //   if (Main.enabled) {
  //     ci.cancel();
  //     System.out.println("custom draw chat ENABLED, ci cancelled");
  //   } else {
  //     System.out.println("custom draw chat disabled");
  //   }
  // }
  
  @Unique
  private static ChatLine chatting$drawingLine;

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

  // @Redirect(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
  // private int redirectDrawString(FontRenderer instance, String text, float x, float y, int color) {
  //     System.out.println("In drawStringWithShadow");
  //     return Main.redirectDrawString(text, x, y, color, Main.chatting$drawingLine);
  // }

  // @Inject(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
  // private int redirectDrawString(FontRenderer instance, String text, float x, float y, int color) {
  //     System.out.println("Before cancel");
  //     // ci.cancel();
  //     // System.out.println("After cancel");
  //     return Main.redirectDrawString(text, x, y, color, Main.chatting$drawingLine);
  // }
  
  // @ModifyVariable(method = "drawChat", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"), index = 1)
  //   private float redirectDrawString(float x) {
  //       System.out.println("In drawStringWithShadow: " + x);
  //       x += 10f;
  //       return (float)x;
  //       // return Main.redirectDrawString(text, x, y, color, Main.chatting$drawingLine);
  //   }

  // @ModifyVariable(method = "drawChat", at = @At(value = "STORE"), require = 1, ordinal = 9)
  //   private int redirectDrawString(int x) {
  //       float temp = (int)x;
        
  //       ChatLineHook hook = (ChatLineHook) Main.chatting$drawingLine;
  //       if (hook.chatting$hasDetected()) {
  //           temp += 10f;
  //           x = (int)temp;
  //       }
  //       NetworkPlayerInfo networkPlayerInfo = hook.chatting$getPlayerInfo();
  //       if (networkPlayerInfo != null) {
  //           GlStateManager.enableBlend();
  //           GlStateManager.enableAlpha();
  //           GlStateManager.enableTexture2D();
  //           Minecraft.getMinecraft().getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
  //           GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
  //           GlStateManager.color(1.0f, 1.0f, 1.0f, Main.color / 255f);
  //           Gui.drawScaledCustomSizeModalRect((int) x, (int) (Main.y - 1f), 8.0f, 8.0f, 8, 8, 8, 8, 64.0f, 64.0f);
  //           Gui.drawScaledCustomSizeModalRect((int) x, (int) (Main.y - 1f), 40.0f, 8.0f, 8, 8, 8, 8, 64.0f, 64.0f);
  //           GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
  //       }
  //       return x;
  //   }
  

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
