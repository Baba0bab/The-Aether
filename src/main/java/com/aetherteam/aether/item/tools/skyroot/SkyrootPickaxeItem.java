package com.aetherteam.aether.item.tools.skyroot;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.SkyrootTool;
import net.minecraft.world.item.Item;

/**
 * Double drops behavior is called by {@link com.aetherteam.aether.loot.functions.DoubleDrops}.
 */
public class SkyrootPickaxeItem extends Item implements SkyrootTool {
    public SkyrootPickaxeItem(Properties properties) {
        super(properties.pickaxe(AetherItemTiers.SKYROOT, 1.0F, -2.8F));
    }
}
