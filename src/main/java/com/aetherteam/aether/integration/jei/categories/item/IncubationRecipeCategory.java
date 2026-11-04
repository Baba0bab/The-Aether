package com.aetherteam.aether.integration.jei.categories.item;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.recipe.recipes.item.IncubationRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

public class IncubationRecipeCategory extends AbstractAetherCookingRecipeCategory<IncubationRecipe> {
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/gui/menu/incubator.png");
    public static final IRecipeType<IncubationRecipe> RECIPE_TYPE = IRecipeType.create(Aether.MODID, "incubation", IncubationRecipe.class);

    public IncubationRecipeCategory(IGuiHelper guiHelper) {
        super(RECIPE_TYPE, "incubating",
            guiHelper.createDrawable(TEXTURE, 72, 16, 70, 54),
            guiHelper.createDrawableItemLike(AetherBlocks.INCUBATOR.get()),
            guiHelper.drawableBuilder(FLAME_TEXTURE, 0, 0, 14, 14).setTextureSize(14, 14).build(),
            guiHelper.createAnimatedDrawable(guiHelper.drawableBuilder(INCUBATION_PROGRESS_TEXTURE, 0, 0, 10, 54).setTextureSize(10, 54).build(), 5700, IDrawableAnimated.StartDirection.BOTTOM, false));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IncubationRecipe recipe, IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).add(recipe.input());
    }

    @Override
    public void draw(IncubationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
        this.animatedProgressArrow.draw(guiGraphics, 31, 0);
        this.fuelIndicator.draw(guiGraphics, 1, 20);
        this.drawCookingTime(guiGraphics, 45, recipe.incubationTime());
    }
}
