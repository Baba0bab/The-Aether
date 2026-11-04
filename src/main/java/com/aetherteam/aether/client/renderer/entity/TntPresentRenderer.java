package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.client.renderer.entity.state.TntPresentRenderState;
import com.aetherteam.aether.entity.block.TntPresent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.entity.TntRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.entity.TntRenderer}.
 */
public class TntPresentRenderer extends EntityRenderer<TntPresent, TntPresentRenderState> {
    private final BlockModelResolver blockModelResolver;

    public TntPresentRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.blockModelResolver = context.getBlockModelResolver();
        this.shadowRadius = 0.5F;
    }

    @Override
    public TntPresentRenderState createRenderState() {
        return new TntPresentRenderState();
    }

    @Override
    public void extractRenderState(TntPresent entity, TntPresentRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.fuse = entity.getFuse();
        reusedState.fuseRemainingInTicks = entity.getFuse() - partialTick + 1.0F;
        this.blockModelResolver.update(reusedState.blockState, AetherBlocks.PRESENT.get().defaultBlockState(), TntRenderer.BLOCK_DISPLAY_CONTEXT);
    }

    @Override
    public void submit(TntPresentRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.translate(0.0, 0.5, 0.0);
        float fuse = renderState.fuseRemainingInTicks;
        if (fuse < 10.0F) {
            float scale = 1.0F + TntRenderer.getSwellAmount(fuse);
            poseStack.scale(scale, scale, scale);
        }
        poseStack.translate(-0.5, -0.5, -0.5);
        if (!renderState.blockState.isEmpty()) {
            TntMinecartRenderer.submitWhiteSolidBlock(renderState.blockState, poseStack, submitNodeCollector, renderState.lightCoords, renderState.fuse / 5 % 2 == 0, renderState.outlineColor);
        }
        poseStack.popPose();
        super.submit(renderState, poseStack, submitNodeCollector, camera);
    }
}
