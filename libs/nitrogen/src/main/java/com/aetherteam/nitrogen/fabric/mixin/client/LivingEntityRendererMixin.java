package com.aetherteam.nitrogen.fabric.mixin.client;

import com.aetherteam.nitrogen.fabric.client.events.LivingEntityRenderEvents;
import com.aetherteam.nitrogen.fabric.events.CancellableCallbackImpl;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends EntityRenderer<T, S>{
    protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @WrapMethod(method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V")
    private void nitrogen_fabric$onLivingEntityRenderer(S state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState, Operation<Void> original) {
        var callback = new CancellableCallbackImpl();

        LivingEntityRenderEvents.BEFORE_RENDER.invoker().beforeRendering((LivingEntityRenderer) (Object) this, state, poseStack, collector, cameraState, callback);

        if (callback.isCanceled()) return;

        original.call(state, poseStack, collector, cameraState);

        LivingEntityRenderEvents.AFTER_RENDER.invoker().afterRendering((LivingEntityRenderer) (Object) this, state, poseStack, collector, cameraState);
    }

    @WrapOperation(
        method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isPassenger()Z")
    )
    private boolean nitrogen_fabric$adjustPassengerCheck(LivingEntity instance, Operation<Boolean> original) {
        return original.call(instance) && (instance.getVehicle() != null && instance.getVehicle().nitrogen_fabric$shouldRiderSit());
    }
}
