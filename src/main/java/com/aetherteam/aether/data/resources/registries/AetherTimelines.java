package com.aetherteam.aether.data.resources.registries;

import com.aetherteam.aether.Aether;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ARGB;
import net.minecraft.util.EasingType;
import net.minecraft.util.Mth;
import net.minecraft.util.TriState;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.attribute.modifier.BooleanModifier;
import net.minecraft.world.attribute.modifier.ColorModifier;
import net.minecraft.world.attribute.modifier.FloatModifier;
import net.minecraft.world.clock.ClockTimeMarkers;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.timeline.Timeline;
import net.minecraft.world.timeline.Timelines;

/**
 * Timelines driven by the Aether's own {@link WorldClock}, so that the sky follows the Aether's day/night cycle instead of the Overworld's.
 */
public class AetherTimelines {
    public static final ResourceKey<Timeline> DAY = createKey("day");
    public static final ResourceKey<Timeline> MOON = createKey("moon");

    /**
     * The ticks at which the sunrise and sunset colors are sampled, matching the vanilla Overworld timeline.
     */
    private static final int[] SUNRISE_KEYFRAMES = {
            71, 310, 565, 730, 11270, 11397, 11522, 11690, 11929, 12243, 12358, 12512, 12613, 12732, 12841, 13035,
            13252, 13775, 13888, 14039, 14192, 21807, 21961, 22112, 22225, 22748, 22965, 23159, 23272, 23488, 23642, 23757
    };

    private static ResourceKey<Timeline> createKey(String name) {
        return ResourceKey.create(Registries.TIMELINE, Identifier.fromNamespaceAndPath(Aether.MODID, name));
    }

    /**
     * [CODE COPY] - {@link Timelines#bootstrap(BootstrapContext)}.<br><br>
     * Modified to use the Aether's clock, and the Aether's warmer sunrise and sunset colors.
     */
    public static void bootstrap(BootstrapContext<Timeline> context) {
        Holder<WorldClock> clock = context.lookup(Registries.WORLD_CLOCK).getOrThrow(AetherDimensions.AETHER_CLOCK);
        EasingType skyAngleEase = EasingType.symmetricCubicBezier(0.362F, 0.241F);
        context.register(DAY, Timeline.builder(clock)
                .setPeriodTicks(24000)
                .addTimeMarker(ClockTimeMarkers.DAY, 1000, true)
                .addTimeMarker(ClockTimeMarkers.NOON, 6000, true)
                .addTimeMarker(ClockTimeMarkers.NIGHT, 13000, true)
                .addTimeMarker(ClockTimeMarkers.MIDNIGHT, 18000, true)
                .addTimeMarker(ClockTimeMarkers.WAKE_UP_FROM_SLEEP, 0)
                .addTrack(EnvironmentAttributes.SUN_ANGLE, track -> track.setEasing(skyAngleEase).addKeyframe(6000, 360.0F).addKeyframe(6000, 0.0F))
                .addTrack(EnvironmentAttributes.MOON_ANGLE, track -> track.setEasing(skyAngleEase).addKeyframe(6000, 540.0F).addKeyframe(6000, 180.0F))
                .addTrack(EnvironmentAttributes.STAR_ANGLE, track -> track.setEasing(skyAngleEase).addKeyframe(6000, 360.0F).addKeyframe(6000, 0.0F))
                .addModifierTrack(EnvironmentAttributes.FIREFLY_BUSH_SOUNDS, BooleanModifier.OR, track -> track.addKeyframe(12600, true).addKeyframe(23401, false))
                .addModifierTrack(EnvironmentAttributes.FOG_COLOR, ColorModifier.MULTIPLY_RGB, track -> track
                        .addKeyframe(133, ARGB.vector3fFromRGB24(-1))
                        .addKeyframe(11867, ARGB.vector3fFromRGB24(-1))
                        .addKeyframe(13670, ARGB.vector3fFromRGB24(Timelines.NIGHT_FOG_COLOR_MULTIPLIER))
                        .addKeyframe(22330, ARGB.vector3fFromRGB24(Timelines.NIGHT_FOG_COLOR_MULTIPLIER)))
                .addModifierTrack(EnvironmentAttributes.SKY_COLOR, ColorModifier.MULTIPLY_RGB, track -> track
                        .addKeyframe(133, ARGB.vector3fFromRGB24(-1))
                        .addKeyframe(11867, ARGB.vector3fFromRGB24(-1))
                        .addKeyframe(13670, ARGB.vector3fFromRGB24(Timelines.NIGHT_SKY_COLOR_MULTIPLIER))
                        .addKeyframe(22330, ARGB.vector3fFromRGB24(Timelines.NIGHT_SKY_COLOR_MULTIPLIER)))
                .addModifierTrack(EnvironmentAttributes.SKY_LIGHT_COLOR, ColorModifier.MULTIPLY_RGB, track -> track
                        .addKeyframe(730, ARGB.vector3fFromRGB24(-1))
                        .addKeyframe(11270, ARGB.vector3fFromRGB24(-1))
                        .addKeyframe(13140, ARGB.vector3fFromRGB24(Timelines.NIGHT_SKY_LIGHT_COLOR))
                        .addKeyframe(22860, ARGB.vector3fFromRGB24(Timelines.NIGHT_SKY_LIGHT_COLOR)))
                .addModifierTrack(EnvironmentAttributes.SKY_LIGHT_FACTOR, FloatModifier.MULTIPLY, track -> track
                        .addKeyframe(730, 1.0F)
                        .addKeyframe(11270, 1.0F)
                        .addKeyframe(13140, Timelines.NIGHT_SKY_LIGHT_FACTOR)
                        .addKeyframe(22860, Timelines.NIGHT_SKY_LIGHT_FACTOR))
                .addModifierTrack(EnvironmentAttributes.SKY_LIGHT_LEVEL, FloatModifier.MULTIPLY, track -> track
                        .addKeyframe(133, 1.0F)
                        .addKeyframe(11867, 1.0F)
                        .addKeyframe(13670, Timelines.NIGHT_SKY_LIGHT_LEVEL / Timelines.DAY_SKY_LIGHT_LEVEL)
                        .addKeyframe(22330, Timelines.NIGHT_SKY_LIGHT_LEVEL / Timelines.DAY_SKY_LIGHT_LEVEL))
                .addTrack(EnvironmentAttributes.SUNRISE_SUNSET_COLOR, track -> {
                    for (int tick : SUNRISE_KEYFRAMES) {
                        track.addKeyframe(tick, ARGB.vector4fFromARGB32(sunriseColor(tick)));
                    }
                })
                .addModifierTrack(EnvironmentAttributes.STAR_BRIGHTNESS, FloatModifier.MAXIMUM, track -> track
                        .addKeyframe(92, 0.037F)
                        .addKeyframe(627, 0.0F)
                        .addKeyframe(11373, 0.0F)
                        .addKeyframe(11732, 0.016F)
                        .addKeyframe(11959, 0.044F)
                        .addKeyframe(12399, 0.143F)
                        .addKeyframe(12729, 0.258F)
                        .addKeyframe(13228, 0.5F)
                        .addKeyframe(22772, 0.5F)
                        .addKeyframe(23032, 0.364F)
                        .addKeyframe(23356, 0.225F)
                        .addKeyframe(23758, 0.101F))
                .addModifierTrack(EnvironmentAttributes.CLOUD_COLOR, ColorModifier.MULTIPLY_ARGB, track -> track
                        .addKeyframe(133, ARGB.vector4fFromARGB32(-1))
                        .addKeyframe(11867, ARGB.vector4fFromARGB32(-1))
                        .addKeyframe(13670, ARGB.vector4fFromARGB32(Timelines.NIGHT_CLOUD_COLOR_MULTIPLIER))
                        .addKeyframe(22330, ARGB.vector4fFromARGB32(Timelines.NIGHT_CLOUD_COLOR_MULTIPLIER)))
                .addTrack(EnvironmentAttributes.EYEBLOSSOM_OPEN, track -> track.addKeyframe(12600, TriState.TRUE).addKeyframe(23401, TriState.FALSE))
                .addModifierTrack(EnvironmentAttributes.CREAKING_ACTIVE, BooleanModifier.OR, track -> track.addKeyframe(12600, true).addKeyframe(23401, false))
                .addModifierTrack(EnvironmentAttributes.TURTLE_EGG_HATCH_CHANCE, FloatModifier.MAXIMUM, track -> track.setEasing(EasingType.CONSTANT).addKeyframe(21062, 1.0F).addKeyframe(21905, 0.002F))
                .addModifierTrack(EnvironmentAttributes.CAT_WAKING_UP_GIFT_CHANCE, FloatModifier.MAXIMUM, track -> track.setEasing(EasingType.CONSTANT).addKeyframe(362, 0.0F).addKeyframe(23667, 0.7F))
                .addModifierTrack(EnvironmentAttributes.BEES_STAY_IN_HIVE, BooleanModifier.OR, track -> track.addKeyframe(12542, true).addKeyframe(23460, false))
                .addModifierTrack(EnvironmentAttributes.MONSTERS_BURN, BooleanModifier.OR, track -> track.addKeyframe(12542, false).addKeyframe(23460, true))
                .build());
        context.register(MOON, Timeline.builder(clock)
                .setPeriodTicks(24000 * MoonPhase.COUNT)
                .addTrack(EnvironmentAttributes.MOON_PHASE, track -> {
                    for (MoonPhase phase : MoonPhase.values()) {
                        track.addKeyframe(phase.startTick(), phase);
                    }
                })
                .addModifierTrack(EnvironmentAttributes.SURFACE_SLIME_SPAWN_CHANCE, FloatModifier.MAXIMUM, track -> {
                    track.setEasing(EasingType.CONSTANT);
                    for (MoonPhase phase : MoonPhase.values()) {
                        track.addKeyframe(phase.startTick(), DimensionType.MOON_BRIGHTNESS_PER_PHASE[phase.index()] * 0.5F);
                    }
                })
                .build());
    }

    /**
     * Samples the Aether's sunrise and sunset coloration, which is warmer than the Overworld's.
     *
     * @param tick The {@link Integer} tick of the day.
     * @return The {@link Integer} ARGB color.
     */
    private static int sunriseColor(int tick) {
        double dayFraction = Mth.frac(tick / 24000.0 - 0.25);
        double smoothed = 0.5 - Math.cos(dayFraction * Math.PI) / 2.0;
        float timeOfDay = (float) (dayFraction * 2.0 + smoothed) / 3.0F;
        float angle = Mth.cos(timeOfDay * Mth.TWO_PI);
        float progress = Mth.clamp(angle / 0.4F * 0.5F + 0.5F, 0.0F, 1.0F);
        float alpha = 0.0F;
        if (angle >= -0.4F && angle <= 0.4F) {
            alpha = 1.0F - (1.0F - Mth.sin(progress * Mth.PI)) * 0.99F;
            alpha *= alpha;
        }
        float red = progress * 0.3F + 0.65F;
        float green = progress * progress * 0.7F + 0.25F;
        float blue = 0.4F;
        return ARGB.colorFromFloat(alpha, red, green, blue);
    }
}
