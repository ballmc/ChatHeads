package me.ballmc.ChatHeads.hooks;

import net.minecraft.client.gui.ChatLine;
import net.weavemc.loader.api.Hook;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class GuiNewChat_TextRenderingHook extends Hook {
    private static ChatLine capturedChatLine;

    public GuiNewChat_TextRenderingHook() {
        super("net.minecraft.client.gui.GuiNewChat");
    }

    @Override
    public void transform(@NotNull ClassNode classNode, @NotNull AssemblerConfig assemblerConfig) {
        MethodNode drawChatMethod = classNode.methods.stream()
                .filter(m -> m.name.equals("drawChat"))
                .findFirst().orElseThrow();

        InsnList instructions = drawChatMethod.instructions;
        AbstractInsnNode targetNode = findTargetNode(instructions);

        if (targetNode != null) {
            instructions.insertBefore(targetNode, createHookInsnNode());
        }
    }

    private AbstractInsnNode findTargetNode(InsnList instructions) {
        for (AbstractInsnNode insnNode : instructions) {
            if (insnNode.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                MethodInsnNode methodInsnNode = (MethodInsnNode) insnNode;
                if (methodInsnNode.owner.equals("net/minecraft/client/gui/ChatLine")
                        && methodInsnNode.name.equals("getChatComponent")
                        && methodInsnNode.desc.equals("()Lnet/minecraft/util/IChatComponent;")) {
                        System.out.println("Found target node:");
                        System.out.println("Opcode: " + insnNode.getOpcode());
                        System.out.println("Type: " + insnNode.getType());
                        System.out.println("Previous: " + insnNode.getPrevious());
                        System.out.println("Next: " + insnNode.getNext());

                    return insnNode; // Move back to load the ChatLine instance
                }
            }
        }
        return null;
    }
    private MethodInsnNode createHookInsnNode() {
        InsnList insnList = new InsnList();
        insnList.add(new VarInsnNode(Opcodes.ASTORE, 1)); // Store the reference to ChatLine in local variable 1
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));  // Load the stored reference back onto the stack
        insnList.add(new MethodInsnNode(
                Opcodes.GETSTATIC,
                Type.getInternalName(GuiNewChat_TextRenderingHook.class),
                "captureChatLine",
                "(Lnet/minecraft/client/gui/ChatLine;)V"
        ));
        return (MethodInsnNode) insnList.get(insnList.size() - 1); // Get the last instruction as MethodInsnNode
    }

    @SuppressWarnings("unused")
    public static void captureChatLine(ChatLine chatLine) {
        // Your hook logic here
        if (chatLine == null) {
            System.out.println("chatLine is null");
        } else {
            System.out.println("in capture");
            capturedChatLine = chatLine;
        }
    }

    public static ChatLine getCapturedChatLine() {
        return capturedChatLine;
    }
}
