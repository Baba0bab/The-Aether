package com.aetherteam.aether.loot.conditions;

import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import com.mojang.serialization.MapCodec;
import com.aetherteam.aether.Aether;
import com.aetherteam.nitrogen.fabric.registries.DeferredHolder;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;

public class AetherLootConditions {
    public static final DeferredRegister<MapCodec<? extends LootItemCondition>> LOOT_CONDITION_TYPES = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, Aether.MODID);

    public static final DeferredHolder<MapCodec<? extends LootItemCondition>, MapCodec<ConfigEnabled>> CONFIG_ENABLED = LOOT_CONDITION_TYPES.register("config_enabled", () -> ConfigEnabled.CODEC);
}
