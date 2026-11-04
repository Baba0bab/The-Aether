package com.aetherteam.aether.item.combat.loot;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class FlamingSwordItem extends Item {
    public FlamingSwordItem(Properties properties) {
        super(properties.sword(AetherItemTiers.FLAMING, 3.0F, -2.4F));
    }

    /**
     * @see Aether#eventSetup(IEventBus)
     * @see FlamingSwordItem#handleFlamingSwordAbility(LivingEntity, DamageSource)
     */
    public static void onLivingDamage(LivingEntity target, DamageSource damageSource) {
        handleFlamingSwordAbility(target, damageSource);
    }

    /**
     * Inflicts 30 seconds of fire on the target with an extra 4 seconds for every level of Fire Aspect the item has. This occurs if the attacker attacked with full strength as determined by {@link EquipmentUtil#isFullStrength(LivingEntity)}.
     *
     * @param target The damaged {@link LivingEntity}.
     * @param source The attacking {@link DamageSource}.
     */
    private static void handleFlamingSwordAbility(LivingEntity target, DamageSource source) {
        if (source.getDirectEntity() instanceof LivingEntity attacker) {
            if (EquipmentUtil.isFullStrength(attacker)) {
                ItemStack heldStack = attacker.getMainHandItem();
                if (heldStack.is(AetherItems.FLAMING_SWORD.get())) {
                    int defaultTime = 30;
                    int fireAspectModifier = EnchantmentHelper.getEnchantmentLevel(attacker.level().registryAccess().nitrogen_fabric$holderOrThrow(Enchantments.FIRE_ASPECT), attacker);
                    if (fireAspectModifier > 0) {
                        defaultTime += (fireAspectModifier * 4);
                    }
                    target.igniteForSeconds(defaultTime);
                }
            }
        }
    }
}
