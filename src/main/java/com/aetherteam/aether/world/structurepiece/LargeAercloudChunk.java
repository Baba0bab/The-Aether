package com.aetherteam.aether.world.structurepiece;

import net.minecraft.core.Holder;
import com.aetherteam.aether.Aether;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

import java.util.HashSet;
import java.util.Set;

/**
 * This piece exists to hold the positions of the Aercloud blocks and place them for the structure.
 */
public class LargeAercloudChunk extends StructurePiece {
    private final Set<BlockPos> positions = new HashSet<>();
    private final Holder<BlockStateProvider> blocks;

    public LargeAercloudChunk(Set<BlockPos> positions, Holder<BlockStateProvider> blocks, BoundingBox bounds, Direction direction) {
        super(AetherStructurePieceTypes.LARGE_AERCLOUD.get(), 0, bounds);
        this.setOrientation(direction);
        this.positions.addAll(positions);
        this.blocks = blocks;
    }

    public LargeAercloudChunk(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.LARGE_AERCLOUD.get(), tag);

        ListTag positions = tag.getListOrEmpty("Positions");
        for (Tag value : positions) {
            if (value instanceof IntArrayTag intArrayTag && intArrayTag.size() == 3) {
                int[] position = intArrayTag.getAsIntArray();
                this.positions.add(new BlockPos(position[0], position[1], position[2]));
            }
        }
        this.blocks = BlockStateProvider.CODEC.parse(context.registryAccess().createSerializationContext(NbtOps.INSTANCE), tag.get("Blocks")).getPartialOrThrow();
    }

    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        ListTag positions = new ListTag();
        for (BlockPos position : this.positions) {
            positions.add(new IntArrayTag(new int[]{position.getX(), position.getY(), position.getZ()}));
        }
        tag.put("Positions", positions);
        BlockStateProvider.CODEC.encodeStart(context.registryAccess().createSerializationContext(NbtOps.INSTANCE), this.blocks).resultOrPartial(Aether.LOGGER::error).ifPresent(value -> tag.put("Blocks", value));
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager manager, ChunkGenerator generator, RandomSource random, BoundingBox bounds, ChunkPos chunkPos, BlockPos blockPos) {
        if (!this.positions.isEmpty()) {
            this.positions.removeIf(pos -> this.placeBlock(level, this.blocks.value().getState(level, random, pos), pos, bounds));
        }
    }

    protected boolean placeBlock(WorldGenLevel level, BlockState state, BlockPos pos, BoundingBox bounds) {
        if (bounds.isInside(pos)) {
            if (this.canBeReplaced(level, pos)) {
                level.setBlock(pos, state, 2);
            }
            return true;
        }
        return false;
    }

    protected boolean canBeReplaced(LevelReader level, BlockPos pos) {
        return level.isEmptyBlock(pos);
    }
}
