package com.aetherteam.aether.data.resources.builders;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.registries.AetherDensityFunctions;
import com.aetherteam.aether.data.resources.registries.AetherMaterialRules;
import com.aetherteam.aether.data.resources.registries.AetherNoises;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.densityfunction.DensityFunction;
import net.minecraft.world.level.levelgen.densityfunction.DensityFunctions;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;
import java.util.Optional;

public class AetherNoiseBuilders {
    public static NoiseGeneratorSettings skylandsNoiseSettings(BootstrapContext<?> context) {
        HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise> noise = context.lookup(Registries.NOISE);
        BlockState holystone = AetherBlocks.HOLYSTONE.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        NoiseRouter router = makeNoiseRouter(densityFunctions, noise);
        return new NoiseGeneratorSettings(
                NoiseSettings.create(0, 128), // noiseSettings
                holystone, // defaultBlock
                Blocks.WATER.defaultBlockState(), // defaultFluid
                router, // noiseRouter
                context.lookup(Registries.MATERIAL_RULE).getOrThrow(AetherMaterialRules.SKYLANDS), // materialRule
                List.of(), // spawnTarget
                -64, // seaLevel
                false, // disableMobGeneration
                Optional.empty(), // aquifers
                false, // useLegacyRandomSource
                new NoiseGeneratorSettings.DebugFunctions(List.of(new NoiseGeneratorSettings.DebugFunctionEntry("N", router.finalDensity())))
        );
    }

    private static NoiseRouter makeNoiseRouter(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise> noise) {
        return createNoiseRouter(densityFunctions, noise, buildFinalDensity(densityFunctions));
    }

    private static DensityFunction buildFinalDensity(HolderGetter<DensityFunction> densityFunctions) {
        DensityFunction density = NoiseRouterData.getFunction(densityFunctions, AetherDensityFunctions.BASE_3D_NOISE_AETHER);
        density = DensityFunctions.add(density, DensityFunctions.constant(-0.13F));
        density = slide(density, 0, 128, 72, 0, -0.2F, 8, 40, -0.1F);
        density = DensityFunctions.add(density, DensityFunctions.constant(-0.05F));
        density = DensityFunctions.blendDensity(density);
        density = DensityFunctions.interpolated(density, 8, 4);
        density = density.squeeze();
        return density;
    }

    /**
     * [CODE COPY] - {@link NoiseRouterData#slide(DensityFunction, int, int, int, int, float, int, int, float)}.
     */
    private static DensityFunction slide(DensityFunction density, int minY, int maxY, int fromYTop, int toYTop, float offset1, int fromYBottom, int toYBottom, float offset2) {
        DensityFunction topSlide = DensityFunctions.yClampedGradient(minY + maxY - fromYTop, minY + maxY - toYTop, 1.0F, 0.0F);
        density = DensityFunctions.lerp(topSlide, offset1, density);
        DensityFunction bottomSlide = DensityFunctions.yClampedGradient(minY + fromYBottom, minY + toYBottom, 0.0F, 1.0F);
        return DensityFunctions.lerp(bottomSlide, offset2, density);
    }

    /**
     * Creates a noise router with only the temperature and vegetation noises used for biome placement, along with the final density for terrain.
     */
    private static NoiseRouter createNoiseRouter(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise> noise, DensityFunction finalDensity) {
        DensityFunction shiftX = NoiseRouterData.getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, Identifier.withDefaultNamespace("shift_x")));
        DensityFunction shiftZ = NoiseRouterData.getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, Identifier.withDefaultNamespace("shift_z")));
        DensityFunction temperature = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noise.getOrThrow(AetherNoises.TEMPERATURE));
        DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noise.getOrThrow(AetherNoises.VEGETATION));
        return new NoiseRouter(
                temperature, // temperature
                vegetation, // vegetation
                DensityFunctions.zero(), // continents
                DensityFunctions.zero(), // erosion
                DensityFunctions.zero(), // depth
                DensityFunctions.zero(), // ridges
                DensityFunctions.zero(), // chunk surface level
                finalDensity); // final density
    }
}
