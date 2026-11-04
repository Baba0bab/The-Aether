package com.aetherteam.aether.client.renderer.entity;

import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import com.aetherteam.aether.client.renderer.entity.state.CrystalRenderState;
import com.aetherteam.aether.entity.projectile.crystal.AbstractCrystal;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class IceCrystalRenderer<T extends AbstractCrystal> extends CloudCrystalRenderer<T> {
    public IceCrystalRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void submit(CrystalRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        int packedLight = renderState.lightCoords;
        poseStack.pushPose();
        poseStack.translate(0, 0.25, 0);
        super.submit(renderState, poseStack, submitNodeCollector, camera);
        poseStack.popPose();
    }
}
