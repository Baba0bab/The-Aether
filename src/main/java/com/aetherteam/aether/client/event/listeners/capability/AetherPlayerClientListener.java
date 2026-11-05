package com.aetherteam.aether.client.event.listeners.capability;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.CapabilityClientHooks;
import net.minecraft.client.player.ClientInput;
import net.minecraft.world.entity.player.Player;

public class AetherPlayerClientListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen() {
        //LocalPlayerMixin.nitrogen_fabric$updateOnInput -> AetherPlayerClientListener.onMove
        //MouseHandlerMixin.nitrogen_fabric$onPostPress -> AetherPlayerClientListener.onClick
        //KeyboardHandlerMixin.nitrogen_fabric$onPostKeyPress -> AetherPlayerClientListener.onPress
    }

    /**
     * @see CapabilityClientHooks.AetherPlayerHooks#movementInput(Player, Input)
     */
    public static void onMove(Player player, ClientInput input) {
        CapabilityClientHooks.AetherPlayerHooks.movementInput(player, input);
    }

    /**
     * @see CapabilityClientHooks.AetherPlayerHooks#mouseInput(int)
     */
    public static void onClick(int button) {
        CapabilityClientHooks.AetherPlayerHooks.mouseInput(button);
    }

    /**
     * @see CapabilityClientHooks.AetherPlayerHooks#keyInput(int)
     */
    public static void onPress(int key) {
        CapabilityClientHooks.AetherPlayerHooks.keyInput(key);
    }
}
