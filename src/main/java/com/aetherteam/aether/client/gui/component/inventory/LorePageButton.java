package com.aetherteam.aether.client.gui.component.inventory;

import com.aetherteam.nitrogen.fabric.BuilderMadeButton;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import com.aetherteam.aether.Aether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class LorePageButton extends BuilderMadeButton {
    private static final WidgetSprites BUTTON_TEXTURES = new WidgetSprites(Identifier.fromNamespaceAndPath(Aether.MODID, "menu/lore_button"), Identifier.fromNamespaceAndPath(Aether.MODID, "menu/lore_button_disabled"), Identifier.fromNamespaceAndPath(Aether.MODID, "menu/lore_button_highlighted"));

    public LorePageButton(Builder builder) {
        super(builder.createNarration(DEFAULT_NARRATION));
        this.active = false;
    }

    @Override
    public void onPress(InputWithModifiers input) {
        if (this.isActive()) {
            this.onPress.onPress(this);
        }
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        Identifier location = BUTTON_TEXTURES.get(this.isActive(), this.isHoveredOrFocused());
        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, location, this.getX(), this.getY(), this.width, this.height);
        int color = this.active ? 0xFFFFFF : 0xA0A0A0;
        guiGraphics.centeredText(fontRenderer, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, ARGB.opaque(color));
    }
}
