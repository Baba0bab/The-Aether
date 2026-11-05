package com.aetherteam.aether.client.gui.component.skins;

import com.aetherteam.nitrogen.fabric.BuilderMadeButton;
import net.minecraft.client.renderer.RenderPipelines;
import com.aetherteam.aether.Aether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.Identifier;

public class RefreshButton extends BuilderMadeButton {
    public static final WidgetSprites REFRESH_WIDGET = new WidgetSprites(Identifier.fromNamespaceAndPath(Aether.MODID, "skins/refresh_button"), Identifier.fromNamespaceAndPath(Aether.MODID, "skins/refresh_button_highlighted"), Identifier.fromNamespaceAndPath(Aether.MODID, "skins/refresh_button_highlighted"));

    public static final int reboundMax = 1200;
    public static int reboundTimer = 0;

    public RefreshButton(Builder builder) {
        super(builder);
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Identifier location = REFRESH_WIDGET.get(this.isActive(), this.isHoveredOrFocused());
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, location, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        guiGraphics.centeredText(minecraft.font, this.getMessage(), this.getX() + (this.getWidth() / 2), this.getY() + (this.getHeight() / 2) - 4, 0xFFFFFFFF);
    }

    @Override
    public boolean isActive() {
        return super.isActive() && reboundTimer <= 0;
    }
}
