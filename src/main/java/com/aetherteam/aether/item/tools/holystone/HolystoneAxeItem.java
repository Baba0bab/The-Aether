package com.aetherteam.aether.item.tools.holystone;

import net.neoforged.neoforge.event.level.block.BreakBlockEvent;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.HolystoneTool;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Ambrosium dropping behavior is called by {@link com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#doHolystoneAbility(BreakBlockEvent)}.
 */
public class HolystoneAxeItem extends Item implements HolystoneTool {
    public HolystoneAxeItem(Properties properties) {
        super(properties.axe(AetherItemTiers.HOLYSTONE, 7.0F, -3.2F));
    }
}
