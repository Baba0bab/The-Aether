package com.aetherteam.aether.mixin.mixins.client.aether_fabric;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow
    private void setSpectatedEntityPostEffect(Identifier postEffect) {
        throw new AssertionError();
    }

    @Inject(method = "checkEntityPostEffect", at = @At("TAIL"))
    private void aetherFabric$checkForEffect(Entity entity, CallbackInfo ci) {
        if (entity != null && entity.getType().equals(AetherEntityTypes.SUN_SPIRIT.get())) {
            // Post effects are loaded from assets/<namespace>/post_effect/ in 26.x.
            this.setSpectatedEntityPostEffect(Identifier.fromNamespaceAndPath(Aether.MODID, "sun_spirit"));
        }
    }
}
