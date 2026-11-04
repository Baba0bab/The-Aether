package com.aetherteam.aether.data.resources.registries;

import com.aetherteam.aether.Aether;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

public class AetherEquipmentModels {
    public static final ResourceKey<EquipmentAsset> ZANITE = createId("zanite");
    public static final ResourceKey<EquipmentAsset> GRAVITITE = createId("gravitite");
    public static final ResourceKey<EquipmentAsset> NEPTUNE = createId("neptune");
    public static final ResourceKey<EquipmentAsset> VALKYRIE = createId("valkyrie");
    public static final ResourceKey<EquipmentAsset> PHOENIX = createId("phoenix");
    public static final ResourceKey<EquipmentAsset> OBSIDIAN = createId("obsidian");
    public static final ResourceKey<EquipmentAsset> SENTRY = createId("sentry");

    private static ResourceKey<EquipmentAsset> createId(String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(Aether.MODID, name));
    }
}
