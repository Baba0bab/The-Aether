package com.aetherteam.aether.recipe.display;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import com.aetherteam.nitrogen.fabric.registries.DeferredHolder;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;

public class AetherRecipeDisplays {
    public static final DeferredRegister<RecipeDisplay.Type<?>> RECIPE_DISPLAYS = DeferredRegister.create(BuiltInRegistries.RECIPE_DISPLAY, Aether.MODID);

    public static final DeferredHolder<RecipeDisplay.Type<?>, RecipeDisplay.Type<IncubatorRecipeDisplay>> ALTAR = RECIPE_DISPLAYS.register("incubator", () -> IncubatorRecipeDisplay.TYPE);
}
