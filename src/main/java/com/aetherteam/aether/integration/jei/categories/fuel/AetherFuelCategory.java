package com.aetherteam.aether.integration.jei.categories.fuel;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.stream.Stream;

/**
 * [CODE COPY] - Nitrogen's fuel category, displaying the fuels of the Altar, Freezer, and Incubator.
 */
public class AetherFuelCategory extends AbstractRecipeCategory<AetherFuelRecipe> {
    public static final Identifier ICON_TEXTURE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/gui/sprites/menu/lit_progress_transparent.png");
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/gui/menu/altar.png");
    public static final IRecipeType<AetherFuelRecipe> RECIPE_TYPE = IRecipeType.create(Aether.MODID, "fuel", AetherFuelRecipe.class);
    private static final int BACKGROUND_HEIGHT = 34;
    private static final int TEXT_PADDING = 20;

    private final IDrawable background;
    private final LoadingCache<Integer, IDrawableAnimated> cachedFuelIndicator;

    public AetherFuelCategory(IGuiHelper helper) {
        this(helper, helper.drawableBuilder(TEXTURE, 55, 36, 18, BACKGROUND_HEIGHT).addPadding(0, 0, 0, TEXT_PADDING + maxTextWidth()).build());
    }

    private AetherFuelCategory(IGuiHelper helper, IDrawable background) {
        super(RECIPE_TYPE, Component.translatable("gui." + Aether.MODID + ".jei.fuel"), helper.drawableBuilder(ICON_TEXTURE, 0, 0, 14, 14).setTextureSize(14, 14).build(), background.getWidth(), background.getHeight());
        this.background = background;
        this.cachedFuelIndicator = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<>() {
            @Override
            public IDrawableAnimated load(Integer burnTime) {
                return helper.drawableBuilder(ICON_TEXTURE, 0, 0, 14, 14).setTextureSize(14, 14).buildAnimated(burnTime, IDrawableAnimated.StartDirection.TOP, true);
            }
        });
    }

    private static int maxTextWidth() {
        Font font = Minecraft.getInstance().font;
        return Stream.of(AetherBlocks.ALTAR.get(), AetherBlocks.FREEZER.get(), AetherBlocks.INCUBATOR.get())
                .mapToInt((block) -> font.width(createBurnTimeText(10000, block.getName())))
                .max().orElse(0);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AetherFuelRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 17).addItemStacks(recipe.inputs());
    }

    @Override
    public void draw(AetherFuelRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
        this.cachedFuelIndicator.getUnchecked(recipe.burnTime()).draw(guiGraphics, 2, -1);
        Font font = Minecraft.getInstance().font;
        Component burnTimeText = createBurnTimeText(recipe.burnTime(), recipe.usage().getName());
        guiGraphics.text(font, burnTimeText.getVisualOrderText(), this.getWidth() - font.width(burnTimeText), 14, 0xFF808080, false);
    }

    private static Component createBurnTimeText(int burnTime, Component usage) {
        return Component.translatable("gui.jei.category.smelting.time.seconds", burnTime / 20).append(" (").append(usage).append(")");
    }
}
