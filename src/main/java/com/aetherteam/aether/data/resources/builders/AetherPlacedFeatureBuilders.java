package com.aetherteam.aether.data.resources.builders;

import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import com.aetherteam.aether.world.placementmodifier.DungeonBlacklistFilter;
import com.aetherteam.aether.world.placementmodifier.ImprovedLayerPlacementModifier;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class AetherPlacedFeatureBuilders {
    public static List<PlacementModifier> aercloudPlacement(int above, int range, int chance) {
        return List.of(
                HeightRangePlacement.uniform(VerticalAnchor.absolute(above), VerticalAnchor.absolute(above + range)),
                RarityFilter.onAverageOnceEvery(chance),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                new DungeonBlacklistFilter());
    }

    /**
     * [CODE COPY] - {@link net.minecraft.data.worldgen.placement.VegetationPlacements#treePlacement(PlacementModifier)}
     */
    public static List<PlacementModifier> treePlacement(PlacementModifier count) {
        return treePlacementBase(count).build();
    }

    /**
     * [CODE COPY] - {@link net.minecraft.data.worldgen.placement.VegetationPlacements#treePlacementBase(PlacementModifier)}.<br><br>
     * Add {@link ImprovedLayerPlacementModifier} and {@link DungeonBlacklistFilter}.
     */
    private static ImmutableList.Builder<PlacementModifier> treePlacementBase(PlacementModifier count) {
        return ImmutableList.<PlacementModifier>builder()
                .add(count)
                .add(SurfaceWaterDepthFilter.forMaxDepth(0))
                .add(ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, UniformInt.of(0, 1), 4))
                .add(BiomeFilter.biome())
                .add(new DungeonBlacklistFilter());
    }

    /**
     * Spreads a single block feature into a patch, replacing the behavior of the removed random patch feature.
     *
     * @param tries The {@link Integer} amount of placement attempts.
     * @return A {@link List} of {@link PlacementModifier}s.
     */
    public static List<PlacementModifier> patch(int tries) {
        return List.of(
                CountPlacement.of(tries),
                OffsetPlacement.ofTriangle(7, 3),
                BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE));
    }

    /**
     * [CODE COPY] - {@link net.minecraft.data.worldgen.placement.OrePlacements#commonOrePlacement(int, PlacementModifier)}.
     */
    public static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange) {
        return List.of(CountPlacement.of(count), InSquarePlacement.spread(), heightRange, BiomeFilter.biome());
    }
}
