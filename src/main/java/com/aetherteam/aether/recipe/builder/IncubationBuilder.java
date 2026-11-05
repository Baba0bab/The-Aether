package com.aetherteam.aether.recipe.builder;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import com.aetherteam.aether.recipe.recipes.item.IncubationRecipe;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Optional;

public class IncubationBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final EntityType<?> entity;
    @Nullable
    private final CompoundTag tag;
    private final int incubationTime;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    @Nullable
    private String group;

    public IncubationBuilder(Ingredient ingredient, EntityType<?> entity, @Nullable CompoundTag tag, int incubationTime) {
        this.ingredient = ingredient;
        this.entity = entity;
        this.tag = tag;
        this.incubationTime = incubationTime;
    }

    public static IncubationBuilder incubation(Ingredient ingredient, EntityType<?> entity, CompoundTag tag, int incubationTime) {
        return new IncubationBuilder(ingredient, entity, tag, incubationTime);
    }

    @Override
    public IncubationBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public @Nullable ResourceKey<Recipe<?>> defaultId() {
        return null;
    }

    @Override
    public IncubationBuilder unlockedBy(String criterionName, Criterion<?> criterionTrigger) {
        this.advancementBuilder.unlockedBy(criterionName, criterionTrigger);
        return this;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> id) {
        recipeOutput.accept(id, new IncubationRecipe(this.group == null ? "" : this.group, this.ingredient, this.entity, Optional.ofNullable(this.tag), this.incubationTime), this.advancementBuilder.build(recipeOutput, id, RecipeCategory.MISC));
    }
}
