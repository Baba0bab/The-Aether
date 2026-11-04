package com.aetherteam.aether.item.combat.loot;

import com.mojang.serialization.Codec;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.ValkyrieTool;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class ValkyrieLanceItem extends Item implements ValkyrieTool {
    public ValkyrieLanceItem(Properties properties) {
        this(AetherItemTiers.VALKYRIE, 3.0F, -2.7F, properties);
    }

    public ValkyrieLanceItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
        super(properties.sword(material, attackDamage, attackSpeed).attributes(ValkyrieTool.createAttributes(material, attackDamage, attackSpeed)));
    }

    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.isPrimaryItemFor(stack, enchantment) && !enchantment.is(Enchantments.SWEEPING_EDGE);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.supportsEnchantment(stack, enchantment) && !enchantment.is(Enchantments.SWEEPING_EDGE);
    }

    @Override
    public boolean canPerformAction(ItemInstance stack, ItemAbility itemAbility) {
        return itemAbility != ItemAbilities.SWORD_SWEEP && super.canPerformAction(stack, itemAbility);
    }
}
