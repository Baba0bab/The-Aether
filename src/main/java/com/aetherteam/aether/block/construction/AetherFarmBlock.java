package com.aetherteam.aether.block.construction;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.mixin.mixins.common.accessor.BushBlockAccessor;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.util.TriState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;

public class AetherFarmBlock extends FarmlandBlock {
    public AetherFarmBlock(Properties properties) {
        super(AetherBlocks.AETHER_DIRT.get(), properties);
    }

    /**
     * Chosen checks based on {@link net.minecraft.world.level.block.Block#canSustainPlant(BlockState, BlockGetter, BlockPos, Direction, BlockState)}.
     */
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
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
