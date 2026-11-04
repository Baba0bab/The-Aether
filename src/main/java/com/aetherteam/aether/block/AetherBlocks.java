package com.aetherteam.aether.block;

import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.DyeColor;
import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.construction.*;
import com.aetherteam.aether.block.dungeon.*;
import com.aetherteam.aether.block.miscellaneous.AetherFrostedIceBlock;
import com.aetherteam.aether.block.miscellaneous.FacingPillarBlock;
import com.aetherteam.aether.block.miscellaneous.FloatingBlock;
import com.aetherteam.aether.block.miscellaneous.UnstableObsidianBlock;
import com.aetherteam.aether.block.natural.*;
import com.aetherteam.aether.block.portal.AetherPortalBlock;
import com.aetherteam.aether.block.utility.*;
import com.aetherteam.aether.blockentity.ChestMimicBlockEntity;
import com.aetherteam.aether.blockentity.TreasureChestBlockEntity;
import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.data.resources.registries.AetherTrimMaterials;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.mixin.mixins.common.accessor.FireBlockAccessor;
import com.aetherteam.aether.world.treegrower.AetherTreeGrowers;
import com.aetherteam.nitrogen.fabric.registries.DeferredBlock;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;
import com.aetherteam.nitrogen.item.block.EntityBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class AetherBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Aether.MODID);

    public static final DeferredBlock<AetherPortalBlock> AETHER_PORTAL = registerBlockWithoutItem("aether_portal", AetherPortalBlock::new, () -> Block.Properties.of().noCollision().randomTicks().strength(-1.0F).sound(SoundType.GLASS).lightLevel(AetherBlocks::lightLevel11).pushReaction(PushReaction.IMMOVEABLE).forceSolidOn());

    public static final DeferredBlock<Block> AETHER_GRASS_BLOCK = registerBlock("aether_grass_block", AetherGrassBlock::new, () -> Block.Properties.of().mapColor(MapColor.WARPED_WART_BLOCK).randomTicks().strength(0.2F).sound(SoundType.GRASS));
    public static final DeferredBlock<Block> ENCHANTED_AETHER_GRASS_BLOCK = registerBlock("enchanted_aether_grass_block", EnchantedAetherGrassBlock::new, () -> Block.Properties.of().mapColor(MapColor.GOLD).randomTicks().strength(0.2F).sound(SoundType.GRASS));
    public static final DeferredBlock<Block> AETHER_DIRT = registerBlock("aether_dirt", AetherDoubleDropBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).strength(0.2F).sound(SoundType.GRAVEL));
    public static final DeferredBlock<Block> QUICKSOIL = registerBlock("quicksoil", QuicksoilBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.SNARE).strength(0.5F).friction(1.1F).sound(SoundType.SAND));
    public static final DeferredBlock<Block> HOLYSTONE = registerBlock("holystone", AetherDoubleDropBlock::new, () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> MOSSY_HOLYSTONE = registerBlock("mossy_holystone", AetherDoubleDropBlock::new, () -> Block.Properties.ofFullCopy(AetherBlocks.HOLYSTONE.get()));
    public static final DeferredBlock<Block> AETHER_FARMLAND = registerBlock("aether_farmland", AetherFarmBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).randomTicks().strength(0.2F).sound(SoundType.GRAVEL).isViewBlocking(AetherBlocks::always).isSuffocating(AetherBlocks::always));
    public static final DeferredBlock<Block> AETHER_DIRT_PATH = registerBlock("aether_dirt_path", AetherDirtPathBlock::new, () -> Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).strength(0.2F).sound(SoundType.GRASS).isViewBlocking(AetherBlocks::always).isSuffocating(AetherBlocks::always));

    public static final DeferredBlock<Block> COLD_AERCLOUD = registerBlock("cold_aercloud", AercloudBlock::new, () -> Block.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.FLUTE).strength(0.3F).sound(SoundType.WOOL).noOcclusion().dynamicShape().isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never));
    public static final DeferredBlock<Block> BLUE_AERCLOUD = registerBlock("blue_aercloud", BlueAercloudBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).instrument(NoteBlockInstrument.FLUTE).strength(0.3F).sound(SoundType.WOOL).noOcclusion().dynamicShape().isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never));
    public static final DeferredBlock<Block> GOLDEN_AERCLOUD = registerBlock("golden_aercloud", AercloudBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.FLUTE).strength(0.3F).sound(SoundType.WOOL).noOcclusion().dynamicShape().isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never));

    public static final DeferredBlock<Block> ICESTONE = registerBlock("icestone", IcestoneBlock::new, () -> Block.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.CHIME).strength(0.5F).randomTicks().sound(SoundType.GLASS).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> AMBROSIUM_ORE = registerBlock("ambrosium_ore", (properties) -> new AetherDoubleDropsOreBlock(UniformInt.of(0, 2), properties), () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> ZANITE_ORE = registerBlock("zanite_ore", (properties) -> new DropExperienceBlock(UniformInt.of(3, 5), properties), () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> GRAVITITE_ORE = registerBlock("gravitite_ore", (properties) -> new FloatingBlock(false, properties), () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F).randomTicks().requiresCorrectToolForDrops());

    public static final DeferredBlock<Block> SKYROOT_LEAVES = registerBlock("skyroot_leaves", AetherDoubleDropsLeaves::new, () -> Block.Properties.of().mapColor(MapColor.GRASS).ignitedByLava().pushReaction(PushReaction.POPPED).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never));
    public static final DeferredBlock<Block> GOLDEN_OAK_LEAVES = registerBlock("golden_oak_leaves", (properties) -> new LeavesWithParticlesBlock(AetherParticleTypes.GOLDEN_OAK_LEAVES, properties), () -> Block.Properties.of().mapColor(MapColor.GOLD).ignitedByLava().pushReaction(PushReaction.POPPED).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never));
    public static final DeferredBlock<Block> CRYSTAL_LEAVES = registerBlock("crystal_leaves", (properties) -> new LeavesWithParticlesBlock(AetherParticleTypes.CRYSTAL_LEAVES, properties), () -> Block.Properties.of().mapColor(MapColor.DIAMOND).ignitedByLava().pushReaction(PushReaction.POPPED).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never));
    public static final DeferredBlock<Block> CRYSTAL_FRUIT_LEAVES = registerBlock("crystal_fruit_leaves", (properties) -> new CrystalFruitLeavesBlock(AetherParticleTypes.CRYSTAL_LEAVES, properties), () -> Block.Properties.of().mapColor(MapColor.DIAMOND).ignitedByLava().pushReaction(PushReaction.POPPED).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never));
    public static final DeferredBlock<Block> HOLIDAY_LEAVES = registerBlock("holiday_leaves", (properties) -> new LeavesWithParticlesBlock(AetherParticleTypes.HOLIDAY_LEAVES, properties), () -> Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).ignitedByLava().pushReaction(PushReaction.POPPED).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never));
    public static final DeferredBlock<Block> DECORATED_HOLIDAY_LEAVES = registerBlock("decorated_holiday_leaves", (properties) -> new LeavesWithParticlesBlock(AetherParticleTypes.HOLIDAY_LEAVES, properties), () -> Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).ignitedByLava().pushReaction(PushReaction.POPPED).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never));

    public static final DeferredBlock<RotatedPillarBlock> SKYROOT_LOG = registerBlock("skyroot_log", AetherLogBlock::new, () -> Block.Properties.ofFullCopy(Blocks.OAK_LOG));
    public static final DeferredBlock<RotatedPillarBlock> GOLDEN_OAK_LOG = registerBlock("golden_oak_log", AetherLogBlock::new, () -> Block.Properties.ofFullCopy(Blocks.OAK_LOG));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_SKYROOT_LOG = registerBlock("stripped_skyroot_log", RotatedPillarBlock::new, () -> Block.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG));
    public static final DeferredBlock<RotatedPillarBlock> SKYROOT_WOOD = registerBlock("skyroot_wood", AetherLogBlock::new, () -> Block.Properties.ofFullCopy(Blocks.OAK_WOOD));
    public static final DeferredBlock<RotatedPillarBlock> GOLDEN_OAK_WOOD = registerBlock("golden_oak_wood", AetherLogBlock::new, () -> Block.Properties.ofFullCopy(Blocks.OAK_WOOD));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_SKYROOT_WOOD = registerBlock("stripped_skyroot_wood", RotatedPillarBlock::new, () -> Block.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<Block> SKYROOT_PLANKS = registerSimpleBlock("skyroot_planks", () -> Block.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> HOLYSTONE_BRICKS = registerSimpleBlock("holystone_bricks", () -> Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(2.0F, 6.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<TransparentBlock> QUICKSOIL_GLASS = registerBlock("quicksoil_glass", QuicksoilGlassBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.HAT).strength(0.2F).friction(1.1F).lightLevel(AetherBlocks::lightLevel11).sound(SoundType.GLASS).noOcclusion().isValidSpawn(AetherBlocks::never).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never));
    public static final DeferredBlock<IronBarsBlock> QUICKSOIL_GLASS_PANE = registerBlock("quicksoil_glass_pane", QuicksoilGlassPaneBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.HAT).strength(0.2F).friction(1.1F).lightLevel(AetherBlocks::lightLevel11).sound(SoundType.GLASS).noOcclusion());
    public static final DeferredBlock<Block> AEROGEL = registerBlock("aerogel", AerogelBlock::new, () -> Block.Properties.of().mapColor(MapColor.DIAMOND).instrument(NoteBlockInstrument.IRON_XYLOPHONE).strength(1.0F, 2000.0F).sound(SoundType.METAL).noOcclusion().requiresCorrectToolForDrops().isViewBlocking(AetherBlocks::never));

    public static final DeferredBlock<Block> AMBROSIUM_BLOCK = registerSimpleBlock("ambrosium_block", () -> Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));
    public static final DeferredBlock<Block> ZANITE_BLOCK = registerSimpleBlock("zanite_block", () -> Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BIT).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));
    public static final DeferredBlock<Block> ENCHANTED_GRAVITITE = registerBlock("enchanted_gravitite", (properties) -> new FloatingBlock(true, properties), () -> Block.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.PLING).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));

    public static final DeferredBlock<Block> ALTAR = registerBlock("altar", AltarBlock::new, () -> Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASEDRUM).strength(2.5F));
    public static final DeferredBlock<Block> FREEZER = registerBlock("freezer", FreezerBlock::new, () -> Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASEDRUM).strength(2.0F));
    public static final DeferredBlock<Block> INCUBATOR = registerBlock("incubator", IncubatorBlock::new, () -> Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASEDRUM).strength(2.0F));

    public static final DeferredBlock<Block> AMBROSIUM_TORCH = registerBlock("ambrosium_torch", (properties) -> new TorchBlock(ParticleTypes.SMOKE, properties), () -> Block.Properties.ofFullCopy(Blocks.TORCH));
    public static final DeferredBlock<Block> AMBROSIUM_WALL_TORCH = registerBlockWithoutItem("ambrosium_wall_torch", (properties) -> new WallTorchBlock(ParticleTypes.SMOKE, properties), () -> Block.Properties.ofFullCopy(Blocks.WALL_TORCH).overrideLootTable(AetherBlocks.AMBROSIUM_TORCH.get().getLootTable()));

    public static final DeferredBlock<StandingSignBlock> SKYROOT_SIGN = registerBlock("skyroot_sign", (properties) -> new SkyrootSignBlock(AetherWoodTypes.SKYROOT, properties), () -> Block.Properties.of().mapColor(MapColor.SAND).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<WallSignBlock> SKYROOT_WALL_SIGN = registerBlockWithoutItem("skyroot_wall_sign", (properties) -> new SkyrootWallSignBlock(AetherWoodTypes.SKYROOT, properties), () -> Block.Properties.of().mapColor(MapColor.SAND).forceSolidOn().ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).sound(SoundType.WOOD).overrideLootTable(SKYROOT_SIGN.get().getLootTable()).overrideDescription(SKYROOT_SIGN.get().getDescriptionId()));
    public static final DeferredBlock<CeilingHangingSignBlock> SKYROOT_HANGING_SIGN = registerBlock("skyroot_hanging_sign", (properties) -> new SkyrootCeilingHangingSignBlock(AetherWoodTypes.SKYROOT, properties), () -> Block.Properties.of().mapColor(Blocks.OAK_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).ignitedByLava());
    public static final DeferredBlock<WallHangingSignBlock> SKYROOT_WALL_HANGING_SIGN = registerBlockWithoutItem("skyroot_wall_hanging_sign", (properties) -> new SkyrootWallHangingSignBlock(AetherWoodTypes.SKYROOT, properties), () -> Block.Properties.of().mapColor(Blocks.OAK_LOG.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).ignitedByLava());

    public static final DeferredBlock<Block> BERRY_BUSH = registerBlock("berry_bush", BerryBushBlock::new, () -> Block.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.POPPED).strength(0.2F).sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherBlocks::ocelotOrParrot).isRedstoneConductor(AetherBlocks::never).isSuffocating(AetherBlocks::never).isViewBlocking(AetherBlocks::never));
    public static final DeferredBlock<Block> BERRY_BUSH_STEM = registerBlock("berry_bush_stem", BerryBushStemBlock::new, () -> Block.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.POPPED).strength(0.2F).sound(SoundType.GRASS).noCollision());
    public static final DeferredBlock<FlowerPotBlock> POTTED_BERRY_BUSH = registerBlockWithoutItem("potted_berry_bush", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BERRY_BUSH, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_BERRY_BUSH_STEM = registerBlockWithoutItem("potted_berry_bush_stem", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BERRY_BUSH_STEM, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));

    public static final DeferredBlock<Block> PURPLE_FLOWER = registerBlock("purple_flower", (properties) -> new AetherFlowerBlock(AetherEffects.INEBRIATION, 12, properties), () -> Block.Properties.ofFullCopy(Blocks.DANDELION));
    public static final DeferredBlock<Block> WHITE_FLOWER = registerBlock("white_flower", (properties) -> new AetherFlowerBlock(MobEffects.SLOW_FALLING, 4, properties), () -> Block.Properties.ofFullCopy(Blocks.DANDELION));
    public static final DeferredBlock<FlowerPotBlock> POTTED_PURPLE_FLOWER = registerBlockWithoutItem("potted_purple_flower", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PURPLE_FLOWER, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WHITE_FLOWER = registerBlockWithoutItem("potted_white_flower", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WHITE_FLOWER, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));

    public static final DeferredBlock<SaplingBlock> SKYROOT_SAPLING = registerBlock("skyroot_sapling", (properties) -> new SaplingBlock(AetherTreeGrowers.SKYROOT, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_SAPLING));
    public static final DeferredBlock<SaplingBlock> GOLDEN_OAK_SAPLING = registerBlock("golden_oak_sapling", (properties) -> new SaplingBlock(AetherTreeGrowers.GOLDEN_OAK, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_SAPLING));
    public static final DeferredBlock<FlowerPotBlock> POTTED_SKYROOT_SAPLING = registerBlockWithoutItem("potted_skyroot_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYROOT_SAPLING, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_GOLDEN_OAK_SAPLING = registerBlockWithoutItem("potted_golden_oak_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GOLDEN_OAK_SAPLING, properties), () -> Block.Properties.ofFullCopy(Blocks.FLOWER_POT));

    public static final DeferredBlock<Block> CARVED_STONE = registerSimpleBlock("carved_stone", () -> Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F, 6.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> SENTRY_STONE = registerSimpleBlock("sentry_stone", () -> Block.Properties.ofFullCopy(CARVED_STONE.get()).lightLevel(AetherBlocks::lightLevel11));
    public static final DeferredBlock<Block> ANGELIC_STONE = registerSimpleBlock("angelic_stone", () -> Block.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F, 6.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> LIGHT_ANGELIC_STONE = registerSimpleBlock("light_angelic_stone", () -> Block.Properties.ofFullCopy(ANGELIC_STONE.get()).lightLevel(AetherBlocks::lightLevel11));
    public static final DeferredBlock<Block> HELLFIRE_STONE = registerSimpleBlock("hellfire_stone", () -> Block.Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F, 6.0F).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> LIGHT_HELLFIRE_STONE = registerSimpleBlock("light_hellfire_stone", () -> Block.Properties.ofFullCopy(HELLFIRE_STONE.get()).lightLevel(AetherBlocks::lightLevel11));

    public static final DeferredBlock<Block> LOCKED_CARVED_STONE = registerSimpleBlock("locked_carved_stone", () -> Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F));
    public static final DeferredBlock<Block> LOCKED_SENTRY_STONE = registerSimpleBlock("locked_sentry_stone", () -> Block.Properties.ofFullCopy(LOCKED_CARVED_STONE.get()).lightLevel(AetherBlocks::lightLevel11));
    public static final DeferredBlock<Block> LOCKED_ANGELIC_STONE = registerSimpleBlock("locked_angelic_stone", () -> Block.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F));
    public static final DeferredBlock<Block> LOCKED_LIGHT_ANGELIC_STONE = registerSimpleBlock("locked_light_angelic_stone", () -> Block.Properties.ofFullCopy(LOCKED_ANGELIC_STONE.get()).lightLevel(AetherBlocks::lightLevel11));
    public static final DeferredBlock<Block> LOCKED_HELLFIRE_STONE = registerSimpleBlock("locked_hellfire_stone", () -> Block.Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F));
    public static final DeferredBlock<Block> LOCKED_LIGHT_HELLFIRE_STONE = registerSimpleBlock("locked_light_hellfire_stone", () -> Block.Properties.ofFullCopy(LOCKED_HELLFIRE_STONE.get()).lightLevel(AetherBlocks::lightLevel11));

    public static final DeferredBlock<Block> TRAPPED_CARVED_STONE = registerBlock("trapped_carved_stone", (properties) -> new TrappedBlock(AetherEntityTypes.SENTRY::get, () -> CARVED_STONE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(CARVED_STONE.get()));
    public static final DeferredBlock<Block> TRAPPED_SENTRY_STONE = registerBlock("trapped_sentry_stone", (properties) -> new TrappedBlock(AetherEntityTypes.SENTRY::get, () -> SENTRY_STONE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(SENTRY_STONE.get()));
    public static final DeferredBlock<Block> TRAPPED_ANGELIC_STONE = registerBlock("trapped_angelic_stone", (properties) -> new TrappedBlock(AetherEntityTypes.VALKYRIE::get, () -> LOCKED_ANGELIC_STONE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(LOCKED_ANGELIC_STONE.get()));
    public static final DeferredBlock<Block> TRAPPED_LIGHT_ANGELIC_STONE = registerBlock("trapped_light_angelic_stone", (properties) -> new TrappedBlock(AetherEntityTypes.VALKYRIE::get, () -> LOCKED_LIGHT_ANGELIC_STONE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(LOCKED_LIGHT_ANGELIC_STONE.get()));
    public static final DeferredBlock<Block> TRAPPED_HELLFIRE_STONE = registerBlock("trapped_hellfire_stone", (properties) -> new TrappedBlock(AetherEntityTypes.FIRE_MINION::get, () -> LOCKED_HELLFIRE_STONE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(LOCKED_HELLFIRE_STONE.get()));
    public static final DeferredBlock<Block> TRAPPED_LIGHT_HELLFIRE_STONE = registerBlock("trapped_light_hellfire_stone", (properties) -> new TrappedBlock(AetherEntityTypes.FIRE_MINION::get, () -> LOCKED_LIGHT_HELLFIRE_STONE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(LOCKED_LIGHT_HELLFIRE_STONE.get()));

    public static final DeferredBlock<Block> BOSS_DOORWAY_CARVED_STONE = registerBlock("boss_doorway_carved_stone", (properties) -> new DoorwayBlock(AetherEntityTypes.SLIDER::get, properties), () -> Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F).forceSolidOn());
    public static final DeferredBlock<Block> BOSS_DOORWAY_SENTRY_STONE = registerBlock("boss_doorway_sentry_stone", (properties) -> new DoorwayBlock(AetherEntityTypes.SLIDER::get, properties), () -> Block.Properties.ofFullCopy(BOSS_DOORWAY_CARVED_STONE.get()));
    public static final DeferredBlock<Block> BOSS_DOORWAY_ANGELIC_STONE = registerBlock("boss_doorway_angelic_stone", (properties) -> new DoorwayBlock(AetherEntityTypes.VALKYRIE_QUEEN::get, properties), () -> Block.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F).forceSolidOn());
    public static final DeferredBlock<Block> BOSS_DOORWAY_LIGHT_ANGELIC_STONE = registerBlock("boss_doorway_light_angelic_stone", (properties) -> new DoorwayBlock(AetherEntityTypes.VALKYRIE_QUEEN::get, properties), () -> Block.Properties.ofFullCopy(BOSS_DOORWAY_ANGELIC_STONE.get()));
    public static final DeferredBlock<Block> BOSS_DOORWAY_HELLFIRE_STONE = registerBlock("boss_doorway_hellfire_stone", (properties) -> new DoorwayBlock(AetherEntityTypes.SUN_SPIRIT::get, properties), () -> Block.Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F).forceSolidOn());
    public static final DeferredBlock<Block> BOSS_DOORWAY_LIGHT_HELLFIRE_STONE = registerBlock("boss_doorway_light_hellfire_stone", (properties) -> new DoorwayBlock(AetherEntityTypes.SUN_SPIRIT::get, properties), () -> Block.Properties.ofFullCopy(BOSS_DOORWAY_HELLFIRE_STONE.get()));

    public static final DeferredBlock<Block> TREASURE_DOORWAY_CARVED_STONE = registerBlock("treasure_doorway_carved_stone", TreasureDoorwayBlock::new, () -> Block.Properties.ofFullCopy(LOCKED_CARVED_STONE.get()));
    public static final DeferredBlock<Block> TREASURE_DOORWAY_SENTRY_STONE = registerBlock("treasure_doorway_sentry_stone", TreasureDoorwayBlock::new, () -> Block.Properties.ofFullCopy(LOCKED_SENTRY_STONE.get()));
    public static final DeferredBlock<Block> TREASURE_DOORWAY_ANGELIC_STONE = registerBlock("treasure_doorway_angelic_stone", TreasureDoorwayBlock::new, () -> Block.Properties.ofFullCopy(LOCKED_ANGELIC_STONE.get()));
    public static final DeferredBlock<Block> TREASURE_DOORWAY_LIGHT_ANGELIC_STONE = registerBlock("treasure_doorway_light_angelic_stone", TreasureDoorwayBlock::new, () -> Block.Properties.ofFullCopy(LOCKED_LIGHT_ANGELIC_STONE.get()));
    public static final DeferredBlock<Block> TREASURE_DOORWAY_HELLFIRE_STONE = registerBlock("treasure_doorway_hellfire_stone", TreasureDoorwayBlock::new, () -> Block.Properties.ofFullCopy(LOCKED_HELLFIRE_STONE.get()));
    public static final DeferredBlock<Block> TREASURE_DOORWAY_LIGHT_HELLFIRE_STONE = registerBlock("treasure_doorway_light_hellfire_stone", TreasureDoorwayBlock::new, () -> Block.Properties.ofFullCopy(LOCKED_LIGHT_HELLFIRE_STONE.get()));

    public static final DeferredBlock<Block> CHEST_MIMIC = registerBlock("chest_mimic", ChestMimicBlock::new, () -> Block.Properties.ofFullCopy(Blocks.CHEST));
    public static final DeferredBlock<Block> TREASURE_CHEST = registerBlock("treasure_chest", TreasureChestBlock::new, () -> Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F).requiresCorrectToolForDrops());

    public static final DeferredBlock<RotatedPillarBlock> PILLAR = registerBlock("pillar", RotatedPillarBlock::new, () -> Block.Properties.of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F).sound(SoundType.METAL).requiresCorrectToolForDrops());
    public static final DeferredBlock<FacingPillarBlock> PILLAR_TOP = registerBlock("pillar_top", FacingPillarBlock::new, () -> Block.Properties.of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F).sound(SoundType.METAL).requiresCorrectToolForDrops());

    public static final DeferredBlock<Block> PRESENT = registerSimpleBlock("present", () -> Block.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BELL).strength(0.1F).sound(SoundType.WOOL));

    public static final DeferredBlock<FenceBlock> SKYROOT_FENCE = registerBlock("skyroot_fence", FenceBlock::new, () -> Block.Properties.ofFullCopy(Blocks.OAK_FENCE));
    public static final DeferredBlock<FenceGateBlock> SKYROOT_FENCE_GATE = registerBlock("skyroot_fence_gate", (properties) -> new FenceGateBlock(AetherWoodTypes.SKYROOT, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE));
    public static final DeferredBlock<DoorBlock> SKYROOT_DOOR = registerBlock("skyroot_door", (properties) -> new DoorBlock(AetherWoodTypes.SKYROOT_BLOCK_SET, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_DOOR));
    public static final DeferredBlock<TrapDoorBlock> SKYROOT_TRAPDOOR = registerBlock("skyroot_trapdoor", (properties) -> new TrapDoorBlock(AetherWoodTypes.SKYROOT_BLOCK_SET, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR));
    public static final DeferredBlock<ButtonBlock> SKYROOT_BUTTON = registerBlock("skyroot_button", (properties) -> new ButtonBlock(AetherWoodTypes.SKYROOT_BLOCK_SET, 30, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_BUTTON));
    public static final DeferredBlock<PressurePlateBlock> SKYROOT_PRESSURE_PLATE = registerBlock("skyroot_pressure_plate", (properties) -> new PressurePlateBlock(AetherWoodTypes.SKYROOT_BLOCK_SET, properties), () -> Block.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE));

    public static final DeferredBlock<ButtonBlock> HOLYSTONE_BUTTON = registerBlock("holystone_button", (properties) -> new ButtonBlock(BlockSetType.STONE, 20, properties), () -> Block.Properties.ofFullCopy(Blocks.STONE_BUTTON));
    public static final DeferredBlock<PressurePlateBlock> HOLYSTONE_PRESSURE_PLATE = registerBlock("holystone_pressure_plate", (properties) -> new PressurePlateBlock(BlockSetType.STONE, properties), () -> Block.Properties.of().mapColor(MapColor.WOOL).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noCollision().strength(0.5F));

    public static final DeferredBlock<WallBlock> CARVED_WALL = registerBlock("carved_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherBlocks.CARVED_STONE.get()).forceSolidOn());
    public static final DeferredBlock<WallBlock> ANGELIC_WALL = registerBlock("angelic_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherBlocks.ANGELIC_STONE.get()).forceSolidOn());
    public static final DeferredBlock<WallBlock> HELLFIRE_WALL = registerBlock("hellfire_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherBlocks.HELLFIRE_STONE.get()).forceSolidOn());
    public static final DeferredBlock<WallBlock> HOLYSTONE_WALL = registerBlock("holystone_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherBlocks.HOLYSTONE.get()).forceSolidOn());
    public static final DeferredBlock<WallBlock> MOSSY_HOLYSTONE_WALL = registerBlock("mossy_holystone_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherBlocks.MOSSY_HOLYSTONE.get()).forceSolidOn());
    public static final DeferredBlock<WallBlock> ICESTONE_WALL = registerBlock("icestone_wall", IcestoneWallBlock::new, () -> Block.Properties.ofFullCopy(AetherBlocks.ICESTONE.get()).forceSolidOn());
    public static final DeferredBlock<WallBlock> HOLYSTONE_BRICK_WALL = registerBlock("holystone_brick_wall", WallBlock::new, () -> Block.Properties.ofFullCopy(AetherBlocks.HOLYSTONE_BRICKS.get()).forceSolidOn());
    public static final DeferredBlock<WallBlock> AEROGEL_WALL = registerBlock("aerogel_wall", AerogelWallBlock::new, () -> Block.Properties.of().mapColor(MapColor.DIAMOND).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).strength(1.0F, 2000.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().isViewBlocking(AetherBlocks::never).noOcclusion());

    public static final DeferredBlock<StairBlock> SKYROOT_STAIRS = registerBlock("skyroot_stairs", (properties) -> new StairBlock(SKYROOT_PLANKS.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherBlocks.SKYROOT_PLANKS.get()));
    public static final DeferredBlock<StairBlock> CARVED_STAIRS = registerBlock("carved_stairs", (properties) -> new StairBlock(CARVED_STONE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherBlocks.CARVED_STONE.get()));
    public static final DeferredBlock<StairBlock> ANGELIC_STAIRS = registerBlock("angelic_stairs", (properties) -> new StairBlock(ANGELIC_STONE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherBlocks.ANGELIC_STONE.get()));
    public static final DeferredBlock<StairBlock> HELLFIRE_STAIRS = registerBlock("hellfire_stairs", (properties) -> new StairBlock(HELLFIRE_STONE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherBlocks.HELLFIRE_STONE.get()));
    public static final DeferredBlock<StairBlock> HOLYSTONE_STAIRS = registerBlock("holystone_stairs", (properties) -> new StairBlock(HOLYSTONE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherBlocks.HOLYSTONE.get()));
    public static final DeferredBlock<StairBlock> MOSSY_HOLYSTONE_STAIRS = registerBlock("mossy_holystone_stairs", (properties) -> new StairBlock(MOSSY_HOLYSTONE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherBlocks.MOSSY_HOLYSTONE.get()));
    public static final DeferredBlock<StairBlock> ICESTONE_STAIRS = registerBlock("icestone_stairs", (properties) -> new IcestoneStairsBlock(ICESTONE.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherBlocks.ICESTONE.get()));
    public static final DeferredBlock<StairBlock> HOLYSTONE_BRICK_STAIRS = registerBlock("holystone_brick_stairs", (properties) -> new StairBlock(HOLYSTONE_BRICKS.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherBlocks.HOLYSTONE_BRICKS.get()));
    public static final DeferredBlock<StairBlock> AEROGEL_STAIRS = registerBlock("aerogel_stairs", (properties) -> new AerogelStairsBlock(AEROGEL.get().defaultBlockState(), properties), () -> Block.Properties.ofFullCopy(AetherBlocks.AEROGEL.get()).isViewBlocking(AetherBlocks::never));

    public static final DeferredBlock<SlabBlock> SKYROOT_SLAB = registerBlock("skyroot_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherBlocks.SKYROOT_PLANKS.get()).strength(2.0F, 3.0F));
    public static final DeferredBlock<SlabBlock> CARVED_SLAB = registerBlock("carved_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherBlocks.CARVED_STONE.get()).strength(0.5F, 6.0F));
    public static final DeferredBlock<SlabBlock> ANGELIC_SLAB = registerBlock("angelic_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherBlocks.ANGELIC_STONE.get()).strength(0.5F, 6.0F));
    public static final DeferredBlock<SlabBlock> HELLFIRE_SLAB = registerBlock("hellfire_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherBlocks.HELLFIRE_STONE.get()).strength(0.5F, 6.0F));
    public static final DeferredBlock<SlabBlock> HOLYSTONE_SLAB = registerBlock("holystone_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherBlocks.HOLYSTONE.get()).strength(0.5F, 6.0F));
    public static final DeferredBlock<SlabBlock> MOSSY_HOLYSTONE_SLAB = registerBlock("mossy_holystone_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherBlocks.MOSSY_HOLYSTONE.get()).strength(0.5F, 6.0F));
    public static final DeferredBlock<SlabBlock> ICESTONE_SLAB = registerBlock("icestone_slab", IcestoneSlabBlock::new, () -> Block.Properties.ofFullCopy(AetherBlocks.ICESTONE.get()).strength(0.5F, 6.0F));
    public static final DeferredBlock<SlabBlock> HOLYSTONE_BRICK_SLAB = registerBlock("holystone_brick_slab", SlabBlock::new, () -> Block.Properties.ofFullCopy(AetherBlocks.HOLYSTONE_BRICKS.get()).strength(2.0F, 6.0F));
    public static final DeferredBlock<SlabBlock> AEROGEL_SLAB = registerBlock("aerogel_slab", AerogelSlabBlock::new, () -> Block.Properties.ofFullCopy(AetherBlocks.AEROGEL.get()).strength(1.0F, 2000.0F).isViewBlocking(AetherBlocks::never));

    public static final DeferredBlock<Block> SUN_ALTAR = registerBlock("sun_altar", SunAltarBlock::new, () -> Block.Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).strength(2.0F).sound(SoundType.METAL));

    public static final DeferredBlock<Block> SKYROOT_BOOKSHELF = registerBlock("skyroot_bookshelf", BookshelfBlock::new, () -> Block.Properties.ofFullCopy(Blocks.BOOKSHELF));

    public static final DeferredBlock<BedBlock> SKYROOT_BED = registerBlock("skyroot_bed", SkyrootBedBlock::new, () -> Block.Properties.ofFullCopy(Blocks.BED.pick(DyeColor.CYAN)));

    public static final DeferredBlock<Block> FROSTED_ICE = registerBlockWithoutItem("frosted_ice", AetherFrostedIceBlock::new, () -> Block.Properties.of().mapColor(MapColor.ICE).friction(0.98F).randomTicks().strength(0.5F).sound(SoundType.GLASS).noOcclusion().isValidSpawn((state, level, pos, entityType) -> entityType == EntityTypes.POLAR_BEAR).isRedstoneConductor(AetherBlocks::never));
    public static final DeferredBlock<Block> UNSTABLE_OBSIDIAN = registerBlockWithoutItem("unstable_obsidian", UnstableObsidianBlock::new, () -> Block.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).randomTicks().requiresCorrectToolForDrops().strength(50.0F, 1200.0F));

    public static void registerFlammability() {
        FireBlockAccessor fireBlockAccessor = (FireBlockAccessor) Blocks.FIRE;
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherBlocks.GOLDEN_OAK_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherBlocks.CRYSTAL_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherBlocks.CRYSTAL_FRUIT_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherBlocks.HOLIDAY_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherBlocks.DECORATED_HOLIDAY_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherBlocks.GOLDEN_OAK_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherBlocks.STRIPPED_SKYROOT_LOG.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherBlocks.GOLDEN_OAK_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherBlocks.STRIPPED_SKYROOT_WOOD.get(), 5, 5);
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_PLANKS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherBlocks.BERRY_BUSH.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(AetherBlocks.BERRY_BUSH_STEM.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherBlocks.PURPLE_FLOWER.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherBlocks.WHITE_FLOWER.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_FENCE_GATE.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_FENCE.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_STAIRS.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_SLAB.get(), 5, 20);
        fireBlockAccessor.callSetFlammable(AetherBlocks.SKYROOT_BOOKSHELF.get(), 30, 20);
    }

    public static void registerFluidInteractions() {
        // Handled by LiquidBlockMixin.nitrogen_fabric$adjustInteraction
//        FluidInteractionRegistry.addInteraction(NeoForgeMod.WATER_TYPE.value(), new FluidInteractionRegistry.InteractionInformation(
//                (level, currentPos, relativePos, currentState) -> level.getBlockState(currentPos.below()).is(AetherBlocks.QUICKSOIL.get()) && level.getBlockState(relativePos).is(Blocks.MAGMA_BLOCK),
//                AetherBlocks.HOLYSTONE.get().defaultBlockState()
//        ));
    }

    public static void registerWoodTypes() {
        //WoodType.register(AetherWoodTypes.SKYROOT);
    }

    public static <B extends Block> DeferredBlock<B> register(String name, Function<Identifier, ? extends B> func) {
        DeferredBlock<B> block = BLOCKS.register(name, func);
        Supplier<? extends Item> item = registerBlockItem(name, block);
        AetherItems.ITEMS.register(name, item);
        return block;
    }

    public static <B extends Block> DeferredBlock<B> registerBlock(String name, Function<BlockBehaviour.Properties, ? extends B> func, Supplier<BlockBehaviour.Properties> props) {
        return register(name, (key) -> func.apply(props.get().setId(ResourceKey.create(Registries.BLOCK, key))));
    }

    /**
     * Registers a block without a block item, such as technical blocks or wall variants of standing blocks.
     */
    public static <B extends Block> DeferredBlock<B> registerBlockWithoutItem(String name, Function<BlockBehaviour.Properties, ? extends B> func, Supplier<BlockBehaviour.Properties> props) {
        return BLOCKS.register(name, (key) -> func.apply(props.get().setId(ResourceKey.create(Registries.BLOCK, key))));
    }

    public static DeferredBlock<Block> registerSimpleBlock(String name, Supplier<BlockBehaviour.Properties> props) {
        return registerBlock(name, Block::new, props);
    }

    /**
     * Applies the cooking fuel and compostable components for block items, matching their vanilla equivalents.
     *
     * @param block      The {@link DeferredBlock} for the item.
     * @param properties The {@link Item.Properties} of the item.
     * @return The modified {@link Item.Properties}.
     */
    private static Item.Properties blockItemComponents(DeferredBlock<?> block, Item.Properties properties) {
        if (block == SKYROOT_LOG || block == GOLDEN_OAK_LOG || block == STRIPPED_SKYROOT_LOG || block == SKYROOT_WOOD || block == GOLDEN_OAK_WOOD || block == STRIPPED_SKYROOT_WOOD
                || block == SKYROOT_PLANKS || block == SKYROOT_BOOKSHELF || block == SKYROOT_FENCE || block == SKYROOT_FENCE_GATE || block == SKYROOT_STAIRS
                || block == SKYROOT_TRAPDOOR || block == SKYROOT_PRESSURE_PLATE || block == CHEST_MIMIC) {
            properties.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS);
        } else if (block == SKYROOT_SLAB) {
            properties.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_SLABS);
        } else if (block == SKYROOT_BUTTON) {
            properties.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_ITEMS_EXTRA_SMALL);
        } else if (block == SKYROOT_DOOR || block == SKYROOT_SIGN) {
            properties.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_ITEMS_LARGE);
        } else if (block == SKYROOT_HANGING_SIGN) {
            properties.cookingFuel(ContextIntProviders.COOKING_TIME_HANGING_SIGNS);
        } else if (block == AMBROSIUM_BLOCK) {
            properties.cookingFuel(ContextIntProviders.COOKING_TIME_COAL_BLOCK);
        } else if (block == SKYROOT_SAPLING || block == GOLDEN_OAK_SAPLING) {
            properties.compostable(ContextIntProviders.COMPOSTABLE_LOW).cookingFuel(ContextIntProviders.COOKING_TIME_DRY_PLANTS);
        } else if (block == SKYROOT_LEAVES || block == GOLDEN_OAK_LEAVES || block == CRYSTAL_LEAVES || block == CRYSTAL_FRUIT_LEAVES || block == HOLIDAY_LEAVES || block == DECORATED_HOLIDAY_LEAVES) {
            properties.compostable(ContextIntProviders.COMPOSTABLE_LOW);
        } else if (block == BERRY_BUSH || block == BERRY_BUSH_STEM) {
            properties.compostable(ContextIntProviders.COMPOSTABLE_LOW_MEDIUM);
        } else if (block == WHITE_FLOWER || block == PURPLE_FLOWER) {
            properties.compostable(ContextIntProviders.COMPOSTABLE_MEDIUM);
        }
        return properties;
    }

    private static <T extends Block> Supplier<BlockItem> registerBlockItem(String name, final DeferredBlock<T> deferredBlock) {
        return () -> {
            DeferredBlock<T> block = Objects.requireNonNull(deferredBlock);
            Item.Properties itemProperties = blockItemComponents(block, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Aether.MODID, name))).useBlockDescriptionPrefix());
            if (block == ENCHANTED_AETHER_GRASS_BLOCK
                    || block == QUICKSOIL_GLASS
                    || block == QUICKSOIL_GLASS_PANE) {
                return new BlockItem(block.get(), itemProperties.rarity(Rarity.RARE));
            } else if (block == ENCHANTED_GRAVITITE) {
                return new BlockItem(block.get(), itemProperties.rarity(Rarity.RARE).trimMaterial(AetherTrimMaterials.GRAVITITE));
            } else if (block == AEROGEL
                    || block == AEROGEL_WALL
                    || block == AEROGEL_STAIRS
                    || block == AEROGEL_SLAB) {
                return new BlockItem(block.get(), itemProperties.rarity(AetherItems.AETHER_LOOT));
            } else if (block == AMBROSIUM_TORCH) {
                return new StandingAndWallBlockItem(AMBROSIUM_TORCH.get(), AMBROSIUM_WALL_TORCH.get(), Direction.DOWN, itemProperties);
            } else if (block == SKYROOT_SIGN) {
                return new StandingAndWallBlockItem(SKYROOT_SIGN.get(), SKYROOT_WALL_SIGN.get(), Direction.DOWN, itemProperties.stacksTo(16).signText());
            } else if (block == SKYROOT_HANGING_SIGN) {
                return new HangingSignItem(SKYROOT_HANGING_SIGN.get(), SKYROOT_WALL_HANGING_SIGN.get(), itemProperties.stacksTo(16).signText());
            } else if (block == CHEST_MIMIC) {
                return new BlockItem(block.get(), itemProperties);
            } else if (block == TREASURE_CHEST) {
                return new BlockItem(block.get(), itemProperties);
            } else if (block == SKYROOT_DOOR) {
                return new DoubleHighBlockItem(block.get(), itemProperties);
            } else if (block == SUN_ALTAR) {
                return new BlockItem(block.get(), itemProperties.fireResistant());
            } else if (block == SKYROOT_BED) {
                return new BlockItem(block.get(), itemProperties.stacksTo(1));
            } else {
                return new BlockItem(block.get(), itemProperties);
            }
        };
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    private static boolean always(BlockState state, BlockGetter getter, BlockPos pos) {
        return true;
    }

    private static <A> boolean never(BlockState state, BlockGetter getter, BlockPos pos, A block) {
        return false;
    }

    private static <A> boolean always(BlockState state, BlockGetter getter, BlockPos pos, A block) {
        return true;
    }

    private static boolean ocelotOrParrot(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> type) {
        return type == EntityTypes.OCELOT || type == EntityTypes.PARROT;
    }

    private static int lightLevel11(BlockState state) {
        return 11;
    }
}
