package com.aetherteam.aether.world.processor;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.world.BlockLogicUtil;
import com.aetherteam.nitrogen.fabric.level.ExtendedStructureProcessor;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

/**
 * This processor is used to replace grass blocks in the Aether with blocks determined by the biome's surface rules.
 */
public class SurfaceRuleProcessor implements StructureProcessor {
    public static final SurfaceRuleProcessor INSTANCE = new SurfaceRuleProcessor();

    public static final MapCodec<SurfaceRuleProcessor> CODEC = MapCodec.unit(SurfaceRuleProcessor.INSTANCE);

    /**
     * Warning for "deprecation" is suppressed because using {@link CarvingContext#topMaterial(Function, ChunkAccess, BlockPos, boolean)} is necessary.
     */
    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        if (level instanceof WorldGenLevel worldGenLevel) {
            // If the processor is running outside the center chunk, return immediately.
            if (worldGenLevel instanceof WorldGenRegion region && BlockLogicUtil.isOutOfBounds(modifiedBlockInfo.pos(), region.getCenter())) {
                return modifiedBlockInfo;
            }
            Optional<BlockState> state = BlockLogicUtil.getTopMaterial(worldGenLevel, modifiedBlockInfo.pos());
            if (state.isPresent()) {
                if (modifiedBlockInfo.state().is(AetherTags.Blocks.AETHER_DIRT) && !modifiedBlockInfo.state().is(AetherBlocks.AETHER_DIRT.get()) && state.get().is(AetherTags.Blocks.AETHER_DIRT)) {
                    return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), state.get(), null);
                }
            }
        }
        return modifiedBlockInfo;
    }

    @Override
    public MapCodec<SurfaceRuleProcessor> codec() {
        return CODEC;
    }
}
