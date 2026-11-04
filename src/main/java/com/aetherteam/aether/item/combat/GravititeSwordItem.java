package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.item.combat.abilities.weapon.GravititeWeapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GravititeSwordItem extends Item implements GravititeWeapon {
    public GravititeSwordItem(Properties properties) {
        super(properties.sword(AetherItemTiers.GRAVITITE, 3.0F, -2.4F));
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        this.launchEntity(target, attacker);
        super.hurtEnemy(stack, target, attacker);
    }
}
