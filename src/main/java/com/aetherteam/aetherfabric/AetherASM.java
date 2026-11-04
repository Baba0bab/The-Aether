package com.aetherteam.aetherfabric;

import me.shedaniel.mm.api.ClassTinkerers;
import net.minecraft.ChatFormatting;

/**
 * Adds the Aether's enum constants at startup through Manningham Mills, as Fabric has no built-in enum extension system.<br><br>
 * Minecraft is no longer obfuscated as of 26.1, so the class names are used directly instead of being mapped from intermediary.
 */
public class AetherASM implements Runnable {
    @Override
    public void run() {
        ClassTinkerers.enumBuilder("net/minecraft/world/inventory/RecipeBookType")
            .addEnum("AETHER_ALTAR")
            .addEnum("AETHER_FREEZER")
            .addEnum("AETHER_INCUBATOR")
            .build();

        ClassTinkerers.enumBuilder("net/minecraft/world/item/Rarity", int.class, String.class, "Lnet/minecraft/ChatFormatting;")
            .addEnum("AETHER_LOOT", () -> new Object[] { 4, "aether:loot", ChatFormatting.GREEN })
            .build();

        ClassTinkerers.enumBuilder("net/minecraft/world/entity/MobCategory", String.class, String.class, int.class, boolean.class, boolean.class, int.class)
            .addEnum("AETHER_AETHER_SURFACE_MONSTER", "aether:aether_surface_monster", "AESM", 15, false, false, 128)
            .addEnum("AETHER_AETHER_DARKNESS_MONSTER", "aether:aether_darkness_monster", "AEDM", 5, false, false, 128)
            .addEnum("AETHER_AETHER_SKY_MONSTER", "aether:aether_sky_monster", "AESK", 4, false, false, 128)
            .addEnum("AETHER_AETHER_AERWHALE", "aether:aether_aerwhale", "AEAW", 1, true, false, 128)
            .build();
    }
}
