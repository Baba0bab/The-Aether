package com.aetherteam.aether.client.renderer.accessory;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.palette.PalettedTextureManager;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.component.DyedItemColor;
import com.aetherteam.aetherfabric.accessories.SlotContext;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.model.geom.ModelPart;
import com.aetherteam.aetherfabric.accessories.SlotContext;

public class GlovesRenderer implements TrinketRenderer {
    private final GlovesModel glovesModel;
    private final GlovesModel glovesTrimModel;
    private final GlovesModel glovesModelSlim;
    private final GlovesModel glovesTrimModelSlim;
    private final GlovesModel glovesFirstPerson;
    private final GlovesModel glovesTrimFirstPerson;

    public GlovesRenderer() {
        this.glovesModel = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.GLOVES));
        this.glovesTrimModel = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.GLOVES_TRIM));
        this.glovesModelSlim = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.GLOVES_SLIM));
        this.glovesTrimModelSlim = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.GLOVES_TRIM_SLIM));
        this.glovesFirstPerson = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.GLOVES_FIRST_PERSON));
        this.glovesTrimFirstPerson = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.GLOVES_TRIM_FIRST_PERSON));
    }

    /**
     * Renders gloves in third person on the entity's model.
     */
    public void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, LivingEntityRenderState renderState) {
        if (!(renderState instanceof HumanoidRenderState humanoidRenderState) || !(stack.getItem() instanceof GlovesItem glovesItem)) {
            return;
        }
        boolean isSlim = renderState instanceof AvatarRenderState avatarRenderState && avatarRenderState.skin.model() == PlayerModelType.SLIM;
        GlovesModel model = isSlim ? this.glovesModelSlim : this.glovesModel;
        GlovesModel trimModel = isSlim ? this.glovesTrimModelSlim : this.glovesTrimModel;

        int color = (stack.is(net.minecraft.tags.ItemTags.DYEABLE) ? net.minecraft.world.item.component.DyedItemColor.getOrDefault(stack, net.minecraft.world.item.component.DyedItemColor.LEATHER_COLOR) : -1);
        submitNodeCollector.order(1).submitModel(model, humanoidRenderState, poseStack, RenderTypes.armorCutoutNoCull(glovesItem.getGlovesTexture()), packedLight, OverlayTexture.NO_OVERLAY, color, null, renderState.outlineColor);

        ArmorTrim trim = stack.get(DataComponents.TRIM);
        if (trim != null) {
            PalettedTextureManager.Handle trimTexture = getTrimTexture(trim);
            submitNodeCollector.order(2).submitModel(trimModel, humanoidRenderState, poseStack, RenderTypes.armorTrim(trimTexture.textureLocation(), trim.pattern().value().decal()), packedLight, OverlayTexture.NO_OVERLAY, -1, trimTexture, renderState.outlineColor);
        }
        if (stack.hasFoil()) {
            submitNodeCollector.order(3).submitModel(model, humanoidRenderState, poseStack, RenderTypes.trimmedArmorGlint(), packedLight, OverlayTexture.NO_OVERLAY, -1, null, renderState.outlineColor);
        }
    }

    /**
     * Renders a glove in the player's hand in first person.
     */
    public void renderFirstPersonHand(ItemStack stack, SlotContext slotContext, HumanoidArm arm, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, AbstractClientPlayer clientPlayer, int packedLight) {
        if (!(stack.getItem() instanceof GlovesItem glovesItem) || (slotContext.entity() instanceof Player player && player.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).isWearingInvisibilityCloak())) {
            return;
        }
        int color = (stack.is(net.minecraft.tags.ItemTags.DYEABLE) ? net.minecraft.world.item.component.DyedItemColor.getOrDefault(stack, net.minecraft.world.item.component.DyedItemColor.LEATHER_COLOR) : -1);

        boolean isSlim = clientPlayer.getSkin().model() == PlayerModelType.SLIM;
        float side = arm != HumanoidArm.LEFT ? 1.0F : -1.0F;
        float offset = isSlim ? 0.0425F : 0.0F;
        poseStack.translate((side * offset) - 0.0025, 0.0025, -0.0025);

        ModelPart gloveArm = this.prepareArm(this.glovesFirstPerson, arm);
        submitNodeCollector.order(1).submitModelPart(gloveArm, poseStack, RenderTypes.armorCutoutNoCull(glovesItem.getGlovesTexture()), packedLight, OverlayTexture.NO_OVERLAY, null, color);

        ArmorTrim trim = stack.get(DataComponents.TRIM);
        if (trim != null) {
            PalettedTextureManager.Handle trimTexture = getTrimTexture(trim);
            ModelPart gloveTrimArm = this.prepareArm(this.glovesTrimFirstPerson, arm);
            submitNodeCollector.order(2).submitModelPart(gloveTrimArm, poseStack, RenderTypes.armorTrim(trimTexture.textureLocation(), trim.pattern().value().decal()), packedLight, OverlayTexture.NO_OVERLAY, trimTexture);
        }
        if (stack.hasFoil()) {
            submitNodeCollector.order(3).submitModelPart(gloveArm, poseStack, RenderTypes.trimmedArmorGlint(), packedLight, OverlayTexture.NO_OVERLAY, null);
        }
    }

    /**
     * Resets the pose of a glove arm for first person rendering, matching how vanilla renders the player's hand.
     *
     * @param model The {@link GlovesModel}.
     * @param arm   The {@link HumanoidArm} to render.
     * @return The {@link ModelPart} for the arm.
     */
    private ModelPart prepareArm(GlovesModel model, HumanoidArm arm) {
        ModelPart gloveArm = arm == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
        gloveArm.resetPose();
        gloveArm.visible = true;
        return gloveArm;
    }

    /**
     * [CODE COPY] - {@link net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer}.<br><br>
     * Prepares the paletted trim texture for gloves, using the leggings trim textures.
     *
     * @param trim The {@link ArmorTrim}.
     * @return The {@link PalettedTextureManager.Handle} for the trim texture.
     */
    private static PalettedTextureManager.Handle getTrimTexture(ArmorTrim trim) {
        Identifier textureId = trim.pattern().value().assetId();
        Identifier paletteId = trim.material().value().paletteId();
        Identifier baseTexture = textureId.withPath((path) -> EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS.trimAssetPrefix() + "/" + path);
        return Minecraft.getInstance().getPalettedTextureManager().getOrPrepare(baseTexture, paletteId);
    }

    @Override
    public void submit(ItemStack stack, TrinketSlotAccess slotReference, EntityModel<? extends LivingEntityRenderState> contextModel, PoseStack poseStack, SubmitNodeCollector submit, int light, LivingEntityRenderState state, float limbAngle, float limbDistance) {
        this.render(stack, SlotContext.of(slotReference), poseStack, submit, light, state);
    }
}
