package com.aetherteam.aether.item.tools.holystone;

import net.neoforged.neoforge.event.level.block.BreakBlockEvent;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.HolystoneTool;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
/**
 * Ambrosium dropping behavior is called by {@link com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#doHolystoneAbility(BreakBlockEvent)}.
 */
public class HolystoneHoeItem extends Item implements HolystoneTool {
    public HolystoneHoeItem(Properties properties) {
        super(properties.hoe(AetherItemTiers.HOLYSTONE, -1.0F, -2.0F));
    }
}
