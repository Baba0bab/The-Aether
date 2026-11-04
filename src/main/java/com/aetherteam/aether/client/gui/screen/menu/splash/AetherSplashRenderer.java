package com.aetherteam.aether.client.gui.screen.menu.splash;

import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import org.joml.Matrix3x2f;

public class AetherSplashRenderer extends SplashRenderer {
    private final boolean alignedLeft;
    private final Component splash;

    public AetherSplashRenderer(boolean alignedLeft, Component splash) {
        super(splash);
        this.alignedLeft = alignedLeft;
        this.splash = splash;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int screenWidth, Font font, float alpha) {
        float splashX = this.alignedLeft ? 205.0F : (screenWidth / 2.0F) + (165.0F / 2.0F);
        float splashY = this.alignedLeft ? 57.0F : 68.0F;
        renderSplash(guiGraphics, font, this.splash, splashX, splashY, alpha);
    }

    /**
     * [CODE COPY] - {@link SplashRenderer#extractRenderState(GuiGraphicsExtractor, int, Font, float)}.<br><br>
     * Modified to render at a custom position.
     */
    public static void renderSplash(GuiGraphicsExtractor guiGraphics, Font font, Component splash, float x, float y, float alpha) {
        int textWidth = font.width(splash);
        ActiveTextCollector textRenderer = guiGraphics.textRenderer();
        float textPhase = 1.8F - Mth.abs(Mth.sin((float) (Util.getMillis() % 1000L) / 1000.0F * Mth.TWO_PI) * 0.1F);
        float textScale = textPhase * 100.0F / (textWidth + 32);
        Matrix3x2f transform = new Matrix3x2f(textRenderer.defaultParameters().pose()).translate(x, y).rotate((float) (-Math.PI / 9)).scale(textScale);
        ActiveTextCollector.Parameters parameters = textRenderer.defaultParameters().withOpacity(alpha).withPose(transform);
        textRenderer.accept(TextAlignment.LEFT, -textWidth / 2, -8, parameters, splash);
    }
}
