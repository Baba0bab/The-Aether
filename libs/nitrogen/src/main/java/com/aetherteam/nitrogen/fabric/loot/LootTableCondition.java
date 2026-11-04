/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package com.aetherteam.nitrogen.fabric.loot;

import com.aetherteam.nitrogen.fabric.pond.LootContextExtension;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import com.mojang.serialization.MapCodec;

import java.util.Objects;

public record LootTableCondition(Identifier lootTableId) implements LootItemCondition {
    public static final MapCodec<LootTableCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Identifier.CODEC.fieldOf("loot_table_id").forGetter(LootTableCondition::lootTableId)
    ).apply(instance, LootTableCondition::new));

    @Override
    public MapCodec<LootTableCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(LootContext context) {
        return Objects.equals(((LootContextExtension) context).nitrogen_fabric$getTableId(), lootTableId);
    }

    public static Builder builder(Identifier lootTableId) {
        return new Builder(lootTableId);
    }

    public record Builder(Identifier lootTableId) implements LootItemCondition.Builder {
        @Override
        public LootItemCondition build() {
            return new LootTableCondition(lootTableId);
        }
    }
}
