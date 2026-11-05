package com.aetherteam.aether.client;

import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.client.Minecraft;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.List;
import java.util.Set;

public class AetherColorResolvers {
    private static final int AETHER_GRASS_COLOR = ARGB.opaque(0xB1FFCB);
    private static final int ENCHANTED_GRASS_COLOR = ARGB.opaque(0xFCEA64);

    /**
     * Recolors tintable plants when placed on Aether Grass and Enchanted Grass.
     *
     * @see AetherClient#eventSetup(IEventBus)
     */
    public static void registerBlockColor() {
        for (Block block : List.of(Blocks.SHORT_GRASS, Blocks.FERN, Blocks.TALL_GRASS, Blocks.LARGE_FERN)) {
            List<BlockTintSource> originalSources = Minecraft.getInstance().getBlockColors().getTintSources(block.defaultBlockState());
            BlockColorRegistry.register(originalSources.stream().map(AetherColorResolvers::wrapTintSource).toList(), block);
        }
    }

    /**
     * Wraps a vanilla {@link BlockTintSource} to use Aether grass colors when the plant is on Aether Grass.
     *
     * @param original The original {@link BlockTintSource}.
     * @return The wrapped {@link BlockTintSource}.
     */
    private static BlockTintSource wrapTintSource(BlockTintSource original) {
        return new BlockTintSource() {
            @Override
            public int color(BlockState state) {
                return original.color(state);
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                BlockPos plantPos = state.hasProperty(DoublePlantBlock.HALF) && state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos;
                BlockState baseState = level.getBlockState(plantPos.below());
                if (baseState.is(AetherTags.Blocks.ENCHANTED_GRASS)) {
                    return ENCHANTED_GRASS_COLOR;
                } else if (baseState.is(AetherBlocks.AETHER_GRASS_BLOCK.get())) {
                    return AETHER_GRASS_COLOR;
                }
                return original.colorInWorld(state, level, pos);
            }

            @Override
            public Set<Property<?>> relevantProperties() {
                return original.relevantProperties();
            }
        };
    }
}
