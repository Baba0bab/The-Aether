package com.aetherteam.aether.world.processor;

import com.aetherteam.nitrogen.fabric.level.ExtendedStructureProcessor;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

/**
 * This processor sets the {@link AetherBlockStateProperties#DOUBLE_DROPS} property to true for blocks that have it.
 */
public class DoubleDropsProcessor implements ExtendedStructureProcessor {
    public static final DoubleDropsProcessor INSTANCE = new DoubleDropsProcessor();

    public static final MapCodec<DoubleDropsProcessor> CODEC = MapCodec.unit(DoubleDropsProcessor.INSTANCE);

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        if (modifiedBlockInfo.state().hasProperty(AetherBlockStateProperties.DOUBLE_DROPS)) {
            return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), modifiedBlockInfo.state().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true), modifiedBlockInfo.nbt());
        }
        return modifiedBlockInfo;
    }

    @Override
    public MapCodec<DoubleDropsProcessor> codec() {
        return CODEC;
    }
}
