package com.aetherteam.aether.item.tools.valkyrie;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.ValkyrieTool;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class ValkyrieHoeItem extends Item implements ValkyrieTool {
    public ValkyrieHoeItem(Properties properties) {
        this(AetherItemTiers.VALKYRIE, -3.0F, -0.3F, properties);
    }

    public ValkyrieHoeItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
        super(properties.hoe(material, attackDamage, attackSpeed).attributes(ValkyrieTool.createAttributes(material, attackDamage, attackSpeed)));
    }
}
