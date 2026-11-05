package com.aetherteam.aether.client.renderer.accessory;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.item.accessories.miscellaneous.ShieldOfRepulsionItem;
import com.aetherteam.nitrogen.ConstantsUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import com.aetherteam.aetherfabric.accessories.SlotContext;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.client.TrinketRenderer;

public class ShieldOfRepulsionRenderer implements TrinketRenderer {
    private final HumanoidModel<HumanoidRenderState> shieldModel;
    public final HumanoidModel<HumanoidRenderState> shieldModelArm;

    public ShieldOfRepulsionRenderer() {
        this.shieldModel = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.SHIELD_OF_REPULSION));
        this.shieldModelArm = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.SHIELD_OF_REPULSION_ARM));
    }

    /**
     * Renders the Shield of Repulsion overlay over the model of the wearer in third person.
     */
    public void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, LivingEntityRenderState renderState) {
        if (!(renderState instanceof HumanoidRenderState humanoidRenderState) || !(stack.getItem() instanceof ShieldOfRepulsionItem shield)) {
            return;
        }
        boolean isSlim = renderState instanceof AvatarRenderState avatarRenderState && avatarRenderState.skin.model() == PlayerModelType.SLIM;
        boolean active = isShieldActive(slotContext.entity());
        Identifier texture;
        if (isSlim) {
            texture = active ? shield.getShieldOfRepulsionSlimTexture() : shield.getShieldOfRepulsionSlimInactiveTexture();
        } else {
            texture = active ? shield.getShieldOfRepulsionTexture() : shield.getShieldOfRepulsionInactiveTexture();
        }
        submitNodeCollector.order(1).submitModel(this.shieldModel, humanoidRenderState, poseStack, RenderTypes.entityTranslucent(texture), packedLight, OverlayTexture.NO_OVERLAY, -1, null, renderState.outlineColor);
    }

    /**
     * Renders the Shield of Repulsion overlay over the hands of the player in first person.
     */
    public void renderFirstPersonHand(ItemStack stack, SlotContext slotContext, HumanoidArm arm, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, AbstractClientPlayer clientPlayer, int packedLight) {
        if (!(stack.getItem() instanceof ShieldOfRepulsionItem shield) || clientPlayer.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).isWearingInvisibilityCloak()) {
            return;
        }
        Identifier texture = isShieldActive(clientPlayer) ? shield.getShieldOfRepulsionTexture() : shield.getShieldOfRepulsionInactiveTexture();

        boolean isSlim = clientPlayer.getSkin().model() == PlayerModelType.SLIM;
        float side = arm != HumanoidArm.LEFT ? 1.0F : -1.0F;
        float offset = isSlim ? 0.0425F : 0.0F;
        poseStack.translate((side * offset) - 0.0025, 0.0025, -0.0025);

        ModelPart shieldArm = arm == HumanoidArm.RIGHT ? this.shieldModelArm.rightArm : this.shieldModelArm.leftArm;
        shieldArm.resetPose();
        shieldArm.visible = true;
        submitNodeCollector.order(1).submitModelPart(shieldArm, poseStack, RenderTypes.entityTranslucent(texture), packedLight, OverlayTexture.NO_OVERLAY, null);
    }

    /**
     * @param entity The {@link LivingEntity} wearing the shield.
     * @return Whether the shield is active, which is when the wearer isn't moving, as a {@link Boolean}.
     */
    private static boolean isShieldActive(LivingEntity entity) {
        Vec3 motion = entity.getDeltaMovement();
        boolean stationary = motion.x() == 0.0 && (motion.y() == ConstantsUtil.DEFAULT_DELTA_MOVEMENT_Y || motion.y() == 0.0) && motion.z() == 0.0;
        if (entity instanceof Player player) {
            return !player.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).isMoving() || stationary;
        }
        return stationary;
    }

    @Override
    public void submit(ItemStack stack, TrinketSlotAccess slotReference, EntityModel<? extends LivingEntityRenderState> contextModel, PoseStack poseStack, SubmitNodeCollector submit, int light, LivingEntityRenderState state, float limbAngle, float limbDistance) {
        this.render(stack, SlotContext.of(slotReference), poseStack, submit, light, state);
    }
}
