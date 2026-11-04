package com.aetherteam.aether.data;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.FreezingBlock;
import com.aetherteam.aether.item.AetherItems;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ProfilerFiller;

public class ReloadListeners {
    /**
     * @see Aether#onInitialize()
     */
    public static void reloadListenerSetup() {
        ResourceLoader loader = ResourceLoader.get(PackType.SERVER_DATA);
        loader.registerReloadListener(Identifier.fromNamespaceAndPath(Aether.MODID, "freezing_block_cache"), new CacheResetListener(() -> {
            FreezingBlock.cachedBlocks.clear();
            FreezingBlock.cachedResults.clear();
        }));
        loader.registerReloadListener(Identifier.fromNamespaceAndPath(Aether.MODID, "swet_banner_cache"), new CacheResetListener(() -> AetherItems.SWET_BANNER = null));
    }

    /**
     * Resets cached data whenever datapacks are reloaded, such as the block caches for {@link FreezingBlock} recipes, or the Swet Banner's patterns.
     */
    public static class CacheResetListener extends SimplePreparableReloadListener<Unit> {
        private final Runnable reset;

        public CacheResetListener(Runnable reset) {
            this.reset = reset;
        }

        @Override
        protected Unit prepare(ResourceManager manager, ProfilerFiller profiler) {
            return Unit.INSTANCE;
        }

        @Override
        protected void apply(Unit preparations, ResourceManager manager, ProfilerFiller profiler) {
            this.reset.run();
        }
    }
}
