//package com.aetherteam.aether.data.generators;
//
//import com.aetherteam.aether.block.AetherBlocks;
//import com.aetherteam.aether.data.resources.AetherFuel;
//import com.aetherteam.aether.data.resources.registries.AetherDataMaps;
//import com.aetherteam.aether.item.AetherItems;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.core.registries.BuiltInRegistries;
//import net.minecraft.data.PackOutput;
//import net.neoforged.neoforge.common.data.DataMapProvider;
//import net.neoforged.neoforge.registries.datamaps.builtin.Transformable;
//import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
//import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider;
//import net.minecraft.world.level.levelgen.feature.stateproviders.CopyPropertiesProvider;
//import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.item.component.BlockTransformers;
//import net.minecraft.tags.BlockTags;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.core.component.BlockTransformer;
//import net.minecraft.core.Direction;
//import com.aetherteam.aether.loot.AetherLoot;
//import com.aetherteam.aether.event.hooks.AbilityHooks;
//
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.CompletableFuture;
//
//public class AetherDataMapData extends DataMapProvider {
//    public AetherDataMapData(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
//        super(output, provider);
//    }
//
//    /**
//     * Vanilla furnace fuels and compostables are defined as item components, see {@link com.aetherteam.aether.item.AetherItems} and {@link com.aetherteam.aether.block.AetherBlocks}.
//     */
//    @Override
//    protected void gather(HolderLookup.Provider provider) {
//        var altar = this.builder(AetherDataMaps.ALTAR_FUEL);
//        altar.add(AetherItems.AMBROSIUM_SHARD, new AetherFuel(250), false);
//        altar.add(BuiltInRegistries.ITEM.wrapAsHolder(AetherBlocks.AMBROSIUM_BLOCK.get().asItem()), new AetherFuel(2500), false);
//
//        var freezer = this.builder(AetherDataMaps.FREEZER_FUEL);
//        freezer.add(BuiltInRegistries.ITEM.wrapAsHolder(AetherBlocks.ICESTONE.get().asItem()), new AetherFuel(400), false);
//        freezer.add(BuiltInRegistries.ITEM.wrapAsHolder(AetherBlocks.ICESTONE_SLAB.get().asItem()), new AetherFuel(200), false);
//        freezer.add(BuiltInRegistries.ITEM.wrapAsHolder(AetherBlocks.ICESTONE_STAIRS.get().asItem()), new AetherFuel(400), false);
//        freezer.add(BuiltInRegistries.ITEM.wrapAsHolder(AetherBlocks.ICESTONE_WALL.get().asItem()), new AetherFuel(400), false);
//
//        var incubator = this.builder(AetherDataMaps.INCUBATOR_FUEL);
//        incubator.add(BuiltInRegistries.ITEM.wrapAsHolder(AetherBlocks.AMBROSIUM_TORCH.get().asItem()), new AetherFuel(500), false);
//
//        // Tool interactions for stripping, flattening, and tilling Aether blocks.
//        Map<Block, Map<ResourceKey<BlockTransformer>, BlockTransformer.BlockTransformData>> transforms = new LinkedHashMap<>();
//        AbilityHooks.ToolHooks.STRIPPABLES.forEach((original, stripped) -> {
//            boolean goldenOak = original == AetherBlocks.GOLDEN_OAK_LOG.get() || original == AetherBlocks.GOLDEN_OAK_WOOD.get();
//            transforms.computeIfAbsent(original, (block) -> new LinkedHashMap<>()).put(BlockTransformers.AXE, this.stripping(original, stripped, goldenOak));
//        });
//        AbilityHooks.ToolHooks.FLATTENABLES.forEach((original, flattened) -> transforms.computeIfAbsent(original, (block) -> new LinkedHashMap<>()).put(BlockTransformers.SHOVEL, this.flattening(original, flattened)));
//        AbilityHooks.ToolHooks.TILLABLES.forEach((original, tilled) -> transforms.computeIfAbsent(original, (block) -> new LinkedHashMap<>()).put(BlockTransformers.HOE, this.tilling(original, tilled)));
//        var transformables = this.builder(NeoForgeDataMaps.TRANSFORMABLES);
//        transforms.forEach((block, data) -> transformables.add(BuiltInRegistries.BLOCK.wrapAsHolder(block), new Transformable(data), false));
//    }
//
//    /**
//     * Based on {@link Transformable#stripping(Block, Block)}, but drops Golden Amber when stripping Golden Oak.
//     */
//    private BlockTransformer.BlockTransformData stripping(Block original, Block stripped, boolean goldenOak) {
//        RuleBasedStateProvider provider = RuleBasedStateProvider.builder()
//                .ifTrueThenProvide(BlockPredicate.matchesBlocks(original), new CopyPropertiesProvider(stripped))
//                .build();
//        BlockTransformer.BlockTransformData.Builder builder = BlockTransformer.BlockTransformData.builder(provider).sound(SoundEvents.AXE_STRIP);
//        if (goldenOak) {
//            builder.loot(AetherLoot.STRIP_GOLDEN_OAK).dropStrategy(BlockTransformer.DropStrategy.CLICKED_FACE);
//        }
//        return builder.build();
//    }
//
//    /**
//     * Based on {@link Transformable#flattening(Block, Block)}, but keeps the block's properties, such as {@link com.aetherteam.aether.block.AetherBlockStateProperties#DOUBLE_DROPS}.
//     */
//    private BlockTransformer.BlockTransformData flattening(Block original, Block flattened) {
//        BlockPredicate predicate = BlockPredicate.allOf(BlockPredicate.matchesBlocks(original), BlockPredicate.matchesTag(Direction.UP, BlockTags.AIR));
//        RuleBasedStateProvider provider = RuleBasedStateProvider.builder().ifTrueThenProvide(predicate, new CopyPropertiesProvider(flattened)).build();
//        return BlockTransformer.BlockTransformData.builder(provider).sound(SoundEvents.SHOVEL_FLATTEN).disallowedFaces(List.of(Direction.DOWN)).build();
//    }
//
//    /**
//     * Based on {@link Transformable#tilling(Block, Block)}, but keeps the block's properties, such as {@link com.aetherteam.aether.block.AetherBlockStateProperties#DOUBLE_DROPS}.
//     */
//    private BlockTransformer.BlockTransformData tilling(Block original, Block tilled) {
//        BlockPredicate predicate = BlockPredicate.allOf(BlockPredicate.matchesBlocks(original), BlockPredicate.matchesTag(Direction.UP, BlockTags.AIR));
//        RuleBasedStateProvider provider = RuleBasedStateProvider.builder().ifTrueThenProvide(predicate, new CopyPropertiesProvider(tilled)).build();
//        return BlockTransformer.BlockTransformData.builder(provider).sound(SoundEvents.HOE_TILL).build();
//    }
//}
