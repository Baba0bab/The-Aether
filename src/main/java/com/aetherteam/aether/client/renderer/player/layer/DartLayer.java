package com.aetherteam.aether.client.renderer.player.layer;

import com.aetherteam.aether.entity.projectile.dart.AbstractDart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;

/**
 * Renders {@link AbstractDart}s stuck in the body of the player, similar to arrows.
 */
public class DartLayer<M extends PlayerModel> extends RenderLayer<AvatarRenderState, M> {
    private final RenderStateDataKey<Integer> dartCount;
    private final ArrowModel model;
    private final ArrowRenderState modelState = new ArrowRenderState();
    private final Identifier texture;
    private final float offset;

    public DartLayer(LivingEntityRenderer<?, AvatarRenderState, M> renderer, EntityModelSet modelSet, RenderStateDataKey<Integer> dartCount, Identifier texture, float offset) {
        super(renderer);
        this.dartCount = dartCount;
        this.model = new ArrowModel(modelSet.bakeLayer(ModelLayers.ARROW));
        this.texture = texture;
        this.offset = offset;
    }

    /**
     * [CODE COPY] - {@link net.minecraft.client.renderer.entity.layers.StuckInBodyLayer#submit(PoseStack, SubmitNodeCollector, int, AvatarRenderState, float, float)}.<br><br>
     * Modified to offset the random positions so darts don't overlap with arrows.
     */
    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, AvatarRenderState renderState, float netHeadYaw, float headPitch) {
        int count = this.numStuck(renderState);
        if (count > 0) {
            RandomSource random = RandomSource.create((long) (renderState.id * (0.25 * this.offset)));
            for (int i = 0; i < count; ++i) {
                poseStack.pushPose();
                ModelPart modelPart = this.getParentModel().getRandomBodyPart(random);
                ModelPart.Cube cube = modelPart.getRandomCube(random);
                modelPart.translateAndRotate(poseStack);
                float midX = random.nextFloat();
                float midY = random.nextFloat();
                float midZ = random.nextFloat();
                switch (random.nextInt(3)) {
                    case 0 -> midX = snapToFace(midX);
                    case 1 -> midY = snapToFace(midY);
                    default -> midZ = snapToFace(midZ);
                }
                poseStack.translate(Mth.lerp(midX, cube.minX, cube.maxX) / 16.0F, Mth.lerp(midY, cube.minY, cube.maxY) / 16.0F, Mth.lerp(midZ, cube.minZ, cube.maxZ) / 16.0F);
                this.submitStuckItem(poseStack, submitNodeCollector, packedLight, -(midX * 2.0F - 1.0F), -(midY * 2.0F - 1.0F), -(midZ * 2.0F - 1.0F), renderState.outlineColor);
                poseStack.popPose();
            }
        }
    }

    private static float snapToFace(float value) {
        return value > 0.5F ? 1.0F : 0.5F;
    }

    private void submitStuckItem(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, float x, float y, float z, int outlineColor) {
        float horizontal = Mth.sqrt(x * x + z * z);
        float yRot = (float) (Math.atan2(x, z) * 180.0F / Math.PI);
        float xRot = (float) (Math.atan2(y, horizontal) * 180.0F / Math.PI);
        poseStack.rotate(Axis.YP.rotationDegrees(yRot - 90.0F));
        poseStack.rotate(Axis.ZP.rotationDegrees(xRot));
        submitNodeCollector.submitModel(this.model, this.modelState, poseStack, this.texture, packedLight, OverlayTexture.NO_OVERLAY, outlineColor);
    }

    protected int numStuck(AvatarRenderState renderState) {
        return renderState.getDataOrDefault(this.dartCount, 0);
    }
}
