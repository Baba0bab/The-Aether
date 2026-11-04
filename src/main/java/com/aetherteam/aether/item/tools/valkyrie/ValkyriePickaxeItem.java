package com.aetherteam.aether.item.tools.valkyrie;

import net.minecraft.world.item.Item;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.ValkyrieTool;
import net.minecraft.world.item.ToolMaterial;

public class ValkyriePickaxeItem extends Item implements ValkyrieTool {
    public ValkyriePickaxeItem(Properties properties) {
        this(AetherItemTiers.VALKYRIE, 1.0F, -3.1F, properties);
    }

    public ValkyriePickaxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
        super(properties.pickaxe(material, attackDamage, attackSpeed).attributes(ValkyrieTool.createAttributes(material, attackDamage, attackSpeed)));
    }
}
