package com.aetherteam.nitrogen.fabric.mixin.client;

import com.aetherteam.nitrogen.fabric.client.events.FogAdjustmentHelper;
import com.aetherteam.nitrogen.fabric.client.events.FogColorHelper;
import com.aetherteam.nitrogen.fabric.client.events.FogEvents;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FogType;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {

    @WrapOperation(method = "setupFog", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/fog/environment/FogEnvironment;isApplicable(Lnet/minecraft/world/level/material/FogType;Lnet/minecraft/world/entity/Entity;)Z"))
    private boolean nitrogen_fabric$captureFogEnv(FogEnvironment instance, FogType fogType, Entity entity, Operation<Boolean> original, @Share(namespace = "nitrogen", value = "chosenEnv") LocalRef<FogEnvironment> ref) {
        var bl = original.call(instance, fogType, entity);

        if (bl) {
            ref.set(instance);
        }

        return bl;
    }

    // In 26.x, setupFog is an instance method that returns the final FogData, so the event is fired when it returns.
    @Inject(method = "setupFog", at = @At("RETURN"))
    private void nitrogen_fabric$onFogRenderering(Camera camera, int renderDistance, DeltaTracker deltaTracker, float darkenWorldAmount, ClientLevel level, CallbackInfoReturnable<FogData> cir,
                                                  @Share(namespace = "nitrogen", value = "chosenEnv") LocalRef<FogEnvironment> ref,
                                                  @Local FogType fogType) {
        float partialTick = deltaTracker.getGameTimeDeltaPartialTick(false);
        var helper = new FogAdjustmentHelper(camera, partialTick, ref.get(), fogType, cir.getReturnValue());

        FogEvents.ON_FOG_RENDER.invoker().onRenderer(helper);
    }

    // In 26.x, computeFogColor writes the color into a Vector4f that is passed to it, instead of creating one.
    @WrapOperation(method = "computeFogColor", at = @At(value = "INVOKE", target = "Lorg/joml/Vector4f;set(FFFF)Lorg/joml/Vector4f;"))
    private Vector4f nitrogen_fabric$adjustFogColor(Vector4f instance, float r, float g, float b, float a, Operation<Vector4f> original,
                                                    @Local(argsOnly = true) Camera activeRenderInfo, @Local(argsOnly = true, ordinal = 0) float partialTicks) {
        var helper = new FogColorHelper(activeRenderInfo, partialTicks, r, g, b);

        FogEvents.ON_FOG_COLORING.invoker().onColor(helper);

        return original.call(instance, helper.getRed(), helper.getGreen(), helper.getBlue(), a);
    }
}
