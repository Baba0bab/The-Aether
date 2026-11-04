package com.aetherteam.nitrogen.fabric.mixin.client;

import com.aetherteam.nitrogen.fabric.client.events.PlayerRenderEvents;
import com.aetherteam.nitrogen.fabric.events.CancellableCallbackImpl;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AvatarRenderer.class)
public abstract class AvatarRendererMixin {
    @WrapMethod(method = "renderLeftHand")
    private void nitrogen_fabric$onRenderLeftHand(PoseStack poseStack, SubmitNodeCollector collector, int packedLight, Identifier skinTexture, boolean isSleeveVisible, Operation<Void> original) {
        this.nitrogen_fabric$renderHand(HumanoidArm.LEFT, poseStack, collector, packedLight, skinTexture, isSleeveVisible, original);
    }

    @WrapMethod(method = "renderRightHand")
    private void nitrogen_fabric$onRenderRightHand(PoseStack poseStack, SubmitNodeCollector collector, int packedLight, Identifier skinTexture, boolean isSleeveVisible, Operation<Void> original) {
        this.nitrogen_fabric$renderHand(HumanoidArm.RIGHT, poseStack, collector, packedLight, skinTexture, isSleeveVisible, original);
    }

    private void nitrogen_fabric$renderHand(HumanoidArm arm, PoseStack poseStack, SubmitNodeCollector collector, int packedLight, Identifier skinTexture, boolean isSleeveVisible, Operation<Void> original) {
        var player = Minecraft.getInstance().player;
        var renderer = (AvatarRenderer<?>) (Object) this;
        var callback = new CancellableCallbackImpl();

        PlayerRenderEvents.BEFORE_ARM_RENDER.invoker().beforeRendering(renderer, player, poseStack, collector, packedLight, arm, callback);

        if (callback.isCanceled()) return;

        original.call(poseStack, collector, packedLight, skinTexture, isSleeveVisible);

        PlayerRenderEvents.AFTER_ARM_RENDER.invoker().afterRendering(renderer, player, poseStack, collector, packedLight, arm);
    }
}
