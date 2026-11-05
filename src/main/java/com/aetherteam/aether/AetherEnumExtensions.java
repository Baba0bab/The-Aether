package com.aetherteam.aether;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;


public class AetherEnumExtensions {
//    public static final EnumProxy<Rarity> AETHER_LOOT_RARITY_PROXY = new EnumProxy<>(
//        Rarity.class, -1, "aether:loot", ChatFormatting.GREEN
//    );

    private static String prefix(String id) {
        return Aether.MODID + ":" + id;
    }
}
