package com.aetherteam.aether.mixin.mixins.client.aether_fabric;

import com.aetherteam.aether.client.event.listeners.capability.AetherPlayerClientListener;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "keyPress", at = @At("TAIL"))
    private void aetherFabric$onPostKeyPress(long windowPointer, int action, KeyEvent event, CallbackInfo ci) {
        if (windowPointer != this.minecraft.getWindow().handle()) return;

        AetherPlayerClientListener.onPress(event.key());
    }
}
