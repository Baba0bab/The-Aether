package com.aetherteam.aether.client.gui.screen.menu.logo;

import net.minecraft.client.renderer.RenderPipelines;
import com.aetherteam.aether.Aether;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class AetherLogoRenderer extends LogoRenderer {
    private static final Identifier AETHER_LOGO = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/gui/title/aether.png");
    private final boolean keepLogoThroughFade;
    private final boolean alignedLeft;

    public AetherLogoRenderer(boolean keepLogoThroughFade, boolean alignedLeft) {
        super(keepLogoThroughFade);
        this.keepLogoThroughFade = keepLogoThroughFade;
        this.alignedLeft = alignedLeft;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int screenWidth, float transparency, int height) {
        float fade = this.keepLogoThroughFade ? 1.0F : transparency;
        int color = ARGB.white(fade);
        int logoX = this.alignedLeft ? 28 : (int) ((screenWidth / 2.0F - (190.0F / 2.0F)));
        int logoY = this.alignedLeft ? 25 : 36;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, AETHER_LOGO, logoX, logoY, 0.0F, 0.0F, 190, 38, 190, 38, color);
    }
}
