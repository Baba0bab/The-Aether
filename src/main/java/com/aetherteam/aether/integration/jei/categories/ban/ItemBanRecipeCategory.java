package com.aetherteam.aether.integration.jei.categories.ban;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.recipe.recipes.ban.ItemBanRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleRecipeInput;

public class ItemBanRecipeCategory extends AbstractPlacementBanRecipeCategory<ItemStack, Ingredient, SingleRecipeInput, ItemBanRecipe> {
    public static final IRecipeType<ItemBanRecipe> RECIPE_TYPE = IRecipeType.create(Aether.MODID, "item_placement_ban", ItemBanRecipe.class);

    public ItemBanRecipeCategory(IGuiHelper guiHelper, IPlatformFluidHelper<?> fluidHelper) {
        super(guiHelper, "item_placement_ban", RECIPE_TYPE, guiHelper.createDrawableItemLike(Items.FLINT_AND_STEEL), fluidHelper);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ItemBanRecipe recipe, IFocusGroup focusGroup) {
        boolean noBypass = recipe.getBypassBlock().isEmpty() || recipe.getBypassBlock().get().isEmpty();
        builder.addSlot(RecipeIngredientRole.INPUT, noBypass ? 50 : 1, 1).add(recipe.getIngredient())
            .addRichTooltipCallback((recipeSlotView, tooltip) -> this.populateAdditionalInformation(recipe, tooltip));
        super.setRecipe(builder, recipe, focusGroup);
    }
}
