//package com.aetherteam.aether.data.providers;
//
//import com.aetherteam.aether.AetherTags;
//import com.aetherteam.aether.item.AetherItems;
//import com.aetherteam.aether.item.components.AetherDataComponents;
//import com.aetherteam.aether.loot.functions.DoubleDrops;
//import com.aetherteam.aether.loot.functions.SpawnTNT;
//import com.aetherteam.aether.loot.functions.SpawnXP;
//import com.aetherteam.nitrogen.data.providers.NitrogenBlockLootSubProvider;
//import net.minecraft.data.loot.LootTableSubProvider;
//import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
//import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
//import net.minecraft.core.component.DataComponents;
//import net.minecraft.core.Holder;
//import net.minecraft.advancements.predicates.ItemPredicate;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.world.flag.FeatureFlagSet;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.item.enchantment.Enchantments;
//import net.minecraft.world.level.ItemLike;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
//import net.minecraft.world.level.storage.loot.LootContext;
//import net.minecraft.world.level.storage.loot.LootPool;
//import net.minecraft.world.level.storage.loot.LootTable;
//import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
//import net.minecraft.world.level.storage.loot.entries.LootItem;
//import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
//import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
//import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
//import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
//import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
//import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
//import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
//import net.minecraft.world.level.storage.loot.predicates.MatchTool;
//
//import java.util.Set;
//
//public abstract class AetherBlockLootSubProvider extends NitrogenBlockLootSubProvider {
//    public AetherBlockLootSubProvider(Set<Item> items, FeatureFlagSet flags, LootTableSubProvider.Context output) {
//        super(items, flags, output);
//    }
//
//    public void dropDoubleWithSilk(Block block, ItemLike drop) {
//        this.add(block, (result) -> this.droppingDoubleWithSilkTouch(result, drop));
//    }
//
//    public void dropSelfDouble(Block block) {
//        this.add(block, this.droppingDouble(block));
//    }
//
//    public void dropDoubleWithFortune(Block block, Item drop) {
//        this.add(block, (result) -> this.droppingDoubleItemsWithFortune(result, drop));
//    }
//
//    public LootTable.Builder droppingDoubleWithSilkTouch(Block block, ItemLike noSilkTouch) {
//        return this.droppingDoubleWithSilkTouch(block, this.applyExplosionCondition(block, LootItem.lootTableItem(noSilkTouch)));
//    }
//
//    public LootTable.Builder droppingDoubleWithSilkTouch(Block block, LootPoolEntryContainer.Builder<?> builder) {
//        return this.droppingDouble(block, this.hasSilkTouch(), builder);
//    }
//
//    public LootTable.Builder droppingDouble(ItemLike item) {
//        return LootTable.lootTable().withPool(this.applyExplosionCondition(item, LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(item))))
//                .apply(DoubleDrops.builder());
//    }
//
//    public LootTable.Builder droppingDouble(Block block, Holder<LootItemCondition> condition, LootPoolEntryContainer.Builder<?> builder) {
//        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(block).when(condition).otherwise(builder)))
//                .apply(DoubleDrops.builder());
//    }
//
//    public LootTable.Builder droppingWithChancesAndSkyrootSticks(Block block, Block sapling, float... chances) {
//        return this.createForgeSilkTouchOrShearsDispatchTable(this.output.lookup(Registries.ITEM), block, this.applyExplosionCondition(block, LootItem.lootTableItem(sapling)).when(BonusLevelTableCondition.bonusLevelFlatChance(this.enchantments.getOrThrow(Enchantments.FORTUNE), chances)))
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1)).when(new AnyOfCondition.Builder().or(this.hasShears()).or(this.hasSilkTouch()).invert())
//                        .add(this.applyExplosionDecay(block,
//                                        LootItem.lootTableItem(AetherItems.SKYROOT_STICK.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
//                                .when(BonusLevelTableCondition.bonusLevelFlatChance(this.enchantments.getOrThrow(Enchantments.FORTUNE), 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))))
//                .apply(DoubleDrops.builder());
//    }
//
//    public LootTable.Builder droppingGoldenOakLeaves(Block block, Block sapling, float... chances) {
//        return this.droppingWithChancesAndSkyrootSticks(block, sapling, chances)
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1)).when(new AnyOfCondition.Builder().or(this.hasShears()).or(this.hasSilkTouch()).invert())
//                        .add(this.applyExplosionCondition(block,
//                                        LootItem.lootTableItem(Items.GOLDEN_APPLE))
//                                .when(BonusLevelTableCondition.bonusLevelFlatChance(this.enchantments.getOrThrow(Enchantments.FORTUNE), 0.00005F, 0.000055555557F, 0.0000625F, 0.00008333334F, 0.00025F))));
//    }
//
//    public LootTable.Builder droppingDoubleItemsWithFortune(Block block, Item item) {
//        return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(item)
//                .apply(ApplyBonusCount.addOreBonusCount(this.enchantments.getOrThrow(Enchantments.FORTUNE)))))
//                .apply(DoubleDrops.builder());
//    }
//
//    public LootTable.Builder droppingWithSkyrootSticks(Block block) {
//        return this.createForgeSilkTouchOrShearsDispatchTable(this.output.lookup(Registries.ITEM), block, this.applyExplosionDecay(block,
//                        LootItem.lootTableItem(AetherItems.SKYROOT_STICK.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
//                .when(BonusLevelTableCondition.bonusLevelFlatChance(this.enchantments.getOrThrow(Enchantments.FORTUNE), 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F)))
//                .apply(DoubleDrops.builder());
//    }
//
//    public LootTable.Builder droppingWithFruitAndSkyrootSticks(Block block, Item fruit) {
//        return this.createForgeSilkTouchOrShearsDispatchTable(this.output.lookup(Registries.ITEM), block, this.applyExplosionDecay(block, LootItem.lootTableItem(fruit)))
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1)).when(new AnyOfCondition.Builder().or(this.hasShears()).or(this.hasSilkTouch()).invert())
//                        .add(this.applyExplosionDecay(block,
//                                        LootItem.lootTableItem(AetherItems.SKYROOT_STICK.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
//                                .when(BonusLevelTableCondition.bonusLevelFlatChance(this.enchantments.getOrThrow(Enchantments.FORTUNE), 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))))
//                .apply(DoubleDrops.builder());
//    }
//
//    public LootTable.Builder droppingDoubleGoldenOak(Block original, Block block, Item item) {
//        return LootTable.lootTable()
//                .withPool(this.applyExplosionDecay(block, LootPool.lootPool().setRolls(ContextIntProviders.exactly(1)).add(LootItem.lootTableItem(original)
//                        .when(this.hasSilkTouch()))))
//                .withPool(this.applyExplosionDecay(block, LootPool.lootPool().setRolls(ContextIntProviders.exactly(1)).add(LootItem.lootTableItem(block)
//                        .when(this.doesNotHaveSilkTouch()))))
//                .withPool(this.applyExplosionDecay(item, LootPool.lootPool().setRolls(ContextIntProviders.exactly(1)).add(LootItem.lootTableItem(item)
//                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(this.output.lookup(Registries.ITEM), AetherTags.Items.GOLDEN_AMBER_HARVESTERS)))
//                        .when(this.doesNotHaveSilkTouch())
//                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2)))
//                        .apply(ApplyBonusCount.addOreBonusCount(this.enchantments.getOrThrow(Enchantments.FORTUNE))))))
//                .apply(DoubleDrops.builder());
//    }
//
//    public LootTable.Builder droppingBerryBush(Block block, Block stem, Item drop) {
//        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                .add(this.applyExplosionDecay(block, LootItem.lootTableItem(drop)
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
//                        .apply(ApplyBonusCount.addUniformBonusCount(this.enchantments.getOrThrow(Enchantments.FORTUNE))))
//                .when(this.doesNotHaveSilkTouch())
//                .apply(DoubleDrops.builder())
//        ).withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                .add(LootItem.lootTableItem(block))
//                .when(this.hasSilkTouch())
//        ).withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                .add(LootItem.lootTableItem(stem)
//                        .when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS).invert()))
//        );
//    }
//
//    public LootTable.Builder droppingTreasureChest(Block block) {
//        return LootTable.lootTable().withPool(this.applyExplosionCondition(block, LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                .add(LootItem.lootTableItem(block)
//                        .apply(CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY)
//                            .include(DataComponents.CUSTOM_NAME)
//                            .include(AetherDataComponents.LOCKED.get())
//                            .include(AetherDataComponents.DUNGEON_KIND.get()))
//                ))
//        );
//    }
//
//    public LootTable.Builder droppingPresentLoot(Block block) {
//        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                .add(EmptyLootItem.emptyItem().setWeight(18)
//                        .apply(SpawnTNT.builder()))
//                .add(EmptyLootItem.emptyItem().setWeight(9)
//                        .apply(SpawnXP.builder()))
//                .add(this.applyExplosionDecay(block, LootItem.lootTableItem(AetherItems.GINGERBREAD_MAN.get()).setWeight(8)
//                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(5, 6)))))
//                .add(this.applyExplosionDecay(block, LootItem.lootTableItem(AetherItems.CANDY_CANE_SWORD.get()).setWeight(1)))
//                .when(this.doesNotHaveSilkTouch())
//        ).withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                .add(LootItem.lootTableItem(block))
//                .when(this.hasSilkTouch())
//        );
//    }
//}
