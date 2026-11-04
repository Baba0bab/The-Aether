package com.aetherteam.aether.client.gui.screen.menu;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.multiplayer.LevelLoadTracker;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import org.jetbrains.annotations.Nullable;

/**
 * A {@link LevelLoadingScreen} that displays the Aether Portal texture when travelling through an Aether Portal.
 */
public class AetherReceivingLevelScreen extends LevelLoadingScreen {
    private boolean isInAetherPortal;
    private float portalIntensity;
    private float oPortalIntensity;
    @Nullable
    private TextureAtlasSprite portalSprite;

    public AetherReceivingLevelScreen(LevelLoadTracker loadTracker, Reason reason) {
        super(loadTracker, reason);
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.portalProcess != null && Minecraft.getInstance().player.portalProcess.isSamePortal(AetherBlocks.AETHER_PORTAL.get())) {
            var data = Minecraft.getInstance().player.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER);
            this.isInAetherPortal = true;
            this.portalIntensity = data.getPortalIntensity();
            this.oPortalIntensity = data.getOldPortalIntensity();
        }
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.isInAetherPortal) {
            if (this.portalSprite == null) {
                this.portalSprite = Minecraft.getInstance().getModelManager().getBlockStateModelSet().getParticleMaterial(AetherBlocks.AETHER_PORTAL.get().defaultBlockState()).sprite();
            }
            guiGraphics.blitSprite(RenderPipelines.GUI_OPAQUE_TEXTURED_BACKGROUND, this.portalSprite, 0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight());
        } else {
            super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public void onClose() {
        if (Minecraft.getInstance().player != null && this.isInAetherPortal) {
            var data = Minecraft.getInstance().player.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER);
            data.portalIntensity = this.portalIntensity;
            data.oPortalIntensity = this.oPortalIntensity;
        }
        super.onClose();
    }
}
