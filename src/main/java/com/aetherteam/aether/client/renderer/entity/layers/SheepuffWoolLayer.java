package com.aetherteam.aether.client.renderer.entity.layers;

import net.minecraft.client.renderer.SubmitNodeCollector;
import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aether.client.renderer.entity.model.SheepuffWoolModel;
import com.aetherteam.aether.client.renderer.entity.state.SheepuffRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.color.ColorLerper;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.entity.layers.SheepWoolLayer}.
 */
public class SheepuffWoolLayer extends RenderLayer<SheepuffRenderState, SheepuffModel> {
    private static final Identifier SHEEPUFF_WOOL_TEXTURE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/sheepuff/sheepuff_wool.png");
    private final EntityModel<SheepuffRenderState> adultModel;
    private final EntityModel<SheepuffRenderState> babyModel;
    private final EntityModel<SheepuffRenderState> adultPuffModel;
    private final EntityModel<SheepuffRenderState> babyPuffModel;

    public SheepuffWoolLayer(RenderLayerParent<SheepuffRenderState, SheepuffModel> entityRenderer, EntityModelSet modelSet) {
        super(entityRenderer);
        this.adultModel = new SheepuffWoolModel(modelSet.bakeLayer(AetherModelLayers.SHEEPUFF_WOOL));
        this.babyModel = new SheepuffWoolModel(modelSet.bakeLayer(AetherModelLayers.SHEEPUFF_BABY_WOOL));
        this.adultPuffModel = new SheepuffWoolModel(modelSet.bakeLayer(AetherModelLayers.SHEEPUFF_WOOL));
        this.babyPuffModel = new SheepuffWoolModel(modelSet.bakeLayer(AetherModelLayers.SHEEPUFF_BABY_WOOL));
    }

    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, SheepuffRenderState renderState, float yRot, float xRot) {
        if (!renderState.isSheared) {
            EntityModel<SheepuffRenderState> entitymodel = renderState.isBaby ? this.babyModel : this.adultModel;
            if (renderState.puff) {
                entitymodel = renderState.isBaby ? this.babyPuffModel : this.adultPuffModel;
            }
            if (renderState.isInvisible) {
                if (renderState.appearsGlowing()) {
                    submitNodeCollector.submitModel(entitymodel, renderState, poseStack, RenderTypes.outline(SHEEPUFF_WOOL_TEXTURE), packedLight, LivingEntityRenderer.getOverlayCoords(renderState, 0.0F), -16777216, null, renderState.outlineColor);
                }
            } else {
                int i = renderState.isJebSheepuff ? ColorLerper.getLerpedColor(ColorLerper.Type.SHEEP, renderState.ageInTicks) : ColorLerper.Type.SHEEP.getColor(renderState.woolColor);
                coloredCutoutModelCopyLayerRender(entitymodel, SHEEPUFF_WOOL_TEXTURE, poseStack, submitNodeCollector, packedLight, renderState, i, 1);
            }
        }
    }
}
