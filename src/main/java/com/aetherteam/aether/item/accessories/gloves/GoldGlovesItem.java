package com.aetherteam.aether.item.accessories.gloves;

import com.aetherteam.aetherfabric.accessories.SlotContext;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterials;

public class GoldGlovesItem extends GlovesItem  {
    public GoldGlovesItem(double punchDamage, Properties properties) {
        super(ArmorMaterials.GOLD, punchDamage, "gold_gloves", SoundEvents.ARMOR_EQUIP_GOLD, properties);
    }

    @Override
    public boolean makesPiglinsNeutral(SlotContext reference, ItemStack stack) {
        return true;
    }
}
