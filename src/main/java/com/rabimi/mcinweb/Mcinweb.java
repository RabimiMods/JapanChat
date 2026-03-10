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
        LOGGER.info("MCInWeb initializing with Official Mappings...");

        openBrowserKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcinweb.open", 
                InputUtil.Type.KEYSYM, 
                GLFW.GLFW_KEY_B, 
                KeyBinding.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client != null) {
                while (openBrowserKey.wasPressed()) {
                    client.setScreen(new WebBrowserScreen());
                }
            }
        });
    }
}
