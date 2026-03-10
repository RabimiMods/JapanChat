package com.rabimi.mcinweb;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
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

        // 1.21.1 対応の3引数コンストラクタ
        // 引数: 翻訳キー, キーコード, カテゴリー翻訳キー
        openBrowserKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcinweb.open", 
                GLFW.GLFW_KEY_B, 
                "category.mcinweb"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openBrowserKey.wasPressed()) {
                LOGGER.info("Browser key pressed!");
                // ここで本来は Screen を開く
            }
        });
    }
}
