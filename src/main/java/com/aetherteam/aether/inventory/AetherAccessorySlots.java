package com.aetherteam.aether.inventory;

import com.aetherteam.aether.AetherConfig;

/**
 * Identifiers for the Trinkets slots (in the format {@code group/slot}) used by the Aether's accessories.<br><br>
 * The Aether's own slots are defined in the built-in "accessories" data pack. If {@link AetherConfig.Common#use_default_accessories_menu} is enabled,
 * the built-in "accessories_override" data pack is used instead, which places the Aether's accessories in Trinkets' default slots.
 */
public final class AetherAccessorySlots {
    public static final String GLOVES_SLOT = "aether/gloves";
    public static final String RING_SLOT = "aether/ring";
    public static final String PENDANT_SLOT = "aether/pendant";
    public static final String CAPE_SLOT = "aether/cape";
    public static final String SHIELD_SLOT = "aether/shield";
    public static final String ACCESSORY_SLOT = "aether/accessory";

    private static boolean useDefaultSlots() {
        return AetherConfig.COMMON_SPEC.isLoaded() && AetherConfig.COMMON.use_default_accessories_menu.get();
    }

    public static String getGlovesSlotType() {
        return useDefaultSlots() ? "hand/glove" : GLOVES_SLOT;
    }

    public static String getRingSlotType() {
        return useDefaultSlots() ? "hand/ring" : RING_SLOT;
    }

    public static String getPendantSlotType() {
        return useDefaultSlots() ? "chest/necklace" : PENDANT_SLOT;
    }

    public static String getCapeSlotType() {
        return useDefaultSlots() ? "chest/cape" : CAPE_SLOT;
    }

    public static String getShieldSlotType() {
        return useDefaultSlots() ? "chest/back" : SHIELD_SLOT;
    }

    public static String getAccessorySlotType() {
        return useDefaultSlots() ? "legs/belt" : ACCESSORY_SLOT;
    }
}
