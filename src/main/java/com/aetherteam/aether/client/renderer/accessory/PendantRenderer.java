package com.aetherteam.aether.client.renderer.accessory;

import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.accessory.model.PendantModel;
import com.aetherteam.aether.item.accessories.pendant.PendantItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherfabric.accessories.SlotContext;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.model.geom.ModelPart;
import com.aetherteam.aetherfabric.accessories.SlotContext;

public class PendantRenderer implements TrinketRenderer {
    private final PendantModel pendant;

    public PendantRenderer() {
        this.pendant = new PendantModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.PENDANT));
    }
    public void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, LivingEntityRenderState renderState) {
        if (renderState instanceof HumanoidRenderState humanoidRenderState && stack.getItem() instanceof PendantItem pendantItem) {
            submitNodeCollector.order(1).submitModel(this.pendant, humanoidRenderState, poseStack, RenderTypes.armorCutoutNoCull(pendantItem.getPendantTexture()), packedLight, OverlayTexture.NO_OVERLAY, -1, null, renderState.outlineColor);
        }
    }

    @Override
    public void submit(ItemStack stack, TrinketSlotAccess slotReference, EntityModel<? extends LivingEntityRenderState> contextModel, PoseStack poseStack, SubmitNodeCollector submit, int light, LivingEntityRenderState state, float limbAngle, float limbDistance) {
        this.render(stack, SlotContext.of(slotReference), poseStack, submit, light, state);
    }
}
