package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemBlockRenderTypes.class)
public class ItemBlockRenderTypesMixin {
    @Shadow
    private static boolean renderCutout;

    @Inject(method = "getChunkRenderType(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/client/renderer/RenderType;", at = @At(value = "HEAD"), cancellable = true)
    private static void getChunkRenderType(BlockState state, CallbackInfoReturnable<RenderType> cir) {
        if (state.is(AetherBlocks.BERRY_BUSH) || state.is(AetherBlocks.POTTED_BERRY_BUSH)) {
            cir.setReturnValue(renderCutout ? RenderType.cutoutMipped() : RenderType.solid());
        }
    }

    @Inject(method = "getMovingBlockRenderType(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/client/renderer/RenderType;", at = @At(value = "HEAD"), cancellable = true)
    private static void getMovingBlockRenderType(BlockState state, CallbackInfoReturnable<RenderType> cir) {
        if (state.is(AetherBlocks.BERRY_BUSH) || state.is(AetherBlocks.POTTED_BERRY_BUSH)) {
            cir.setReturnValue(renderCutout ? RenderType.cutoutMipped() : RenderType.solid());
        }
    }
}
