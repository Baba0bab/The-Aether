package com.aetherteam.aether.entity;

import com.aetherteam.aether.Aether;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Optional;
import java.util.UUID;

/**
 * Custom {@link EntityDataSerializer}s, for data types that vanilla no longer provides serializers for.
 */
public class AetherEntityDataSerializers {
    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, Aether.MODID);

    public static final EntityDataSerializer<Optional<UUID>> OPTIONAL_UUID = EntityDataSerializer.forValueType(ByteBufCodecs.optional(UUIDUtil.STREAM_CODEC));

    static {
        ENTITY_DATA_SERIALIZERS.register("optional_uuid", () -> OPTIONAL_UUID);
    }
}
