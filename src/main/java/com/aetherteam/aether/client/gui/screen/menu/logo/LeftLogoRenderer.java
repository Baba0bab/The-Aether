package com.aetherteam.aether.client.gui.screen.menu.logo;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.LogoRenderer;

/**
 * Renders the vanilla logo aligned to the left side of the screen.
 */
public class LeftLogoRenderer extends LogoRenderer {
    public int screenWidth;

    public LeftLogoRenderer(boolean keepLogoThroughFade) {
        super(keepLogoThroughFade);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int width, float alpha, int heightOffset) {
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(-(width / 2) + 144, -11);
        super.extractRenderState(guiGraphics, width, alpha, heightOffset);
        guiGraphics.pose().popMatrix();
    }
}
