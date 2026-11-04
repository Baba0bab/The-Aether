package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.client.renderer.entity.state.ParachuteRenderState;
import com.aetherteam.aether.entity.miscellaneous.Parachute;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class ParachuteRenderer extends EntityRenderer<Parachute, ParachuteRenderState> {
    private final Supplier<? extends Block> parachuteBlock;
    private final BlockModelResolver blockModelResolver;

    public ParachuteRenderer(EntityRendererProvider.Context context, Supplier<? extends Block> parachuteBlock) {
        super(context);
        this.parachuteBlock = parachuteBlock;
        this.blockModelResolver = context.getBlockModelResolver();
        this.shadowRadius = 0.0F;
    }

    @Override
    public ParachuteRenderState createRenderState() {
        return new ParachuteRenderState();
    }

    @Override
    public void extractRenderState(Parachute parachute, ParachuteRenderState reusedState, float partialTick) {
        super.extractRenderState(parachute, reusedState, partialTick);
        Entity passenger = parachute.getControllingPassenger();
        if (passenger != null) {
            if (passenger instanceof Player player) {
                reusedState.yRot = Mth.lerp(partialTick, player.yHeadRotO, player.getYHeadRot());
            } else {
                reusedState.yRot = Mth.lerp(partialTick, passenger.yRotO, passenger.getYRot());
            }
        }
        this.blockModelResolver.update(reusedState.blockState, this.parachuteBlock.get().defaultBlockState(), TntRenderer.BLOCK_DISPLAY_CONTEXT);
    }

    /**
     * Renders and rotates the Parachute with the player.
     *
     * @param renderState         The {@link ParachuteRenderState} for the entity.
     * @param poseStack           The rendering {@link PoseStack}.
     * @param submitNodeCollector The rendering {@link SubmitNodeCollector}.
     * @param camera              The {@link CameraRenderState}.
     */
    @Override
    public void submit(ParachuteRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.rotate(Axis.YP.rotationDegrees(-renderState.yRot));
        poseStack.translate(-0.5, 0.0, -0.5);
        renderState.blockState.submitMultiLayer(poseStack, submitNodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.outlineColor);
        poseStack.popPose();
        super.submit(renderState, poseStack, submitNodeCollector, camera);
    }
}
