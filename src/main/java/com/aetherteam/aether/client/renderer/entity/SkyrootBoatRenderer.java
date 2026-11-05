package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import net.minecraft.client.model.object.boat.BoatModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.AbstractBoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.entity.BoatRenderer}.<br><br>
 * Modified to use the Skyroot boat textures.
 */
public class SkyrootBoatRenderer extends AbstractBoatRenderer {
    public static final Identifier SKYROOT_BOAT = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/entity/miscellaneous/boat/skyroot.png");
    public static final Identifier SKYROOT_CHEST_BOAT = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/entity/miscellaneous/chest_boat/skyroot.png");
    private final Model.Simple waterPatchModel;
    private final EntityModel<BoatRenderState> model;

    public SkyrootBoatRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelLayer, Identifier texture) {
        super(context, texture);
        this.waterPatchModel = new Model.Simple(context.bakeLayer(ModelLayers.BOAT_WATER_PATCH), (location) -> RenderTypes.waterMask());
        this.model = new BoatModel(context.bakeLayer(modelLayer));
    }

    @Override
    protected EntityModel<BoatRenderState> model() {
        return this.model;
    }

    @Override
    protected void submitTypeAdditions(BoatRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords) {
        if (!state.isUnderWater) {
            submitNodeCollector.submitModel(this.waterPatchModel, Unit.INSTANCE, poseStack, this.texture, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        }
    }
}
