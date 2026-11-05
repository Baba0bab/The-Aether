package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.renderer.AetherRenderStateModifiers;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.TippableArrowRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fills the Aether's extra render state data during render state extraction, replacing NeoForge's render state modifiers.
 */
public final class RenderStateModifierMixins {
    private RenderStateModifierMixins() { }

    @Mixin(AvatarRenderer.class)
    public static class AvatarRendererMixin {
        @Inject(at = @At("TAIL"), method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V")
        private void extractRenderState(Avatar avatar, AvatarRenderState renderState, float partialTicks, CallbackInfo ci) {
            AetherRenderStateModifiers.modifyAvatarRenderState(avatar, renderState);
        }
    }

    @Mixin(ArmorStandRenderer.class)
    public static class ArmorStandRendererMixin {
        @Inject(at = @At("TAIL"), method = "extractRenderState(Lnet/minecraft/world/entity/decoration/ArmorStand;Lnet/minecraft/client/renderer/entity/state/ArmorStandRenderState;F)V")
        private void extractRenderState(ArmorStand armorStand, ArmorStandRenderState renderState, float partialTicks, CallbackInfo ci) {
            AetherRenderStateModifiers.modifyArmorStandRenderState(armorStand, renderState);
        }
    }

    @Mixin(TippableArrowRenderer.class)
    public static class TippableArrowRendererMixin {
        @Inject(at = @At("TAIL"), method = "extractRenderState(Lnet/minecraft/world/entity/projectile/arrow/Arrow;Lnet/minecraft/client/renderer/entity/state/TippableArrowRenderState;F)V")
        private void extractRenderState(Arrow arrow, TippableArrowRenderState renderState, float partialTicks, CallbackInfo ci) {
            AetherRenderStateModifiers.modifyArrowRenderState(arrow, renderState);
        }
    }
}
