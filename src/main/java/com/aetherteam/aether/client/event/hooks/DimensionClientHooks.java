package com.aetherteam.aether.client.event.hooks;

import com.aetherteam.aether.world.LevelUtil;
import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.environment.AtmosphericFogEnvironment;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.util.Mth;
import net.minecraft.world.attribute.EnvironmentAttributes;
import org.joml.Vector3fc;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.Nullable;

public class DimensionClientHooks {
    /**
     * @param level The {@link ClientLevel}.
     * @return Whether the level uses the Aether's sky and fog behavior, as a {@link Boolean}.
     */
    public static boolean hasAetherSky(@Nullable ClientLevel level) {
        return level != null && LevelUtil.isAetherDimensionType(level);
    }

    /**
     * Halves the far fog distance in the Aether to add to the dimension's ambience, similar to beta.
     *
     * @param camera      The {@link Camera} for rendering the fog.
     * @param environment The {@link FogEnvironment} for the fog.
     * @param fogType     The {@link FogType} of the camera.
     * @param farDistance The far distance for the fog, as a {@link Float}.
     * @return The new near distance for the fog, as a {@link Float}.
     * @see com.aetherteam.aether.client.event.listeners.DimensionClientListener#onRenderFog(ViewportEvent.RenderFog)
     */
    @Nullable
    public static Float renderNearFog(Camera camera, @Nullable FogEnvironment environment, FogType fogType, float farDistance) {
        if (camera.entity() != null && camera.entity().level() instanceof ClientLevel clientLevel && hasAetherSky(clientLevel)) {
            if (environment instanceof AtmosphericFogEnvironment && fogType == FogType.ATMOSPHERIC) {
                return farDistance / 2.0F;
            }
        }
        return null;
    }

    /**
     * Increases the view distance while in lava when wearing Phoenix Armor.
     *
     * @param camera       The {@link Camera} for rendering the fog.
     * @param nearDistance The near distance for the fog, as a {@link Float}.
     * @return The new near distance for the fog, as a {@link Float}.
     * @see com.aetherteam.aether.client.event.listeners.DimensionClientListener#onRenderFog(ViewportEvent.RenderFog)
     */
    @Nullable
    public static Float reduceLavaFog(Camera camera, float nearDistance) {
        if (camera.entity() instanceof LivingEntity livingEntity && EquipmentUtil.hasFullPhoenixSet(livingEntity)) {
            if (camera.getFluidInCamera() == FogType.LAVA) {
                return Math.max(nearDistance, 0.25F) * 5.0F;
            }
        }
        return null;
    }

    /**
     * Prevents the sky fog from turning black near the void in the Aether, by reversing the darkening applied by {@link net.minecraft.client.renderer.fog.FogRenderer}.
     *
     * @param camera The {@link Camera} for rendering the fog.
     * @param red    The red value of the fog color, as a {@link Float}.
     * @param green  The green value of the fog color, as a {@link Float}.
     * @param blue   The blue value of the fog color, as a {@link Float}.
     * @return A {@link Triple} of {@link Float}s, containing the RGB values for the fog color.
     * @see com.aetherteam.aether.client.event.listeners.DimensionClientListener#onRenderFogColor(ViewportEvent.ComputeFogColor)
     */
    @Nullable
    public static Triple<Float, Float, Float> renderFogColors(Camera camera, float red, float green, float blue) {
        if (camera.entity() != null && camera.entity().level() instanceof ClientLevel clientLevel && hasAetherSky(clientLevel)) {
            FogType fluidState = camera.getFluidInCamera();
            if (fluidState != FogType.LAVA && fluidState != FogType.POWDER_SNOW) {
                float onsetRange = clientLevel.getLevelData().voidDarknessOnsetRange();
                float darkness = Mth.clamp((onsetRange + clientLevel.getMinY() - (float) camera.position().y()) / onsetRange, 0.0F, 1.0F);
                float brightness = Mth.square(1.0F - darkness);
                if (darkness > 0.0F && brightness > 0.0F) {
                    return Triple.of(red / brightness, green / brightness, blue / brightness);
                }
            }
        }
        return null;
    }

    /**
     * Makes the sky fog less dark in the Aether during weather.
     *
     * @param camera The {@link Camera} for rendering the fog.
     * @param red    The red value of the fog color, as a {@link Float}.
     * @param green  The green value of the fog color, as a {@link Float}.
     * @param blue   The blue value of the fog color, as a {@link Float}.
     * @return A {@link Triple} of {@link Float}s, containing the RGB values for the fog color.
     * @see com.aetherteam.aether.client.event.listeners.DimensionClientListener#onRenderFogColor(ViewportEvent.ComputeFogColor)
     */
    @Nullable
    public static Triple<Float, Float, Float> adjustWeatherFogColors(Camera camera, float red, float green, float blue) {
        if (camera.entity() != null && camera.entity().level() instanceof ClientLevel clientLevel && hasAetherSky(clientLevel)) {
            if (camera.getFluidInCamera() == FogType.ATMOSPHERIC) {
                Vector3fc defaultFog = clientLevel.environmentAttributes().getValue(EnvironmentAttributes.FOG_COLOR, camera.position());
                float rainLevel = clientLevel.getRainLevel(1.0F);
                float thunderLevel = clientLevel.getThunderLevel(1.0F);
                if (rainLevel > 0.0F) {
                    float f14 = 1.0F + rainLevel * 0.8F;
                    float f17 = 1.0F + rainLevel * 0.56F;
                    red *= f14;
                    green *= f14;
                    blue *= f17;
                }
                if (thunderLevel > 0.0F) {
                    float f18 = 1.0F + thunderLevel * 0.66F;
                    float f19 = 1.0F + thunderLevel * 0.76F;
                    red *= f18;
                    green *= f18;
                    blue *= f19;
                }
                red = Math.min(red, defaultFog.x());
                green = Math.min(green, defaultFog.y());
                blue = Math.min(blue, defaultFog.z());
                return Triple.of(red, green, blue);
            }
        }
        return null;
    }
}
