package com.aetherteam.aether.item.combat.loot;

import net.minecraft.world.item.Item;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class CandyCaneSwordItem extends Item {
    public CandyCaneSwordItem(Properties properties) {
        super(properties.sword(AetherItemTiers.CANDY_CANE, 3.0F, -2.4F));
    }

    /**
     * Drops Candy Canes if the target can drop them and if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}, with a 1/2 chance.
     *
     * @param stack    The {@link ItemStack} used to hurt the target
     * @param target   The hurt {@link LivingEntity}.
     * @param attacker The attacking {@link LivingEntity}.
     * @return Whether the enemy was hurt or not, as a {@link Boolean}.
     */
    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (EquipmentUtil.isFullStrength(attacker)) {
            if (!target.is(AetherTags.Entities.NO_CANDY_CANE_DROPS) && target.level().getRandom().nextBoolean() && target.level() instanceof ServerLevel serverLevel) {
                target.spawnAtLocation(serverLevel, AetherItems.CANDY_CANE.get());
            }
        }
        super.hurtEnemy(stack, target, attacker);
    }
}
