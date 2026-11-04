package com.aetherteam.aether.client.renderer.player.layer;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.AetherRenderStateModifiers;
import com.aetherteam.aether.client.renderer.entity.model.ValkyrieWingsModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

import java.util.Objects;

public class PlayerWingsLayer<T extends AvatarRenderState, M extends PlayerModel> extends RenderLayer<T, M> {
    private static final Identifier VALKYRIE_TEXTURE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/valkyrie/valkyrie.png");
    private final ValkyrieWingsModel<AvatarRenderState> wings;

    public PlayerWingsLayer(RenderLayerParent<T, M> entityRenderer, EntityModelSet modelSet) {
        super(entityRenderer);
        this.wings = new ValkyrieWingsModel<>(modelSet.bakeLayer(AetherModelLayers.VALKYRIE_ARMOR_WINGS));
    }

    /**
     * Renders wings for the player when wearing Valkyrie Armor.
     *
     * @param poseStack       The rendering {@link PoseStack}.
     * @param submitNodeCollector          The rendering {@link SubmitNodeCollector}.
     * @param packedLight     The {@link Integer} for the packed lighting for rendering.
     * @param entity          The entity.
     * @param netHeadYaw      The {@link Float} for the head yaw rotation.
     * @param headPitch       The {@link Float} for the head pitch rotation.
     */
    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, T entity, float netHeadYaw, float headPitch) {
        boolean hasFullValkyrieSet = entity.getRenderDataOrDefault(AetherRenderStateModifiers.HAS_FULL_VALKYRIE_ARMOR, false);
        int wingRotationO = entity.getRenderDataOrDefault(AetherRenderStateModifiers.VALKYRIE_WING_ROTATION_OLD, 0);
        int wingRotation = entity.getRenderDataOrDefault(AetherRenderStateModifiers.VALKYRIE_WING_ROTATION, 0);
        if (hasFullValkyrieSet) {
            this.setupWingRotation(entity, Mth.lerp(entity.partialTick, wingRotationO, wingRotation));
            submitNodeCollector.submitModel(this.wings, entity, poseStack, RenderTypes.entityCutout(VALKYRIE_TEXTURE), packedLight, OverlayTexture.NO_OVERLAY, -1, null, entity.outlineColor);
        }
    }

    /**
     * Handles the wings' rotation.
     *
     * @param entity The entity.
     * @param sinage The {@link Float} for the rotation value.
     */
    public void setupWingRotation(T entity, float sinage) {
        boolean onGround = entity.getRenderDataOrDefault(AetherRenderStateModifiers.ON_GROUND, false);
        boolean isInFluidType = entity.getRenderDataOrDefault(AetherRenderStateModifiers.IN_FLUID_TYPE, false);
        boolean hasVehicle = entity.getRenderDataOrDefault(AetherRenderStateModifiers.HAS_VEHICLE, false);
        boolean hasVehicleOnGround = entity.getRenderDataOrDefault(AetherRenderStateModifiers.HAS_VEHICLE_ON_GROUND, false);

        if (!onGround && !isInFluidType && (hasVehicle && !hasVehicleOnGround)) {
            sinage *= 1.5F;
        } else {
            sinage *= 0.3F;
        }

        this.wings.rightWing.yRot = 0.4F;
        this.wings.rightWing.zRot = 0.125F;
        this.wings.leftWing.yRot = -0.4F;
        this.wings.leftWing.zRot = -0.125F;

        if (entity.isCrouching) {
            this.wings.rightWing.xRot = 0.45F;
            this.wings.rightWing.y = 3.33F;
            this.wings.rightWing.z = 3.388F;
            this.wings.leftWing.xRot = 0.45F;
            this.wings.leftWing.y = 3.33F;
            this.wings.leftWing.z = 3.388F;
        } else {
            this.wings.rightWing.xRot = 0.0F;
            this.wings.leftWing.xRot = 0.0F;
        }

        this.wings.rightWing.yRot -= Mth.sin(sinage) / 6.0F;
        this.wings.rightWing.zRot -= Mth.cos(sinage) / (onGround || isInFluidType ? 8.0F : 3.0F);
        this.wings.leftWing.yRot += Mth.sin(sinage) / 6.0F;
        this.wings.leftWing.zRot += Mth.cos(sinage) / (onGround || isInFluidType ? 8.0F : 3.0F);
    }
}
