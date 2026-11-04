package com.aetherteam.aether.loot;

import com.aetherteam.aether.Aether;
import net.minecraft.util.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.*;

public class AetherLoot {
    private static final Set<ResourceKey<LootTable>> LOOT_TABLES = new HashSet<>();
    public static final Set<ResourceKey<LootTable>> IMMUTABLE_LOOT_TABLES = Collections.unmodifiableSet(LOOT_TABLES);

    public static final Map<DyeColor, ResourceKey<LootTable>> SHEEPUFF_BY_DYE = Util.make(new EnumMap<>(DyeColor.class), (map) -> makeDyeKeyMap(map, "entities/sheepuff"));

    public static final ResourceKey<LootTable> SHEAR_SHEEPUFF = register("shearing/sheepuff");
    public static final Map<DyeColor, ResourceKey<LootTable>> SHEAR_SHEEPUFF_BY_DYE = Util.make(new EnumMap<>(DyeColor.class), (map) -> makeDyeKeyMap(map, "shearing/sheepuff"));

    public static final ResourceKey<LootTable> BRONZE_DUNGEON = register("chests/dungeon/bronze/bronze_dungeon");
    public static final ResourceKey<LootTable> BRONZE_DUNGEON_LOOT = register("chests/dungeon/bronze/bronze_dungeon_loot");
    public static final ResourceKey<LootTable> BRONZE_DUNGEON_TRASH = register("chests/dungeon/bronze/bronze_dungeon_trash");
    public static final ResourceKey<LootTable> BRONZE_DUNGEON_DISC = register("chests/dungeon/bronze/bronze_dungeon_disc");
    public static final ResourceKey<LootTable> BRONZE_DUNGEON_REWARD = register("chests/dungeon/bronze/bronze_dungeon_reward");
    public static final ResourceKey<LootTable> BRONZE_DUNGEON_TREASURE = register("chests/dungeon/bronze/bronze_dungeon_treasure");
    public static final ResourceKey<LootTable> BRONZE_DUNGEON_NEPTUNE = register("chests/dungeon/bronze/bronze_dungeon_neptune");
    public static final ResourceKey<LootTable> BRONZE_DUNGEON_GUMMIES = register("chests/dungeon/bronze/bronze_dungeon_gummies");

    public static final ResourceKey<LootTable> SILVER_DUNGEON = register("chests/dungeon/silver/silver_dungeon");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_LOOT = register("chests/dungeon/silver/silver_dungeon_loot");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_TRASH = register("chests/dungeon/silver/silver_dungeon_trash");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_DISC = register("chests/dungeon/silver/silver_dungeon_disc");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_REWARD = register("chests/dungeon/silver/silver_dungeon_reward");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_TREASURE = register("chests/dungeon/silver/silver_dungeon_treasure");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_VALKYRIE = register("chests/dungeon/silver/silver_dungeon_valkyrie");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_GRAVITITE = register("chests/dungeon/silver/silver_dungeon_gravitite");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_CAPE_CONFIG = register("chests/dungeon/silver/silver_dungeon_cape_config");
    public static final ResourceKey<LootTable> SILVER_DUNGEON_GUMMIES = register("chests/dungeon/silver/silver_dungeon_gummies");

    public static final ResourceKey<LootTable> GOLD_DUNGEON_REWARD = register("chests/dungeon/gold/gold_dungeon_reward");
    public static final ResourceKey<LootTable> GOLD_DUNGEON_TREASURE = register("chests/dungeon/gold/gold_dungeon_treasure");

    public static final ResourceKey<LootTable> RUINED_PORTAL = register("chests/ruined_portal");

    public static final ResourceKey<LootTable> ENTER_AETHER = register("advancements/enter_aether");

    public static final ResourceKey<LootTable> STRIP_GOLDEN_OAK = register("stripping/strip_golden_oak");

    public static final ResourceKey<LootTable> WHIRLWIND_JUNK = register("selectors/whirlwind_junk");
    public static final ResourceKey<LootTable> EVIL_WHIRLWIND_JUNK = register("selectors/evil_whirlwind_junk");

    private static ResourceKey<LootTable> register(String id) {
        return register(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(Aether.MODID, id)));
    }

    private static ResourceKey<LootTable> register(ResourceKey<LootTable> id) {
        if (LOOT_TABLES.add(id)) {
            return id;
        } else {
            throw new IllegalArgumentException(id + " is already a registered built-in loot table");
        }
    }

    private static void makeDyeKeyMap(EnumMap<DyeColor, ResourceKey<LootTable>> output, String name) {
        for (DyeColor dyecolor : DyeColor.values()) {
            output.put(dyecolor, register(name + "/" + dyecolor.getName()));
        }
    }
}
