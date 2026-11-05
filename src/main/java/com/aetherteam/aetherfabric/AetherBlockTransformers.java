package com.aetherteam.aetherfabric;

import com.aetherteam.aether.Aether;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.item.v1.BlockTransformerEvents;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.resources.RegistryOps;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Adds the Aether's tilling, flattening and stripping behaviors to the vanilla axe, hoe and shovel block transformers.<br><br>
 * NeoForge reads these from the "neoforge:transformables" block data map. Fabric has no such data map, so the same generated file
 * is read from the mod's resources and its entries are added through Fabric's {@link BlockTransformerEvents#MODIFY}.
 */
public final class AetherBlockTransformers {
    private static final String TRANSFORMABLES_PATH = "/data/neoforge/data_maps/block/transformables.json";
    @Nullable
    private static JsonObject transformables;

    private AetherBlockTransformers() {
    }

    public static void init() {
        BlockTransformerEvents.MODIFY.register((key, transforms, source, lookup) -> {
            if (!source.isBuiltIn()) {
                return;
            }
            JsonObject values = getTransformables();
            if (values == null) {
                return;
            }
            String transformerId = key.identifier().toString();
            RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, lookup);
            for (Map.Entry<String, JsonElement> entry : values.entrySet()) {
                JsonObject value = entry.getValue().getAsJsonObject();
                JsonElement data = null;
                if (value.has("transformers")) {
                    data = value.getAsJsonObject("transformers").get(transformerId);
                } else if (value.has("transformer") && transformerId.equals(value.get("transformer").getAsString())) {
                    data = value.get("transform_data");
                }
                if (data != null) {
                    BlockTransformer.BlockTransformData.CODEC.parse(ops, data)
                        .resultOrPartial(error -> Aether.LOGGER.error("Could not read the {} transform for {}: {}", transformerId, entry.getKey(), error))
                        .ifPresent(transforms::add);
                }
            }
        });
    }

    @Nullable
    private static JsonObject getTransformables() {
        if (transformables == null) {
            try (InputStream stream = AetherBlockTransformers.class.getResourceAsStream(TRANSFORMABLES_PATH)) {
                if (stream == null) {
                    Aether.LOGGER.error("Could not find the Aether's block transformables at {}", TRANSFORMABLES_PATH);
                    return null;
                }
                transformables = JsonParser.parseReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).getAsJsonObject().getAsJsonObject("values");
            } catch (IOException | RuntimeException e) {
                Aether.LOGGER.error("Could not read the Aether's block transformables", e);
                return null;
            }
        }
        return transformables;
    }
}
