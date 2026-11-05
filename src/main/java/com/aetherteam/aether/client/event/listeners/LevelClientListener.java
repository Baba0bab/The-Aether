package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.LevelClientHooks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;

public class LevelClientListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen() {
        LevelRenderEvents.COLLECT_SUBMITS.register(LevelClientListener::onSubmitCustomGeometry);
    }

    /**
     * @see LevelClientHooks#renderDungeonBlockOverlays(PoseStack, Camera, Frustum, Minecraft)
     */
    public static void onSubmitCustomGeometry(LevelRenderContext context) {
        LevelClientHooks.renderDungeonBlockOverlays(context.submitNodeCollector(), context.poseStack(), context.levelState().cameraRenderState, Minecraft.getInstance());
    }
}
