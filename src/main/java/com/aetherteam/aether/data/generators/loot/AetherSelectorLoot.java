//package com.aetherteam.aether.data.generators.loot;
//
//import net.minecraft.world.entity.EntityTypes;
//import com.aetherteam.aether.loot.AetherLoot;
//import com.aetherteam.aether.loot.functions.WhirlwindSpawnEntity;
//import net.minecraft.advancements.predicates.entity.EntityTypePredicate;
//import net.minecraft.core.registries.BuiltInRegistries;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.data.loot.LootTableSubProvider;
//import net.minecraft.util.valueproviders.ConstantInt;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
//import net.minecraft.world.level.storage.loot.LootPool;
//import net.minecraft.world.level.storage.loot.LootTable;
//import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
//import net.minecraft.world.level.storage.loot.entries.LootItem;
//
//
//public record AetherSelectorLoot(LootTableSubProvider.Context output) implements LootTableSubProvider {
//    @Override
//    public void run() {
//        this.output.accept(AetherLoot.WHIRLWIND_JUNK, LootTable.lootTable()
//                .withPool(whirlwindLoot())
//        );
//        this.output.accept(AetherLoot.EVIL_WHIRLWIND_JUNK, LootTable.lootTable()
//                .withPool(whirlwindLoot().add(EmptyLootItem.emptyItem().apply(WhirlwindSpawnEntity.builder(EntityTypePredicate.of(BuiltInRegistries.ENTITY_TYPE, EntityTypes.CREEPER), ConstantInt.of(1))).setWeight(60)))
//        );
//    }
//
//    private static LootPool.Builder whirlwindLoot() {
//        return LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1))
//                .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(4))
//                .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5))
//                .add(LootItem.lootTableItem(Items.COAL).setWeight(9))
//                .add(LootItem.lootTableItem(Items.PUMPKIN).setWeight(2))
//                .add(LootItem.lootTableItem(Items.GRAVEL).setWeight(5))
//                .add(LootItem.lootTableItem(Items.CLAY).setWeight(11))
//                .add(LootItem.lootTableItem(Items.STICK).setWeight(12))
//                .add(LootItem.lootTableItem(Items.FLINT).setWeight(14))
//                .add(LootItem.lootTableItem(Blocks.OAK_LOG).setWeight(17))
//                .add(LootItem.lootTableItem(Blocks.SAND).setWeight(20));
//    }
//}
