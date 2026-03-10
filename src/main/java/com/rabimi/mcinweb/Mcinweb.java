package com.rabimi.mcinweb;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
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
        LOGGER.info("MCInWeb initializing on client...");

        // 1.21.1で最も安全なキーバインド登録方法
        openBrowserKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcinweb.open",             // 翻訳キー
                InputUtil.Type.KEYSYM,          // 入力タイプ
                GLFW.GLFW_KEY_B,                // デフォルトキー
                "category.mcinweb"              // カテゴリー (Stringで通らない場合は下の補足参照)
        ));

        // もし上のコードでまた Category エラーが出る場合は、最後の引数を以下に書き換えてください：
        // KeyBinding.MISC_CATEGORY

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openBrowserKey.wasPressed()) {
                LOGGER.info("Browser key pressed!");
            }
        });
    }
}
