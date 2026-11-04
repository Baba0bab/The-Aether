package com.aetherteam.aether.data.resources.registries;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.data.resources.builders.AetherBiomeBuilders;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ARGB;
import net.minecraft.world.attribute.BedRule;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.timeline.Timeline;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.Optional;

public class AetherDimensions {
    private final static Identifier AETHER_LEVEL_ID = Identifier.fromNamespaceAndPath(Aether.MODID, "the_aether");

    // DimensionType - Specifies the logic and settings for a dimension.
    public static final ResourceKey<DimensionType> AETHER_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, AETHER_LEVEL_ID);
    // Level - The dimension during runtime.
    public static final ResourceKey<Level> AETHER_LEVEL = ResourceKey.create(Registries.DIMENSION, AETHER_LEVEL_ID);
    // LevelStem - The dimension during lifecycle start and datagen.
    public static final ResourceKey<LevelStem> AETHER_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, AETHER_LEVEL_ID);
    // WorldClock - The clock driving the dimension's day/night cycle.
    public static final ResourceKey<WorldClock> AETHER_CLOCK = ResourceKey.create(Registries.WORLD_CLOCK, AETHER_LEVEL_ID);

    public static void bootstrapWorldClock(BootstrapContext<WorldClock> context) {
        context.register(AETHER_CLOCK, new WorldClock());
    }

    public static void bootstrapDimensionType(BootstrapContext<DimensionType> context) {
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
        HolderGetter<Timeline> timelines = context.lookup(Registries.TIMELINE);
        HolderGetter<WorldClock> clocks = context.lookup(Registries.WORLD_CLOCK);
        EnvironmentAttributeMap attributes = EnvironmentAttributeMap.builder()
                .set(EnvironmentAttributes.AMBIENT_LIGHT_COLOR, ARGB.vector3fFromRGB24(0xff0a0a0a))
                .set(EnvironmentAttributes.CLOUD_COLOR, ARGB.vector4fFromARGB32(ARGB.white(0.8F)))
                .set(EnvironmentAttributes.CLOUD_HEIGHT, 9.5F) // Clouds are rendered beneath the islands.
                .set(EnvironmentAttributes.BED_RULE, BedRule.CAN_SLEEP_WHEN_DARK)
                .set(EnvironmentAttributes.STRAW_BED_RULE, BedRule.DESTROY_ON_LEAVE)
                .set(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, false)
                .set(EnvironmentAttributes.CAN_START_RAID, false)
                .build();
        context.register(AETHER_DIMENSION_TYPE, new DimensionType(
                false, // hasFixedTime
                true, // hasSkyLight
                false, // hasCeiling
                false, // hasEnderDragonFight
                1.0, // coordinateScale
                0, // minY
                256, // height
                256, // logicalHeight
                blocks.getOrThrow(BlockTags.INFINIBURN_OVERWORLD), // infiniburn
                0.0F, // ambientLight
                new DimensionType.MonsterSettings(UniformInt.of(0, 7), 0), // monsterSettings
                DimensionType.Skybox.OVERWORLD, // skybox
                CardinalLighting.Type.DEFAULT, // cardinalLightType
                attributes, // attributes
                timelines.getOrThrow(AetherTags.Timelines.IN_AETHER), // timelines
                Optional.of(clocks.getOrThrow(AETHER_CLOCK)))); // defaultClock
    }

    public static void bootstrapLevelStem(BootstrapContext<LevelStem> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        BiomeSource source = AetherBiomeBuilders.buildAetherBiomeSource(biomes);
        NoiseBasedChunkGenerator aetherChunkGen = new NoiseBasedChunkGenerator(source, noiseSettings.getOrThrow(AetherNoiseSettings.SKYLANDS));
        context.register(AETHER_LEVEL_STEM, new LevelStem(dimensionTypes.getOrThrow(AetherDimensions.AETHER_DIMENSION_TYPE), aetherChunkGen));
    }
}
