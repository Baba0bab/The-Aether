package com.aetherteam.aether.integration.jei.categories.item;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.recipe.recipes.item.AltarRepairRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class AltarRepairRecipeCategory extends AbstractAetherCookingRecipeCategory<AltarRepairRecipe> {
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/gui/menu/altar.png");
    public static final IRecipeType<AltarRepairRecipe> RECIPE_TYPE = IRecipeType.create(Aether.MODID, "repairing", AltarRepairRecipe.class);

    public AltarRepairRecipeCategory(IGuiHelper guiHelper) {
        super(RECIPE_TYPE, "altar.repairing",
            guiHelper.createDrawable(TEXTURE, 55, 16, 82, 54),
            guiHelper.createDrawableItemLike(AetherBlocks.ALTAR.get()),
            guiHelper.drawableBuilder(FLAME_TEXTURE, 0, 0, 14, 14).setTextureSize(14, 14).build(),
            guiHelper.createAnimatedDrawable(guiHelper.drawableBuilder(ARROW_TEXTURE, 0, 0, 24, 16).setTextureSize(24, 16).build(), 100, IDrawableAnimated.StartDirection.LEFT, false));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AltarRepairRecipe recipe, IFocusGroup focusGroup) {
        ItemStack damagedItem = recipe.input().items().findFirst().map((item) -> new ItemStack(item)).orElse(ItemStack.EMPTY);
        damagedItem.setDamageValue(damagedItem.getMaxDamage() * 3 / 4);
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).add(damagedItem);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 19).add(recipe.input().items().findFirst().map((item) -> new ItemStack(item)).orElse(ItemStack.EMPTY));
    }

    @Override
    public void draw(AltarRepairRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
        this.animatedProgressArrow.draw(guiGraphics, 24, 18);
        this.fuelIndicator.draw(guiGraphics, 1, 20);
        this.drawExperience(recipe.experience(), guiGraphics, 1);
        this.drawCookingTime(guiGraphics, 45, recipe.cookingTime());
    }
}
