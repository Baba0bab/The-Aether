package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.mixin.AetherMixinHooks;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Avatar.class)
public class AvatarMixin {
    /**
     * Sets the player as having a loaded cape if they have a cape accessory equipped and visible.
     *
     * @param part The {@link PlayerModelPart} to check.
     * @param cir  The {@link Boolean} {@link CallbackInfoReturnable} used for the method's return value.
     */
    @Inject(at = @At(value = "HEAD"), method = "isModelPartShown(Lnet/minecraft/world/entity/player/PlayerModelPart;)Z", cancellable = true)
    private void isModelPartShown(PlayerModelPart part, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player player && !AetherMixinHooks.isCapeVisible(player).isEmpty()) {
            cir.setReturnValue(true);
        }
    }
}
