package com.aetherteam.aether;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
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
