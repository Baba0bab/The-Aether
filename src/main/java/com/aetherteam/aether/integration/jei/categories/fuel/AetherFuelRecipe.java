package com.aetherteam.aether.integration.jei.categories.fuel;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.List;

/**
 * A fuel usable in one of the Aether's furnace-like blocks.
 *
 * @param inputs   The {@link List} of fuel {@link ItemStack}s.
 * @param burnTime The {@link Integer} burn time in ticks.
 * @param usage    The {@link Block} that can use the fuel.
 */
public record AetherFuelRecipe(List<ItemStack> inputs, int burnTime, Block usage) {
}
