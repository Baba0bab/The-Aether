package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.AetherConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    /**
     * Sends the player straight back to the title screen when disconnecting from a server if the server button is enabled.
     *
     * @param screen The original {@link Screen} to open after disconnecting.
     * @return The modified {@link Screen}.
     */
    @ModifyArg(method = "disconnectFromWorld(Lnet/minecraft/network/chat/Component;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V"))
    private Screen disconnectFromWorld(Screen screen) {
        if (screen instanceof JoinMultiplayerScreen && AetherConfig.CLIENT.enable_server_button.get()) {
            return new TitleScreen();
        }
        return screen;
    }
}
