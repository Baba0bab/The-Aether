package com.aetherteam.aether.integration.jei.categories.item;

import com.aetherteam.aether.Aether;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public abstract class AbstractAetherCookingRecipeCategory<T> extends AbstractRecipeCategory<T> {
    public static final Identifier FLAME_TEXTURE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/gui/sprites/menu/lit_progress.png");
    public static final Identifier ARROW_TEXTURE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/gui/sprites/menu/burn_progress.png");
    public static final Identifier INCUBATION_PROGRESS_TEXTURE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/gui/sprites/menu/incubation_progress.png");
    protected final IDrawable background;
    protected final IDrawable fuelIndicator;
    protected final IDrawableAnimated animatedProgressArrow;

    public AbstractAetherCookingRecipeCategory(IRecipeType<T> recipeType, String id, IDrawable background, IDrawable icon, IDrawable fuelIndicator, IDrawableAnimated animatedProgressArrow) {
        super(recipeType, Component.translatable("gui.aether.jei." + id), icon, background.getWidth(), background.getHeight());
        this.background = background;
        this.fuelIndicator = fuelIndicator;
        this.animatedProgressArrow = animatedProgressArrow;
    }

    protected void drawExperience(float experience, GuiGraphicsExtractor guiGraphics, int y) {
        if (experience > 0) {
            Component experienceString = Component.translatable("gui.jei.category.smelting.experience", experience);
            Font font = Minecraft.getInstance().font;
            guiGraphics.text(font, experienceString.getVisualOrderText(), this.getWidth() - font.width(experienceString), y, 0xFF808080, false);
        }
    }

    protected void drawCookingTime(GuiGraphicsExtractor guiGraphics, int y, int time) {
        if (time > 0) {
            Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", time / 20);
            Font font = Minecraft.getInstance().font;
            guiGraphics.text(font, timeString.getVisualOrderText(), this.getWidth() - font.width(timeString), y, 0xFF808080, false);
        }
    }
}
