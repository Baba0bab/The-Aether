package com.aetherteam.aether.item.tools.zanite;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.ZaniteTool;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Zanite mining speed boost behavior is called by {@link com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#modifyBreakSpeed(Player, BlockState, float)}.
 */
public class ZanitePickaxeItem extends Item implements ZaniteTool {
    public ZanitePickaxeItem(Properties properties) {
        super(properties.pickaxe(AetherItemTiers.ZANITE, 1.0F, -2.8F));
    }
}
