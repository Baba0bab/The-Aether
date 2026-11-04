package com.aetherteam.aether.entity;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;

public class AetherEntityKeys {
    public static final ResourceKey<EntityType<?>> PHYG = createKey("phyg");
    public static final ResourceKey<EntityType<?>> FLYING_COW = createKey("flying_cow");
    public static final ResourceKey<EntityType<?>> SHEEPUFF = createKey("sheepuff");
    public static final ResourceKey<EntityType<?>> MOA = createKey("moa");
    public static final ResourceKey<EntityType<?>> AERBUNNY = createKey("aerbunny");
    public static final ResourceKey<EntityType<?>> AERWHALE = createKey("aerwhale");
    public static final ResourceKey<EntityType<?>> BLUE_SWET = createKey("blue_swet");
    public static final ResourceKey<EntityType<?>> GOLDEN_SWET = createKey("golden_swet");
    public static final ResourceKey<EntityType<?>> WHIRLWIND = createKey("whirlwind");
    public static final ResourceKey<EntityType<?>> EVIL_WHIRLWIND = createKey("evil_whirlwind");
    public static final ResourceKey<EntityType<?>> AECHOR_PLANT = createKey("aechor_plant");
    public static final ResourceKey<EntityType<?>> COCKATRICE = createKey("cockatrice");
    public static final ResourceKey<EntityType<?>> ZEPHYR = createKey("zephyr");
    public static final ResourceKey<EntityType<?>> MIMIC = createKey("mimic");
    public static final ResourceKey<EntityType<?>> SENTRY = createKey("sentry");
    public static final ResourceKey<EntityType<?>> SLIDER = createKey("slider");
    public static final ResourceKey<EntityType<?>> VALKYRIE = createKey("valkyrie");
    public static final ResourceKey<EntityType<?>> VALKYRIE_QUEEN = createKey("valkyrie_queen");
    public static final ResourceKey<EntityType<?>> FIRE_MINION = createKey("fire_minion");
    public static final ResourceKey<EntityType<?>> SUN_SPIRIT = createKey("sun_spirit");
    public static final ResourceKey<EntityType<?>> SKYROOT_BOAT = createKey("skyroot_boat");
    public static final ResourceKey<EntityType<?>> SKYROOT_CHEST_BOAT = createKey("skyroot_chest_boat");
    public static final ResourceKey<EntityType<?>> CLOUD_MINION = createKey("cloud_minion");
    public static final ResourceKey<EntityType<?>> COLD_PARACHUTE = createKey("cold_parachute");
    public static final ResourceKey<EntityType<?>> GOLDEN_PARACHUTE = createKey("golden_parachute");
    public static final ResourceKey<EntityType<?>> FLOATING_BLOCK = createKey("floating_block");
    public static final ResourceKey<EntityType<?>> TNT_PRESENT = createKey("tnt_present");
    public static final ResourceKey<EntityType<?>> ZEPHYR_SNOWBALL = createKey("zephyr_snowball");
    public static final ResourceKey<EntityType<?>> CLOUD_CRYSTAL = createKey("cloud_crystal");
    public static final ResourceKey<EntityType<?>> FIRE_CRYSTAL = createKey("fire_crystal");
    public static final ResourceKey<EntityType<?>> ICE_CRYSTAL = createKey("ice_crystal");
    public static final ResourceKey<EntityType<?>> THUNDER_CRYSTAL = createKey("thunder_crystal");
    public static final ResourceKey<EntityType<?>> GOLDEN_DART = createKey("golden_dart");
    public static final ResourceKey<EntityType<?>> POISON_DART = createKey("poison_dart");
    public static final ResourceKey<EntityType<?>> ENCHANTED_DART = createKey("enchanted_dart");
    public static final ResourceKey<EntityType<?>> POISON_NEEDLE = createKey("poison_needle");
    public static final ResourceKey<EntityType<?>> LIGHTNING_KNIFE = createKey("lightning_knife");
    public static final ResourceKey<EntityType<?>> HAMMER_PROJECTILE = createKey("hammer_projectile");

    private static ResourceKey<EntityType<?>> createKey(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Aether.MODID, name));
    }
}
