package com.aetherteam.aether.item.accessories.ring;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.item.accessories.abilities.FreezingAccessory;
import com.aetherteam.aetherfabric.accessories.SlotContext;
import net.minecraft.world.item.ItemStack;

public class IceRingItem extends RingItem implements FreezingAccessory {
    public IceRingItem(Properties properties) {
        super(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_ICE_RING, properties.repairable(AetherTags.Items.ICE_REPAIRING));
    }

    @Override
    public void curioTick(SlotContext reference, ItemStack stack) {
        if (!reference.entity().nitrogen_fabric$isInFluidType()) {
            this.freezeTick(reference, stack);
        }
    }
}
