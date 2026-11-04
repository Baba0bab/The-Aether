package com.aetherteam.nitrogen.fabric.client.events;

import com.aetherteam.nitrogen.fabric.events.CancellableCallback;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerRenderEvents {

    public static final Event<PreMain> BEFORE_RENDER = EventFactory.createArrayBacked(PreMain.class, invokers -> (renderer, state, poseStack, collector, cameraState, callback) -> {
        for (var invoker : invokers) invoker.beforeRendering(renderer, state, poseStack, collector, cameraState, callback);
    });

    public static final Event<PostMain> AFTER_RENDER = EventFactory.createArrayBacked(PostMain.class, invokers -> (renderer, state, poseStack, collector, cameraState) -> {
        for (var invoker : invokers) invoker.afterRendering(renderer, state, poseStack, collector, cameraState);
    });

    public static final Event<PreArm> BEFORE_ARM_RENDER = EventFactory.createArrayBacked(PreArm.class, invokers -> (renderer, player, poseStack, collector, packedLight, arm, callback) -> {
        for (var invoker : invokers) invoker.beforeRendering(renderer, player, poseStack, collector, packedLight, arm, callback);
    });

    public static final Event<PostArm> AFTER_ARM_RENDER = EventFactory.createArrayBacked(PostArm.class, invokers -> (renderer, player, poseStack, collector, packedLight, arm) -> {
        for (var invoker : invokers) invoker.afterRendering(renderer, player, poseStack, collector, packedLight, arm);
    });

    public interface PreMain {
        void beforeRendering(AvatarRenderer<?> renderer, AvatarRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState, CancellableCallback callback);
    }

    public interface PostMain {
        void afterRendering(AvatarRenderer<?> renderer, AvatarRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState);
    }

    public interface PreArm {
        void beforeRendering(AvatarRenderer<?> renderer, @Nullable Player player, PoseStack poseStack, SubmitNodeCollector collector, int packedLight, HumanoidArm arm, CancellableCallback callback);
    }

    public interface PostArm {
        void afterRendering(AvatarRenderer<?> renderer, @Nullable Player player, PoseStack poseStack, SubmitNodeCollector collector, int packedLight, HumanoidArm arm);
    }

    static {
        LivingEntityRenderEvents.BEFORE_RENDER.register((renderer, state, poseStack, collector, cameraState, callback) -> {
            if (!(renderer instanceof AvatarRenderer<?> avatarRenderer) || !(state instanceof AvatarRenderState avatarState)) return;

            BEFORE_RENDER.invoker().beforeRendering(avatarRenderer, avatarState, poseStack, collector, cameraState, callback);
        });

        LivingEntityRenderEvents.AFTER_RENDER.register((renderer, state, poseStack, collector, cameraState) -> {
            if (!(renderer instanceof AvatarRenderer<?> avatarRenderer) || !(state instanceof AvatarRenderState avatarState)) return;

            AFTER_RENDER.invoker().afterRendering(avatarRenderer, avatarState, poseStack, collector, cameraState);
        });
    }
}
