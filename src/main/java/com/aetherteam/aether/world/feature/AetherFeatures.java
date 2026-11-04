package com.aetherteam.aether.world.feature;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.world.configuration.AercloudConfiguration;
import com.aetherteam.aether.world.configuration.AetherLakeConfiguration;
import com.aetherteam.aether.world.configuration.ShelfConfiguration;
import com.aetherteam.nitrogen.fabric.registries.DeferredHolder;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;

public class AetherFeatures {
    public static final DeferredRegister<MapCodec<? extends Feature>> FEATURES = DeferredRegister.create(Registries.FEATURE_TYPE, Aether.MODID);

    public static final DeferredHolder<MapCodec<? extends Feature>, MapCodec<ShelfFeature>> SHELF = FEATURES.register("shelf", () -> ShelfFeature.CODEC);
    public static final DeferredHolder<MapCodec<? extends Feature>, MapCodec<AercloudFeature>> AERCLOUD = FEATURES.register("aercloud", () -> AercloudFeature.CODEC);
    public static final DeferredHolder<MapCodec<? extends Feature>, MapCodec<CrystalIslandFeature>> CRYSTAL_ISLAND = FEATURES.register("crystal_island", () -> CrystalIslandFeature.CODEC);
    public static final DeferredHolder<MapCodec<? extends Feature>, MapCodec<AetherLakeFeature>> LAKE = FEATURES.register("lake", () -> AetherLakeFeature.CODEC);
}
