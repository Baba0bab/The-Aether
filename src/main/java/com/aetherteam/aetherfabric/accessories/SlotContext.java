package com.aetherteam.aetherfabric.accessories;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.LivingEntity;

import java.util.Optional;

/**
 * Identifies an accessory slot on an entity. Backed by Trinkets on Fabric.
 *
 * @param identifier The Trinkets slot id, in the format {@code group/slot}.
 * @param entity     The {@link LivingEntity} wearing the accessory.
 * @param index      The index of the slot within its slot type.
 * @param cosmetic   Whether this is a cosmetic slot.
 * @param visible    Whether the accessory in this slot is rendered.
 */
public record SlotContext(String identifier, LivingEntity entity, int index, boolean cosmetic, boolean visible) {
    public static SlotContext of(TrinketSlotAccess access, LivingEntity entity) {
        return new SlotContext(access.slotType().getId(), entity, access.index(), access.cosmetic(), access.isVisible());
    }

    public static SlotContext of(TrinketSlotAccess access) {
        return of(access, access.inventory().getAttachment().getEntity());
    }

    /**
     * @return The Trinkets {@link TrinketSlotAccess} for this slot, if the entity still has it.
     */
    public Optional<TrinketSlotAccess> access() {
        var inventory = TrinketsApi.getAttachment(this.entity).getInventory(this.identifier);
        if (inventory == null || this.index >= inventory.getContainerSize()) {
            return Optional.empty();
        }
        return Optional.ofNullable(this.cosmetic ? inventory.getCosmeticSlotAccess(this.index) : inventory.getSlotAccess(this.index));
    }
}
