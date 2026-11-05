package com.aetherteam.aether.inventory;

import net.minecraft.world.inventory.RecipeBookType;

/**
 * The Aether's recipe book types, added to the vanilla enum by {@link com.aetherteam.aetherfabric.AetherASM}.<br><br>
 * Vanilla only stores settings for its own types, so the settings for these are kept by {@link com.aetherteam.aether.mixin.mixins.common.aether_fabric.RecipeBookSettingsMixin}.
 */
public class AetherRecipeBookTypes {
    public static final RecipeBookType ALTAR = RecipeBookType.valueOf("AETHER_ALTAR");
    public static final RecipeBookType FREEZER = RecipeBookType.valueOf("AETHER_FREEZER");
    public static final RecipeBookType INCUBATOR = RecipeBookType.valueOf("AETHER_INCUBATOR");

    public static boolean isAetherType(RecipeBookType type) {
        return type == ALTAR || type == FREEZER || type == INCUBATOR;
    }
}
