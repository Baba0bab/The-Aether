package com.aetherteam.aether.data.resources.registries;

import com.aetherteam.aether.Aether;
import com.aetherteam.nitrogen.fabric.registries.datamaps.DataMapType;
import com.aetherteam.nitrogen.fabric.registries.datamaps.builtin.FurnaceFuel;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import com.aetherteam.aether.data.resources.AetherFuel;

public class AetherDataMaps {
    public static final DataMapType<Item, AetherFuel> ALTAR_FUEL = DataMapType.builder(Identifier.fromNamespaceAndPath(Aether.MODID, "altar_fuel"), Registries.ITEM, AetherFuel.CODEC).synced(AetherFuel.BURN_TIME_CODEC, false).build();
    public static final DataMapType<Item, AetherFuel> FREEZER_FUEL = DataMapType.builder(Identifier.fromNamespaceAndPath(Aether.MODID, "freezer_fuel"), Registries.ITEM, AetherFuel.CODEC).synced(AetherFuel.BURN_TIME_CODEC, false).build();
    public static final DataMapType<Item, AetherFuel> INCUBATOR_FUEL = DataMapType.builder(Identifier.fromNamespaceAndPath(Aether.MODID, "incubator_fuel"), Registries.ITEM, AetherFuel.CODEC).synced(AetherFuel.BURN_TIME_CODEC, false).build();
}
