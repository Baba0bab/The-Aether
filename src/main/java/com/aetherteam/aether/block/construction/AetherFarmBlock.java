package com.aetherteam.aether.block.construction;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.mixin.mixins.common.accessor.BushBlockAccessor;
import net.fabricmc.fabric.api.util.TriState;
import com.aetherteam.nitrogen.fabric.pond.FarmBlockExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;

public class AetherFarmBlock extends FarmlandBlock implements FarmBlockExtension {
    public AetherFarmBlock(Properties properties) {
        super(AetherBlocks.AETHER_DIRT.get(), properties);
    }

    /**
     * Chosen checks based on {@link net.minecraft.world.level.block.Block#canSustainPlant(BlockState, BlockGetter, BlockPos, Direction, BlockState)}.
     */
    @Override
    public TriState nitrogen_fabric$canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        Block plantBlock = plant.getBlock();
        if ((plantBlock instanceof VegetationBlock vegetationBlock && ((BushBlockAccessor) vegetationBlock).callMayPlaceOn(Blocks.FARMLAND.defaultBlockState(), level, soilPosition))) {
            return TriState.TRUE;
        } else {
            return TriState.DEFAULT;
        }
    }

    /**
     * [CODE COPY] - {@link net.neoforged.neoforge.common.extensions.IBlockExtension#isFertile(BlockState, BlockGetter, BlockPos)}.
     */
//    public boolean isFertile(BlockState state, BlockGetter level, BlockPos pos) {
//        return state.getValue(FarmlandBlock.MOISTURE) > 0;
//    }
}
