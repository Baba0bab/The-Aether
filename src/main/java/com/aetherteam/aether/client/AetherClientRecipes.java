package com.aetherteam.aether.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.recipe.v1.sync.ClientRecipeSynchronizedEvent;
import net.fabricmc.fabric.api.recipe.v1.sync.SynchronizedRecipes;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * Keeps the Aether's recipes that are synced from the server, for use in recipe viewers.<br><br>
 * The Aether's recipe serializers are registered for synchronization with Fabric's recipe API in {@link com.aetherteam.aether.Aether#onInitialize()}.
 */
public final class AetherClientRecipes {
    private static final AetherClientRecipes INSTANCE = new AetherClientRecipes();
    @Nullable
    private SynchronizedRecipes recipes = null;

    private AetherClientRecipes() {
    }

    public static AetherClientRecipes getRecipes() {
        return INSTANCE;
    }

    public <T extends Recipe<?>> Collection<RecipeHolder<T>> byType(RecipeType<T> type) {
        return this.recipes != null ? this.recipes.getAllOfType((RecipeType) type) : List.of();
    }

    public static void listen() {
        ClientRecipeSynchronizedEvent.EVENT.register((minecraft, synchronizedRecipes) -> INSTANCE.recipes = synchronizedRecipes);
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> INSTANCE.recipes = null);
    }
}
