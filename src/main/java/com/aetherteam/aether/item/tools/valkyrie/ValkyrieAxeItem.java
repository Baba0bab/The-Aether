package com.aetherteam.aether.item.tools.valkyrie;

import net.minecraft.world.item.Item;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.ValkyrieTool;
import net.minecraft.world.item.ToolMaterial;

public class ValkyrieAxeItem extends Item implements ValkyrieTool {
    public ValkyrieAxeItem(Properties properties) {
        this(AetherItemTiers.VALKYRIE, 5.0F, -3.3F, properties);
    }

    public ValkyrieAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
        super(properties.axe(material, attackDamage, attackSpeed).attributes(ValkyrieTool.createAttributes(material, attackDamage, attackSpeed)));
    }
}
