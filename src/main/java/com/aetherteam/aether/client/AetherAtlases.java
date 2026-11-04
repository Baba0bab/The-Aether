package com.aetherteam.aether.client;

import com.aetherteam.aether.Aether;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.client.resources.model.sprite.SpriteId;

public class AetherAtlases {
    public static final SpriteId TREASURE_CHEST_MATERIAL = getChestMaterial("treasure_chest");
    public static final SpriteId TREASURE_CHEST_LEFT_MATERIAL = getChestMaterial("treasure_chest_left");
    public static final SpriteId TREASURE_CHEST_RIGHT_MATERIAL = getChestMaterial("treasure_chest_right");

    public static SpriteId getChestMaterial(String chestName) {
        return Sheets.CHEST_MAPPER.apply(Identifier.fromNamespaceAndPath(Aether.MODID, chestName));
    }

    private static Material createSignMaterial(WoodType woodType) {
        return new Material(Sheets.SIGN_SHEET, Identifier.withDefaultNamespace("entity/signs/" + woodType.name()));
    }

    private static Material createHangingSignMaterial(WoodType woodType) {
        return new Material(Sheets.SIGN_SHEET, Identifier.withDefaultNamespace("entity/signs/hanging/" + woodType.name()));
    }

}
