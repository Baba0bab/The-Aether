package com.aetherteam.aether.item.accessories;

import com.aetherteam.aether.block.dispenser.AetherDispenseBehaviors;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aetherfabric.accessories.SlotContext;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.function.BiConsumer;

/**
 * Base class for the Aether's accessories. On Fabric, accessories are Trinkets; the Trinkets callbacks are forwarded
 * to the slot-context based methods below, which the accessory items override.
 */
public class AccessoryItem extends Item implements TrinketCallback {
    private final Holder<SoundEvent> soundEventSupplier;

    public AccessoryItem(Properties properties) {
        this(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_GENERIC, properties);
    }

    public AccessoryItem(Holder<SoundEvent> soundEventSupplier, Properties properties) {
        super(properties);
        this.soundEventSupplier = soundEventSupplier;
        DispenserBlock.registerBehavior(this, AetherDispenseBehaviors.DISPENSE_ACCESSORY_BEHAVIOR); // Behavior to allow accessories to be equipped from a Dispenser.
    }

    /**
     * Called every tick while the accessory is equipped.
     */
    public void curioTick(SlotContext slotContext, ItemStack stack) { }

    /**
     * Called when the accessory is equipped.
     */
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) { }

    /**
     * Called when the accessory is unequipped.
     */
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) { }

    /**
     * @return Whether the wearer can walk on powder snow while this accessory is equipped.
     */
    public boolean canWalkOnPowderedSnow(SlotContext slotContext, ItemStack stack) {
        return false;
    }

    /**
     * @return Whether this accessory makes Piglins neutral towards the wearer.
     */
    public boolean makesPiglinsNeutral(SlotContext slotContext, ItemStack stack) {
        return false;
    }

    /**
     * Adds the attribute modifiers that this accessory gives while equipped.
     */
    public void addAttributeModifiers(ItemStack stack, BiConsumer<Holder<Attribute>, AttributeModifier> consumer) { }

    @Override
    public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        this.curioTick(SlotContext.of(slot, entity), stack);
    }

    @Override
    public void onEquip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        this.onEquip(SlotContext.of(slot, entity), ItemStack.EMPTY, stack);
    }

    @Override
    public void onUnequip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        this.onUnequip(SlotContext.of(slot, entity), slot.get(), stack);
    }

    @Override
    public boolean canEquipFromUse(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public Holder<SoundEvent> getEquipSound(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        return this.soundEventSupplier;
    }

    @Override
    public void forEachTrinketModifier(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity, Identifier slotIdentifier, BiConsumer<Holder<Attribute>, AttributeModifier> consumer) {
        this.addAttributeModifiers(stack, consumer);
    }
}
