package com.rabimi.javaskinchanger.mixin;

import com.rabimi.javaskinchanger.SkinChangeScreen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {

    @Inject(
        method = "getSkinTexture",
        at = @At("HEAD"),
        cancellable = true
    )
    private void javaskinchanger$overrideSkin(CallbackInfoReturnable<Identifier> cir) {
        Identifier custom = SkinChangeScreen.getCustomSkin();
        if (custom != null) {
            cir.setReturnValue(custom);
        }
    }
}
