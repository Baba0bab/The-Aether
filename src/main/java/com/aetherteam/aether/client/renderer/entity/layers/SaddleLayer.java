package com.aetherteam.aether.client.renderer.entity.layers;

import com.aetherteam.aether.client.renderer.entity.state.SaddleableRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

import org.jetbrains.annotations.Nullable;

/**
 * Renders a saddle model with a fixed texture for entities with a {@link SaddleableRenderState}.
 */
public class SaddleLayer<S extends LivingEntityRenderState & SaddleableRenderState, M extends EntityModel<? super S>> extends RenderLayer<S, M> {
    private final EntityModel<? super S> adultModel;
    @Nullable
    private final EntityModel<? super S> babyModel;
    private final Identifier texture;

    public SaddleLayer(RenderLayerParent<S, M> renderer, EntityModel<? super S> adultModel, @Nullable EntityModel<? super S> babyModel, Identifier texture) {
        super(renderer);
        this.adultModel = adultModel;
        this.babyModel = babyModel;
        this.texture = texture;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, S renderState, float yRot, float xRot) {
        if (renderState.isSaddled() && !renderState.isInvisible && (!renderState.isBaby || this.babyModel != null)) {
            EntityModel<? super S> model = renderState.isBaby ? this.babyModel : this.adultModel;
            submitNodeCollector.order(1).submitModel(model, renderState, poseStack, RenderTypes.entityCutout(this.texture), packedLight, OverlayTexture.NO_OVERLAY, -1, null, renderState.outlineColor);
        }
    }
}
