package com.aetherteam.aether.client.renderer.entity;

import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import com.aetherteam.aether.client.renderer.entity.state.LightningKnifeRenderState;
import com.aetherteam.aether.entity.projectile.weapon.ThrownLightningKnife;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Quaternionf;

public class LightningKnifeRenderer extends EntityRenderer<ThrownLightningKnife, LightningKnifeRenderState> {
    private final ItemModelResolver itemModelResolver;

    public LightningKnifeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemModelResolver = context.getItemModelResolver();
        this.shadowRadius = 0.0F;
    }

    @Override
    public LightningKnifeRenderState createRenderState() {
        return new LightningKnifeRenderState();
    }

    @Override
    public void extractRenderState(ThrownLightningKnife entity, LightningKnifeRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.xRot = entity.getXRot(partialTick);
        reusedState.yRot = (-(entity.xRotO + (entity.getXRot() - entity.xRotO) * partialTick));
        this.itemModelResolver.updateForNonLiving(reusedState.item, entity.getItem(), ItemDisplayContext.GUI, entity);
    }

    /**
     * Rotates and renders the Lightning Knife to look as if it has been thrown.
     *
     * @param renderState    The {@link LightningKnifeRenderState} for the entity.
     * @param poseStack      The rendering {@link PoseStack}.
     * @param submitNodeCollector         The rendering {@link SubmitNodeCollector}.
     * @param packedLight    The {@link Integer} for the packed lighting for rendering.
     */
    @Override
    public void submit(LightningKnifeRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        int packedLight = renderState.lightCoords;
        poseStack.pushPose();
        Quaternionf quaternion = Axis.YP.rotationDegrees(renderState.yRot);
        quaternion.mul(Axis.XP.rotationDegrees(renderState.yRot - 90.0F));
        quaternion.mul(Axis.ZP.rotationDegrees(-135.0F));
        poseStack.rotate(quaternion);
        renderState.item.submit(poseStack, submitNodeCollector, packedLight, OverlayTexture.NO_OVERLAY, renderState.outlineColor);
        poseStack.popPose();
        super.submit(renderState, poseStack, submitNodeCollector, camera);
    }

}
