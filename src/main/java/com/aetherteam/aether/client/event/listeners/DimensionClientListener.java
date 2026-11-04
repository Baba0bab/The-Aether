package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.DimensionClientHooks;
import com.aetherteam.nitrogen.fabric.client.events.FogAdjustmentHelper;
import com.aetherteam.nitrogen.fabric.client.events.FogColorHelper;
import com.aetherteam.nitrogen.fabric.client.events.FogEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.world.level.material.FogType;
import org.apache.commons.lang3.tuple.Triple;

public class DimensionClientListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen(IEventBus bus) {
        bus.addListener(DimensionClientListener::onRenderFog);
        bus.addListener(DimensionClientListener::onRenderFogColor);
    }

    /**
     * @see DimensionClientHooks#renderNearFog(Camera, FogEnvironment, FogType, float)
     * @see DimensionClientHooks#reduceLavaFog(Camera, float)
     */
    public static void onRenderFog(FogAdjustmentHelper event) {
        Camera camera = event.getCamera();
        Float renderNearFog = DimensionClientHooks.renderNearFog(camera, event.getEnvironment(), event.getType(), event.getFarPlaneDistance());
        if (renderNearFog != null) {
            event.setNearPlaneDistance(renderNearFog);
        }
        Float reduceLavaFog = DimensionClientHooks.reduceLavaFog(camera, event.getNearPlaneDistance());
        if (reduceLavaFog != null) {
            event.setNearPlaneDistance(reduceLavaFog);
            event.setFarPlaneDistance(reduceLavaFog * 4);
        }
    }

    /**
     * @see DimensionClientHooks#renderFogColors(Camera, float, float, float)
     * @see DimensionClientHooks#adjustWeatherFogColors(Camera, float, float, float)
     */
    public static void onRenderFogColor(FogColorHelper event) {
        //if (true) return;
        Camera camera = event.getCamera();
        Triple<Float, Float, Float> renderFogColors = DimensionClientHooks.renderFogColors(camera, event.getRed(), event.getGreen(), event.getBlue());
        if (renderFogColors != null) {
            event.setRed(renderFogColors.getLeft());
            event.setGreen(renderFogColors.getMiddle());
            event.setBlue(renderFogColors.getRight());
        }
        Triple<Float, Float, Float> adjustWeatherFogColors = DimensionClientHooks.adjustWeatherFogColors(camera, event.getRed(), event.getGreen(), event.getBlue());
        if (adjustWeatherFogColors != null) {
            event.setRed(adjustWeatherFogColors.getLeft());
            event.setGreen(adjustWeatherFogColors.getMiddle());
            event.setBlue(adjustWeatherFogColors.getRight());
        }
    }
}
