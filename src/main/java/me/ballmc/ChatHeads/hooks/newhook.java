package me.ballmc.ChatHeads.hooks;

import net.weavemc.loader.api.Hook;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import me.ballmc.ChatHeads.Main;
import me.ballmc.ChatHeads.Main.ChatLineHook;

public class newhook extends Hook {
    public newhook() {
        super("net/minecraft/client/gui/GuiNewChat");
    }

    @Override
    public void transform(@NotNull ClassNode classNode, @NotNull AssemblerConfig assemblerConfig) {
        // MethodNode drawChatMethod = classNode.methods.stream()
        //         .filter(m -> m.name.equals("drawChat"))
        //         .findFirst().orElseThrow();

        classNode.methods.forEach(m -> System.out.println(m.name));

    }


    // @Override
    // public void transform(@NotNull ClassNode classNode, @NotNull AssemblerConfig assemblerConfig) {
    //     MethodNode drawChatMethod = classNode.methods.stream()
    //             .filter(m -> m.name.equals("drawChat"))
    //             .findFirst().orElseThrow();

    //     InsnList instructions = drawChatMethod.instructions;
    //     AbstractInsnNode targetNode = findTargetNode(instructions);

    //     if (targetNode != null) {
    //         // Find a suitable position to insert instructions
    //         AbstractInsnNode insertPosition = targetNode.getPrevious(); // Or any other suitable position

    //         // Load the reference to Main.chatting$drawingLine onto the stack
    //         instructions.insertBefore(insertPosition, new FieldInsnNode(
    //                 Opcodes.GETSTATIC,
    //                 Type.getInternalName(Main.class),
    //                 "chatting$drawingLine",
    //                 "Lnet/minecraft/client/gui/ChatLine;"
    //         ));

    //         // Insert the MethodInsnNode
    //         instructions.insertBefore(insertPosition, createRedirectInsnNode());
    //     }
    // }

    // private AbstractInsnNode findTargetNode(InsnList instructions) {
    //     for (AbstractInsnNode insnNode : instructions) {
    //         if (insnNode.getOpcode() == Opcodes.INVOKEVIRTUAL) {
    //             MethodInsnNode methodInsnNode = (MethodInsnNode) insnNode;
    //             if (methodInsnNode.owner.equals("net/minecraft/client/gui/FontRenderer")
    //                     && methodInsnNode.name.equals("drawStringWithShadow")
    //                     && methodInsnNode.desc.equals("(Ljava/lang/String;FFI)I")) {
    //                 System.out.println("Target node found");
    //                 return insnNode; // Move back to load the FontRenderer instance
    //             }
    //         }
    //     }
    //     System.out.println("Target node not found");
    //     return null;
    // }

    // private MethodInsnNode createRedirectInsnNode() {
    //     System.out.println("Creating redirect instruction");
    //     return new MethodInsnNode(
    //         Opcodes.INVOKESTATIC,
    //         Type.getInternalName(newhook.class),
    //         "redirectDrawString",
    //         "(Ljava/lang/String;FFI;Lnet/minecraft/client/gui/ChatLine;)I"
    //     );
    // }

    // public static int redirectDrawString(String text, float x, float y, int color, ChatLine chatLine) {
    //     float actualX = x;
    //     System.out.println("In redirectDrawString");
    //     ChatLineHook hook = (ChatLineHook) chatLine;
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
    //     return Minecraft.getMinecraft().fontRendererObj.drawString(text, actualX, y, color, true);
    // }
}
