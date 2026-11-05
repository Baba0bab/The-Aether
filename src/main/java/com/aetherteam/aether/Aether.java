package com.aetherteam.aether;

import com.aetherteam.aetherfabric.AetherBlockTransformers;
import com.aetherteam.aether.entity.AetherEntityDataSerializers;
import net.minecraft.core.cauldron.CauldronInteractions;
import java.util.stream.Stream;
import java.util.Arrays;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackMetadataResources;
import com.aetherteam.aether.advancement.AetherAdvancementTriggers;
import com.aetherteam.aether.api.AetherAdvancementSoundOverrides;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.AetherCauldronInteractions;
import com.aetherteam.aether.block.dispenser.AetherDispenseBehaviors;
import com.aetherteam.aether.block.dispenser.DispenseUsableItemBehavior;
import com.aetherteam.aether.blockentity.AetherBlockEntityTypes;
import com.aetherteam.aether.blockentity.TreasureChestBlockEntity;
import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.client.TriviaGenerator;
import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.aetherteam.aether.command.AetherCommands;
import com.aetherteam.aether.command.SunAltarWhitelist;
import com.aetherteam.aether.data.ReloadListeners;
import com.aetherteam.aether.data.resources.AetherMobCategory;
import com.aetherteam.aether.data.resources.registries.AetherDataMaps;
import com.aetherteam.aether.data.resources.registries.AetherMoaTypes;
import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.ai.attribute.AetherAttributes;
import com.aetherteam.aether.event.listeners.DimensionListener;
import com.aetherteam.aether.event.listeners.EntityListener;
import com.aetherteam.aether.event.listeners.PerkListener;
import com.aetherteam.aether.event.listeners.RecipeListener;
import com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener;
import com.aetherteam.aether.event.listeners.abilities.ArmorAbilityListener;
import com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener;
import com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener;
import com.aetherteam.aether.event.listeners.capability.AetherPlayerListener;
import com.aetherteam.aether.event.listeners.capability.AetherTimeListener;
import com.aetherteam.aether.inventory.AetherRecipeBookTypes;
import com.aetherteam.aether.inventory.menu.AetherMenuTypes;
import com.aetherteam.aether.item.AetherCreativeTabs;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.combat.ZaniteSwordItem;
import com.aetherteam.aether.item.combat.loot.FlamingSwordItem;
import com.aetherteam.aether.item.combat.loot.HolySwordItem;
import com.aetherteam.aether.item.combat.loot.PigSlayerItem;
import com.aetherteam.aether.item.components.AetherDataComponents;
import com.aetherteam.aether.loot.conditions.AetherLootConditions;
import com.aetherteam.aether.loot.functions.AetherLootFunctions;
import com.aetherteam.aether.loot.modifiers.AetherLootModifiers;
import com.aetherteam.aether.network.packet.AetherPlayerSyncPacket;
import com.aetherteam.aether.network.packet.AetherTimeSyncPacket;
import com.aetherteam.aether.network.packet.PhoenixArrowSyncPacket;
import com.aetherteam.aether.network.packet.clientbound.*;
import com.aetherteam.aether.network.packet.serverbound.*;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.book.AetherRecipeBookCategories;
import com.aetherteam.aether.recipe.display.AetherRecipeDisplays;
import com.aetherteam.aether.recipe.recipes.set.AetherRecipePropertySets;
import com.aetherteam.aether.world.AetherPoi;
import com.aetherteam.aether.world.feature.AetherFeatures;
import com.aetherteam.aether.world.foliageplacer.AetherFoliagePlacerTypes;
import com.aetherteam.aether.world.placementmodifier.AetherPlacementModifiers;
import com.aetherteam.aether.world.processor.AetherStructureProcessors;
import com.aetherteam.aether.world.structure.AetherStructureTypes;
import com.aetherteam.aether.world.structurepiece.AetherStructurePieceTypes;
import com.aetherteam.aether.world.treedecorator.AetherTreeDecoratorTypes;
import com.aetherteam.aether.world.trunkplacer.AetherTrunkPlacerTypes;
import com.aetherteam.nitrogen.fabric.NetworkRegisterHelper;
import com.aetherteam.nitrogen.fabric.WrappedInventoryStorage;
import com.aetherteam.nitrogen.fabric.events.AddPackFindersEvent;
import com.aetherteam.nitrogen.fabric.events.ItemAttributeModifierHelper;
import com.aetherteam.nitrogen.fabric.events.LivingEntityEvents;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;
import com.aetherteam.nitrogen.fabric.registries.datamaps.RegisterDataMapTypesEvent;
import com.google.common.reflect.Reflection;
import com.mojang.logging.LogUtils;
import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.fabric.api.transfer.v1.item.ContainerStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.core.dispenser.ProjectileDispenseBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class Aether implements ModInitializer {
    public static final String MODID = "aether";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Path DIRECTORY = FabricLoader.getInstance().getConfigDir().resolve(Aether.MODID);

    public static final TriviaGenerator TRIVIA_READER = new TriviaGenerator();

    @Override
    public void onInitialize() {
        DIRECTORY.toFile().mkdirs(); // Ensures the Aether's config folder is generated.
        ConfigRegistry.INSTANCE.register(Aether.MODID, ModConfig.Type.STARTUP, AetherConfig.STARTUP_SPEC);
        ConfigRegistry.INSTANCE.register(Aether.MODID, ModConfig.Type.SERVER, AetherConfig.SERVER_SPEC);
        ConfigRegistry.INSTANCE.register(Aether.MODID, ModConfig.Type.COMMON, AetherConfig.COMMON_SPEC);
        ConfigRegistry.INSTANCE.register(Aether.MODID, ModConfig.Type.CLIENT, AetherConfig.CLIENT_SPEC);

        DeferredRegister<?>[] registers = {
                AetherEntityTypes.ENTITY_TYPES,
                AetherDataComponents.DATA_COMPONENT_TYPES,
                AetherBlocks.BLOCKS,
                AetherItems.ITEMS,
                AetherAttributes.ATTRIBUTES,
                AetherBlockEntityTypes.BLOCK_ENTITY_TYPES,
                AetherMenuTypes.MENU_TYPES,
                AetherEffects.EFFECTS,
                AetherParticleTypes.PARTICLES,
                AetherFeatures.FEATURES,
                AetherFoliagePlacerTypes.FOLIAGE_PLACERS,
                AetherPlacementModifiers.PLACEMENT_MODIFIERS,
                AetherTrunkPlacerTypes.TRUNK_PLACERS,
                AetherTreeDecoratorTypes.TREE_DECORATORS,
                AetherStructureTypes.STRUCTURE_TYPES,
                AetherStructurePieceTypes.STRUCTURE_PIECE_TYPES,
                AetherStructureProcessors.STRUCTURE_PROCESSOR_TYPES,
                AetherRecipeTypes.RECIPE_TYPES,
                AetherRecipeSerializers.RECIPE_SERIALIZERS,
                AetherRecipeDisplays.RECIPE_DISPLAYS,
                AetherRecipeBookCategories.RECIPE_BOOK_CATEGORIES,
                AetherLootFunctions.LOOT_FUNCTION_TYPES,
                AetherLootConditions.LOOT_CONDITION_TYPES,
                AetherLootModifiers.GLOBAL_LOOT_MODIFIERS,
                AetherSoundEvents.SOUNDS,
                AetherGameEvents.GAME_EVENTS,
                AetherCreativeTabs.CREATIVE_MODE_TABS,
                AetherAdvancementSoundOverrides.ADVANCEMENT_SOUND_OVERRIDES,
                AetherAdvancementTriggers.TRIGGERS,
        };

        for (DeferredRegister<?> register : registers) {
            register.addEntriesToRegistry();
        }

        AetherEntityDataSerializers.init();
        AetherBlockTransformers.init();
        AetherDataAttachments.init();

        AetherPoi.init();

        this.registerCapabilities();
        this.registerPackets();
        RegisterDataMapTypesEvent.EVENT.register(this::registerDataMaps);
        AddPackFindersEvent.EVENT.register(this::packSetup);
        DynamicRegistries.registerSynced(AetherMoaTypes.MOA_TYPE_REGISTRY_KEY, MoaType.CODEC);

        this.eventSetup();

        AetherBlocks.registerWoodTypes(); // Registered this early to avoid bugs with WoodTypes and signs.

        Reflection.initialize(SunAltarWhitelist.class);
        Reflection.initialize(AetherRecipeBookTypes.class);
        Reflection.initialize(AetherMobCategory.class);
        Reflection.initialize(AetherAdvancementTriggers.class);

        AetherBlocks.registerFlammability();
        AetherBlocks.registerFluidInteractions();

        AetherItems.setupBucketReplacements();

        AetherRecipePropertySets.addToMap();
        AetherRecipeSerializers.RECIPE_SERIALIZERS.getEntries().forEach((serializer) -> RecipeSynchronization.synchronizeRecipeSerializer(serializer.get()));

        this.registerDispenserBehaviors();
        this.registerCauldronInteractions();
    }

    public void registerPackets() {
        NetworkRegisterHelper registrar = NetworkRegisterHelper.INSTANCE;

        // CLIENTBOUND
        registrar.playToClient(AetherTravelPacket.TYPE, AetherTravelPacket.STREAM_CODEC, AetherTravelPacket::execute);
        registrar.playToClient(BossInfoPacket.Display.TYPE, BossInfoPacket.Display.STREAM_CODEC, BossInfoPacket.Display::execute);
        registrar.playToClient(BossInfoPacket.Remove.TYPE, BossInfoPacket.Remove.STREAM_CODEC, BossInfoPacket.Remove::execute);
        registrar.playToClient(ClientDeveloperGlowPacket.Apply.TYPE, ClientDeveloperGlowPacket.Apply.STREAM_CODEC, ClientDeveloperGlowPacket.Apply::execute);
        registrar.playToClient(ClientDeveloperGlowPacket.Remove.TYPE, ClientDeveloperGlowPacket.Remove.STREAM_CODEC, ClientDeveloperGlowPacket.Remove::execute);
        registrar.playToClient(ClientDeveloperGlowPacket.Sync.TYPE, ClientDeveloperGlowPacket.Sync.STREAM_CODEC, ClientDeveloperGlowPacket.Sync::execute);
        registrar.playToClient(ClientGrabItemPacket.TYPE, ClientGrabItemPacket.STREAM_CODEC, ClientGrabItemPacket::execute);
        registrar.playToClient(ClientHaloPacket.Apply.TYPE, ClientHaloPacket.Apply.STREAM_CODEC, ClientHaloPacket.Apply::execute);
        registrar.playToClient(ClientHaloPacket.Remove.TYPE, ClientHaloPacket.Remove.STREAM_CODEC, ClientHaloPacket.Remove::execute);
        registrar.playToClient(ClientHaloPacket.Sync.TYPE, ClientHaloPacket.Sync.STREAM_CODEC, ClientHaloPacket.Sync::execute);
        registrar.playToClient(ClientMoaSkinPacket.Apply.TYPE, ClientMoaSkinPacket.Apply.STREAM_CODEC, ClientMoaSkinPacket.Apply::execute);
        registrar.playToClient(ClientMoaSkinPacket.Remove.TYPE, ClientMoaSkinPacket.Remove.STREAM_CODEC, ClientMoaSkinPacket.Remove::execute);
        registrar.playToClient(ClientMoaSkinPacket.Sync.TYPE, ClientMoaSkinPacket.Sync.STREAM_CODEC, ClientMoaSkinPacket.Sync::execute);
        registrar.playToClient(CloudMinionPacket.TYPE, CloudMinionPacket.STREAM_CODEC, CloudMinionPacket::execute);
        registrar.playToClient(HealthResetPacket.TYPE, HealthResetPacket.STREAM_CODEC, HealthResetPacket::execute);
        registrar.playToClient(LeavingAetherPacket.TYPE, LeavingAetherPacket.STREAM_CODEC, LeavingAetherPacket::execute);
        registrar.playToClient(MoaInteractPacket.TYPE, MoaInteractPacket.STREAM_CODEC, MoaInteractPacket::execute);
        registrar.playToClient(OpenSunAltarPacket.TYPE, OpenSunAltarPacket.STREAM_CODEC, OpenSunAltarPacket::execute);
        registrar.playToClient(PortalInteractPacket.TYPE, PortalInteractPacket.STREAM_CODEC, PortalInteractPacket::execute);
        registrar.playToClient(PortalTravelSoundPacket.TYPE, PortalTravelSoundPacket.STREAM_CODEC, PortalTravelSoundPacket::execute);
        registrar.playToClient(QueenDialoguePacket.TYPE, QueenDialoguePacket.STREAM_CODEC, QueenDialoguePacket::execute);
        registrar.playToClient(RegisterMoaSkinsPacket.TYPE, RegisterMoaSkinsPacket.STREAM_CODEC, RegisterMoaSkinsPacket::execute);
        registrar.playToClient(RemountAerbunnyPacket.TYPE, RemountAerbunnyPacket.STREAM_CODEC, RemountAerbunnyPacket::execute);
        registrar.playToClient(SetInvisibilityPacket.TYPE, SetInvisibilityPacket.STREAM_CODEC, SetInvisibilityPacket::execute);
        registrar.playToClient(ToolDebuffPacket.TYPE, ToolDebuffPacket.STREAM_CODEC, ToolDebuffPacket::execute);
        registrar.playToClient(ZephyrSnowballHitPacket.TYPE, ZephyrSnowballHitPacket.STREAM_CODEC, ZephyrSnowballHitPacket::execute);

        // SERVERBOUND
        registrar.playToServer(AerbunnyPuffPacket.TYPE, AerbunnyPuffPacket.STREAM_CODEC, AerbunnyPuffPacket::execute);
        registrar.playToServer(ClearItemPacket.TYPE, ClearItemPacket.STREAM_CODEC, ClearItemPacket::execute);
        registrar.playToServer(HammerProjectileLaunchPacket.TYPE, HammerProjectileLaunchPacket.STREAM_CODEC, HammerProjectileLaunchPacket::execute);
        registrar.playToServer(LoreExistsPacket.TYPE, LoreExistsPacket.STREAM_CODEC, LoreExistsPacket::execute);
        registrar.playToServer(NpcPlayerInteractPacket.TYPE, NpcPlayerInteractPacket.STREAM_CODEC, NpcPlayerInteractPacket::execute);
        registrar.playToServer(OpenAccessoriesPacket.TYPE, OpenAccessoriesPacket.STREAM_CODEC, OpenAccessoriesPacket::execute);
        registrar.playToServer(OpenInventoryPacket.TYPE, OpenInventoryPacket.STREAM_CODEC, OpenInventoryPacket::execute);
        registrar.playToServer(ServerDeveloperGlowPacket.Apply.TYPE, ServerDeveloperGlowPacket.Apply.STREAM_CODEC, ServerDeveloperGlowPacket.Apply::execute);
        registrar.playToServer(ServerDeveloperGlowPacket.Remove.TYPE, ServerDeveloperGlowPacket.Remove.STREAM_CODEC, ServerDeveloperGlowPacket.Remove::execute);
        registrar.playToServer(ServerHaloPacket.Apply.TYPE, ServerHaloPacket.Apply.STREAM_CODEC, ServerHaloPacket.Apply::execute);
        registrar.playToServer(ServerHaloPacket.Remove.TYPE, ServerHaloPacket.Remove.STREAM_CODEC, ServerHaloPacket.Remove::execute);
        registrar.playToServer(ServerMoaSkinPacket.Apply.TYPE, ServerMoaSkinPacket.Apply.STREAM_CODEC, ServerMoaSkinPacket.Apply::execute);
        registrar.playToServer(ServerMoaSkinPacket.Remove.TYPE, ServerMoaSkinPacket.Remove.STREAM_CODEC, ServerMoaSkinPacket.Remove::execute);
        registrar.playToServer(StepHeightPacket.TYPE, StepHeightPacket.STREAM_CODEC, StepHeightPacket::execute);
        registrar.playToServer(SunAltarUpdatePacket.TYPE, SunAltarUpdatePacket.STREAM_CODEC, SunAltarUpdatePacket::execute);

        // BOTH
        registrar.playBidirectional(AetherPlayerSyncPacket.TYPE, AetherPlayerSyncPacket.STREAM_CODEC, AetherPlayerSyncPacket::execute);
        registrar.playBidirectional(AetherTimeSyncPacket.TYPE, AetherTimeSyncPacket.STREAM_CODEC, AetherTimeSyncPacket::execute);
        registrar.playBidirectional(PhoenixArrowSyncPacket.TYPE, PhoenixArrowSyncPacket.STREAM_CODEC, PhoenixArrowSyncPacket::execute);
    }

    public void registerCapabilities() {
        // Treasure Chests can't be extracted from while they are locked.
        ItemStorage.SIDED.registerForBlocks((level, pos, state, blockEntity, side) -> {
            if (blockEntity instanceof TreasureChestBlockEntity treasureChest) {
                return new WrappedInventoryStorage(ContainerStorage.of(treasureChest, null)) {
                    @Override
                    public boolean supportsExtraction() {
                        return !treasureChest.getLocked();
                    }

                    @Override
                    public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
                        return treasureChest.getLocked() ? 0 : super.extract(resource, maxAmount, transaction);
                    }
                };
            }
            return null;
        }, AetherBlocks.TREASURE_CHEST.get());

        ItemStorage.SIDED.registerForBlockEntity(ContainerStorage::of, AetherBlockEntityTypes.INCUBATOR.get());
    }

    public void registerDataMaps(RegisterDataMapTypesEvent event) {
        event.register(AetherDataMaps.ALTAR_FUEL);
        event.register(AetherDataMaps.FREEZER_FUEL);
        event.register(AetherDataMaps.INCUBATOR_FUEL);
    }

    public void packSetup(AddPackFindersEvent event) {
        // Resource Packs
        this.setupReleasePack(event);
        this.setupBetaPack(event);
        this.setupCTMFixPack(event);
        this.setupTipsPack(event);
        this.setupColorblindPack(event);
        this.setupTooltipsPack(event);

        // Data Packs
        this.setupAccessoriesPack(event);
        this.setupAccessoriesOverridePack(event);
        this.setupTemporaryFreezingPack(event);
        this.setupRuinedPortalPack(event);
    }

    /**
     * A built-in resource pack for programmer art based on the 1.2.5 version of the mod.
     */
    private void setupReleasePack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            this.createCombinedPack(event, "packs/classic_125", "builtin/aether_125_art", "pack.aether.125.title", "pack.aether.125.description");
        }
    }

    /**
     * A built-in resource pack for programmer art based on the b1.7.3 version of the mod.
     */
    private void setupBetaPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            this.createCombinedPack(event, "packs/classic_b173", "builtin/aether_b173_art", "pack.aether.b173.title", "pack.aether.b173.description");
        }
    }

    /**
     * Creates a built-in resource pack that combines asset files from two different locations.
     *
     * @param sourcePath  The {@link Path} of the non-base assets.
     * @param name        The {@link String} internal name of the resource pack.
     * @param title       The {@link String} title of the resource pack.
     * @param description The {@link String} description of the resource pack.
     */
    private void createCombinedPack(AddPackFindersEvent event, String sourcePath, String name, String title, String description) {
        PackLocationInfo locationInfo = new PackLocationInfo(name, Component.translatable(title), PackSource.BUILT_IN, Optional.empty());
        Pack.Metadata metadata = new Pack.Metadata(Component.translatable(description), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of());
        event.addRepositorySource((source) ->
            source.accept(new Pack(
                    locationInfo,
                    packSupplier("packs/classic_base", sourcePath),
                    metadata,
                    new PackSelectionConfig(false, Pack.Position.TOP, false)
                )
            )
        );
    }

    /**
     * Creates a {@link Pack.ResourcesSupplier} for built-in packs located in the mod's resources.
     * When multiple paths are given, they are layered in order, with later paths taking priority.
     *
     * @param paths The {@link String} paths of the pack folders, relative to the mod's resources root.
     * @return The {@link Pack.ResourcesSupplier}.
     */
    private static Pack.ResourcesSupplier packSupplier(String... paths) {
        ModContainer container = FabricLoader.getInstance().getModContainer(Aether.MODID).orElseThrow();
        List<Path> roots = Arrays.stream(paths).map((path) -> container.findPath(path).orElseThrow()).toList();
        return new Pack.ResourcesSupplier() {
            @Override
            public PackMetadataResources openMetadata(PackLocationInfo location) {
                return new PathPackResources(location, roots.getLast());
            }

            @Override
            public Stream<PackResources> openResources(PackLocationInfo location, Pack.Metadata metadata) {
                return roots.stream().map((root) -> new PathPackResources(location, root));
            }
        };
    }

    /**
     * A built-in resource pack to change the model of Quicksoil Glass Panes when using CTM, as CTM's connected textures won't properly work with the normal Quicksoil Glass Pane model.<br><br>
     * The pack is loaded and automatically applied if CTM is installed.
     */
    private void setupCTMFixPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES && FabricLoader.getInstance().isModLoaded("ctm")) {
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                            new PackLocationInfo("builtin/aether_ctm_fix", Component.translatable("pack.aether.ctm.title"), PackSource.BUILT_IN, Optional.empty()),
                            packSupplier("packs/ctm_fix"),
                            new Pack.Metadata(Component.translatable("pack.aether.ctm.description"), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()),
                            new PackSelectionConfig(true, Pack.Position.TOP, false)
                        )
                    )
            );
        }
    }

    /**
     * A built-in resource pack to include Pro Tips messages in Tips' UI.<br><br>
     * The pack is loaded and automatically applied if Tips is installed through {@link AetherClient#autoApplyPacks()}.
     */
    private void setupTipsPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES && FabricLoader.getInstance().isModLoaded("tipsmod") && AetherConfig.STARTUP.enable_trivia.get()) {
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                            new PackLocationInfo("builtin/aether_tips", Component.translatable("pack.aether.tips.title"), PackSource.BUILT_IN, Optional.empty()),
                            packSupplier("packs/tips"),
                            new Pack.Metadata(Component.translatable("pack.aether.tips.description"), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()),
                            new PackSelectionConfig(false, Pack.Position.TOP, false)
                        )
                    )
            );
        }
    }

    /**
     * A built-in resource pack to change textures for color blindness accessibility.
     */
    private void setupColorblindPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                            new PackLocationInfo("builtin/aether_colorblind", Component.translatable("pack.aether.colorblind.title"), PackSource.BUILT_IN, Optional.empty()),
                            packSupplier("packs/colorblind"),
                            new Pack.Metadata(Component.translatable("pack.aether.colorblind.description"), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()),
                            new PackSelectionConfig(false, Pack.Position.TOP, false)
                        )
                    )
            );
        }
    }

    /**
     * A built-in resource pack to include ability tooltips for items.<br><br>
     */
    private void setupTooltipsPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                            new PackLocationInfo("builtin/aether_tooltips", Component.translatable("pack.aether.tooltips.title"), PackSource.BUILT_IN, Optional.empty()),
                            packSupplier("packs/tooltips"),
                            new Pack.Metadata(Component.translatable("pack.aether.tooltips.description"), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()),
                            new PackSelectionConfig(false, Pack.Position.TOP, false)
                        )
                    )
            );
        }
    }

    /**
     * A built-in data pack to set up the compatibility for Immersive Portals.<br><br>
     * The pack is loaded and automatically applied if Immersive Portals is installed and if the {@link AetherConfig.Common#enable_immersive_portals_compatibility} config is enabled.
     */
    private void setupImmersivePortalsPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA && FabricLoader.getInstance().isModLoaded("immersive_portals_core") && AetherConfig.COMMON.enable_immersive_portals_compatibility.get()) {
            event.addRepositorySource((source) ->
                source.accept(new Pack(
                        new PackLocationInfo("builtin/aether_imm_ptl_compat", Component.translatable("pack.aether.imm_ptl_compat.title"), PackSource.BUILT_IN, Optional.empty()),
                        packSupplier("packs/imm_ptl_compat"),
                        new Pack.Metadata(Component.translatable("pack.aether.imm_ptl_compat.description"), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()),
                        new PackSelectionConfig(true, Pack.Position.TOP, false)
                    )
                )
            );
        }
    }

    /**
     * A built-in data pack to set up the default slots for accessories.<br><br>
     * The pack is loaded and automatically applied if the {@link AetherConfig.Common#use_default_accessories_menu} config isn't enabled.
     */
    private void setupAccessoriesPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA && !AetherConfig.COMMON.use_default_accessories_menu.get()) {
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                            new PackLocationInfo("builtin/aether_accessories", Component.translatable("pack.aether.aether_accessories.title"), PackSource.BUILT_IN, Optional.empty()),
                            packSupplier("packs/accessories"),
                            new Pack.Metadata(Component.translatable("pack.aether.aether_accessories.description"), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()),
                            new PackSelectionConfig(true, Pack.Position.TOP, false)
                        )
                    )
            );
        }
    }

    /**
     * A built-in data pack to empty the Aether's accessory slot tags and use the default accessory slot tags instead, as well as register the default accessories slots.<br><br>
     * The pack is loaded and automatically applied if the {@link AetherConfig.Common#use_default_accessories_menu} config is enabled.
     */
    private void setupAccessoriesOverridePack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA && AetherConfig.COMMON.use_default_accessories_menu.get()) {
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                            new PackLocationInfo("builtin/aether_accessories_override", Component.translatable("pack.aether.default_accessories.title"), PackSource.BUILT_IN, Optional.empty()),
                            packSupplier("packs/accessories_override"),
                            new Pack.Metadata(Component.translatable("pack.aether.default_accessories.description"), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()),
                            new PackSelectionConfig(true, Pack.Position.TOP, false)
                        )
                    )
            );
        }
    }

    /**
     * A built-in data pack to make ice accessories create temporary blocks instead of permanent blocks when freezing liquids.
     */
    private void setupTemporaryFreezingPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                            new PackLocationInfo("builtin/aether_temporary_freezing", Component.translatable("pack.aether.freezing.title"), create(decorateWithSource("pack.source.builtin"), AetherConfig.COMMON.add_temporary_freezing_automatically.get()), Optional.empty()),
                            packSupplier("packs/temporary_freezing"),
                            new Pack.Metadata(Component.translatable("pack.aether.freezing.description"), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()),
                            new PackSelectionConfig(false, Pack.Position.TOP, false)
                        )
                    )
            );
        }
    }

    /**
     * A built-in data pack for generating ruined Aether Portals.
     */
    private void setupRuinedPortalPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            event.addRepositorySource((source) ->
                    source.accept(new Pack(
                            new PackLocationInfo("builtin/aether_ruined_portal", Component.translatable("pack.aether.ruined_portal.title"), create(decorateWithSource("pack.source.builtin"), AetherConfig.COMMON.add_ruined_portal_automatically.get()), Optional.empty()),
                            packSupplier("packs/ruined_portal"),
                            new Pack.Metadata(Component.translatable("pack.aether.ruined_portal.description"), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()),
                            new PackSelectionConfig(false, Pack.Position.TOP, false)
                        )
                    )
            );
        }
    }

    public void eventSetup() {
        AccessoryAbilityListener.listen();
        ArmorAbilityListener.listen();
        ToolAbilityListener.listen();
        WeaponAbilityListener.listen();
        AetherPlayerListener.listen();
        AetherTimeListener.listen();
        DimensionListener.listen();
        EntityListener.listen();
        //ItemListener.listen();
        PerkListener.listen();
        RecipeListener.listen();

        CommandRegistrationCallback.EVENT.register(AetherCommands::registerCommands);
        ReloadListeners.reloadListenerSetup();
        ItemAttributeModifierHelper.ON_MODIFICATION.register(ZaniteSwordItem::onModifyAttributes);
        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamageTaken, damageTaken, blocked) -> FlamingSwordItem.onLivingDamage(entity, source));
        LivingEntityEvents.ON_DAMAGE.register((entity, source, originalDamage, newDamage) -> {
            HolySwordItem.onLivingDamage(entity, source, newDamage);
            PigSlayerItem.onLivingDamage(entity, source, newDamage);
        });

        CreativeModeTabEvents.MODIFY_OUTPUT_ALL.register(AetherCreativeTabs::buildCreativeModeTabs);
        AetherEntityTypes.registerSpawnPlacements();
        AetherEntityTypes.registerEntityAttributes();

    }

    /**
     * [CODE COPY] - {@link PackSource#create(UnaryOperator, boolean)}.
     */
    static PackSource create(final UnaryOperator<Component> decorator, final boolean shouldAddAutomatically) {
        return new PackSource() {
            public Component decorate(Component component) {
                return decorator.apply(component);
            }

            public boolean shouldAddAutomatically() {
                return shouldAddAutomatically;
            }
        };
    }

    /**
     * [CODE COPY] - {@link PackSource#decorateWithSource(String)}.
     */
    private static UnaryOperator<Component> decorateWithSource(String translationKey) {
        Component component = Component.translatable(translationKey);
        return (name) -> Component.translatable("pack.nameAndSource", name, component).withStyle(ChatFormatting.GRAY);
    }

    private void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(AetherItems.GOLDEN_DART.get(), new ProjectileDispenseBehavior(AetherItems.GOLDEN_DART.get()));
        DispenserBlock.registerBehavior(AetherItems.POISON_DART.get(), new ProjectileDispenseBehavior(AetherItems.POISON_DART.get()));
        DispenserBlock.registerBehavior(AetherItems.ENCHANTED_DART.get(), new ProjectileDispenseBehavior(AetherItems.ENCHANTED_DART.get()));
        DispenserBlock.registerBehavior(AetherItems.LIGHTNING_KNIFE.get(), new ProjectileDispenseBehavior(AetherItems.LIGHTNING_KNIFE.get()));
        DispenserBlock.registerBehavior(AetherItems.HAMMER_OF_KINGBDOGZ.get(), AetherDispenseBehaviors.DISPENSE_KINGBDOGZ_HAMMER_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherDispenseBehaviors.SKYROOT_BUCKET_DISPENSE_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherItems.SKYROOT_BUCKET.get(), AetherDispenseBehaviors.SKYROOT_BUCKET_PICKUP_BEHAVIOR);
        DispenserBlock.registerBehavior(AetherItems.AMBROSIUM_SHARD.get(), new DispenseUsableItemBehavior<>(AetherRecipeTypes.AMBROSIUM_ENCHANTING.get()));
        DispenserBlock.registerBehavior(AetherItems.SWET_BALL.get(), new DispenseUsableItemBehavior<>(AetherRecipeTypes.SWET_BALL_CONVERSION.get()));
        DispenserBlock.registerBehavior(AetherItems.SKYROOT_BOAT.get(), new BoatDispenseItemBehavior(AetherEntityTypes.SKYROOT_BOAT.get()));
        DispenserBlock.registerBehavior(AetherItems.SKYROOT_CHEST_BOAT.get(), new BoatDispenseItemBehavior(AetherEntityTypes.SKYROOT_CHEST_BOAT.get()));
    }

    private void registerCauldronInteractions() {
        CauldronInteractions.EMPTY.put(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherCauldronInteractions.FILL_WATER);
        CauldronInteractions.WATER.put(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherCauldronInteractions.FILL_WATER);
        CauldronInteractions.LAVA.put(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherCauldronInteractions.FILL_WATER);
        CauldronInteractions.POWDER_SNOW.put(AetherItems.SKYROOT_WATER_BUCKET.get(), AetherCauldronInteractions.FILL_WATER);
        CauldronInteractions.EMPTY.put(AetherItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherCauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteractions.WATER.put(AetherItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherCauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteractions.LAVA.put(AetherItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherCauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteractions.POWDER_SNOW.put(AetherItems.SKYROOT_POWDER_SNOW_BUCKET.get(), AetherCauldronInteractions.FILL_POWDER_SNOW);
        CauldronInteractions.WATER.put(AetherItems.SKYROOT_BUCKET.get(), AetherCauldronInteractions.EMPTY_WATER);
        CauldronInteractions.POWDER_SNOW.put(AetherItems.SKYROOT_BUCKET.get(), AetherCauldronInteractions.EMPTY_POWDER_SNOW);
        CauldronInteractions.WATER.put(AetherItems.LEATHER_GLOVES.get(), AetherCauldronInteractions::dyedItemIteration);
        CauldronInteractions.WATER.put(AetherItems.RED_CAPE.get(), AetherCauldronInteractions.CAPE);
        CauldronInteractions.WATER.put(AetherItems.BLUE_CAPE.get(), AetherCauldronInteractions.CAPE);
        CauldronInteractions.WATER.put(AetherItems.YELLOW_CAPE.get(), AetherCauldronInteractions.CAPE);
    }
}
