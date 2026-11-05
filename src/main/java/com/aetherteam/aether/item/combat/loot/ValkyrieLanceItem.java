package com.aetherteam.aether.item.combat.loot;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.ValkyrieTool;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class ValkyrieLanceItem extends Item implements ValkyrieTool {
    public ValkyrieLanceItem(Properties properties) {
        this(AetherItemTiers.VALKYRIE, 3.0F, -2.7F, properties);
    }

    public ValkyrieLanceItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
        super(properties.sword(material, attackDamage, attackSpeed).attributes(ValkyrieTool.createAttributes(material, attackDamage, attackSpeed)));
    }



}
