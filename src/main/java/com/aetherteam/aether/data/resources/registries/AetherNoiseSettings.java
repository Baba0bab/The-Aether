package com.aetherteam.aether.data.resources.registries;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.data.resources.builders.AetherNoiseBuilders;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class AetherNoiseSettings {
    public static final ResourceKey<NoiseGeneratorSettings> SKYLANDS = createKey("skylands");

    private static ResourceKey<NoiseGeneratorSettings> createKey(String name) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, Identifier.fromNamespaceAndPath(Aether.MODID, name));
    }

    public static void bootstrap(BootstrapContext<NoiseGeneratorSettings> context) {
        context.register(SKYLANDS, AetherNoiseBuilders.skylandsNoiseSettings(context));
    }
}
