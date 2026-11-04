/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package com.aetherteam.nitrogen.fabric.network.payload;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public record KnownRegistryDataMapsReplyPayload(Map<ResourceKey<? extends Registry<?>>, Collection<Identifier>> dataMaps) implements CustomPacketPayload {
    public static final Type<KnownRegistryDataMapsReplyPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath("neoforge", "known_registry_data_maps_reply"));
    public static final StreamCodec<FriendlyByteBuf, KnownRegistryDataMapsReplyPayload> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.map(Maps::newHashMapWithExpectedSize, registryKey(), Identifier.STREAM_CODEC.apply(ByteBufCodecs.collection(ArrayList::new))),
        KnownRegistryDataMapsReplyPayload::dataMaps,
        KnownRegistryDataMapsReplyPayload::new);

    public static <B extends FriendlyByteBuf> StreamCodec<B, ResourceKey<? extends Registry<?>>> registryKey() {
        return new StreamCodec<>() {
            @Override
            public ResourceKey<? extends Registry<?>> decode(B buf) {
                return ResourceKey.createRegistryKey(buf.readIdentifier());
            }

            @Override
            public void encode(B buf, ResourceKey<? extends Registry<?>> value) {
                buf.writeIdentifier(value.identifier());
            }
        };
    }

    @Override
    public Type<KnownRegistryDataMapsReplyPayload> type() {
        return TYPE;
    }
}
