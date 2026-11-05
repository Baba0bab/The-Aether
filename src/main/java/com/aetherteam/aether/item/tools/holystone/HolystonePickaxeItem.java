package com.aetherteam.aether.item.tools.holystone;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.HolystoneTool;
import net.minecraft.world.item.Item;

/**
 * Ambrosium dropping behavior is called by {@link com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#doHolystoneAbility(BreakBlockEvent)}.
 */
public class HolystonePickaxeItem extends Item implements HolystoneTool {
    public HolystonePickaxeItem(Properties properties) {
        super(properties.pickaxe(AetherItemTiers.HOLYSTONE, 1.0F, -2.8F));
    }
}
