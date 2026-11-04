package com.aetherteam.aether.integration.jei.categories.block;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.recipe.recipes.block.SwetBallRecipe;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.types.IRecipeType;

public class SwetBallRecipeCategory extends AbstractBiomeParameterRecipeCategory<SwetBallRecipe> {
    public static final IRecipeType<SwetBallRecipe> RECIPE_TYPE = IRecipeType.create(Aether.MODID, "swet_ball_conversion", SwetBallRecipe.class);

    public SwetBallRecipeCategory(IGuiHelper guiHelper, IPlatformFluidHelper<?> fluidHelper) {
        super("swet_ball_conversion", RECIPE_TYPE, guiHelper.createDrawableItemLike(AetherItems.SWET_BALL.get()), fluidHelper);
    }
}
