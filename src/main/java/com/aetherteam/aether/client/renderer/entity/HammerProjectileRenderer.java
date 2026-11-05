package com.aetherteam.aether.client.renderer.entity;

import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.entity.state.HammerRenderState;
import com.aetherteam.aether.entity.projectile.weapon.HammerProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class HammerProjectileRenderer extends EntityRenderer<HammerProjectile, HammerRenderState> {
    public static final Identifier KINGBDOGZ_WAVE_TEXTURE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/entity/projectile/kingbdogz_wave.png");
    public static final Identifier JEB_WAVE_TEXTURE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/entity/projectile/jeb_wave.png");

    public HammerProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    @Override
    public HammerRenderState createRenderState() {
        return new HammerRenderState();
    }

    @Override
    public void extractRenderState(HammerProjectile entity, HammerRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.jeb = entity.getIsJeb();
    }

    /**
     * [VANILLA COPY] - {@link net.minecraft.client.renderer.entity.DragonFireballRenderer}.
     */
    @Override
    public void submit(HammerRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        int packedLight = renderState.lightCoords;
        poseStack.pushPose();
        poseStack.rotate(camera.orientation);
        submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.entityCutout(this.getTextureLocation(renderState)), (pose, consumer) -> {
            vertex(consumer, pose, packedLight, 0.0F, 0.0F, 0.0F, 1.0F);
            vertex(consumer, pose, packedLight, 1.0F, 0.0F, 1.0F, 1.0F);
            vertex(consumer, pose, packedLight, 1.0F, 1.0F, 1.0F, 0.0F);
            vertex(consumer, pose, packedLight, 0.0F, 1.0F, 0.0F, 0.0F);
        });
        poseStack.popPose();
        super.submit(renderState, poseStack, submitNodeCollector, camera);
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight, float offsetX, float offsetY, float textureX, float textureY) {
        consumer.addVertex(pose, offsetX - 0.5F, offsetY - 0.25F, 0.0F)
            .setColor(-1)
            .setUv(textureX, textureY)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(packedLight)
            .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    /**
     * Checks whether the projectile should use the Kingbdogz or Jeb hammer texture.
     *
     * @param renderState The {@link HammerRenderState} entity.
     * @return The texture {@link Identifier}.
     */
    public Identifier getTextureLocation(HammerRenderState renderState) {
        return !renderState.jeb ? KINGBDOGZ_WAVE_TEXTURE : JEB_WAVE_TEXTURE;
    }
}
