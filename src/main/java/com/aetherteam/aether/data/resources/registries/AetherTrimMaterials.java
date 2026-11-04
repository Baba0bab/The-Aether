package com.aetherteam.aether.data.resources.registries;

import com.aetherteam.aether.Aether;
import net.minecraft.util.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.equipment.trim.TrimMaterial;


public class AetherTrimMaterials {
    public static final ResourceKey<TrimMaterial> ZANITE = createKey("zanite");
    public static final ResourceKey<TrimMaterial> GRAVITITE = createKey("gravitite");
    public static final ResourceKey<TrimMaterial> GOLDEN_AMBER = createKey("golden_amber");

    private static ResourceKey<TrimMaterial> createKey(String name) {
        return ResourceKey.create(Registries.TRIM_MATERIAL, Identifier.fromNamespaceAndPath(Aether.MODID, name));
    }

    public static void bootstrap(BootstrapContext<TrimMaterial> context) {
        register(context, ZANITE, Style.EMPTY.withColor(8009440));
        register(context, GRAVITITE, Style.EMPTY.withColor(13391043));
        register(context, GOLDEN_AMBER, Style.EMPTY.withColor(16299311));
    }

    /**
     * Registers a trim material, using a palette texture at {@code textures/palettes/trim/<name>.png} in the Aether's namespace.
     */
    private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> materialKey, Style style) {
        Identifier palette = materialKey.identifier().withPrefix("trim/");
        Component description = Component.translatable(Util.makeDescriptionId("trim_material", materialKey.identifier())).withStyle(style);
        context.register(materialKey, new TrimMaterial(palette, description));
    }
}
