//package com.aetherteam.aether.data.generators.loot;
//
//import com.aetherteam.aether.loot.AetherLoot;
//import net.minecraft.advancements.predicates.entity.EntityPredicate;
//import net.minecraft.core.Holder;
//import net.minecraft.core.component.DataComponentExactPredicate;
//import net.minecraft.core.component.DataComponents;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.data.loot.LootTableSubProvider;
//import net.minecraft.world.item.DyeColor;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.ColorCollection;
//import net.minecraft.world.level.storage.loot.LootContext;
//import net.minecraft.world.level.storage.loot.LootPool;
//import net.minecraft.world.level.storage.loot.LootTable;
//import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
//import net.minecraft.world.level.storage.loot.entries.LootItem;
//import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
//import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
//import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
//
//public record AetherShearingLoot(LootTableSubProvider.Context output) implements LootTableSubProvider {
//    @Override
//    public void run() {
//        ColorCollection.zipApply(ColorCollection.VALUES, Blocks.WOOL, (dyeColor, wool) -> this.output.accept(
//                AetherLoot.SHEAR_SHEEPUFF_BY_DYE.get(dyeColor),
//                LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.between(1, 3)).add(LootItem.lootTableItem(wool)))
//        ));
//        this.output.accept(AetherLoot.SHEAR_SHEEPUFF, LootTable.lootTable().withPool(this.createSheepuffDispatchPool()));
//    }
//
//    /**
//     * [CODE COPY] - {@link net.minecraft.data.loot.EntityLootSubProvider#createSheepDispatchPool(ColorCollection)}.<br><br>
//     * Modified to only check for the Sheepuff's color, as the Sheep predicate is exclusive to Sheep.
//     */
//    private LootPool.Builder createSheepuffDispatchPool() {
//        AlternativesEntry.Builder variants = AlternativesEntry.alternatives();
//        for (DyeColor color : DyeColor.VALUES) {
//            Holder<LootTable> table = this.output.lookup(Registries.LOOT_TABLE).getOrThrow(AetherLoot.SHEAR_SHEEPUFF_BY_DYE.get(color));
//            variants = variants.otherwise(NestedLootTable.lootTableReference(table)
//                    .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity()
//                            .components(DataComponentExactPredicate.expect(DataComponents.SHEEP_COLOR, color)))));
//        }
//        return LootPool.lootPool().add(variants);
//    }
//}
