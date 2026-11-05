package com.aetherteam.aether.blockentity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.nitrogen.fabric.registries.DeferredHolder;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class AetherBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Aether.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AetherFlowerBlockEntity>> FLOWER = BLOCK_ENTITY_TYPES.register("flower", () ->
            new BlockEntityType<>(AetherFlowerBlockEntity::new, java.util.Set.of(AetherBlocks.PURPLE_FLOWER.get(), AetherBlocks.WHITE_FLOWER.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IcestoneBlockEntity>> ICESTONE = BLOCK_ENTITY_TYPES.register("icestone", () ->
            new BlockEntityType<>(IcestoneBlockEntity::new, java.util.Set.of(AetherBlocks.ICESTONE.get(), AetherBlocks.ICESTONE_SLAB.get(), AetherBlocks.ICESTONE_STAIRS.get(), AetherBlocks.ICESTONE_WALL.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AltarBlockEntity>> ALTAR = BLOCK_ENTITY_TYPES.register("altar", () ->
            new BlockEntityType<>(AltarBlockEntity::new, java.util.Set.of(AetherBlocks.ALTAR.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FreezerBlockEntity>> FREEZER = BLOCK_ENTITY_TYPES.register("freezer", () ->
            new BlockEntityType<>(FreezerBlockEntity::new, java.util.Set.of(AetherBlocks.FREEZER.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IncubatorBlockEntity>> INCUBATOR = BLOCK_ENTITY_TYPES.register("incubator", () ->
            new BlockEntityType<>(IncubatorBlockEntity::new, java.util.Set.of(AetherBlocks.INCUBATOR.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ChestMimicBlockEntity>> CHEST_MIMIC = BLOCK_ENTITY_TYPES.register("chest_mimic", () ->
            new BlockEntityType<>(ChestMimicBlockEntity::new, java.util.Set.of(AetherBlocks.CHEST_MIMIC.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TreasureChestBlockEntity>> TREASURE_CHEST = BLOCK_ENTITY_TYPES.register("treasure_chest", () ->
            new BlockEntityType<>(TreasureChestBlockEntity::new, java.util.Set.of(AetherBlocks.TREASURE_CHEST.get())));


    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SkyrootSignBlockEntity>> SKYROOT_SIGN = BLOCK_ENTITY_TYPES.register("skyroot_sign", () ->
            new BlockEntityType<>(SkyrootSignBlockEntity::new, java.util.Set.of(AetherBlocks.SKYROOT_WALL_SIGN.get(), AetherBlocks.SKYROOT_SIGN.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SkyrootHangingSignBlockEntity>> SKYROOT_HANGING_SIGN = BLOCK_ENTITY_TYPES.register("skyroot_hanging_sign", () ->
            new BlockEntityType<>(SkyrootHangingSignBlockEntity::new, java.util.Set.of(AetherBlocks.SKYROOT_WALL_HANGING_SIGN.get(), AetherBlocks.SKYROOT_HANGING_SIGN.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SunAltarBlockEntity>> SUN_ALTAR = BLOCK_ENTITY_TYPES.register("sun_altar", () ->
            new BlockEntityType<>(SunAltarBlockEntity::new, java.util.Set.of(AetherBlocks.SUN_ALTAR.get())));
}
