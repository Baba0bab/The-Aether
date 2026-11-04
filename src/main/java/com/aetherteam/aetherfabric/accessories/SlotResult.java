package com.aetherteam.aetherfabric.accessories;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import net.minecraft.world.item.ItemStack;

/**
 * An equipped accessory and the slot it is in.
 *
 * @param slotContext The {@link SlotContext} of the slot.
 * @param stack       The equipped {@link ItemStack}.
 */
public record SlotResult(SlotContext slotContext, ItemStack stack) {
    public static SlotResult of(TrinketSlotAccess access) {
        return new SlotResult(SlotContext.of(access), access.get());
    }
}
