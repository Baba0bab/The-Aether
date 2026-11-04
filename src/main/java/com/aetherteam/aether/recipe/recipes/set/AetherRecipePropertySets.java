package com.aetherteam.aether.recipe.recipes.set;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.mixin.mixins.common.accessor.RecipeManagerAccessor;
import com.aetherteam.aether.recipe.recipes.item.EnchantingRecipe;
import com.aetherteam.aether.recipe.recipes.item.FreezingRecipe;
import com.aetherteam.aether.recipe.recipes.item.IncubationRecipe;
import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipePropertySet;

import java.util.Optional;

public class AetherRecipePropertySets {
    public static final ResourceKey<RecipePropertySet> ALTAR_INPUT = register("altar_input");
    public static final ResourceKey<RecipePropertySet> FREEZER_INPUT = register("freezer_input");
    public static final ResourceKey<RecipePropertySet> INCUBATOR_INPUT = register("incubator_recipe");

    private static ResourceKey<RecipePropertySet> register(String id) {
        return ResourceKey.create(RecipePropertySet.TYPE_KEY, Identifier.fromNamespaceAndPath(Aether.MODID, id));
    }

    public static void addToMap() {
        ImmutableMap.Builder<ResourceKey<RecipePropertySet>, RecipeManager.IngredientExtractor> propertySets = ImmutableMap.<ResourceKey<RecipePropertySet>, RecipeManager.IngredientExtractor>builder()
            .put(AetherRecipePropertySets.ALTAR_INPUT, (recipe) -> recipe instanceof EnchantingRecipe enchantingRecipe ? Optional.of(enchantingRecipe.input()) : Optional.empty())
            .put(AetherRecipePropertySets.FREEZER_INPUT, (recipe) -> recipe instanceof FreezingRecipe freezingRecipe ? Optional.of(freezingRecipe.input()) : Optional.empty())
            .put(AetherRecipePropertySets.INCUBATOR_INPUT, (recipe) -> recipe instanceof IncubationRecipe incubationRecipe ? Optional.of(incubationRecipe.input()) : Optional.empty())
            .putAll(RecipeManagerAccessor.getPropertySets());

        RecipeManagerAccessor.setPropertySets(propertySets.build());
    }
}
