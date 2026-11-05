package com.aetherteam.aether.client.event.listeners.abilities;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.nitrogen.fabric.client.events.PlayerRenderEvents;
import com.aetherteam.nitrogen.fabric.events.CancellableCallback;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import com.aetherteam.aether.client.renderer.AetherRenderStateModifiers;

public class AccessoryAbilityClientListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen() {
        PlayerRenderEvents.BEFORE_RENDER.register((renderer, state, poseStack, collector, cameraState, callback) -> onRenderPlayer(state, callback));
        PlayerRenderEvents.BEFORE_ARM_RENDER.register((renderer, player, poseStack, collector, packedLight, arm, callback) -> onRenderHand(player, callback));
    }

    /**
     * Disables the player's rendering completely if wearing an Invisibility Cloak.
     */
    public static void onRenderPlayer(AvatarRenderState state, CancellableCallback callback) {
        if (!callback.isCanceled()) {
            if (state.getDataOrDefault(AetherRenderStateModifiers.HAS_INVISIBILITY_CLOAK, false)) {
                callback.setCanceled(true);
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
