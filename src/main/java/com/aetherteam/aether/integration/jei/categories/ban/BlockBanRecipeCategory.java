package com.aetherteam.aether.integration.jei.categories.ban;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.recipe.recipes.ban.BlockBanRecipe;
import com.aetherteam.nitrogen.integration.jei.BlockStateIngredientRenderer;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.input.BlockStateRecipeInput;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BlockBanRecipeCategory extends AbstractPlacementBanRecipeCategory<BlockState, BlockStateIngredient, BlockStateRecipeInput, BlockBanRecipe> {
    public static final IRecipeType<BlockBanRecipe> RECIPE_TYPE = IRecipeType.create(Aether.MODID, "block_placement_ban", BlockBanRecipe.class);

    public BlockBanRecipeCategory(IGuiHelper guiHelper, IPlatformFluidHelper<?> fluidHelper) {
        super(guiHelper, "block_placement_ban", RECIPE_TYPE, guiHelper.createDrawableItemLike(Blocks.TORCH), fluidHelper);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BlockBanRecipe recipe, IFocusGroup focusGroup) {
        BlockPropertyPair[] pairs = recipe.getIngredient().getPairs();
        boolean noBypass = recipe.getBypassBlock().isEmpty() || recipe.getBypassBlock().get().isEmpty();
        builder.addSlot(RecipeIngredientRole.INPUT, noBypass ? 50 : 1, 1).addIngredientsUnsafe(this.setupIngredients(pairs))
            .setCustomRenderer(VanillaTypes.ITEM_STACK, new BlockStateIngredientRenderer(pairs))
            .addRichTooltipCallback((recipeSlotView, tooltip) -> this.populateAdditionalInformation(recipe, tooltip));
        super.setRecipe(builder, recipe, focusGroup);
    }
}
