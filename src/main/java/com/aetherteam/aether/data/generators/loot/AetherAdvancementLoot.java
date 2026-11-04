//package com.aetherteam.aether.data.generators.loot;
//
//import com.aetherteam.aether.AetherConfig;
//import com.aetherteam.aether.item.AetherItems;
//import com.aetherteam.aether.loot.AetherLoot;
//import com.aetherteam.aether.loot.conditions.ConfigEnabled;
//import net.minecraft.data.loot.LootTableSubProvider;
//import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
//import net.minecraft.world.level.storage.loot.LootPool;
//import net.minecraft.world.level.storage.loot.LootTable;
//import net.minecraft.world.level.storage.loot.entries.LootItem;
//
//
//public record AetherAdvancementLoot(LootTableSubProvider.Context output) implements LootTableSubProvider {
//    @Override
//    public void run() {
//        this.output.accept(AetherLoot.ENTER_AETHER, LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1)).add(LootItem.lootTableItem(AetherItems.GOLDEN_PARACHUTE.get())).when(ConfigEnabled.isEnabled(AetherConfig.COMMON.enable_startup_loot)))
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1)).add(LootItem.lootTableItem(AetherItems.BOOK_OF_LORE.get())).when(ConfigEnabled.isEnabled(AetherConfig.COMMON.enable_startup_loot)))
//        );
//    }
//}
