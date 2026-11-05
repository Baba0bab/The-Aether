package com.aetherteam.aether.mixin.mixins.client.aether_fabric;

import com.aetherteam.aether.client.renderer.blockentity.TreasureChestRenderer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Lets the Treasure Chest renderer use its own sprites, replacing NeoForge's custom chest sprite hook.
 */
@Mixin(ChestRenderer.class)
public abstract class ChestRendererMixin {
    @WrapOperation(
        method = "submit(Lnet/minecraft/client/renderer/blockentity/state/ChestRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Sheets;chooseSprite(Lnet/minecraft/client/renderer/blockentity/state/ChestRenderState$ChestMaterialType;Lnet/minecraft/world/level/block/state/properties/ChestType;)Lnet/minecraft/client/resources/model/sprite/SpriteId;")
    )
    private SpriteId aetherFabric$adjustSprite(ChestRenderState.ChestMaterialType material, ChestType type, Operation<SpriteId> original) {
        if ((Object) this instanceof TreasureChestRenderer) {
            return TreasureChestRenderer.getTreasureChestSprite(type);
        }
        return original.call(material, type);
    }
}
