package com.aetherteam.aether.client.gui.component.menu;

import org.jetbrains.annotations.Nullable;
import net.minecraft.util.ARGB;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import org.joml.Matrix3x2fStack;
import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.screen.menu.AetherTitleScreen;
import com.aetherteam.aether.mixin.mixins.client.accessor.ButtonAccessor;
import com.aetherteam.nitrogen.fabric.BuilderMadeButton;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class AetherMenuButton extends Button {
    private static final WidgetSprites AETHER_WIDGETS = new WidgetSprites(Identifier.fromNamespaceAndPath(Aether.MODID, "title/button"), Identifier.fromNamespaceAndPath(Aether.MODID, "title/button_highlighted"));
    private static final WidgetSprites AETHER_WIDGETS_SMALL = new WidgetSprites(Identifier.fromNamespaceAndPath(Aether.MODID, "title/button"), Identifier.fromNamespaceAndPath(Aether.MODID, "title/button_highlighted_small"));
    public final int originalX;
    public final int originalY;
    public int hoverOffset;
    public int buttonCountOffset;
    public boolean serverButton;
    @Nullable
    private final Button wrappedButton;

    public AetherMenuButton(AetherTitleScreen screen, Builder builder) {
        this(screen, builder, null);
    }

    private AetherMenuButton(AetherTitleScreen screen, Builder builder, @Nullable Button wrappedButton) {
        super(builder);
        this.wrappedButton = wrappedButton;
        this.originalX = this.getX();
        this.originalY = this.getY();
        this.hoverOffset = 0;
    }

    public AetherMenuButton(AetherTitleScreen screen, Button oldButton) {
        this(screen, new Builder(oldButton.getMessage(), (button) -> { }).bounds(oldButton.getX(), oldButton.getY(), oldButton.getWidth(), oldButton.getHeight()).createNarration((button) -> ((ButtonAccessor) oldButton).callCreateNarrationMessage()), oldButton);
        oldButton.visible = false;
        oldButton.active = false;
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        Matrix3x2fStack poseStack = guiGraphics.pose();
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;

        Identifier location = this.getWidth() < 100 ? AETHER_WIDGETS_SMALL.get(this.isActive(), this.isHoveredOrFocused()) : AETHER_WIDGETS.get(this.isActive(), this.isHoveredOrFocused());

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, location, 200, 20, 0, 0, this.getX() + this.hoverOffset, this.getY(), 200, 20);

        poseStack.pushMatrix();
        float textX = this.getX() + 35 + this.hoverOffset;
        float textY = this.getY() + (this.height - 8) / 2.0F;
        poseStack.translate(textX, textY);
        guiGraphics.text(font, this.getMessage(), 0, 0, ARGB.color(Mth.ceil(this.alpha * 255.0F), this.getTextColor(mouseX, mouseY)));
        poseStack.popMatrix();
    }

    @Override
    public void onPress(InputWithModifiers input) {
        if (this.wrappedButton != null) {
            this.wrappedButton.onPress(input);
        } else {
            super.onPress(input);
        }
    }

    /**
     * Determines the color for the button text depending on if its hovered over.
     *
     * @param mouseX The {@link Integer} for the mouse's x-position.
     * @param mouseY The {@link Integer} for the mouse's y-position.
     * @return The decimal {@link Integer} for the color.
     */
    public int getTextColor(int mouseX, int mouseY) {
        if (!this.serverButton) {
            return this.isMouseOver(mouseX, mouseY) ? 11842776 : 13948116;
        } else {
            return this.isMouseOver(mouseX, mouseY) ? 13746759 : 15457113;
        }
    }
}
