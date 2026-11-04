package com.aetherteam.aether.integration.jei.categories.block;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.recipe.recipes.block.AmbrosiumRecipe;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.types.IRecipeType;

public class AmbrosiumRecipeCategory extends AbstractAetherBlockStateRecipeCategory<AmbrosiumRecipe> {
    public static final IRecipeType<AmbrosiumRecipe> RECIPE_TYPE = IRecipeType.create(Aether.MODID, "ambrosium_enchanting", AmbrosiumRecipe.class);

    public AmbrosiumRecipeCategory(IGuiHelper guiHelper, IPlatformFluidHelper<?> fluidHelper) {
        super("ambrosium_enchanting", RECIPE_TYPE, guiHelper.createDrawableItemLike(AetherItems.AMBROSIUM_SHARD.get()), fluidHelper);
    }
}
