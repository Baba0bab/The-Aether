package com.aetherteam.aether.integration.jei.categories.fuel;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.registries.AetherDataMaps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class AetherFuelRecipeMaker {
    private AetherFuelRecipeMaker() {
    }

    public static List<AetherFuelRecipe> getFuelRecipes() {
        List<AetherFuelRecipe> fuelRecipes = new ArrayList<>();
        BuiltInRegistries.ITEM.nitrogen_fabric$getDataMap(AetherDataMaps.ALTAR_FUEL).forEach((item, fuel) -> fuelRecipes.add(new AetherFuelRecipe(List.of(new ItemStack(BuiltInRegistries.ITEM.getValue(item))), fuel.burnTime(), AetherBlocks.ALTAR.get())));
        BuiltInRegistries.ITEM.nitrogen_fabric$getDataMap(AetherDataMaps.FREEZER_FUEL).forEach((item, fuel) -> fuelRecipes.add(new AetherFuelRecipe(List.of(new ItemStack(BuiltInRegistries.ITEM.getValue(item))), fuel.burnTime(), AetherBlocks.FREEZER.get())));
        BuiltInRegistries.ITEM.nitrogen_fabric$getDataMap(AetherDataMaps.INCUBATOR_FUEL).forEach((item, fuel) -> fuelRecipes.add(new AetherFuelRecipe(List.of(new ItemStack(BuiltInRegistries.ITEM.getValue(item))), fuel.burnTime(), AetherBlocks.INCUBATOR.get())));
        return fuelRecipes;
    }
}
