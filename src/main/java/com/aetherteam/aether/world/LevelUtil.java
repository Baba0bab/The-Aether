package com.aetherteam.aether.world;

import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.aether.AetherConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;

public final class LevelUtil {
    /**
     * Used to determine a destination dimension for Aether-related teleportation. By default, this is "aether:the_aether".
     *
     * @return A {@link ResourceKey ResourceKey&lt;Level&gt;} retrieved from {@link AetherConfig.Server#portal_destination_dimension_ID}.
     * @see com.aetherteam.aether.block.portal.AetherPortalBlock
     * @see com.aetherteam.aether.block.portal.AetherPortalForcer
     * @see com.aetherteam.aether.event.hooks.DimensionHooks
     */
    public static ResourceKey<Level> destinationDimension() {
        return ResourceKey.create(Registries.DIMENSION, Identifier.parse(AetherConfig.SERVER.portal_destination_dimension_ID.get()));
    }

    /**
     * Used to determine a return dimension for Aether-related teleportation. By default, this is "minecraft:overworld".
     *
     * @return A {@link ResourceKey ResourceKey&lt;Level&gt;} retrieved from {@link AetherConfig.Server#portal_return_dimension_ID}.
     * @see com.aetherteam.aether.block.portal.AetherPortalBlock
     * @see com.aetherteam.aether.block.portal.AetherPortalForcer
     * @see com.aetherteam.aether.event.hooks.DimensionHooks
     */
    public static ResourceKey<Level> returnDimension() {
        return ResourceKey.create(Registries.DIMENSION, Identifier.parse(AetherConfig.SERVER.portal_return_dimension_ID.get()));
    }

    /**
     * Checks whether a level uses the Aether's dimension type, which determines whether Aether-specific behavior like the day/night cycle should apply.
     *
     * @param level The {@link Level}.
     * @return Whether the level uses the Aether's dimension type, as a {@link Boolean}.
     */
    public static boolean isAetherDimensionType(Level level) {
        return level.dimensionTypeRegistration().is(AetherDimensions.AETHER_DIMENSION_TYPE);
    }
}
