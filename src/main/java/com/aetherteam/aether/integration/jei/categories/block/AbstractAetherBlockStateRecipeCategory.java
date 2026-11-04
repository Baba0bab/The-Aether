package com.aetherteam.aether.integration.jei.categories.block;

import com.aetherteam.nitrogen.integration.jei.categories.block.AbstractBlockStateRecipeCategory;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.network.chat.Component;

public abstract class AbstractAetherBlockStateRecipeCategory<T extends AbstractBlockStateRecipe> extends AbstractBlockStateRecipeCategory<T> {
    public AbstractAetherBlockStateRecipeCategory(String id, IRecipeType<T> recipeType, IDrawable icon, IPlatformFluidHelper<?> fluidHelper) {
        super(recipeType, Component.translatable("gui.aether.jei." + id), icon, fluidHelper);
    }
}
