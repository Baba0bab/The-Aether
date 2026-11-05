package com.aetherteam.aether.client.renderer.blockentity;

import net.minecraft.world.level.block.state.properties.ChestType;

import com.aetherteam.aether.blockentity.TreasureChestBlockEntity;
import com.aetherteam.aether.client.AetherAtlases;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.client.resources.model.sprite.SpriteId;

public class TreasureChestRenderer extends ChestRenderer<TreasureChestBlockEntity> {
    public TreasureChestRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    /**
     * @see com.aetherteam.aether.mixin.mixins.client.aether_fabric.ChestRendererMixin
     */
    public static SpriteId getTreasureChestSprite(ChestType type) {
        return switch (type) {
            case LEFT -> AetherAtlases.TREASURE_CHEST_LEFT_MATERIAL;
            case RIGHT -> AetherAtlases.TREASURE_CHEST_RIGHT_MATERIAL;
            case SINGLE -> AetherAtlases.TREASURE_CHEST_MATERIAL;
        };
    }
}
