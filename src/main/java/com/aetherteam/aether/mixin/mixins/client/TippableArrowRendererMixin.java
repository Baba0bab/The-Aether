package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.PhoenixArrowAttachment;
import com.aetherteam.aether.client.renderer.AetherRenderStateModifiers;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.client.renderer.entity.state.TippableArrowRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TippableArrowRenderer.class)
public class TippableArrowRendererMixin {
    @Unique
    private static final Identifier FLAMING_ARROW_LOCATION = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/entity/projectile/flaming_arrow.png");

    /**
     * Changes the texture for any arrows if they are marked as Phoenix Arrows by {@link PhoenixArrowAttachment}.
     *
     * @param original The original {@link Identifier} of the texture returned by the target method.
     * @param renderState The {@link TippableArrowRenderState} entity render state.
     * @return The {@link Identifier} for the texture,
     * either what it was before or the Phoenix Arrow one in the case that it is a Phoenix Arrow.
     */
    @ModifyReturnValue(at = @At("RETURN"), method = "getTextureLocation(Lnet/minecraft/client/renderer/entity/state/TippableArrowRenderState;)Lnet/minecraft/resources/Identifier;")
    private Identifier getTextureLocation(Identifier original, @Local(ordinal = 0, argsOnly = true) TippableArrowRenderState renderState) {
        if (renderState.getDataOrDefault(AetherRenderStateModifiers.IS_PHOENIX_ARROW, false) && !renderState.isTipped)
            return FLAMING_ARROW_LOCATION;
        return original;
    }
}
