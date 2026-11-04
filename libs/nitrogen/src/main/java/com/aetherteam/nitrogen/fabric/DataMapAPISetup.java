package com.aetherteam.nitrogen.fabric;

import com.aetherteam.nitrogen.fabric.mixin.accessor.ConnectionAccessor;
import com.aetherteam.nitrogen.fabric.mixin.accessor.ServerCommonPacketListenerImplAccessor;
import com.aetherteam.nitrogen.fabric.network.payload.AdvancedAddEntityPayload;
import com.aetherteam.nitrogen.fabric.network.payload.KnownRegistryDataMapsPayload;
import com.aetherteam.nitrogen.fabric.network.payload.KnownRegistryDataMapsReplyPayload;
import com.aetherteam.nitrogen.fabric.network.payload.RegistryDataMapSyncPayload;
import com.aetherteam.nitrogen.fabric.network.tasks.RegistryDataMapNegotiation;
import com.aetherteam.nitrogen.fabric.pond.IRegistryExtension;
import com.aetherteam.nitrogen.fabric.registries.DataMapLoader;
import com.aetherteam.nitrogen.fabric.registries.RegistryManager;
import com.aetherteam.nitrogen.fabric.registries.datamaps.DataMapType;
import com.aetherteam.nitrogen.fabric.registries.datamaps.DataMapsUpdatedEvent;
import com.aetherteam.nitrogen.fabric.registries.datamaps.RegisterDataMapTypesEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataMapAPISetup {

public static void init() {
        // Data Map
        PayloadTypeRegistry.clientboundConfiguration().register(KnownRegistryDataMapsPayload.TYPE, KnownRegistryDataMapsPayload.STREAM_CODEC);
        PayloadTypeRegistry.serverboundConfiguration().register(KnownRegistryDataMapsReplyPayload.TYPE, KnownRegistryDataMapsReplyPayload.STREAM_CODEC);

        PayloadTypeRegistry.clientboundPlay().register(RegistryDataMapSyncPayload.TYPE, RegistryDataMapSyncPayload.STREAM_CODEC);

        ServerConfigurationConnectionEvents.CONFIGURE.register((handler, server) -> handler.addTask(new RegistryDataMapNegotiation(handler)));

        ServerConfigurationNetworking.registerGlobalReceiver(KnownRegistryDataMapsReplyPayload.TYPE, RegistryManager::handleKnownDataMapsReply);

RegistryManager.initDataMaps();

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(DataMapLoader.ID, DataMapLoader::new);

        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> {
            RegistryManager.getDataMaps().forEach((registry, values) -> {
                final var regOpt = player.level().getServer().overworld().registryAccess().lookup(registry);
                if (regOpt.isEmpty()) return;
                if (!ServerPlayNetworking.canSend(player, RegistryDataMapSyncPayload.TYPE)) return;
                var connection = ((ServerCommonPacketListenerImplAccessor) player.connection).nitrogen_fabric$connection();
                // Note: don't send data maps over in-memory connections, else the client-side handling will wipe non-synced data maps.
                if (connection.isMemoryConnection()) return;
                final var playerMaps = ((ConnectionAccessor) connection).nitrogen_fabric$getChannel().attr(RegistryManager.ATTRIBUTE_KNOWN_DATA_MAPS).get();
                if (playerMaps == null) return; // Skip gametest players for instance
                RegistryManager.handleSync(player, regOpt.get(), playerMaps.getOrDefault(registry, List.of()));
            });
        });

        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
            if (client) return;

            DataMapLoader.apply(registries);
        });

        //--

        PayloadTypeRegistry.clientboundPlay().register(AdvancedAddEntityPayload.TYPE, AdvancedAddEntityPayload.STREAM_CODEC);


    }
}
