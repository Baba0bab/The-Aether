//package com.aetherteam.aether.data.generators;
//
//import com.aetherteam.aether.Aether;
//import com.aetherteam.aether.data.resources.registries.AetherEquipmentModels;
//import net.minecraft.client.data.models.EquipmentAssetProvider;
//import net.minecraft.client.resources.model.EquipmentClientInfo;
//import net.minecraft.data.PackOutput;
//import net.minecraft.resources.Identifier;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.world.item.equipment.EquipmentAsset;
//
//import java.util.function.BiConsumer;
//
//public class AetherEquipmentModelData extends EquipmentAssetProvider {
//    public AetherEquipmentModelData(PackOutput packOutput) {
//        super(packOutput);
//    }
//
//    @Override
//    protected void registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
//        output.accept(AetherEquipmentModels.ZANITE, humanoid("zanite"));
//        output.accept(AetherEquipmentModels.GRAVITITE, humanoid("gravitite"));
//        output.accept(AetherEquipmentModels.NEPTUNE, humanoid("neptune"));
//        output.accept(AetherEquipmentModels.VALKYRIE, humanoid("valkyrie"));
//        output.accept(AetherEquipmentModels.PHOENIX, humanoid("phoenix"));
//        output.accept(AetherEquipmentModels.OBSIDIAN, humanoid("obsidian"));
//        output.accept(AetherEquipmentModels.SENTRY, humanoidLeggings("sentry"));
//    }
//
//    private static EquipmentClientInfo humanoid(String name) {
//        return EquipmentClientInfo.builder().addHumanoidLayers(Identifier.fromNamespaceAndPath(Aether.MODID, name)).build();
//    }
//
//    private static EquipmentClientInfo humanoidLeggings(String name) {
//        return EquipmentClientInfo.builder().addLayers(EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS, EquipmentClientInfo.Layer.leatherDyeable(Identifier.fromNamespaceAndPath(Aether.MODID, name), false)).build();
//    }
//
//    @Override
//    public String getName() {
//        return "Aether Equipment Asset Definitions";
//    }
//}
