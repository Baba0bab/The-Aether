package com.aetherteam.aether.mixin.mixins.client.aether_fabric;

import com.aetherteam.aether.client.event.listeners.capability.AetherPlayerClientListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "onButton", at = @At("TAIL"))
    private void aetherFabric$onPostPress(long windowPointer, MouseButtonInfo buttonInfo, int action, CallbackInfo ci) {
        if (windowPointer != this.minecraft.getWindow().handle()) return;

        AetherPlayerClientListener.onClick(buttonInfo.button());
    }
}
