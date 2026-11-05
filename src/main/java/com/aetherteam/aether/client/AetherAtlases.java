package com.aetherteam.aether.client;

import com.aetherteam.aether.Aether;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;

public class AetherAtlases {
    public static final SpriteId TREASURE_CHEST_MATERIAL = getChestMaterial("treasure_chest");
    public static final SpriteId TREASURE_CHEST_LEFT_MATERIAL = getChestMaterial("treasure_chest_left");
    public static final SpriteId TREASURE_CHEST_RIGHT_MATERIAL = getChestMaterial("treasure_chest_right");

    public static SpriteId getChestMaterial(String chestName) {
        return Sheets.CHEST_MAPPER.apply(Identifier.fromNamespaceAndPath(Aether.MODID, chestName));
    }
}
