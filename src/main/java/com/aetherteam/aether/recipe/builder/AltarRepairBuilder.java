package com.aetherteam.aether.recipe.builder;

import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import com.aetherteam.aether.recipe.recipes.item.AltarRepairRecipe;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.item.crafting.Recipe;


public class AltarRepairBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Ingredient ingredient;
    private final int repairTime;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    @Nullable
    private String group;

    private AltarRepairBuilder(RecipeCategory category, Ingredient ingredient, int repairTime) {
        this.category = category;
        this.ingredient = ingredient;
        this.repairTime = repairTime;
    }

    public static AltarRepairBuilder repair(Ingredient item, RecipeCategory category, int repairTime) {
        return new AltarRepairBuilder(category, item, repairTime);
    }

    @Override
    public AltarRepairBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.ingredient.items().findFirst().orElseThrow().value().getDefaultInstance());
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> id) {
        AltarRepairRecipe recipe = new AltarRepairRecipe(this.group == null ? "" : this.group, this.ingredient, this.repairTime);
        recipeOutput.accept(id, recipe, this.advancementBuilder.build(recipeOutput, id, this.category));
    }
}
