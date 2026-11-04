package com.aetherteam.nitrogen.data.providers;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Holder;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.Set;

public abstract class NitrogenBlockLootSubProvider extends BlockLootSubProvider {
    public NitrogenBlockLootSubProvider(Set<Item> items, FeatureFlagSet flags, LootTableSubProvider.Context context) {
        super(items, flags, context);
    }

    public void dropNone(Block block) {
        this.add(block, noDrop());
    }

    public void dropWithFortune(Block block, Item drop) {
        this.add(block, (result) -> this.createOreDrop(result, drop));
    }

    public LootTable.Builder droppingNameableBlockEntityTable(Block block) {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(block, LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
            .add(LootItem.lootTableItem(block)
                .apply(CopyNameFunction.copyName(LootContext.BlockEntityTarget.BLOCK_ENTITY))))
        );
    }

    public LootTable.Builder createForgeSilkTouchOrShearsDispatchTable(HolderGetter<Item> holderGetter, Block block, LootPoolEntryContainer.Builder<?> builder) {
        return createSelfDropDispatchTable(block, Holder.direct(new AnyOfCondition.Builder().or(MatchTool.toolMatches(ItemPredicate.Builder.item().of(holderGetter, ConventionalItemTags.SHEAR_TOOLS))).or(this.hasSilkTouch()).build()), builder);
    }
}
