package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.item.combat.abilities.weapon.HolystoneWeapon;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HolystoneSwordItem extends Item implements HolystoneWeapon {
    public HolystoneSwordItem(Properties properties) {
        super(properties.sword(AetherItemTiers.HOLYSTONE, 3.0F, -2.4F));
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.level() instanceof ServerLevel serverLevel) {
            this.dropAmbrosium(serverLevel, target, attacker);
        }
        super.hurtEnemy(stack, target, attacker);
    }
}
