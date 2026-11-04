package com.aetherteam.aether.loot.functions;

import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import com.mojang.serialization.MapCodec;
import com.aetherteam.aether.Aether;
import com.aetherteam.nitrogen.fabric.registries.DeferredHolder;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class AetherLootFunctions {
    public static final DeferredRegister<MapCodec<? extends LootItemFunction>> LOOT_FUNCTION_TYPES = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, Aether.MODID);

    public static final DeferredHolder<MapCodec<? extends LootItemFunction>, MapCodec<DoubleDrops>> DOUBLE_DROPS = LOOT_FUNCTION_TYPES.register("double_drops", () -> DoubleDrops.CODEC);
    public static final DeferredHolder<MapCodec<? extends LootItemFunction>, MapCodec<SpawnTNT>> SPAWN_TNT = LOOT_FUNCTION_TYPES.register("spawn_tnt", () -> SpawnTNT.CODEC);
    public static final DeferredHolder<MapCodec<? extends LootItemFunction>, MapCodec<SpawnXP>> SPAWN_XP = LOOT_FUNCTION_TYPES.register("spawn_xp", () -> SpawnXP.CODEC);
    public static final DeferredHolder<MapCodec<? extends LootItemFunction>, MapCodec<WhirlwindSpawnEntity>> WHIRLWIND_SPAWN_ENTITY = LOOT_FUNCTION_TYPES.register("whirlwind_spawn_entity", () -> WhirlwindSpawnEntity.CODEC);
}
