package com.aetherteam.nitrogen.fabric.client.events;

import com.aetherteam.nitrogen.fabric.events.CancellableCallback;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;

public class LivingEntityRenderEvents {

    public static final Event<PreMainGeneric> BEFORE_RENDER = EventFactory.createArrayBacked(PreMainGeneric.class, invokers -> (renderer, state, poseStack, collector, cameraState, callback) -> {
        for (var invoker : invokers) invoker.beforeRendering(renderer, state, poseStack, collector, cameraState, callback);
    });

    public static final Event<PostMainGeneric> AFTER_RENDER = EventFactory.createArrayBacked(PostMainGeneric.class, invokers -> (renderer, state, poseStack, collector, cameraState) -> {
        for (var invoker : invokers) invoker.afterRendering(renderer, state, poseStack, collector, cameraState);
    });

    public interface PreMainGeneric {
        void beforeRendering(LivingEntityRenderer<?, ?, ?> renderer, LivingEntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState, CancellableCallback callback);
    }

    public interface PostMainGeneric {
        void afterRendering(LivingEntityRenderer<?, ?, ?> renderer, LivingEntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState);
    }
}
