package com.aetherteam.nitrogen.world.foliageplacer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

/**
 * Places leaves in a spiky "pine tree" pattern.
 */
public class AetherPineFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<AetherPineFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((codec) -> foliagePlacerParts(codec)
            .and(IntProviders.codec(0, 24).fieldOf("trunk_height").forGetter((placer) -> placer.trunkHeight))
            .apply(codec, AetherPineFoliagePlacer::new));
    private final IntProvider trunkHeight;

    public AetherPineFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider height) {
        super(radius, offset);
        this.trunkHeight = height;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return NitrogenFoliagePlacerTypes.AETHER_PINE_FOLIAGE_PLACER.value();
    }

    @Override
    protected void createFoliage(WorldGenLevel level, FoliagePlacer.FoliageSetter setter, RandomSource random, TreeFeature configuration, int maxTreeHeight, FoliagePlacer.FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos blockpos = attachment.pos();
        int i = 0;
        int j = 2;
        int k = 1;

        if (foliageHeight % 2 != 0) {
            j -= 1;
        }

        for (int l = offset; l >= -foliageHeight; --l) {
            this.placeLeavesRow(level, setter, random, configuration, blockpos, i, l, attachment.doubleTrunk());
            if (i >= j) {
                i = k;
                j = Math.min(j + 1, foliageRadius + attachment.radiusOffsetXZ());
            } else {
                ++i;
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeFeature configuration) {
        int value = height - this.trunkHeight.sample(random);
        return Math.max(3, value);
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return localX + localX + localZ + localZ > range + 2;
    }
}
