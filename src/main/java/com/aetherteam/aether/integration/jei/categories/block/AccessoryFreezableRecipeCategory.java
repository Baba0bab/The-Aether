package com.aetherteam.aether.integration.jei.categories.block;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.recipe.recipes.block.AccessoryFreezableRecipe;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.types.IRecipeType;

public class AccessoryFreezableRecipeCategory extends AbstractAetherBlockStateRecipeCategory<AccessoryFreezableRecipe> {
    public static final IRecipeType<AccessoryFreezableRecipe> RECIPE_TYPE = IRecipeType.create(Aether.MODID, "accessory_freezable", AccessoryFreezableRecipe.class);

    public AccessoryFreezableRecipeCategory(IGuiHelper guiHelper, IPlatformFluidHelper<?> fluidHelper) {
        super("accessory_freezable", RECIPE_TYPE, guiHelper.createDrawableItemLike(AetherItems.ICE_RING.get()), fluidHelper);
    }
}
