package com.aetherteam.aether.client.gui.screen.inventory;

import net.minecraft.client.Minecraft;
import com.aetherteam.aether.attachment.AetherTimeAttachment;
import net.minecraft.client.renderer.RenderPipelines;
import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.component.inventory.SunAltarSlider;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;

public class SunAltarScreen extends Screen {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/gui/menu/sun_altar.png");
    private final int timeScale;

    public SunAltarScreen(Component title, int timeScale) {
        super(title);
        this.timeScale = timeScale;
    }

    @Override
    public void init() {
        super.init();
        if (Minecraft.getInstance().level != null) {
            double sliderValue = (AetherTimeAttachment.getClockTime(Minecraft.getInstance().level) % AetherTimeAttachment.DAY_LENGTH) / (double) AetherTimeAttachment.DAY_LENGTH; // What position the slider bar should be at.
            this.addRenderableWidget(new SunAltarSlider(this.width / 2 - 75, this.height / 2, 150, 20, Component.translatable("gui.aether.sun_altar.time"), sliderValue, this.timeScale));
        }
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.extractTransparentBackground(guiGraphics);
        int xSize = 176;
        int ySize = 79;
        int x = (this.width - xSize) / 2;
        int y = (this.height - ySize) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, xSize, ySize, 256, 256);

        FormattedCharSequence sequence = this.title.getVisualOrderText();
        guiGraphics.text(this.font, this.title, (int) ((this.width - this.font.width(sequence)) / 2.0F), y + 20, 0xFF404040, false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
