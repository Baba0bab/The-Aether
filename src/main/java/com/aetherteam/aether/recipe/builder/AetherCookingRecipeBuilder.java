package com.aetherteam.aether.recipe.builder;

import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.world.item.ItemStackTemplate;
import com.aetherteam.aether.recipe.book.AetherBookCategory;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.serializer.AetherCookingSerializer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * [CODE COPY] - {@link net.minecraft.data.recipes.SimpleCookingRecipeBuilder}.<br><br>
 * Cleaned up, removed unnecessary static builder methods, and added support for Aether recipe categories.
 */
public class AetherCookingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final AetherBookCategory bookCategory;
    private final ItemStackTemplate result;
    private final Ingredient ingredient;
    private final float experience;
    private final int cookingTime;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    @Nullable
    private String group;
    private final AetherCookingSerializer.CookieBaker<?> factory;

    public AetherCookingRecipeBuilder(RecipeCategory category, AetherBookCategory bookCategory, ItemStackTemplate result, Ingredient ingredient, float experience, int cookingTime, AetherCookingSerializer.CookieBaker<?> factory) {
        this.category = category;
        this.bookCategory = bookCategory;
        this.result = result;
        this.ingredient = ingredient;
        this.experience = experience;
        this.cookingTime = cookingTime;
        this.factory = factory;
    }

    public static AetherCookingRecipeBuilder generic(Ingredient ingredient, RecipeCategory category, ItemLike result, float experience, int cookingTime, RecipeSerializer<?> serializer, AetherCookingSerializer.CookieBaker<?> factory) {
        return new AetherCookingRecipeBuilder(category, determineRecipeCategory(serializer, category), new ItemStackTemplate(result.asItem()), ingredient, experience, cookingTime, factory);
    }

    @Override
    public AetherCookingRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    @Override
    public AetherCookingRecipeBuilder unlockedBy(String criterionName, Criterion<?> criterionTrigger) {
        this.advancementBuilder.unlockedBy(criterionName, criterionTrigger);
        return this;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> id) {
        AbstractCookingRecipe recipe = this.factory.create(Objects.requireNonNullElse(this.group, ""), this.bookCategory, this.ingredient, this.result, this.experience, this.cookingTime);
        recipeOutput.accept(id, recipe, this.advancementBuilder.build(recipeOutput, id, this.category));
    }

    private static AetherBookCategory determineRecipeCategory(RecipeSerializer<?> serializer, RecipeCategory category) {
        AetherBookCategory bookCategory;
        if (serializer == AetherRecipeSerializers.ENCHANTING.get()) {
            switch (category) {
                case BUILDING_BLOCKS, DECORATIONS -> bookCategory = AetherBookCategory.ENCHANTING_BLOCKS;
                case FOOD -> bookCategory = AetherBookCategory.ENCHANTING_FOOD;
                default -> bookCategory = AetherBookCategory.ENCHANTING_MISC;
            }
            return bookCategory;
        } else if (serializer == AetherRecipeSerializers.REPAIRING.get()) {
            return AetherBookCategory.ENCHANTING_REPAIR;
        } else if (serializer == AetherRecipeSerializers.FREEZING.get()) {
            switch (category) {
                case BUILDING_BLOCKS, DECORATIONS -> bookCategory = AetherBookCategory.FREEZABLE_BLOCKS;
                default -> bookCategory = AetherBookCategory.FREEZABLE_MISC;
            }
            return bookCategory;
        } else {
            throw new IllegalStateException("Unknown cooking recipe type; may not belong to the Aether");
        }
    }
}
