package com.aetherteam.aether.client.renderer;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.client.renderer.accessory.layer.ArmorStandCapeLayer;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.accessories.cape.CapeItem;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Avatar;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.renderstate.AvatarRenderStateModifier;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;

import java.util.UUID;

public class AetherRenderStateModifiers {
    public static ContextKey<UUID> UUID = new ContextKey<>(Identifier.fromNamespaceAndPath(Aether.MODID, "uuid"));
    public static ContextKey<Boolean> HAS_VEHICLE = new ContextKey<>(Identifier.fromNamespaceAndPath(Aether.MODID, "has_vehicle"));
    public static ContextKey<Boolean> ON_GROUND = new ContextKey<>(Identifier.fromNamespaceAndPath(Aether.MODID, "on_ground"));
    public static ContextKey<Boolean> HAS_VEHICLE_ON_GROUND = new ContextKey<>(Identifier.fromNamespaceAndPath(Aether.MODID, "has_vehicle_on_ground"));
    public static ContextKey<Boolean> IN_FLUID_TYPE = new ContextKey<>(Identifier.fromNamespaceAndPath(Aether.MODID, "in_fluid_type"));
    public static ContextKey<Integer> GOLDEN_DART_COUNT = new ContextKey<>(Identifier.fromNamespaceAndPath(Aether.MODID, "golden_dart_count"));
    public static ContextKey<Integer> POISON_DART_COUNT = new ContextKey<>(Identifier.fromNamespaceAndPath(Aether.MODID, "poison_dart_count"));
    public static ContextKey<Integer> ENCHANTED_DART_COUNT = new ContextKey<>(Identifier.fromNamespaceAndPath(Aether.MODID, "enchanted_dart_count"));
    public static ContextKey<Boolean> HAS_FULL_VALKYRIE_ARMOR = new ContextKey<>(Identifier.fromNamespaceAndPath(Aether.MODID, "has_full_valkyrie_armor"));
    public static ContextKey<Integer> VALKYRIE_WING_ROTATION = new ContextKey<>(Identifier.fromNamespaceAndPath(Aether.MODID, "valkyrie_wing_rotation"));
    public static ContextKey<Integer> VALKYRIE_WING_ROTATION_OLD = new ContextKey<>(Identifier.fromNamespaceAndPath(Aether.MODID, "valkyrie_wing_rotation_old"));
    public static ContextKey<Boolean> HAS_INVISIBILITY_CLOAK = new ContextKey<>(Identifier.fromNamespaceAndPath(Aether.MODID, "has_invisibility_cloak"));

    public static ContextKey<ItemStack> IS_CAPE_VISIBLE = new ContextKey<>(Identifier.fromNamespaceAndPath(Aether.MODID, "is_cape_visible"));
    public static ContextKey<Identifier> CAPE_TEXTURE = new ContextKey<>(Identifier.fromNamespaceAndPath(Aether.MODID, "cape_texture"));

    public static ContextKey<Boolean> IS_PHOENIX_ARROW = new ContextKey<>(Identifier.fromNamespaceAndPath(Aether.MODID, "is_phoenix_arrow"));

    public static void registerRenderStateModifier(RegisterRenderStateModifiersEvent event) {
        event.registerAvatarEntityModifier(new AvatarRenderStateModifier() {
            @Override
            public <T extends Avatar & ClientAvatarEntity> void accept(T avatar, AvatarRenderState playerRenderState) {
                if (avatar instanceof AbstractClientPlayer abstractClientPlayer) {
                    modifyPlayerRenderState(abstractClientPlayer, playerRenderState);
                }
            }
        });

        event.registerEntityModifier(ArmorStandRenderer.class, (armorStand, armorStandRenderState) -> {
            armorStandRenderState.setRenderData(IS_CAPE_VISIBLE, AetherMixinHooks.isCapeVisible(armorStand));

            Identifier texture = null;
            String identifier = CapeItem.getStaticIdentifier();
            ItemStack itemStack = EquipmentUtil.getStackInSlot(armorStand, identifier, 0);
            if (itemStack.getItem() instanceof CapeItem capeItem) {
                texture = capeItem.getCapeTexture();
                if (itemStack.getHoverName().getString().equalsIgnoreCase("swuff_'s cape")) { // Easter Egg cape texture.
                    texture = ArmorStandCapeLayer.SWUFF_CAPE_LOCATION;
                }
            }
            armorStandRenderState.setRenderData(CAPE_TEXTURE, texture);
        });

        event.registerEntityModifier(TippableArrowRenderer.class, (arrow, tippableArrowRenderState) -> {
            tippableArrowRenderState.setRenderData(IS_PHOENIX_ARROW, arrow.getAttachedOrCreate(AetherDataAttachments.PHOENIX_ARROW).isPhoenixArrow());
        });
    }

    private static void modifyPlayerRenderState(AbstractClientPlayer abstractClientPlayer, AvatarRenderState playerRenderState) {
        playerRenderState.setRenderData(UUID, abstractClientPlayer.getUUID());
        playerRenderState.setRenderData(HAS_VEHICLE, abstractClientPlayer.getVehicle() != null);
        playerRenderState.setRenderData(ON_GROUND, abstractClientPlayer.onGround());
        playerRenderState.setRenderData(HAS_VEHICLE_ON_GROUND, abstractClientPlayer.getVehicle() != null && abstractClientPlayer.getVehicle().onGround());
        playerRenderState.setRenderData(IN_FLUID_TYPE, abstractClientPlayer.nitrogen_fabric$isInFluidType());
        playerRenderState.setRenderData(GOLDEN_DART_COUNT, abstractClientPlayer.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).getGoldenDartCount());
        playerRenderState.setRenderData(POISON_DART_COUNT, abstractClientPlayer.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).getPoisonDartCount());
        playerRenderState.setRenderData(ENCHANTED_DART_COUNT, abstractClientPlayer.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).getEnchantedDartCount());
        playerRenderState.setRenderData(HAS_FULL_VALKYRIE_ARMOR, EquipmentUtil.hasFullValkyrieSet(abstractClientPlayer));
        playerRenderState.setRenderData(VALKYRIE_WING_ROTATION, abstractClientPlayer.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).getWingRotation());
        playerRenderState.setRenderData(VALKYRIE_WING_ROTATION_OLD, abstractClientPlayer.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).getWingRotationO());
        playerRenderState.setRenderData(HAS_INVISIBILITY_CLOAK, abstractClientPlayer.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).isWearingInvisibilityCloak());
        playerRenderState.setRenderData(IS_CAPE_VISIBLE, AetherMixinHooks.isCapeVisible(abstractClientPlayer));
    }
}
