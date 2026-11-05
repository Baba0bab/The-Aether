package com.aetherteam.aether.item.tools.holystone;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.HolystoneTool;
import net.minecraft.world.item.Item;
/**
 * Ambrosium dropping behavior is called by {@link com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#doHolystoneAbility(BreakBlockEvent)}.
 */
public class HolystoneHoeItem extends Item implements HolystoneTool {
    public HolystoneHoeItem(Properties properties) {
        super(properties.hoe(AetherItemTiers.HOLYSTONE, -1.0F, -2.0F));
    }
}
