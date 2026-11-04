package com.aetherteam.aether.block.portal;

import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.client.sound.PortalTriggerSoundInstance;
import com.aetherteam.aether.client.sound.FadeOutSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class PortalClientUtil {
    public static void handleAetherPortal(Player player, AetherPlayerAttachment attachment) {
        if (player instanceof LocalPlayer localPlayer) {
            if (!(Minecraft.getInstance().gui.screen() instanceof LevelLoadingScreen)) {
                attachment.oPortalIntensity = attachment.portalIntensity;
                float f = 0.0F;
                if (localPlayer.portalProcess != null && localPlayer.portalProcess.isInsidePortalThisTick() && localPlayer.portalProcess.isSamePortal(AetherBlocks.AETHER_PORTAL.get())) {
                    if (Minecraft.getInstance().gui.screen() != null
                        && !Minecraft.getInstance().gui.screen().isPauseScreen()
                        && !(Minecraft.getInstance().gui.screen() instanceof DeathScreen)
                        && !(Minecraft.getInstance().gui.screen() instanceof WinScreen)) {
                        if (Minecraft.getInstance().gui.screen() instanceof AbstractContainerScreen) {
                            localPlayer.closeContainer();
                        }

                        Minecraft.getInstance().gui.setScreen(null);
                    }

                    if (attachment.portalIntensity == 0.0F) {
                        playTriggerSound();
                    }

                    f = 0.0125F;
                    localPlayer.portalProcess.setAsInsidePortalThisTick(false);
                } else if (attachment.portalIntensity > 0.0F) {
                    f = -0.05F;
                }

                attachment.portalIntensity = Mth.clamp(attachment.portalIntensity + f, 0.0F, 1.0F);
            }
        }
    }

    public static void playTriggerSound() {
        Minecraft.getInstance().getSoundManager().play(PortalTriggerSoundInstance.forLocalAmbience(Minecraft.getInstance().player, AetherSoundEvents.BLOCK_AETHER_PORTAL_TRIGGER.get(), Minecraft.getInstance().level.getRandom().nextFloat() * 0.4F + 0.8F, 0.25F));
    }

    public static void playTravelSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forLocalAmbience(AetherSoundEvents.BLOCK_AETHER_PORTAL_TRAVEL.get(), Minecraft.getInstance().level.getRandom().nextFloat() * 0.4F + 0.8F, 0.25F));
    }

    public static void playAmbientSound(BlockPos pos, RandomSource random) {
        Minecraft.getInstance().getSoundManager().play(new FadeOutSoundInstance(AetherSoundEvents.BLOCK_AETHER_PORTAL_AMBIENT.get(), SoundSource.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, RandomSource.create(random.nextLong()), false, 0, SoundInstance.Attenuation.LINEAR, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, false));
    }
}
