package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.world.LevelUtil;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    /**
     * Prevents the weather cycle from advancing in the Aether, as the weather is shared with the Overworld, which already advances it.
     */
    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"), method = "advanceWeatherCycle()V")
    private Object advanceWeatherCycle(Object original) {
        ServerLevel level = (ServerLevel) (Object) this;
        if (LevelUtil.isAetherDimensionType(level)) {
            return false;
        }
        return original;
    }

    /**
     * Prevents fire from spreading in the Aether.
     */
    @Inject(at = @At("HEAD"), method = "canSpreadFireAround(Lnet/minecraft/core/BlockPos;)Z", cancellable = true)
    private void canSpreadFireAround(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        ServerLevel level = (ServerLevel) (Object) this;
        if (LevelUtil.isAetherDimensionType(level)) {
            cir.setReturnValue(false);
        }
    }
}
