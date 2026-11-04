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
public class HolystoneShovelItem extends Item implements HolystoneTool {
    public HolystoneShovelItem(Properties properties) {
        super(properties.shovel(AetherItemTiers.HOLYSTONE, 1.5F, -3.0F));
    }
}
