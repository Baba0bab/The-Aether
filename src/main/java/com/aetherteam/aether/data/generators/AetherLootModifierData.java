//package com.aetherteam.aether.data.generators;
//
//import com.aetherteam.nitrogen.fabric.loot.IGlobalLootModifier;
//import java.util.Optional;
//import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
//import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.item.Item;
//import net.minecraft.core.HolderGetter;
//import net.minecraft.core.Holder;
//import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
//import com.aetherteam.aether.Aether;
//import com.aetherteam.aether.AetherTags;
//import com.aetherteam.aether.block.AetherBlocks;
//import com.aetherteam.aether.item.AetherItems;
//import com.aetherteam.aether.loot.AetherLoot;
//import com.aetherteam.aether.loot.modifiers.*;
//import net.minecraft.advancements.predicates.ItemPredicate;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.data.PackOutput;
//import net.minecraft.world.item.ItemStackTemplate;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.storage.loot.predicates.*;
//import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
//import net.neoforged.neoforge.common.loot.LootTableIdCondition;
//
//import java.util.concurrent.CompletableFuture;
//
//public class AetherLootModifierData extends GlobalLootModifierProvider {
//    public AetherLootModifierData(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
//        super(output, completableFuture, Aether.MODID);
//    }
//
//    @Override
//    protected void start() {
//        HolderGetter<Block> blocks = this.registries.lookupOrThrow(Registries.BLOCK);
//        HolderGetter<Item> items = this.registries.lookupOrThrow(Registries.ITEM);
//        this.add("remove_seeds", new RemoveSeedsModifier(condition(AllOfCondition.allOf(
//                AnyOfCondition.anyOf(MatchBlock.blockMatches(blocks, Blocks.SHORT_GRASS), MatchBlock.blockMatches(blocks, Blocks.TALL_GRASS)),
//                MatchTool.toolMatches(ItemPredicate.Builder.item().of(items, Items.SHEARS)).invert())), IGlobalLootModifier.DEFAULT_PRIORITY));
//        this.add("enchanted_grass_berry_bush", new EnchantedGrassModifier(condition(MatchBlock.blockMatches(blocks, AetherBlocks.BERRY_BUSH.get())), IGlobalLootModifier.DEFAULT_PRIORITY, new ItemStackTemplate(AetherItems.BLUE_BERRY.get())));
//        this.add("double_drops", new DoubleDropsModifier(Optional.empty(), IGlobalLootModifier.DEFAULT_PRIORITY));
//        this.add("pig_drops", new PigDropsModifier(Optional.empty(), IGlobalLootModifier.DEFAULT_PRIORITY));
//        this.add("gloves_loot_leather", new GlovesLootModifier(Optional.empty(), IGlobalLootModifier.DEFAULT_PRIORITY, new ItemStackTemplate(AetherItems.LEATHER_GLOVES.get()), AetherTags.Items.LEATHER_ARMOR));
//        this.add("gloves_loot_chain", new GlovesLootModifier(condition(InvertedLootItemCondition.invert(LootTableIdCondition.builder(AetherLoot.GOLD_DUNGEON_REWARD.identifier()))), IGlobalLootModifier.DEFAULT_PRIORITY, new ItemStackTemplate(AetherItems.CHAINMAIL_GLOVES.get()), AetherTags.Items.CHAINMAIL_ARMOR));
//        this.add("gloves_loot_iron", new GlovesLootModifier(condition(InvertedLootItemCondition.invert(LootTableIdCondition.builder(AetherLoot.RUINED_PORTAL.identifier()))), IGlobalLootModifier.DEFAULT_PRIORITY, new ItemStackTemplate(AetherItems.IRON_GLOVES.get()), AetherTags.Items.IRON_ARMOR));
//        this.add("gloves_loot_gold", new GlovesLootModifier(Optional.empty(), IGlobalLootModifier.DEFAULT_PRIORITY, new ItemStackTemplate(AetherItems.GOLDEN_GLOVES.get()), AetherTags.Items.GOLDEN_ARMOR));
//        this.add("gloves_loot_diamond", new GlovesLootModifier(Optional.empty(), IGlobalLootModifier.DEFAULT_PRIORITY, new ItemStackTemplate(AetherItems.DIAMOND_GLOVES.get()), AetherTags.Items.DIAMOND_ARMOR));
//        this.add("gloves_loot_netherite", new GlovesLootModifier(Optional.empty(), IGlobalLootModifier.DEFAULT_PRIORITY, new ItemStackTemplate(AetherItems.NETHERITE_GLOVES.get()), AetherTags.Items.NETHERITE_ARMOR));
//    }
//
//    private static Optional<Holder<LootItemCondition>> condition(LootItemCondition.Builder condition) {
//        return Optional.of(Holder.direct(condition.build()));
//    }
//}
