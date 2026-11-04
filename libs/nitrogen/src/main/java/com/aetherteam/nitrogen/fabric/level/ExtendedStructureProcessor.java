package com.aetherteam.nitrogen.fabric.level;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

/**
 * A {@link StructureProcessor} that also receives the {@link StructureTemplate} being placed, like NeoForge's processor extension.
 */
public interface ExtendedStructureProcessor extends StructureProcessor {
    @Nullable
    StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos offset, BlockPos pos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template);

    @Override
    @Nullable
    default StructureTemplate.StructureBlockInfo processBlock(LevelReader level, BlockPos offset, BlockPos pos, BlockPos originalPos, StructureTemplate.StructureBlockInfo blockInfo, StructurePlaceSettings settings) {
        return this.process(level, offset, pos, new StructureTemplate.StructureBlockInfo(originalPos, blockInfo.state(), blockInfo.nbt()), blockInfo, settings, null);
    }
}
