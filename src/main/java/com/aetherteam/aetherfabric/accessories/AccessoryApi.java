package com.aetherteam.aetherfabric.accessories;

import eu.pb4.trinkets.api.TrinketInventory;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.TrinketSlotUtils;
import eu.pb4.trinkets.api.TrinketsApi;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Accessory helpers used by the Aether, implemented with Trinkets.
 */
public final class AccessoryApi {
    private AccessoryApi() { }

    public static Optional<SlotResult> findFirst(LivingEntity entity, Predicate<ItemStack> predicate) {
        return TrinketsApi.getAttachment(entity).findFirst(predicate, false).map(SlotResult::of);
    }

    public static List<SlotResult> findAll(LivingEntity entity, Predicate<ItemStack> predicate) {
        return TrinketsApi.getAttachment(entity).equipped(predicate, false).stream().map(SlotResult::of).toList();
    }

    public static Optional<TrinketInventory> getInventory(LivingEntity entity, String identifier) {
        return Optional.ofNullable(TrinketsApi.getAttachment(entity).getInventory(identifier));
    }

    public static Map<String, TrinketInventory> getInventories(LivingEntity entity) {
        return TrinketsApi.getAttachment(entity).getInventories();
    }

    /**
     * Places a stack in a slot, triggering the equip callbacks like Curios' {@code setEquippedCurio}.
     */
    public static boolean setEquipped(LivingEntity entity, String identifier, int index, ItemStack stack) {
        return new SlotContext(identifier, entity, index, false, true).access().map((access) -> access.set(stack)).orElse(false);
    }

    /**
     * Breaks the accessory in a slot, playing the break effects, like Curios' {@code broadcastCurioBreakEvent}.
     */
    public static void onBroken(SlotContext slotContext, ItemStack stack) {
        slotContext.access().ifPresent((access) -> TrinketsApi.onTrinketBroken(stack, access, slotContext.entity()));
    }

    public static boolean isStackValid(SlotContext slotContext, ItemStack stack) {
        return slotContext.access().map((access) -> TrinketSlotUtils.isEquipable(access, stack)).orElse(false);
    }

    public static void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        slotContext.access().ifPresent((access) -> {
            TrinketCallback callback = TrinketCallback.getCallback(stack);
            slotContext.entity().playSound(callback.getEquipSound(stack, access, slotContext.entity()).value(), 1.0F, 1.0F);
        });
    }

    public static Optional<TrinketSlotAccess> access(SlotContext slotContext) {
        return slotContext.access();
    }
}
