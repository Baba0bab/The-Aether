package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.nitrogen.fabric.registries.DeferredHolder;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;
import net.minecraft.util.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import com.aetherteam.aether.data.resources.registries.AetherEquipmentModels;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.EnumMap;

public class AetherArmorMaterials {
    public static final ArmorMaterial ZANITE = new ArmorMaterial(15, Util.make(new EnumMap<>(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 2);
        map.put(ArmorType.LEGGINGS, 5);
        map.put(ArmorType.CHESTPLATE, 6);
        map.put(ArmorType.HELMET, 2);
    }), 9, AetherSoundEvents.ITEM_ARMOR_EQUIP_ZANITE, 0.0F, 0.0F, AetherTags.Items.ZANITE_REPAIRING, AetherEquipmentModels.ZANITE);
    public static final ArmorMaterial GRAVITITE = new ArmorMaterial(33, Util.make(new EnumMap<>(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 3);
        map.put(ArmorType.LEGGINGS, 6);
        map.put(ArmorType.CHESTPLATE, 8);
        map.put(ArmorType.HELMET, 3);
    }), 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_GRAVITITE, 2.0F, 0.0F, AetherTags.Items.GRAVITITE_REPAIRING, AetherEquipmentModels.GRAVITITE);
    public static final ArmorMaterial NEPTUNE = new ArmorMaterial(15, Util.make(new EnumMap<>(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 2);
        map.put(ArmorType.LEGGINGS, 5);
        map.put(ArmorType.CHESTPLATE, 6);
        map.put(ArmorType.HELMET, 2);
    }), 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_NEPTUNE, 1.0F, 0.0F, AetherTags.Items.NEPTUNE_REPAIRING, AetherEquipmentModels.NEPTUNE);
    public static final ArmorMaterial VALKYRIE = new ArmorMaterial(33, Util.make(new EnumMap<>(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 3);
        map.put(ArmorType.LEGGINGS, 6);
        map.put(ArmorType.CHESTPLATE, 8);
        map.put(ArmorType.HELMET, 3);
    }), 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_VALKYRIE, 2.0F, 0.0F, AetherTags.Items.VALKYRIE_REPAIRING, AetherEquipmentModels.VALKYRIE);
    public static final ArmorMaterial PHOENIX = new ArmorMaterial(33, Util.make(new EnumMap<>(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 3);
        map.put(ArmorType.LEGGINGS, 6);
        map.put(ArmorType.CHESTPLATE, 8);
        map.put(ArmorType.HELMET, 3);
    }), 10, AetherSoundEvents.ITEM_ARMOR_EQUIP_PHOENIX, 2.0F, 0.0F, AetherTags.Items.PHOENIX_REPAIRING, AetherEquipmentModels.PHOENIX);
    public static final ArmorMaterial OBSIDIAN = new ArmorMaterial(37, Util.make(new EnumMap<>(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 3);
        map.put(ArmorType.LEGGINGS, 6);
        map.put(ArmorType.CHESTPLATE, 8);
        map.put(ArmorType.HELMET, 3);
    }), 15, AetherSoundEvents.ITEM_ARMOR_EQUIP_OBSIDIAN, 3.0F, 0.0F, AetherTags.Items.OBSIDIAN_REPAIRING, AetherEquipmentModels.OBSIDIAN);
    public static final ArmorMaterial SENTRY = new ArmorMaterial(15, Util.make(new EnumMap<>(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 2);
        map.put(ArmorType.LEGGINGS, 5);
        map.put(ArmorType.CHESTPLATE, 6);
        map.put(ArmorType.HELMET, 2);
    }), 9, AetherSoundEvents.ITEM_ARMOR_EQUIP_SENTRY, 0.0F, 0.0F, AetherTags.Items.SENTRY_REPAIRING, AetherEquipmentModels.SENTRY);
}
