package com.rabimi.javaskinchanger.preview;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;

public class PreviewPlayer extends ClientPlayerEntity {
    private Identifier skinId;

    public PreviewPlayer(ClientWorld world, Identifier skin) {
        // 1.21のコンストラクタ引数に対応
        super(MinecraftClient.getInstance(), world, 
              MinecraftClient.getInstance().getNetworkHandler(), 
              MinecraftClient.getInstance().getStats(), 
              MinecraftClient.getInstance().getRecipeBook(), 
              false, false);
        this.skinId = skin;
    }

    @Override
    public Identifier getSkinId() { // メソッド名が getSkinId に変更されています
        return skinId;
    }

    public void setSkin(Identifier id) {
        this.skinId = id;
    }
}
