package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.item.combat.abilities.weapon.SkyrootWeapon;
import net.minecraft.world.item.Item;

public class SkyrootSwordItem extends Item implements SkyrootWeapon {
    public SkyrootSwordItem(Properties properties) {
        super(properties.sword(AetherItemTiers.SKYROOT, 3.0F, -2.4F));
    }
}
