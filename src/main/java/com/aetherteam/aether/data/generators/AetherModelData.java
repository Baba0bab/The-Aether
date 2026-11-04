//package com.aetherteam.aether.data.generators;
//
//import com.aetherteam.aether.Aether;
//import com.aetherteam.aether.block.AetherBlocks;
//import net.minecraft.client.data.models.BlockModelGenerators;
//import net.minecraft.client.data.models.ItemModelGenerators;
//import net.minecraft.client.data.models.ModelProvider;
//import net.minecraft.core.Holder;
//import net.minecraft.data.PackOutput;
//import net.minecraft.world.level.block.Block;
//
//import java.util.Set;
//import java.util.stream.Stream;
//
///**
// * Generates the Aether's blockstates, block models, item models, and item model definitions.
// */
//public class AetherModelData extends ModelProvider {
//    public AetherModelData(PackOutput output) {
//        super(output, Aether.MODID);
//    }
//
//    @Override
//    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
//        new AetherBlockStateData(blockModels, itemModels).run();
//        new AetherItemModelData(itemModels, blockModels).run();
//    }
//
//    /**
//     * Excludes blocks with handwritten blockstates from validation.
//     */
//    @Override
//    protected Stream<? extends Holder<Block>> getKnownBlocks() {
//        Set<Block> handwritten = Set.of(AetherBlocks.AEROGEL_STAIRS.get(), AetherBlocks.AEROGEL_WALL.get());
//        return super.getKnownBlocks().filter((holder) -> !handwritten.contains(holder.value()));
//    }
//}
