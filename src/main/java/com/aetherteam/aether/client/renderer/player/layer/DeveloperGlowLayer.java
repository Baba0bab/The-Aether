package com.aetherteam.aether.client.renderer.player.layer;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import com.aetherteam.aether.client.gui.screen.perks.AetherCustomizationsScreen;
import com.aetherteam.aether.client.renderer.AetherRenderStateModifiers;
import com.aetherteam.aether.client.renderer.AetherRenderers;
import com.aetherteam.aether.perk.PerkUtil;
import com.aetherteam.aether.perk.data.ClientDeveloperGlowPerkData;
import com.aetherteam.aether.perk.types.DeveloperGlow;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.api.users.UserData;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.Map;
import java.util.UUID;

public class DeveloperGlowLayer<T extends AvatarRenderState, M extends PlayerModel> extends RenderLayer<T, M> {
    public DeveloperGlowLayer(RenderLayerParent<T, M> entityRenderer) {
        super(entityRenderer);
    }

    /**
     * If the player has Developer Glow, this will render it in the {@link AetherCustomizationsScreen} or in the world, and color it based on the settings the player has defined.
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
        if (!entity.isInvisible) {
            User user = UserData.Client.getClientUser();
            UUID playerUUID = entity.getRenderData(AetherRenderStateModifiers.UUID);
            Map<UUID, DeveloperGlow> developerGlows = ClientDeveloperGlowPerkData.INSTANCE.getClientPerkData();
            if (playerUUID != null && (Minecraft.getInstance().gui.screen() instanceof AetherCustomizationsScreen aetherCustomizationsScreen && aetherCustomizationsScreen.developerGlowEnabled && Minecraft.getInstance().player != null && playerUUID.equals(Minecraft.getInstance().player.getUUID()) && user != null && PerkUtil.hasDeveloperGlow().test(user))
                    || (!(Minecraft.getInstance().gui.screen() instanceof AetherCustomizationsScreen) && developerGlows.containsKey(playerUUID))) {
                Triple<Float, Float, Float> color;
                if (Minecraft.getInstance().gui.screen() instanceof AetherCustomizationsScreen aetherCustomizationsScreen) {
                    color = PerkUtil.getPerkColor(aetherCustomizationsScreen.developerGlowColor);
                } else {
                    color = PerkUtil.getPerkColor(developerGlows.get(playerUUID).hexColor());
                }
                if (color != null) {
                    submitNodeCollector.submitModel(this.getParentModel(), entity, poseStack, RenderTypes.eyes(entity.skin.body().texturePath()), 15728640, OverlayTexture.NO_OVERLAY, new Color(color.getLeft(), color.getMiddle(), color.getRight(), 1.0F).getRGB(), null, entity.outlineColor);
                } else {
                    submitNodeCollector.submitModel(this.getParentModel(), entity, poseStack, RenderTypes.eyes(entity.skin.body().texturePath()), 15728640, OverlayTexture.NO_OVERLAY, -1, null, entity.outlineColor);
                }
            }
        }
    }
}
