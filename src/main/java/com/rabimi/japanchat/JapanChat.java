package com.rabimi.japanchat;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.gui.screen.ChatScreen;
import org.lwjgl.glfw.GLFW;

public class JapanChat implements ClientModInitializer {
    private static KeyBinding f10Key;

    @Override
    public void onInitializeClient() {
        key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.japanchat.open", 
            InputUtil.Type.KEYSYM, 
            GLFW.GLFW_KEY_F10, 
            "category.japanchat"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (key.wasPressed()) {
                if (client.currentScreen instanceof ChatScreen) {
                    InputWindow.open((text) -> {
                        client.execute(() -> {
                            if (client.player != null && !text.isEmpty()) {
                                client.player.networkHandler.sendChatMessage(text);
                            }
                            client.setScreen(null);
                        });
                    });
                }
            }
        });
    }
}