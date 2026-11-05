package com.aetherteam.aether.entity;

import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

import java.util.Optional;
import java.util.UUID;

/**
 * Custom {@link EntityDataSerializer}s, for data types that vanilla no longer provides serializers for.<br><br>
 * Fabric has no registry for these, so they are added to vanilla's serializer map directly, which must happen in the same order on both sides.
 */
public class AetherEntityDataSerializers {
    public static final EntityDataSerializer<Optional<UUID>> OPTIONAL_UUID = EntityDataSerializer.forValueType(ByteBufCodecs.optional(UUIDUtil.STREAM_CODEC));

    static {
        EntityDataSerializers.registerSerializer(OPTIONAL_UUID);
    }

    public static void init() { }
}
