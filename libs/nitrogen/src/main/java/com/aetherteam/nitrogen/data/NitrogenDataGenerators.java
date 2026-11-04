package com.aetherteam.nitrogen.data;

import com.aetherteam.nitrogen.data.generators.NitrogenLanguageData;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.DetectedVersion;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;

import java.util.Optional;

public class NitrogenDataGenerators implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();

        // Client Data
        pack.addProvider(NitrogenLanguageData::new);

        // pack.mcmeta
        pack.addProvider((FabricPackOutput output) -> {
            return new PackMetadataGenerator(output).add(PackMetadataSection.SERVER_TYPE, new PackMetadataSection(
                Component.translatable("pack.nitrogen_internals.mod.description"),
                new InclusiveRange<>(DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA))));
        });
    }
}
