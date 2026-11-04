package com.aetherteam.aether.client;

import com.aetherteam.aether.client.gui.screen.ValkyrieQueenDialogueScreen;
import com.aetherteam.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import com.aetherteam.aether.perk.types.MoaSkins;
import net.minecraft.client.Minecraft;

/**
 * Client-only logic called from common code, kept separate so that client classes are never loaded on a dedicated server.
 */
public class AetherClientUtil {
    public static void openValkyrieQueenDialogue(ValkyrieQueen valkyrieQueen) {
        Minecraft.getInstance().gui.setScreen(new ValkyrieQueenDialogueScreen(valkyrieQueen));
    }

    public static void registerMoaSkins() {
        if (Minecraft.getInstance().level != null) {
            MoaSkins.registerMoaSkins(Minecraft.getInstance().level);
        }
    }
}
