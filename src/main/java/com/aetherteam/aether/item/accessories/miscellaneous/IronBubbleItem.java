package com.aetherteam.aether.item.accessories.miscellaneous;

import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aetherfabric.accessories.SlotContext;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class IronBubbleItem extends AccessoryItem {
    public IronBubbleItem(Properties properties) {
        super(properties);
    }

    /**
     * Keeps the wearer's air supply at 30 if they're underwater.
     *
     * @param stack       The accessory {@link ItemStack}.
     * @param reference The {@link SlotContext} of the accessory.
     */
    @Override
    public void curioTick(SlotContext reference, ItemStack stack) {
        LivingEntity livingEntity = reference.entity();
        if (livingEntity.isUnderWater()) {
            livingEntity.setAirSupply(30);
        }
    }
}
