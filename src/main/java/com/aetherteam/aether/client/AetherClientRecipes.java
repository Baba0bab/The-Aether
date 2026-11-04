package com.aetherteam.aether.client;

import net.minecraft.world.item.crafting.RecipeMap;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;

/**
 * Keeps the Aether's recipes that are synced from the server, for use in recipe viewers.
 *
 * @see com.aetherteam.aether.event.listeners.RecipeListener#syncRecipes(net.neoforged.neoforge.event.OnDatapackSyncEvent)
 */
public final class AetherClientRecipes {
    private static RecipeMap recipes = RecipeMap.EMPTY;

    private AetherClientRecipes() {
    }

    public static RecipeMap getRecipes() {
        return recipes;
    }

    public static void listen(IEventBus bus) {
        bus.addListener(EventPriority.HIGHEST, AetherClientRecipes::onRecipesReceived);
        bus.addListener(AetherClientRecipes::onLoggingOut);
    }

    private static void onRecipesReceived(RecipesReceivedEvent event) {
        recipes = event.getRecipeMap();
    }

    private static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        recipes = RecipeMap.EMPTY;
    }
}
