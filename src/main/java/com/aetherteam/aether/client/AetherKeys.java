package com.aetherteam.aether.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import com.aetherteam.aether.Aether;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.resources.Identifier;

public class AetherKeys {
    public static final KeyMapping.Category CATEGORY = new KeyMapping.Category(Identifier.fromNamespaceAndPath(Aether.MODID, "aether"));

    public final static KeyMapping OPEN_ACCESSORY_INVENTORY = new KeyMapping("key.aether.open_accessories.desc", InputConstants.KEY_I, CATEGORY);
    public final static KeyMapping GRAVITITE_JUMP_ABILITY = new KeyMapping("key.aether.gravitite_jump_ability.desc", InputConstants.KEY_SPACE, CATEGORY);
    public final static KeyMapping INVISIBILITY_TOGGLE = new KeyMapping("key.aether.invisibility_toggle.desc", InputConstants.KEY_V, CATEGORY);

    /**
     * @see AetherClient#eventSetup()
     */
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.registerCategory(CATEGORY);
        event.register(OPEN_ACCESSORY_INVENTORY);
        event.register(GRAVITITE_JUMP_ABILITY);
        event.register(INVISIBILITY_TOGGLE);
    }
}
