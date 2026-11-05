package com.aetherteam.aether.entity;

import com.aetherteam.aether.Aether;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityDataRegistry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.Identifier;

import java.util.Optional;
import java.util.UUID;

/**
 * Custom {@link EntityDataSerializer}s, for data types that vanilla no longer provides serializers for.
 */
public class AetherEntityDataSerializers {
    public static final EntityDataSerializer<Optional<UUID>> OPTIONAL_UUID = EntityDataSerializer.forValueType(ByteBufCodecs.optional(UUIDUtil.STREAM_CODEC));

    static {
        FabricEntityDataRegistry.register(Identifier.fromNamespaceAndPath(Aether.MODID, "optional_uuid"), OPTIONAL_UUID);
    }

    public static void init() { }
}
