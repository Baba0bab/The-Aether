//package com.aetherteam.aether.data.generators;
//
//import net.minecraft.world.item.DyeColor;
//import com.aetherteam.aether.Aether;
//import com.aetherteam.aether.AetherTags;
//import com.aetherteam.aether.advancement.IncubationTrigger;
//import com.aetherteam.aether.advancement.LoreTrigger;
//import com.aetherteam.aether.block.AetherBlocks;
//import com.aetherteam.aether.data.resources.registries.AetherDimensions;
//import com.aetherteam.aether.data.resources.registries.AetherMoaTypes;
//import com.aetherteam.aether.entity.AetherEntityTypes;
//import com.aetherteam.aether.event.hooks.AbilityHooks;
//import com.aetherteam.aether.item.AetherItems;
//import com.aetherteam.aether.loot.AetherLoot;
//import net.minecraft.advancements.*;
//import net.minecraft.advancements.predicates.*;
//import net.minecraft.advancements.predicates.entity.*;
//import net.minecraft.advancements.triggers.*;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.HolderGetter;
//import net.minecraft.core.Holder;
//import net.minecraft.core.component.DataComponentExactPredicate;
//import net.minecraft.core.component.DataComponents;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.Identifier;
//import net.minecraft.tags.DamageTypeTags;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
//import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
//import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
//import net.minecraft.world.level.storage.loot.predicates.MatchTool;
//import net.minecraft.data.advancements.AdvancementSubProvider;
//import net.minecraft.data.worldgen.BootstrapContext;
//import net.minecraft.world.item.ItemStackTemplate;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Stream;
//
//public class AetherAdvancementData extends AdvancementSubProvider {
//    public AetherAdvancementData(BootstrapContext<Advancement> output) {
//        super(output);
//    }
//
//    @SuppressWarnings("unused")
//    @Override
//    public void generate() {
//        HolderGetter<Item> itemGetter = this.output.lookup(Registries.ITEM);
//        HolderGetter<Block> blockGetter = this.output.lookup(Registries.BLOCK);
//        HolderGetter<EntityType<?>> entityGetter = this.output.lookup(Registries.ENTITY_TYPE);
//
//        AdvancementHolder theAether = Advancement.Builder.advancement()
//                .rootDisplay(AetherItems.AETHER_PORTAL_FRAME.get(),
//                        Component.translatable("advancement.aether.the_aether"),
//                        Component.translatable("advancement.aether.the_aether.desc"),
//                        Identifier.fromNamespaceAndPath(Aether.MODID, "block/dungeon/carved_stone"),
//                        AdvancementType.TASK, false, false, false)
//                .addCriterion("the_aether", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(AetherDimensions.AETHER_LEVEL))
//                .save(this.output, name("the_aether"));
//
//        AdvancementHolder enterAether = Advancement.Builder.advancement()
//                .parent(theAether)
//                .display(Blocks.GLOWSTONE.asItem(),
//                        Component.translatable("advancement.aether.enter_aether"),
//                        Component.translatable("advancement.aether.enter_aether.desc"),
//                        AdvancementType.TASK, true, true, false)
//                .addCriterion("enter_aether", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(AetherDimensions.AETHER_LEVEL))
//                .rewards(AdvancementRewards.Builder.loot(this.output.lookup(Registries.LOOT_TABLE).getOrThrow(AetherLoot.ENTER_AETHER)))
//                .save(this.output, name("enter_aether"));
//
//        AdvancementHolder readLore = Advancement.Builder.advancement()
//                .parent(enterAether)
//                .display(AetherItems.BOOK_OF_LORE.get(),
//                        Component.translatable("advancement.aether.read_lore"),
//                        Component.translatable("advancement.aether.read_lore.desc"),
//                        AdvancementType.TASK, true, true, false)
//                .addCriterion("lore_book_entry", LoreTrigger.Instance.forAny())
//                .save(this.output, name("read_lore"));
//
//        AdvancementHolder loreception = Advancement.Builder.advancement()
//                .parent(readLore)
//                .display(AetherItems.BOOK_OF_LORE.get(),
//                        Component.translatable("advancement.aether.loreception"),
//                        Component.translatable("advancement.aether.loreception.desc"),
//                        AdvancementType.GOAL, true, true, true)
//                .addCriterion("lore_book_entry", LoreTrigger.Instance.forItem(itemGetter, AetherItems.BOOK_OF_LORE.get()))
//                .save(this.output, name("loreception"));
//
//        AdvancementHolder zanite = Advancement.Builder.advancement()
//                .parent(enterAether)
//                .display(AetherItems.ZANITE_GEMSTONE.get(),
//                        Component.translatable("advancement.aether.zanite"),
//                        Component.translatable("advancement.aether.zanite.desc"),
//                        AdvancementType.TASK, true, true, false)
//                .addCriterion("zanite", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.ZANITE_GEMSTONE.get()))
//                .save(this.output, name("zanite"));
//
//        AdvancementHolder craftAltar = Advancement.Builder.advancement()
//                .parent(zanite)
//                .display(AetherBlocks.ALTAR.get().asItem(),
//                        Component.translatable("advancement.aether.craft_altar"),
//                        Component.translatable("advancement.aether.craft_altar.desc"),
//                        AdvancementType.TASK, true, true, false)
//                .addCriterion("craft_altar", InventoryChangeTrigger.TriggerInstance.hasItems(AetherBlocks.ALTAR.get()))
//                .save(this.output, name("craft_altar"));
//
//        AdvancementHolder icestone = Advancement.Builder.advancement()
//                .parent(craftAltar)
//                .display(AetherBlocks.ICESTONE.get().asItem(),
//                        Component.translatable("advancement.aether.icestone"),
//                        Component.translatable("advancement.aether.icestone.desc"),
//                        AdvancementType.TASK, true, true, false)
//                .addCriterion("icestone", InventoryChangeTrigger.TriggerInstance.hasItems(AetherBlocks.ICESTONE.get()))
//                .save(this.output, name("icestone"));
//
//        AdvancementHolder iceAccessory = Advancement.Builder.advancement()
//                .parent(icestone)
//                .display(AetherItems.ICE_PENDANT.get(),
//                        Component.translatable("advancement.aether.ice_accessory"),
//                        Component.translatable("advancement.aether.ice_accessory.desc"),
//                        AdvancementType.TASK, true, true, false)
//                .requirements(AdvancementRequirements.Strategy.OR)
//                .addCriterion("ice_pendant", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.ICE_PENDANT.get()))
//                .addCriterion("ice_ring", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.ICE_RING.get()))
//                .save(this.output, name("ice_accessory"));
//
//        AdvancementHolder blueAercloud = Advancement.Builder.advancement()
//                .parent(enterAether)
//                .display(AetherBlocks.BLUE_AERCLOUD.get().asItem(),
//                        Component.translatable("advancement.aether.blue_aercloud"),
//                        Component.translatable("advancement.aether.blue_aercloud.desc"),
//                        AdvancementType.TASK, true, true, false)
//                .addCriterion("blue_aercloud", EnterBlockTrigger.TriggerInstance.entersBlock(blockGetter, AetherBlocks.BLUE_AERCLOUD.get()))
//                .save(this.output, name("blue_aercloud"));
//
//        AdvancementHolder obtainEgg = Advancement.Builder.advancement()
//                .parent(blueAercloud)
//                .display(AetherItems.BLUE_MOA_EGG.get(),
//                        Component.translatable("advancement.aether.obtain_egg"),
//                        Component.translatable("advancement.aether.obtain_egg.desc"),
//                        AdvancementType.TASK, true, true, false)
//                .requirements(AdvancementRequirements.Strategy.OR)
//                .addCriterion("moa_egg", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemGetter, AetherTags.Items.MOA_EGGS).build()))
//                .save(this.output, name("obtain_egg"));
//
//        AdvancementHolder obtainPetal = Advancement.Builder.advancement()
//                .parent(obtainEgg)
//                .display(AetherItems.AECHOR_PETAL.get(),
//                        Component.translatable("advancement.aether.obtain_petal"),
//                        Component.translatable("advancement.aether.obtain_petal.desc"),
//                        AdvancementType.TASK, true, true, false)
//                .addCriterion("aechor_petal", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.AECHOR_PETAL.get()))
//                .save(this.output, name("obtain_petal"));
//
//        AdvancementHolder preventAechorPlantSpawning = Advancement.Builder.advancement()
//            .parent(obtainPetal)
//            .display(AetherBlocks.PURPLE_FLOWER.get().asItem(),
//                Component.translatable("advancement.aether.prevent_aechor_petal_spawning"),
//                Component.translatable("advancement.aether.prevent_aechor_petal_spawning.desc"),
//                AdvancementType.TASK, true, true, false)
//            .requirements(AdvancementRequirements.Strategy.OR)
//            .addCriterion("place_flower", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blockGetter, AetherTags.Blocks.ENCHANTED_GRASS)), ItemPredicate.Builder.item().of(itemGetter, AetherTags.Items.AECHOR_PLANT_SPAWNABLE_DETERRENT)))
//            .addCriterion("enchant_grass", itemUsedOnBlockCheckAbove(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blockGetter, AetherTags.Blocks.ENCHANTED_GRASS)), LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blockGetter, AetherTags.Blocks.AECHOR_PLANT_SPAWNABLE_DETERRENT)), ItemPredicate.Builder.item().of(itemGetter, AetherItems.AMBROSIUM_SHARD)))
//            .save(this.output, name("prevent_aechor_petal_spawning"));
//
//        AdvancementHolder preventSwetSpawning = Advancement.Builder.advancement()
//            .parent(preventAechorPlantSpawning)
//            .display(AetherItems.createSwetBannerTemplate(this.output.lookup(Registries.BANNER_PATTERN)),
//                Component.translatable("advancement.aether.prevent_swet_spawning"),
//                Component.translatable("advancement.aether.prevent_swet_spawning.desc"),
//                AdvancementType.TASK, true, true, false)
//            .addCriterion("place_banner", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location(), ItemPredicate.Builder.item().of(itemGetter, Items.BANNER.pick(DyeColor.BLACK)).withComponents(DataComponentMatchers.Builder.components().exact(DataComponentExactPredicate.expect(DataComponents.BANNER_PATTERNS, AetherItems.createSwetBannerPatterns(this.output.lookup(Registries.BANNER_PATTERN)))).build())))
//            .save(this.output, name("prevent_swet_spawning"));
//
//        AdvancementHolder incubateMoa = Advancement.Builder.advancement()
//                .parent(obtainEgg)
//                .display(AetherBlocks.INCUBATOR.get().asItem(),
//                        Component.translatable("advancement.aether.incubate_moa"),
//                        Component.translatable("advancement.aether.incubate_moa.desc"),
//                        AdvancementType.TASK, true, true, false)
//                .addCriterion("incubate_moa", IncubationTrigger.Instance.forItem(ItemPredicate.Builder.item().of(itemGetter, AetherTags.Items.MOA_EGGS).build()))
//                .save(this.output, name("incubate_moa"));
//
//        CompoundTag moaTag = new CompoundTag();
//        moaTag.putString("MoaType", AetherMoaTypes.BLACK.identifier().toString());
//
//        AdvancementHolder blackMoa = Advancement.Builder.advancement()
//                .parent(incubateMoa)
//                .display(Items.FEATHER,
//                        Component.translatable("advancement.aether.black_moa"),
//                        Component.translatable("advancement.aether.black_moa.desc"),
//                        AdvancementType.GOAL, true, true, false)
//                .addCriterion("black_moa", StartRidingTrigger.TriggerInstance.playerStartsRiding(EntityPredicate.Builder.entity().vehicle(EntityPredicate.Builder.entity().of(entityGetter, AetherEntityTypes.MOA.get()).nbt(new NbtPredicate(moaTag)))))
//                .save(this.output, name("black_moa"));
//
//        AdvancementHolder mountPhyg = Advancement.Builder.advancement()
//                .parent(blueAercloud)
//                .display(Items.SADDLE,
//                        Component.translatable("advancement.aether.mount_phyg"),
//                        Component.translatable("advancement.aether.mount_phyg.desc"),
//                        AdvancementType.TASK, true, true, false)
//                .addCriterion("mount_phyg", StartRidingTrigger.TriggerInstance.playerStartsRiding(EntityPredicate.Builder.entity().vehicle(EntityPredicate.Builder.entity().of(entityGetter, AetherEntityTypes.PHYG.get()))))
//                .save(this.output, name("mount_phyg"));
//
//        AdvancementHolder enchantedGravitite = Advancement.Builder.advancement()
//                .parent(craftAltar)
//                .display(AetherBlocks.ENCHANTED_GRAVITITE.get().asItem(),
//                        Component.translatable("advancement.aether.enchanted_gravitite"),
//                        Component.translatable("advancement.aether.enchanted_gravitite.desc"),
//                        AdvancementType.TASK, true, true, false)
//                .addCriterion("enchanted_gravitite", InventoryChangeTrigger.TriggerInstance.hasItems(AetherBlocks.ENCHANTED_GRAVITITE.get()))
//                .save(this.output, name("enchanted_gravitite"));
//
//        AdvancementHolder gravititeArmor = Advancement.Builder.advancement()
//                .parent(enchantedGravitite)
//                .display(AetherItems.GRAVITITE_CHESTPLATE.get(),
//                        Component.translatable("advancement.aether.gravitite_armor"),
//                        Component.translatable("advancement.aether.gravitite_armor.desc"),
//                        AdvancementType.GOAL, true, true, false)
//                .addCriterion("gravitite_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_HELMET.get()))
//                .addCriterion("gravitite_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_CHESTPLATE.get()))
//                .addCriterion("gravitite_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_LEGGINGS.get()))
//                .addCriterion("gravitite_boots", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_BOOTS.get()))
//                .addCriterion("gravitite_gloves", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.GRAVITITE_GLOVES.get()))
//                .save(this.output, name("gravitite_armor"));
//
//        AdvancementHolder bronzeDungeon = Advancement.Builder.advancement()
//                .parent(enchantedGravitite)
//                .display(AetherItems.BRONZE_DUNGEON_KEY.get(),
//                        Component.translatable("advancement.aether.bronze_dungeon"),
//                        Component.translatable("advancement.aether.bronze_dungeon.desc"),
//                        AdvancementType.GOAL, true, true, false)
//                .addCriterion("kill_slider", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityGetter, AetherEntityTypes.SLIDER.get())))
//                .save(this.output, name("bronze_dungeon"));
//
//        AdvancementHolder hammerLoot = Advancement.Builder.advancement()
//                .parent(bronzeDungeon)
//                .display(AetherItems.HAMMER_OF_KINGBDOGZ.get(),
//                        Component.translatable("advancement.aether.hammer_loot"),
//                        Component.translatable("advancement.aether.hammer_loot.desc"),
//                        AdvancementType.GOAL, true, true, false)
//                .addCriterion("hammer_loot", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.HAMMER_OF_KINGBDOGZ.get()))
//                .save(this.output, name("hammer_loot"));
//
//        AdvancementHolder zephyrHammer = Advancement.Builder.advancement()
//                .parent(hammerLoot)
//                .display(Items.SNOWBALL,
//                        Component.translatable("advancement.aether.zephyr_hammer"),
//                        Component.translatable("advancement.aether.zephyr_hammer.desc"),
//                        AdvancementType.CHALLENGE, true, true, true)
//                .addCriterion("zephyr_hammer", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityGetter, AetherEntityTypes.ZEPHYR.get()), DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(this.damageTypes, DamageTypeTags.IS_PROJECTILE)).direct(EntityPredicate.Builder.entity().of(entityGetter, AetherEntityTypes.HAMMER_PROJECTILE.get()))))
//                .save(this.output, name("zephyr_hammer"));
//
//        AdvancementHolder lanceLoot = Advancement.Builder.advancement()
//                .parent(bronzeDungeon)
//                .display(AetherItems.VALKYRIE_LANCE.get(),
//                        Component.translatable("advancement.aether.lance_loot"),
//                        Component.translatable("advancement.aether.lance_loot.desc"),
//                        AdvancementType.TASK, true, true, false)
//                .addCriterion("lance_loot", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_LANCE.get()))
//                .save(this.output, name("lance_loot"));
//
//        AdvancementHolder silverDungeon = Advancement.Builder.advancement()
//                .parent(lanceLoot)
//                .display(AetherItems.SILVER_DUNGEON_KEY.get(),
//                        Component.translatable("advancement.aether.silver_dungeon"),
//                        Component.translatable("advancement.aether.silver_dungeon.desc"),
//                        AdvancementType.GOAL, true, true, false)
//                .addCriterion("kill_valkyrie_queen", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityGetter, AetherEntityTypes.VALKYRIE_QUEEN.get())))
//                .save(this.output, name("silver_dungeon"));
//
//        AdvancementHolder valkyrieLoot = Advancement.Builder.advancement()
//                .parent(silverDungeon)
//                .display(AetherItems.VALKYRIE_HELMET.get(),
//                        Component.translatable("advancement.aether.valkyrie_loot"),
//                        Component.translatable("advancement.aether.valkyrie_loot.desc"),
//                        AdvancementType.GOAL, true, true, false)
//                .requirements(AdvancementRequirements.Strategy.OR)
//                .addCriterion("valkyrie_pickaxe", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_PICKAXE.get()))
//                .addCriterion("valkyrie_hoe", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_HOE.get()))
//                .addCriterion("valkyrie_axe", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_AXE.get()))
//                .addCriterion("valkyrie_shovel", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_SHOVEL.get()))
//                .addCriterion("valkyrie_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_HELMET.get()))
//                .addCriterion("valkyrie_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_CHESTPLATE.get()))
//                .addCriterion("valkyrie_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_LEGGINGS.get()))
//                .addCriterion("valkyrie_boots", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_BOOTS.get()))
//                .addCriterion("valkyrie_gloves", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.VALKYRIE_GLOVES.get()))
//                .save(this.output, name("valkyrie_loot"));
//
//        AdvancementHolder valkyrieHoe = Advancement.Builder.advancement()
//                .parent(valkyrieLoot)
//                .display(AetherBlocks.AETHER_FARMLAND.get().asItem(),
//                        Component.translatable("advancement.aether.valkyrie_hoe"),
//                        Component.translatable("advancement.aether.valkyrie_hoe.desc"),
//                        AdvancementType.CHALLENGE, true, true, true)
//                .addCriterion("valkyrie_hoe", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blockGetter, Stream.concat(AbilityHooks.ToolHooks.TILLABLES.keySet().stream(), Stream.of(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.ROOTED_DIRT).sorted(Comparator.comparing(Block::getDescriptionId))).toArray(Block[]::new))), ItemPredicate.Builder.item().of(itemGetter, AetherItems.VALKYRIE_HOE.get())))
//                .save(this.output, name("valkyrie_hoe"));
//
//        AdvancementHolder regenStone = Advancement.Builder.advancement()
//                .parent(silverDungeon)
//                .display(AetherItems.REGENERATION_STONE.get(),
//                        Component.translatable("advancement.aether.regen_stone"),
//                        Component.translatable("advancement.aether.regen_stone.desc"),
//                        AdvancementType.TASK, true, true, false)
//                .addCriterion("regen_stone", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.REGENERATION_STONE.get()))
//                .save(this.output, name("regen_stone"));
//
//        AdvancementHolder goldDungeon = Advancement.Builder.advancement()
//                .parent(regenStone)
//                .display(AetherItems.GOLD_DUNGEON_KEY.get(),
//                        Component.translatable("advancement.aether.gold_dungeon"),
//                        Component.translatable("advancement.aether.gold_dungeon.desc"),
//                        AdvancementType.GOAL, true, true, false)
//                .addCriterion("kill_sun_spirit", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityGetter, AetherEntityTypes.SUN_SPIRIT.get())))
//                .save(this.output, name("gold_dungeon"));
//
//        AdvancementHolder phoenixArmor = Advancement.Builder.advancement()
//                .parent(goldDungeon)
//                .display(AetherItems.PHOENIX_HELMET.get(),
//                        Component.translatable("advancement.aether.phoenix_armor"),
//                        Component.translatable("advancement.aether.phoenix_armor.desc"),
//                        AdvancementType.GOAL, true, true, false)
//                .requirements(AdvancementRequirements.Strategy.OR)
//                .addCriterion("phoenix_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.PHOENIX_HELMET.get()))
//                .addCriterion("phoenix_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.PHOENIX_CHESTPLATE.get()))
//                .addCriterion("phoenix_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.PHOENIX_LEGGINGS.get()))
//                .addCriterion("phoenix_boots", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.PHOENIX_BOOTS.get()))
//                .addCriterion("phoenix_gloves", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.PHOENIX_GLOVES.get()))
//                .save(this.output, name("phoenix_armor"));
//
//        AdvancementHolder obsidianArmor = Advancement.Builder.advancement()
//                .parent(phoenixArmor)
//                .display(AetherItems.OBSIDIAN_CHESTPLATE.get(),
//                        Component.translatable("advancement.aether.obsidian_armor"),
//                        Component.translatable("advancement.aether.obsidian_armor.desc"),
//                        AdvancementType.CHALLENGE, true, true, true)
//                .addCriterion("obsidian_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.OBSIDIAN_HELMET.get()))
//                .addCriterion("obsidian_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.OBSIDIAN_CHESTPLATE.get()))
//                .addCriterion("obsidian_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.OBSIDIAN_LEGGINGS.get()))
//                .addCriterion("obsidian_boots", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.OBSIDIAN_BOOTS.get()))
//                .addCriterion("obsidian_gloves", InventoryChangeTrigger.TriggerInstance.hasItems(AetherItems.OBSIDIAN_GLOVES.get()))
//                .save(this.output, name("obsidian_armor"));
//
//        AdvancementHolder aetherSleep = Advancement.Builder.advancement()
//                .parent(goldDungeon)
//                .display(AetherBlocks.SKYROOT_BED.get().asItem(),
//                        Component.translatable("advancement.aether.aether_sleep"),
//                        Component.translatable("advancement.aether.aether_sleep.desc"),
//                        AdvancementType.CHALLENGE, true, true, true)
//                .addCriterion("aether_sleep", CriteriaTriggers.SLEPT_IN_BED.createCriterion(new PlayerTrigger.TriggerInstance(Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().located(LocationPredicate.Builder.inDimension(AetherDimensions.AETHER_LEVEL)))))))
//                .save(this.output, name("aether_sleep"));
//    }
//
//    private static ItemUsedOnLocationTrigger.TriggerInstance itemUsedOnLocationCheckAbove(LocationPredicate.Builder location, LocationPredicate.Builder above, ItemPredicate.Builder item) {
//        Holder<LootItemCondition> condition = Holder.direct(AllOfCondition.allOf(LocationCheck.checkLocation(location), LocationCheck.checkLocation(above, BlockPos.ZERO.above()), MatchTool.toolMatches(item)).build());
//        return new ItemUsedOnLocationTrigger.TriggerInstance(Optional.empty(), Optional.of(condition));
//    }
//
//    public static Criterion<ItemUsedOnLocationTrigger.TriggerInstance> itemUsedOnBlockCheckAbove(LocationPredicate.Builder location, LocationPredicate.Builder above, ItemPredicate.Builder item) {
//        return CriteriaTriggers.ITEM_USED_ON_BLOCK.createCriterion(itemUsedOnLocationCheckAbove(location, above, item));
//    }
//
//    private static String name(String name) {
//        return Identifier.fromNamespaceAndPath(Aether.MODID, name).toString();
//    }
//}
