//package com.aetherteam.aether.data.generators.loot;
//
//import com.aetherteam.aether.AetherTags;
//import com.aetherteam.aether.item.AetherItems;
//import net.minecraft.advancements.predicates.ItemPredicate;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
//import net.minecraft.world.level.storage.loot.predicates.MatchTool;
//import com.aetherteam.aether.loot.AetherLoot;
//import net.minecraft.data.loot.LootTableSubProvider;
//import net.minecraft.world.item.enchantment.Enchantments;
//import net.minecraft.world.level.storage.loot.LootPool;
//import net.minecraft.world.level.storage.loot.LootTable;
//import net.minecraft.world.level.storage.loot.entries.LootItem;
//import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
//import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
//
//
//public record AetherStrippingLoot(LootTableSubProvider.Context output) implements LootTableSubProvider {
//    @Override
//    public void run() {
//        this.output.accept(AetherLoot.STRIP_GOLDEN_OAK, LootTable.lootTable()
//                .withPool(LootPool.lootPool().when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(this.output.lookup(Registries.ITEM), AetherTags.Items.GOLDEN_AMBER_HARVESTERS)))
//                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_AMBER.get())
//                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2)))
//                        .apply(ApplyBonusCount.addOreBonusCount(this.output.lookup(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE))))));
//    }
//}
