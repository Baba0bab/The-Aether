package com.aetherteam.nitrogen.client.renderer;

import com.aetherteam.nitrogen.client.renderer.state.BlockStateRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.LightCoordsUtil;

public class BlockStateRenderer extends PictureInPictureRenderer<BlockStateRenderState> {
    private static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();
    private final BlockModelRenderState blockModel = new BlockModelRenderState();

    @Override
    public Class<BlockStateRenderState> getRenderStateClass() {
        return BlockStateRenderState.class;
    }

    @org.jetbrains.annotations.Nullable
    private net.minecraft.client.renderer.block.BlockModelResolver blockModelResolver;

    @Override
    protected void renderToTexture(BlockStateRenderState state, PoseStack stack, SubmitNodeCollector submitNodeCollector) {
        stack.translate(0.425F, -0.25F, 5.0F);
        stack.rotate(Axis.XP.rotationDegrees(-30.0F));
        stack.rotate(Axis.YP.rotationDegrees(45.0F));
        stack.scale(-0.6F, -0.6F, -0.6F);

        this.blockModel.clear();
        this.getBlockModelResolver().update(this.blockModel, state.blockState(), BLOCK_DISPLAY_CONTEXT);
        this.blockModel.submit(stack, submitNodeCollector, LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 0);
    }

    private net.minecraft.client.renderer.block.BlockModelResolver getBlockModelResolver() {
        if (this.blockModelResolver == null) {
            this.blockModelResolver = new net.minecraft.client.renderer.block.BlockModelResolver(Minecraft.getInstance().getModelManager());
        }
        return this.blockModelResolver;
    }

    @Override
    protected String getTextureLabel() {
        return "BlockState";
    }
}
