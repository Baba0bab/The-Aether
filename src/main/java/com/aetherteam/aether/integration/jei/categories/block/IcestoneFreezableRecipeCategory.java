package com.aetherteam.aether.integration.jei.categories.block;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.recipe.recipes.block.IcestoneFreezableRecipe;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.types.IRecipeType;

public class IcestoneFreezableRecipeCategory extends AbstractAetherBlockStateRecipeCategory<IcestoneFreezableRecipe> {
    public static final IRecipeType<IcestoneFreezableRecipe> RECIPE_TYPE = IRecipeType.create(Aether.MODID, "icestone_freezable", IcestoneFreezableRecipe.class);

    public IcestoneFreezableRecipeCategory(IGuiHelper guiHelper, IPlatformFluidHelper<?> fluidHelper) {
        super("icestone_freezable", RECIPE_TYPE, guiHelper.createDrawableItemLike(AetherBlocks.ICESTONE.get()), fluidHelper);
    }
}
