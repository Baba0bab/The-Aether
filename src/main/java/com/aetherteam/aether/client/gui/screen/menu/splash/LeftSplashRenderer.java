package com.aetherteam.aether.client.gui.screen.menu.splash;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.network.chat.Component;

public class LeftSplashRenderer extends SplashRenderer {
    private final Component splash;

    public LeftSplashRenderer(Component splash) {
        super(splash);
        this.splash = splash;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int screenWidth, Font font, float alpha) {
        AetherSplashRenderer.renderSplash(guiGraphics, font, this.splash, 267.0F, 58.0F, alpha);
    }
}
