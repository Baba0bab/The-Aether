package com.aetherteam.aether.recipe.recipes.item;

import com.aetherteam.aether.recipe.book.AetherBookCategory;
import com.aetherteam.aether.recipe.book.AetherRecipeBookCategories;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;

public abstract class AbstractAetherCookingRecipe extends AbstractCookingRecipe {
    private final AetherBookCategory category;

    public AbstractAetherCookingRecipe(String group, AetherBookCategory category, Ingredient ingredient, ItemStackTemplate result, float experience, int cookingTime) {
        super(new Recipe.CommonInfo(true), new AbstractCookingRecipe.CookingBookInfo(CookingBookCategory.MISC, group), ingredient, result, experience, cookingTime);
        this.category = category;
    }

    public AetherBookCategory aetherCategory() {
        return this.category;
    }

    @Override
    public ItemStackTemplate result() {
        return super.result();
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return switch (this.category) {
            case ENCHANTING_FOOD -> AetherRecipeBookCategories.ENCHANTING_FOOD.get();
            case ENCHANTING_BLOCKS -> AetherRecipeBookCategories.ENCHANTING_BLOCKS.get();
            case ENCHANTING_REPAIR -> AetherRecipeBookCategories.ENCHANTING_REPAIR.get();
            case FREEZABLE_BLOCKS -> AetherRecipeBookCategories.FREEZABLE_BLOCKS.get();
            case FREEZABLE_MISC -> AetherRecipeBookCategories.FREEZABLE_MISC.get();
            default -> AetherRecipeBookCategories.ENCHANTING_MISC.get();
        };
    }
}
