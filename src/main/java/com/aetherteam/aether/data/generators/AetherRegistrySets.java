//package com.aetherteam.aether.data.generators;
//
//import com.aetherteam.aether.data.resources.registries.*;
//import net.minecraft.core.registries.Registries;
//import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;
//
///**
// * Bootstraps all of the Aether's datapack registry entries for data generation.
// */
//public class AetherRegistrySets {
//    public static void gatherEntries(GatherDataRegistryEntriesEvent event) {
//        event
//                .add(Registries.FEATURE, AetherConfiguredFeatures::bootstrap)
//                .add(Registries.PLACED_FEATURE, AetherPlacedFeatures::bootstrap)
//                .add(Registries.BIOME, AetherBiomes::bootstrap)
//                .add(Registries.DENSITY_FUNCTION, AetherDensityFunctions::bootstrap)
//                .add(Registries.NOISE, AetherNoises::bootstrap)
//                .add(Registries.MATERIAL_RULE, AetherMaterialRules::bootstrap)
//                .add(Registries.NOISE_SETTINGS, AetherNoiseSettings::bootstrap)
//                .add(Registries.WORLD_CLOCK, AetherDimensions::bootstrapWorldClock)
//                .add(Registries.TIMELINE, AetherTimelines::bootstrap)
//                .add(Registries.DIMENSION_TYPE, AetherDimensions::bootstrapDimensionType)
//                .add(Registries.LEVEL_STEM, AetherDimensions::bootstrapLevelStem)
//                .add(Registries.STRUCTURE, AetherStructures::bootstrap)
//                .add(Registries.PROCESSOR_LIST, AetherStructureProcessorLists::bootstrap)
//                .add(Registries.STRUCTURE_SET, AetherStructureSets::bootstrap)
//                .add(Registries.DAMAGE_TYPE, AetherDamageTypes::bootstrap)
//                .add(Registries.TRIM_MATERIAL, AetherTrimMaterials::bootstrap)
//                .add(Registries.JUKEBOX_SONG, AetherJukeboxSongs::bootstrap)
//                .add(AetherMoaTypes.MOA_TYPE_REGISTRY_KEY, AetherMoaTypes::bootstrap);
//    }
//}
