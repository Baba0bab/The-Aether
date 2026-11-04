package com.aetherteam.aether.item.accessories.pendant;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.item.accessories.abilities.FreezingAccessory;
import com.aetherteam.aetherfabric.accessories.SlotContext;
import net.minecraft.world.item.ItemStack;

public class IcePendantItem extends PendantItem implements FreezingAccessory {
    public IcePendantItem(Properties properties) {
        super("ice_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_ICE_PENDANT, properties.repairable(AetherTags.Items.ICE_REPAIRING));
    }

    @Override
    public void curioTick(SlotContext reference, ItemStack stack) {
        if (!reference.entity().nitrogen_fabric$isInFluidType()) {
            this.freezeTick(reference, stack);
        }
    }
}
