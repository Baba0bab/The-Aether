package com.aetherteam.aether.client;

import com.aetherteam.aether.client.gui.screen.inventory.SunAltarScreen;

import net.minecraft.client.Minecraft;

import com.aetherteam.aether.client.event.listeners.capability.AetherPlayerClientListener;

import net.neoforged.neoforge.client.gui.ConfigurationScreen;

import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;

import java.util.function.Predicate;

import java.util.Map;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

import net.fabricmc.api.ClientModInitializer;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.event.listeners.*;
import com.aetherteam.aether.client.event.listeners.abilities.AccessoryAbilityClientListener;
import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.aetherteam.aether.client.renderer.AetherOverlays;
import com.aetherteam.aether.client.renderer.AetherRenderers;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import com.aetherteam.aether.event.listeners.ItemListener;
import com.aetherteam.aether.event.listeners.capability.AetherPlayerListener;
import com.aetherteam.aether.inventory.menu.AetherMenuTypes;
import com.aetherteam.aether.inventory.menu.LoreBookMenu;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.perk.CustomizationsOptions;
import com.aetherteam.nitrogen.event.listeners.TooltipListeners;
import com.google.common.reflect.Reflection;
import fuzs.forgeconfigapiport.fabric.api.v5.client.ConfigScreenFactoryRegistry;
import com.aetherteam.aether.client.renderer.item.NamedItemProperty;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class AetherClient implements ClientModInitializer {
    public static Map<Predicate<ItemStack>, Identifier> CAPE_SECRETS = new HashMap<>();

    @Override
    public void onInitializeClient() {
        AetherClient.clientSetup();
        AetherClient.eventSetup();

        ConfigScreenFactoryRegistry.INSTANCE.register(Aether.MODID, ConfigurationScreen::new);
    }

    public static void clientSetup() {
        Reflection.initialize(CustomizationsOptions.class);
        AetherRenderers.registerAccessoryRenderers();
        registerCapeSecrets();
        registerTooltipOverrides();
        registerLoreOverrides();

        //--

        AetherPlayerListener.listenClient();
        ItemListener.listen();
    }

    /**
     * Registers custom item model properties used in item model definitions.
     */
    public static void registerItemModelProperties() {
        ConditionalItemModelProperties.ID_MAPPER.put(Identifier.fromNamespaceAndPath(Aether.MODID, "named"), NamedItemProperty.MAP_CODEC);
    }

    public static void registerCapeSecrets() {
        CAPE_SECRETS.put((stack) -> stack.getHoverName().getString().equalsIgnoreCase("swuff_'s cape"), Identifier.fromNamespaceAndPath(Aether.MODID, "textures/models/accessory/capes/swuff_accessory.png"));
    }

    public static void registerTooltipOverrides() {
        TooltipListeners.PREDICATES.put(AetherItems.BLUE_GUMMY_SWET, (player, stack, components, context, component) -> {
            if (AetherConfig.SERVER.healing_gummy_swets.get() && component.getContents() instanceof TranslatableContents contents && contents.getKey().endsWith(".1")) {
                return Component.translatable(contents.getKey() + ".health");
            } else {
                return component;
            }
        });
        TooltipListeners.PREDICATES.put(AetherItems.GOLDEN_GUMMY_SWET, (player, stack, components, context, component) -> {
            if (AetherConfig.SERVER.healing_gummy_swets.get() && component.getContents() instanceof TranslatableContents contents && contents.getKey().endsWith(".1")) {
                return Component.translatable(contents.getKey() + ".health");
            } else {
                return component;
            }
        });
        TooltipListeners.PREDICATES.put(AetherItems.LIFE_SHARD, (player, stack, components, context, component) -> {
            if (component.getContents() instanceof TranslatableContents contents && contents.getKey().endsWith(".1")) {
                return Component.translatable(contents.getKey(), AetherConfig.SERVER.maximum_life_shards.get());
            } else {
                return component;
            }
        });
    }

    /**
     * Applies a unique lore entry in the Book of Lore for the Hammer of Jeb Easter Egg item texture.
     */
    public static void registerLoreOverrides() {
        LoreBookMenu.addLoreEntryOverride(registryAccess -> stack -> stack.is(AetherItems.HAMMER_OF_KINGBDOGZ.get()) && stack.getHoverName().getString().equalsIgnoreCase("hammer of jeb"), "lore.item.aether.hammer_of_jeb");
        LoreBookMenu.addLoreEntryOverride(registryAccess -> stack -> ItemStack.isSameItemSameComponents(stack, AetherItems.createSwetBannerItemStack(registryAccess.lookupOrThrow(Registries.BANNER_PATTERN))), "lore.item.aether.swet_banner");
    }

    public static void eventSetup() {
        AccessoryAbilityClientListener.listen();
        AetherClientRecipes.listen();
        AetherPlayerClientListener.listen();
        AudioListener.listen();
        DimensionClientListener.listen();
        GuiListener.listen();
        LevelClientListener.listen();
        MenuListener.listen();

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> AbilityHooks.ToolHooks.resetDebuffToolsState());

        AetherMenuTypes.registerMenuScreens();
        ClientLifecycleEvents.CLIENT_STARTED.register((client) -> AetherColorResolvers.registerBlockColor());
        AetherClient.registerItemModelProperties();
        AetherKeys.registerKeyMappings();
        AetherParticleTypes.registerParticleFactories();
        AetherOverlays.registerOverlays();
        AetherRenderers.registerEntityRenderers();
        AetherRenderers.registerLayerDefinitions();
        AetherRenderers.addEntityLayers();
    }

    /**
     * Registers a unique shader for spectating the Sun Spirit, which tints the screen red.
     */
//    public static void registerSpectatorShaders(RegisterEntitySpectatorShadersEvent event) {
//        event.register(AetherEntityTypes.SUN_SPIRIT.get(), Identifier.fromNamespaceAndPath(Aether.MODID, "shaders/post/sun_spirit.json"));
//    }

//    public static void registerDimensionTransitionScreens(RegisterDimensionTransitionScreenEvent event) {
//        event.registerIncomingEffect(AetherDimensions.AETHER_LEVEL, AetherReceivingLevelScreen::new);
//        event.registerOutgoingEffect(AetherDimensions.AETHER_LEVEL, AetherReceivingLevelScreen::new);
//    }

    public static void setToSunAltarScreen(Component name, int timeScale) {
        Minecraft.getInstance().gui.setScreen(new SunAltarScreen(name, timeScale));
    }
}
