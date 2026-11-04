package com.aetherteam.nitrogen.fabric.mixin;

import com.aetherteam.nitrogen.fabric.events.BlockEvents;
import com.aetherteam.nitrogen.fabric.events.CancellableCallbackImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(TrunkPlacer.class)
public abstract class TrunkPlacerMixin {
    @Inject(method = "placeBelowTrunkBlock", at = @At("HEAD"), cancellable = true)
    private static void nitrogen_fabric$onTreeGrow(WorldGenLevel level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, TreeFeature tree, CallbackInfo ci) {
        var state = level.getBlockState(pos);
        var callback = new CancellableCallbackImpl();

        BlockEvents.ON_TREE_GROW.invoker().onTreeGrow(state, level, blockSetter, random, pos, tree, callback);

        if (callback.isCanceled()) ci.cancel();
    }
}
