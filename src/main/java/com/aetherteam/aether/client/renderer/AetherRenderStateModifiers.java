package com.aetherteam.aether.client.renderer;

import net.minecraft.client.renderer.entity.state.TippableArrowRenderState;

import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;

import net.minecraft.world.entity.projectile.arrow.AbstractArrow;

import net.minecraft.world.entity.decoration.ArmorStand;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.client.renderer.accessory.layer.ArmorStandCapeLayer;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.accessories.cape.CapeItem;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Avatar;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class AetherRenderStateModifiers {
    public static final RenderStateDataKey<UUID> UUID = RenderStateDataKey.create(() -> Aether.MODID + ":uuid");
    public static final RenderStateDataKey<Boolean> HAS_VEHICLE = RenderStateDataKey.create(() -> Aether.MODID + ":has_vehicle");
    public static final RenderStateDataKey<Boolean> ON_GROUND = RenderStateDataKey.create(() -> Aether.MODID + ":on_ground");
    public static final RenderStateDataKey<Boolean> HAS_VEHICLE_ON_GROUND = RenderStateDataKey.create(() -> Aether.MODID + ":has_vehicle_on_ground");
    public static final RenderStateDataKey<Boolean> IN_FLUID_TYPE = RenderStateDataKey.create(() -> Aether.MODID + ":in_fluid_type");
    public static final RenderStateDataKey<Integer> GOLDEN_DART_COUNT = RenderStateDataKey.create(() -> Aether.MODID + ":golden_dart_count");
    public static final RenderStateDataKey<Integer> POISON_DART_COUNT = RenderStateDataKey.create(() -> Aether.MODID + ":poison_dart_count");
    public static final RenderStateDataKey<Integer> ENCHANTED_DART_COUNT = RenderStateDataKey.create(() -> Aether.MODID + ":enchanted_dart_count");
    public static final RenderStateDataKey<Boolean> HAS_FULL_VALKYRIE_ARMOR = RenderStateDataKey.create(() -> Aether.MODID + ":has_full_valkyrie_armor");
    public static final RenderStateDataKey<Integer> VALKYRIE_WING_ROTATION = RenderStateDataKey.create(() -> Aether.MODID + ":valkyrie_wing_rotation");
    public static final RenderStateDataKey<Integer> VALKYRIE_WING_ROTATION_OLD = RenderStateDataKey.create(() -> Aether.MODID + ":valkyrie_wing_rotation_old");
    public static final RenderStateDataKey<Boolean> HAS_INVISIBILITY_CLOAK = RenderStateDataKey.create(() -> Aether.MODID + ":has_invisibility_cloak");

    public static final RenderStateDataKey<ItemStack> IS_CAPE_VISIBLE = RenderStateDataKey.create(() -> Aether.MODID + ":is_cape_visible");
    public static final RenderStateDataKey<Identifier> CAPE_TEXTURE = RenderStateDataKey.create(() -> Aether.MODID + ":cape_texture");

    public static final RenderStateDataKey<Boolean> IS_PHOENIX_ARROW = RenderStateDataKey.create(() -> Aether.MODID + ":is_phoenix_arrow");

    /**
     * Called at the end of {@link net.minecraft.client.renderer.entity.player.AvatarRenderer#extractRenderState}.
     *
     * @see com.aetherteam.aether.mixin.mixins.client.RenderStateModifierMixins
     */
    public static void modifyAvatarRenderState(Avatar avatar, AvatarRenderState playerRenderState) {
        if (avatar instanceof AbstractClientPlayer abstractClientPlayer) {
            modifyPlayerRenderState(abstractClientPlayer, playerRenderState);
        }
    }

    /**
     * Called at the end of {@link ArmorStandRenderer#extractRenderState}.
     */
    public static void modifyArmorStandRenderState(ArmorStand armorStand, ArmorStandRenderState armorStandRenderState) {
        armorStandRenderState.setData(IS_CAPE_VISIBLE, AetherMixinHooks.isCapeVisible(armorStand));

        Identifier texture = null;
        String identifier = CapeItem.getStaticIdentifier();
        ItemStack itemStack = EquipmentUtil.getStackInSlot(armorStand, identifier, 0);
        if (itemStack.getItem() instanceof CapeItem capeItem) {
            texture = capeItem.getCapeTexture();
            if (itemStack.getHoverName().getString().equalsIgnoreCase("swuff_'s cape")) { // Easter Egg cape texture.
                texture = ArmorStandCapeLayer.SWUFF_CAPE_LOCATION;
            }
        }
        armorStandRenderState.setData(CAPE_TEXTURE, texture);
    }

    /**
     * Called at the end of {@link TippableArrowRenderer#extractRenderState}.
     */
    public static void modifyArrowRenderState(AbstractArrow arrow, TippableArrowRenderState tippableArrowRenderState) {
        tippableArrowRenderState.setData(IS_PHOENIX_ARROW, arrow.getAttachedOrCreate(AetherDataAttachments.PHOENIX_ARROW).isPhoenixArrow());
    }

    private static void modifyPlayerRenderState(AbstractClientPlayer abstractClientPlayer, AvatarRenderState playerRenderState) {
        playerRenderState.setData(UUID, abstractClientPlayer.getUUID());
        playerRenderState.setData(HAS_VEHICLE, abstractClientPlayer.getVehicle() != null);
        playerRenderState.setData(ON_GROUND, abstractClientPlayer.onGround());
        playerRenderState.setData(HAS_VEHICLE_ON_GROUND, abstractClientPlayer.getVehicle() != null && abstractClientPlayer.getVehicle().onGround());
        playerRenderState.setData(IN_FLUID_TYPE, abstractClientPlayer.nitrogen_fabric$isInFluidType());
        playerRenderState.setData(GOLDEN_DART_COUNT, abstractClientPlayer.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).getGoldenDartCount());
        playerRenderState.setData(POISON_DART_COUNT, abstractClientPlayer.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).getPoisonDartCount());
        playerRenderState.setData(ENCHANTED_DART_COUNT, abstractClientPlayer.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).getEnchantedDartCount());
        playerRenderState.setData(HAS_FULL_VALKYRIE_ARMOR, EquipmentUtil.hasFullValkyrieSet(abstractClientPlayer));
        playerRenderState.setData(VALKYRIE_WING_ROTATION, abstractClientPlayer.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).getWingRotation());
        playerRenderState.setData(VALKYRIE_WING_ROTATION_OLD, abstractClientPlayer.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).getWingRotationO());
        playerRenderState.setData(HAS_INVISIBILITY_CLOAK, abstractClientPlayer.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).isWearingInvisibilityCloak());
        playerRenderState.setData(IS_CAPE_VISIBLE, AetherMixinHooks.isCapeVisible(abstractClientPlayer));
    }
}
