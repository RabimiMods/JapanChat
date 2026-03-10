package com.rabimi.mcinweb;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil; // これが必要です
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mcinweb implements ModInitializer {
    public static final String MOD_ID = "mcinweb";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static KeyBinding openBrowserKey;

    @Override
    public void onInitialize() {
        LOGGER.info("MCInWeb initializing...");

        // 引数の型を明示的に指定して、コンパイラの迷いをなくします
        openBrowserKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcinweb.open",             // 翻訳キー (String)
                InputUtil.Type.KEYSYM,          // 入力タイプ (Type)
                GLFW.GLFW_KEY_B,                // キーコード (int)
                "category.mcinweb"              // カテゴリー (String)
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openBrowserKey.wasPressed()) {
                LOGGER.info("Browser key pressed!");
            }
        });
    }
}
