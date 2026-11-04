/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package com.aetherteam.nitrogen.fabric.network.payload;

import com.aetherteam.nitrogen.fabric.registries.RegistryManager;
import com.aetherteam.nitrogen.fabric.registries.datamaps.DataMapType;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.util.GsonHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public record RegistryDataMapSyncPayload<T>(ResourceKey<? extends Registry<T>> registryKey, Map<Identifier, Map<ResourceKey<T>, ?>> dataMaps) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<RegistryDataMapSyncPayload<?>> TYPE = new Type<>(Identifier.fromNamespaceAndPath("neoforge", "registry_data_map_sync"));
    public static final StreamCodec<RegistryFriendlyByteBuf, RegistryDataMapSyncPayload<?>> STREAM_CODEC = StreamCodec.ofMember(RegistryDataMapSyncPayload::write, RegistryDataMapSyncPayload::decode);

    @SuppressWarnings("unchecked")
    public static <T> RegistryDataMapSyncPayload<T> decode(RegistryFriendlyByteBuf buf) {
        //noinspection RedundantCast javac complains about this cast
        final ResourceKey<Registry<T>> registryKey = (ResourceKey<Registry<T>>) (Object) buf.readRegistryKey();
        final Map<Identifier, Map<ResourceKey<T>, ?>> attach = new HashMap<>();
        int mapCount = buf.readVarInt();
        for (int i = 0; i < mapCount; i++) {
            Identifier key = buf.readIdentifier();
            final DataMapType<T, ?> dataMap = RegistryManager.getDataMap(registryKey, key);
            int entryCount = buf.readVarInt();
            Map<ResourceKey<T>, Object> values = new HashMap<>();
            for (int e = 0; e < entryCount; e++) {
                ResourceKey<T> entryKey = buf.readResourceKey(registryKey);
                values.put(entryKey, readJsonWithRegistryCodec(buf, dataMap.networkCodec()));
            }
            ((Map<ResourceKey<T>, Object>) attach.computeIfAbsent(key, k -> new HashMap<>())).putAll(values);
        }
        return new RegistryDataMapSyncPayload<>(registryKey, attach);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeResourceKey(registryKey);
        buf.writeVarInt(dataMaps.size());
        for (Map.Entry<Identifier, Map<ResourceKey<T>, ?>> entry : dataMaps.entrySet()) {
            var key = entry.getKey();
            buf.writeIdentifier(key);

            final DataMapType<T, ?> dataMap = RegistryManager.getDataMap(registryKey, key);
            // TODO - make datamaps use stream codecs once datapack registries use them too
            buf.writeVarInt(entry.getValue().size());
            for (Map.Entry<ResourceKey<T>, ?> value : entry.getValue().entrySet()) {
                buf.writeResourceKey(value.getKey());
                writeJsonWithRegistryCodec(buf, (Codec) dataMap.networkCodec(), value.getValue());
            }
        }
    }

    @Override
    public Type<RegistryDataMapSyncPayload<?>> type() {
        return TYPE;
    }

    private static final Gson GSON = new Gson();

    private static <T> T readJsonWithRegistryCodec(RegistryFriendlyByteBuf buf, Codec<T> codec) {
        JsonElement jsonelement = GsonHelper.fromJson(GSON, buf.readUtf(), JsonElement.class);
        DataResult<T> dataresult = codec.parse(buf.registryAccess().createSerializationContext(JsonOps.INSTANCE), jsonelement);
        return dataresult.getOrThrow(name -> new DecoderException("Failed to decode json: " + name));
    }

    private static <T> void writeJsonWithRegistryCodec(RegistryFriendlyByteBuf buf, Codec<T> codec, T value) {
        DataResult<JsonElement> dataresult = codec.encodeStart(buf.registryAccess().createSerializationContext(JsonOps.INSTANCE), value);
        buf.writeUtf(GSON.toJson(dataresult.getOrThrow(message -> new EncoderException("Failed to encode: " + message + " " + value))));
    }
}
