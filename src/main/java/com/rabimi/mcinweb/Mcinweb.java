package com.rabimi.mcinweb;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding; // Minecraft内部ではKeyBinding（マッピングによりKeyMapping）
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mcinweb implements ClientModInitializer {
    public static final String MOD_ID = "mcinweb";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static KeyBinding openBrowserKey;

    @Override
    public void onInitializeClient() {
        LOGGER.info("MCInWeb initializing with Official Mappings...");

        // ドキュメントの例に完全に合わせた記述
        openBrowserKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcinweb.open", 
                InputUtil.Type.KEYSYM, 
                GLFW.GLFW_KEY_B, 
                KeyBinding.Category.MISC // Category列挙型を使用
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openBrowserKey.wasPressed()) {
                if (client != null) {
                    client.setScreen(new WebBrowserScreen());
                }
        });
    }
}
