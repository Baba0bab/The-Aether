package com.aetherteam.aether.client.renderer.accessory.layer;

import com.aetherteam.aether.Aether;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.AetherRenderStateModifiers;
import com.aetherteam.aether.client.renderer.accessory.model.CapeModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.object.armorstand.ArmorStandModel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.entity.layers.CapeLayer}.<br><br>
 * Modified to check for capes in the Armor Stand's slots, as well as remove rotational fields and instead keep rotations constant.
 */
public class ArmorStandCapeLayer extends RenderLayer<ArmorStandRenderState, ArmorStandModel> {
    public static final Identifier SWUFF_CAPE_LOCATION = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/models/accessory/capes/swuff_accessory.png");

    private final CapeModel cape;

    public ArmorStandCapeLayer(RenderLayerParent<ArmorStandRenderState, ArmorStandModel> renderer) {
        super(renderer);
        this.cape = new CapeModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.CAPE));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, ArmorStandRenderState armorStandRenderState, float netHeadYaw, float headPitch) {
        Identifier texture = armorStandRenderState.getRenderData(AetherRenderStateModifiers.CAPE_TEXTURE);
        if (!armorStandRenderState.isInvisible && texture != null) {
            ItemStack itemstack = armorStandRenderState.chestEquipment;
            if (!itemstack.is(Items.ELYTRA)) {
                poseStack.pushPose();
                poseStack.translate(0.0F, 0.0F, 0.0925F);
                poseStack.rotate(Axis.XP.rotationDegrees(3.0F));
                poseStack.rotate(Axis.ZP.rotationDegrees(0.0F));
                poseStack.rotate(Axis.YP.rotationDegrees(180.0F));
                submitNodeCollector.submitModel(this.cape, armorStandRenderState, poseStack, RenderTypes.entitySolid(texture), packedLight, OverlayTexture.NO_OVERLAY, -1, null, armorStandRenderState.outlineColor);
                poseStack.popPose();
            }
        }
    }
}
