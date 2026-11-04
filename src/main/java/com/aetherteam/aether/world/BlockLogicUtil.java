package com.aetherteam.aether.world;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.densityfunction.DensitySamplerSet;
import net.minecraft.world.level.levelgen.densityfunction.SamplerContext;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.Optional;

public final class BlockLogicUtil {
    /**
     * Find the entry point for a tunnel piece to a room with an odd-numbered width
     *
     * @param box       - The room to tunnel from
     * @param direction - The direction to tunnel in
     * @param width     - The width of the tunnel to build
     */
    public static BlockPos tunnelFromOddSquareRoom(BoundingBox box, Direction direction, int width) {
        int offsetFromCenter = (direction.getAxis() == Direction.Axis.X ? box.getZSpan() : box.getXSpan()) >> 1;
        int sidedOffset = (width >> 1);

        int xOffset = direction.getStepX() * offsetFromCenter - direction.getStepZ() * sidedOffset - Math.max(0, direction.getStepX());
        int zOffset = direction.getStepZ() * offsetFromCenter + direction.getStepX() * sidedOffset - Math.max(0, direction.getStepZ());

        return box.getCenter().offset(
                xOffset,
                -(box.getYSpan() >> 1),
                zOffset
        );
    }

    /**
     * Find the entry point for a tunnel piece to a room with an even-numbered width
     *
     * @param box       - The room to tunnel from
     * @param direction - The direction to tunnel in
     * @param width     - The width of the tunnel to build
     */
    public static BlockPos tunnelFromEvenSquareRoom(BoundingBox box, Direction direction, int width) {
        int offsetFromCenter = (((direction.getAxis() == Direction.Axis.X ? box.getZSpan() : box.getXSpan()) + 1) >> 1);
        int sidedOffset = (width >> 1);

        int xOffset = direction.getStepX() * offsetFromCenter - direction.getStepZ() * sidedOffset - Math.max(0, direction.getStepX()) + Math.min(0, direction.getStepZ());
        int zOffset = direction.getStepZ() * offsetFromCenter + direction.getStepX() * sidedOffset - Math.max(0, direction.getStepZ()) - Math.max(0, direction.getStepX());

        return box.getCenter().offset(
                xOffset,
                -(box.getYSpan() >> 1),
                zOffset
        );
    }

    /**
     * Checks if a position for block placement is within a center chunk.
     *
     * @param pos         The {@link BlockPos}.
     * @param centerChunk The center {@link ChunkPos}.
     * @return Whether the position is in bounds, as a {@link Boolean}.
     */
    public static boolean isOutOfBounds(BlockPos pos, ChunkPos centerChunk) {
        int x = SectionPos.blockToSectionCoord(pos.getX());
        int z = SectionPos.blockToSectionCoord(pos.getZ());
        int xDistance = Math.abs(x - centerChunk.x());
        int zDistance = Math.abs(z - centerChunk.z());
        return xDistance > 1 || zDistance > 1;
    }

    /**
     * Determines the top surface block at a position according to the material rules of the level's noise generator settings.
     * This is used to blend generated features and structures with the surrounding biome surface.
     *
     * @param level The {@link WorldGenLevel}.
     * @param pos   The {@link BlockPos} to check.
     * @return An {@link Optional} containing the top {@link BlockState}, if any could be determined.
     */
    @SuppressWarnings("deprecation")
    public static Optional<BlockState> getTopMaterial(WorldGenLevel level, BlockPos pos) {
        if (level.getChunkSource() instanceof ServerChunkCache serverChunkCache && serverChunkCache.getGenerator() instanceof NoiseBasedChunkGenerator generator) {
            RandomState randomState = serverChunkCache.randomState();
            ChunkAccess chunk = level.getChunk(pos);
            DensitySamplerSet samplers = randomState.samplersWithContext(SamplerContext.builder().build());
            WorldGenerationContext context = new WorldGenerationContext(generator, chunk.getHeightAccessorForGeneration());
            MaterialRule materialRule = generator.generatorSettings().value().materialRule().value();
            return randomState.surfaceSystem().topMaterial(materialRule, randomState, context, level.getBiomeManager()::getNoiseBiomeAtPosition, chunk, samplers, pos, false);
        }
        return Optional.empty();
    }
}
