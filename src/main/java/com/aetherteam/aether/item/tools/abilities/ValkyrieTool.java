package com.aetherteam.aether.item.tools.abilities;

import com.aetherteam.aether.Aether;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public interface ValkyrieTool {
    /**
     * The default reach modifier value as a {@link Double}.
     */
    double RANGE_MODIFER = 3.5;
    /**
     * The unique identifier for the item's mining reach distance in the main hand.
     */
    Identifier BLOCK_INTERACTION_RANGE_MODIFIER_UUID = Identifier.fromNamespaceAndPath(Aether.MODID, "valkyrie_tool_block_interaction_range");
    /**
     * The unique identifier for the item's attack range distance in the main hand.
     */
    Identifier ENTITY_INTERACTION_RANGE_MODIFIER_UUID = Identifier.fromNamespaceAndPath(Aether.MODID, "valkyrie_tool_entity_interaction_range");

    static ItemAttributeModifiers createAttributes(ToolMaterial material, float attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder()
            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, attackDamage + material.attackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(BLOCK_INTERACTION_RANGE_MODIFIER_UUID, RANGE_MODIFER, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ENTITY_INTERACTION_RANGE_MODIFIER_UUID, RANGE_MODIFER, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .build();
    }
}
