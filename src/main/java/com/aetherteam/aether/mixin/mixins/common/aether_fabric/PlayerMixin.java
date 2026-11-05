package com.aetherteam.aether.mixin.mixins.common.aether_fabric;

import com.aetherteam.aether.item.combat.loot.ValkyrieLanceItem;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerMixin {
    /**
     * Prevents the Valkyrie Lance from performing sweep attacks, replacing NeoForge's {@code SWORD_SWEEP} item ability check.
     */
    @ModifyReturnValue(method = "isSweepAttack", at = @At("RETURN"))
    private boolean aetherFabric$preventSweeping(boolean original) {
        return original && !(((Player) (Object) this).getMainHandItem().getItem() instanceof ValkyrieLanceItem);
    }
}
