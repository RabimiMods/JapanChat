package com.rabimi.japanchat.mixin;

import com.rabimi.japanchat.FcitxController;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    
    @Inject(method = "init", at = @At("RETURN"))
    private void onChatOpen(CallbackInfo ci) {
        FcitxController.setFcitxState(true);
    }

    @Inject(method = "removed", at = @At("HEAD"))
    private void onChatClose(CallbackInfo ci) {
        FcitxController.setFcitxState(false);
    }
}