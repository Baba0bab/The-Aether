//package com.aetherteam.aether.data.generators.loot;
//
//import com.aetherteam.aether.block.AetherBlocks;
//import com.aetherteam.aether.entity.AetherEntityTypes;
//import com.aetherteam.aether.item.AetherItems;
//import com.aetherteam.aether.loot.AetherLoot;
//import net.minecraft.data.loot.LootTableSubProvider;
//import net.minecraft.data.loot.EntityLootSubProvider;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.level.block.ColorCollection;
//import net.minecraft.world.flag.FeatureFlags;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.level.ItemLike;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
//import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
//import net.minecraft.world.level.storage.loot.BuiltInLootTables;
//import net.minecraft.world.level.storage.loot.LootPool;
//import net.minecraft.world.level.storage.loot.LootTable;
//import net.minecraft.world.level.storage.loot.entries.LootItem;
//import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
//import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
//import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
//
//public class AetherEntityLoot extends EntityLootSubProvider {
//    public AetherEntityLoot(LootTableSubProvider.Context output) {
//        super(FeatureFlags.REGISTRY.allFlags(), output);
//    }
//
//    @Override
//    public void generate() {
//        this.add(AetherEntityTypes.PHYG.get(), LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(Items.PORKCHOP)
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3)))
//                                .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
//                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
//                        )
//                )
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(Items.FEATHER)
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 1)))
//                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
//                        )
//                )
//        );
//
//        this.add(AetherEntityTypes.FLYING_COW.get(), LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(Items.LEATHER)
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
//                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
//                        )
//                )
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(Items.BEEF)
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3)))
//                                .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
//                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
//                        )
//                )
//        );
//
//        this.add(AetherEntityTypes.SHEEPUFF.get(), LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(Items.MUTTON)
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2)))
//                                .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
//                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
//                        )
//                )
//        );
//        ColorCollection.zipApply(ColorCollection.VALUES, Blocks.WOOL, (dyeColor, wool) -> this.add(AetherEntityTypes.SHEEPUFF.get(), AetherLoot.SHEEPUFF_BY_DYE.get(dyeColor), LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1)).add(LootItem.lootTableItem(wool)))));
//
//        this.add(AetherEntityTypes.MOA.get(), LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(Items.FEATHER)
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 3)))
//                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 2.0F)))
//                        )
//                )
//        );
//
//        this.add(AetherEntityTypes.AERWHALE.get(), LootTable.lootTable());
//
//        this.add(AetherEntityTypes.AERBUNNY.get(), LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(Items.STRING)
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
//                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
//                        )
//                )
//        );
//
//        this.add(AetherEntityTypes.BLUE_SWET.get(), LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(AetherItems.SWET_BALL.get())
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 1)))
//                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
//                        )
//                )
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(AetherBlocks.BLUE_AERCLOUD.get())
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 1)))
//                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
//                        )
//                )
//        );
//
//        this.add(AetherEntityTypes.GOLDEN_SWET.get(), LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(Blocks.GLOWSTONE)
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 1)))
//                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
//                        )
//                )
//        );
//
//        this.add(AetherEntityTypes.WHIRLWIND.get(), LootTable.lootTable());
//        this.add(AetherEntityTypes.EVIL_WHIRLWIND.get(), LootTable.lootTable());
//
//        this.add(AetherEntityTypes.AECHOR_PLANT.get(), LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(AetherItems.AECHOR_PETAL.get())
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 1)))
//                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
//                        )
//                )
//        );
//
//        this.add(AetherEntityTypes.COCKATRICE.get(), LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(Items.FEATHER)
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 3)))
//                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 2.0F)))
//                        )
//                )
//        );
//
//        this.add(AetherEntityTypes.ZEPHYR.get(), LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(AetherBlocks.COLD_AERCLOUD.get())
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
//                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
//                        )
//                )
//        );
//
//        this.add(AetherEntityTypes.SENTRY.get(), LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(AetherBlocks.CARVED_STONE.get()).setWeight(4)
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 1)))
//                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
//                        )
//                        .add(LootItem.lootTableItem(AetherBlocks.SENTRY_STONE.get()).setWeight(1)
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 1)))
//                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
//                        )
//                )
//        );
//
//        this.add(AetherEntityTypes.MIMIC.get(), LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(Blocks.CHEST)
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 1)))
//                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
//                        )
//                )
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.between(1, 3))
//                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_DART.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(4))))
//                        .add(LootItem.lootTableItem(AetherItems.ENCHANTED_DART.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(4))))
//                        .add(LootItem.lootTableItem(AetherItems.POISON_DART.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(4))))
//                        .add(LootItem.lootTableItem(AetherItems.SKYROOT_PICKAXE.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
//                        .add(LootItem.lootTableItem(AetherItems.IRON_RING.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
//                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_AMBER.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
//                        .add(LootItem.lootTableItem(AetherItems.ZANITE_GEMSTONE.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
//                        .add(LootItem.lootTableItem(AetherItems.HOLYSTONE_PICKAXE.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
//                        .add(LootItem.lootTableItem(AetherBlocks.ICESTONE.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
//                        .add(LootItem.lootTableItem(AetherItems.AMBROSIUM_SHARD.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
//                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
//                )
//        );
//
//        this.add(AetherEntityTypes.VALKYRIE.get(), LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(AetherItems.VICTORY_MEDAL.get())
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
//                        )
//                )
//        );
//
//        this.add(AetherEntityTypes.FIRE_MINION.get(), LootTable.lootTable());
//
//        this.add(AetherEntityTypes.SLIDER.get(), LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(AetherItems.BRONZE_DUNGEON_KEY.get())
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
//                        )
//                )
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(AetherBlocks.CARVED_STONE.get())
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(7, 9)))
//                        )
//                )
//        );
//
//        this.add(AetherEntityTypes.VALKYRIE_QUEEN.get(), LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(AetherItems.SILVER_DUNGEON_KEY.get())
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
//                        )
//                )
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(Items.GOLDEN_SWORD)
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
//                        )
//                )
//        );
//
//        this.add(AetherEntityTypes.SUN_SPIRIT.get(), LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(AetherItems.GOLD_DUNGEON_KEY.get())
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
//                        )
//                )
//                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
//                        .add(LootItem.lootTableItem(AetherBlocks.SUN_ALTAR.get())
//                                .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
//                        )
//                )
//        );
//    }
//
//    @Override
//    public Stream<EntityType<?>> getKnownEntityTypes() {
//        return AetherEntityTypes.ENTITY_TYPES.getEntries().stream().flatMap(entityType -> Stream.of(entityType.get()));
//    }
//}
