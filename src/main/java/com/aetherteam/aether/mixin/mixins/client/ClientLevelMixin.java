package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.attribute.EnvironmentAttributeSystem;
import net.minecraft.world.attribute.EnvironmentAttributes;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {
    private static final Vector3fc NEUTRAL_BLOCK_LIGHT_TINT = new Vector3f(1.0F, 1.0F, 1.0F);

    /**
     * Adds the Aether's client-side rendering options as environment attribute layers in the Aether:
     * <ul>
     *     <li>{@link AetherConfig.Client#disable_clouds} hides the clouds.</li>
     *     <li>{@link AetherConfig.Client#colder_lightmap} removes the warm tint from block light.</li>
     *     <li>{@link AetherConfig.Client#green_sunset} recolors the sunrise and sunset.</li>
     * </ul>
     *
     * @param builder The original {@link EnvironmentAttributeSystem.Builder}.
     * @param cir     The {@link EnvironmentAttributeSystem.Builder} {@link CallbackInfoReturnable} used for the method's return value.
     */
    @Inject(at = @At("RETURN"), method = "addEnvironmentAttributeLayers(Lnet/minecraft/world/attribute/EnvironmentAttributeSystem$Builder;)Lnet/minecraft/world/attribute/EnvironmentAttributeSystem$Builder;")
    private void addEnvironmentAttributeLayers(EnvironmentAttributeSystem.Builder builder, CallbackInfoReturnable<EnvironmentAttributeSystem.Builder> cir) {
        ClientLevel level = (ClientLevel) (Object) this;
        if (level.dimension() != AetherDimensions.AETHER_LEVEL) {
            return;
        }
        builder.addTimeBasedLayer(EnvironmentAttributes.CLOUD_COLOR, (color, cacheTickId) -> AetherConfig.CLIENT.disable_clouds.get() ? new Vector4f(color.x(), color.y(), color.z(), 0.0F) : color);
        builder.addTimeBasedLayer(EnvironmentAttributes.BLOCK_LIGHT_TINT, (tint, cacheTickId) -> AetherConfig.CLIENT.colder_lightmap.get() ? NEUTRAL_BLOCK_LIGHT_TINT : tint);
        builder.addTimeBasedLayer(EnvironmentAttributes.SUNRISE_SUNSET_COLOR, (color, cacheTickId) -> AetherConfig.CLIENT.green_sunset.get() ? greenSunset(color) : color);
    }

    /**
     * Converts the Aether's sunrise and sunset color into the green variant.<br><br>
     * The Aether's default sunrise colors have a red channel of {@code progress * 0.3 + 0.65}, so the progress through the sunrise can be recovered from it.
     *
     * @param color The original {@link Vector4fc} color.
     * @return The green {@link Vector4fc} color.
     */
    private static Vector4fc greenSunset(Vector4fc color) {
        if (color.w() <= 0.0F) {
            return color;
        }
        float progress = Math.clamp((color.x() - 0.65F) / 0.3F, 0.0F, 1.0F);
        return new Vector4f(progress * 0.5F, progress * progress * 0.3F + 0.3F, progress * progress * 0.5F + 0.3F, color.w());
    }
}
