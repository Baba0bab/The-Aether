package com.aetherteam.aether.item.tools.holystone;

import net.neoforged.neoforge.event.level.block.BreakBlockEvent;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.HolystoneTool;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.level.BlockEvent;

/**
 * Ambrosium dropping behavior is called by {@link com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#doHolystoneAbility(BreakBlockEvent)}.
 */
public class HolystonePickaxeItem extends Item implements HolystoneTool {
    public HolystonePickaxeItem(Properties properties) {
        super(properties.pickaxe(AetherItemTiers.HOLYSTONE, 1.0F, -2.8F));
    }
}
