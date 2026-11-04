package com.aetherteam.aether.client.event.listeners.abilities;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.nitrogen.fabric.client.events.PlayerRenderEvents;
import com.aetherteam.nitrogen.fabric.events.CancellableCallback;
import com.aetherteam.aether.client.renderer.AetherRenderStateModifiers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class AccessoryAbilityClientListener {
    /**
     * @see AetherClient#eventSetup(IEventBus)
     */
    public static void listen() {
        PlayerRenderEvents.BEFORE_RENDER.register((player, renderer, partialTick, poseStack, multiBufferSource, packedLight, callback) -> onRenderPlayer(player, callback));
        PlayerRenderEvents.BEFORE_ARM_RENDER.register((player, renderer, partialTick, poseStack, multiBufferSource, packedLight, arm, callback) -> onRenderHand(player, callback));
    }

    /**
     * Disables the player's rendering completely if wearing an Invisibility Cloak.
     */
    public static void onRenderPlayer(RenderPlayerEvent.Pre<?> event) {
        AvatarRenderState playerRenderState = event.getRenderState();
        if (!event.isCanceled()) {
            if (playerRenderState.getRenderDataOrDefault(AetherRenderStateModifiers.HAS_INVISIBILITY_CLOAK, false)) {
                event.setCanceled(true);
            }
        }
    }

    /**
     * Disables the player's first-person arm rendering completely if wearing an Invisibility Cloak.
     */
    public static void onRenderHand(Player player, CancellableCallback callback) {
        if (!callback.isCanceled() && player != null) {
            if (player.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).isWearingInvisibilityCloak()) {
                callback.setCanceled(true);
            }
        }
    }
}
