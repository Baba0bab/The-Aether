package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.block.AetherBlocks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ModelBlockRenderer.class)
public class ModelBlockRendererMixin {
    /**
     * Renders Berry Bushes as opaque when the "Cutout Leaves" graphics option is disabled, matching the behavior of leaves.
     *
     * @param original     The original {@link Boolean} result.
     * @param cutoutLeaves Whether leaves should be rendered with cutout textures, as a {@link Boolean}.
     * @param blockState   The {@link BlockState} being rendered.
     * @return Whether the block should be rendered as opaque, as a {@link Boolean}.
     */
    @ModifyReturnValue(at = @At("RETURN"), method = "forceOpaque(ZLnet/minecraft/world/level/block/state/BlockState;)Z")
    private static boolean forceOpaque(boolean original, boolean cutoutLeaves, BlockState blockState) {
        return original || (!cutoutLeaves && (blockState.is(AetherBlocks.BERRY_BUSH.get()) || blockState.is(AetherBlocks.POTTED_BERRY_BUSH.get())));
    }
}
