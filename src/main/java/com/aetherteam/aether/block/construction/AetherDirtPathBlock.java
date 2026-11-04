package com.aetherteam.aether.block.construction;

import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.world.level.block.PathBlock;

public class AetherDirtPathBlock extends PathBlock {
    public AetherDirtPathBlock(Properties properties) {
        super(AetherBlocks.AETHER_DIRT.get(), properties);
    }
}
