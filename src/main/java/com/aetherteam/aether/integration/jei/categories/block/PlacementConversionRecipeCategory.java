package com.aetherteam.aether.integration.jei.categories.block;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.recipe.recipes.block.PlacementConversionRecipe;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.types.IRecipeType;

public class PlacementConversionRecipeCategory extends AbstractBiomeParameterRecipeCategory<PlacementConversionRecipe> {
    public static final IRecipeType<PlacementConversionRecipe> RECIPE_TYPE = IRecipeType.create(Aether.MODID, "placement_conversion", PlacementConversionRecipe.class);

    public PlacementConversionRecipeCategory(IGuiHelper helper, IPlatformFluidHelper<?> fluidHelper) {
        super("placement_conversion", RECIPE_TYPE, helper.createDrawableItemLike(AetherItems.AETHER_PORTAL_FRAME.get()), fluidHelper);
    }
}
