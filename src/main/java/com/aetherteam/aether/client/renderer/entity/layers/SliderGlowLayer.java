package com.aetherteam.aether.client.renderer.entity.layers;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.entity.model.SliderModel;
import com.aetherteam.aether.client.renderer.entity.state.SliderRenderState;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class SliderGlowLayer extends EyesLayer<SliderRenderState, SliderModel> {
    private static final RenderType SLIDER_AWAKE_GLOW = RenderTypes.eyes(Identifier.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/slider/slider_awake_glow.png"));
    private static final RenderType SLIDER_AWAKE_CRITICAL_GLOW = RenderTypes.eyes(Identifier.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/slider/slider_awake_critical_glow.png"));

    public SliderGlowLayer(RenderLayerParent<SliderRenderState, SliderModel> entityRenderer) {
        super(entityRenderer);
    }

    /**
     * Renders the glowing eye layer for the Slider when it is awake.
     *
     * @param poseStack       The rendering {@link PoseStack}.
     * @param submitNodeCollector          The rendering {@link SubmitNodeCollector}.
     * @param packedLight     The {@link Integer} for the packed lighting for rendering.
     * @param renderState     The {@link SliderRenderState} for the entity.
     * @param netHeadYaw      The {@link Float} for the head yaw rotation.
     * @param headPitch       The {@link Float} for the head pitch rotation.
     */
    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, SliderRenderState renderState, float netHeadYaw, float headPitch) {
        if (renderState.awake) {
            submitNodeCollector.submitModel(this.getParentModel(), renderState, poseStack, this.renderType(renderState), 15728640, OverlayTexture.NO_OVERLAY, -1, null, renderState.outlineColor);
        }
    }

    /**
     * Selects the red glow render if the Slider is critical, and otherwise selects the default blue glow render.
     *
     * @param renderState The {@link SliderRenderState}.
     * @return The {@link RenderType}.
     */
    public RenderType renderType(SliderRenderState renderState) {
        if (renderState.critical) {
            return SLIDER_AWAKE_CRITICAL_GLOW;
        }
        return this.renderType();
    }

    @Override
    public RenderType renderType() {
        return SLIDER_AWAKE_GLOW;
    }
}
