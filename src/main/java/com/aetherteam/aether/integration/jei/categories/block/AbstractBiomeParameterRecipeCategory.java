package com.aetherteam.aether.integration.jei.categories.block;

import com.aetherteam.aether.integration.jei.categories.BiomeTooltip;
import com.aetherteam.aether.recipe.recipes.block.AbstractBiomeParameterRecipe;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;

public abstract class AbstractBiomeParameterRecipeCategory<T extends AbstractBiomeParameterRecipe> extends AbstractAetherBlockStateRecipeCategory<T> implements BiomeTooltip {
    public AbstractBiomeParameterRecipeCategory(String id, IRecipeType<T> recipeType, IDrawable icon, IPlatformFluidHelper<?> fluidHelper) {
        super(id, recipeType, icon, fluidHelper);
    }

    @Override
    protected void populateAdditionalInformation(T recipe, ITooltipBuilder tooltip) {
        if (Minecraft.getInstance().level != null && recipe.getBiome().isPresent()) {
            this.populateBiomeInformation(recipe.getBiome().get().left().orElse(null), recipe.getBiome().get().right().orElse(null), tooltip);
        }
    }
}
