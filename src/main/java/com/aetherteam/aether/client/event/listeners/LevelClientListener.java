package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.LevelClientHooks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;

public class LevelClientListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen(IEventBus bus) {
        bus.addListener(LevelClientListener::onSubmitCustomGeometry);
    }

    /**
     * @see LevelClientHooks#renderDungeonBlockOverlays(SubmitNodeCollector, PoseStack, CameraRenderState, Minecraft)
     */
    public static void onSubmitCustomGeometry(SubmitCustomGeometryEvent event) {
        LevelClientHooks.renderDungeonBlockOverlays(event.getSubmitNodeCollector(), event.getPoseStack(), event.getLevelRenderState().cameraRenderState, Minecraft.getInstance());
    }
}
