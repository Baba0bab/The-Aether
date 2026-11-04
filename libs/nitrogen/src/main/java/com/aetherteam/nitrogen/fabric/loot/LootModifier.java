/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package com.aetherteam.nitrogen.fabric.loot;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Optional;

/**
 * A base implementation of a Global Loot Modifier for modders to extend.
 * Takes care of ILootCondition matching and comes with the base codec to extend.
 */
public abstract class LootModifier implements IGlobalLootModifier {
    protected final Optional<Holder<LootItemCondition>> condition;
    protected final int priority;

    protected static <T extends LootModifier> Products.P2<RecordCodecBuilder.Mu<T>, Optional<Holder<LootItemCondition>>, Integer> codecStart(RecordCodecBuilder.Instance<T> instance) {
        return instance.group(
            LootItemCondition.CODEC.optionalFieldOf("condition").forGetter(lm -> lm.condition),
            Codec.INT.optionalFieldOf("priority", IGlobalLootModifier.DEFAULT_PRIORITY).forGetter(lm -> lm.priority));
    }

    /**
     * Constructs a LootModifier.
     *
     * @param condition the LootItemCondition that needs to be matched before the loot is modified.
     * @param priority  the priority of this modifier, lower values are applied first.
     */
    protected LootModifier(Optional<Holder<LootItemCondition>> condition, int priority) {
        this.condition = condition;
        this.priority = priority;
    }

    @Override
    public final ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        return (this.condition.isEmpty() || this.condition.get().value().test(context)) ? this.doApply(generatedLoot, context) : generatedLoot;
    }

    @Override
    public int priority() {
        return this.priority;
    }

    protected abstract ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context);
}
