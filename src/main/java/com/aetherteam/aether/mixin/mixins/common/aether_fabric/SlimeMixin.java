package com.aetherteam.aether.mixin.mixins.common.aether_fabric;

import com.aetherteam.aether.entity.monster.Swet;
import com.aetherteam.aether.event.hooks.EntityHooks;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.cubemob.AbstractCubeMob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractCubeMob.class)
/**
 * Slime splitting and particles are handled by {@link AbstractCubeMob} in 26.x.
 */
public abstract class SlimeMixin {
    @ModifyExpressionValue(method = "remove", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/cubemob/AbstractCubeMob;isDeadOrDying()Z"))
    private boolean aetherFabric$dontSplitForSwets(boolean original) {
        return original && !EntityHooks.preventSplit((Mob) (Object) this);
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    private void aetherFabric$dontSpawnParticles(Level instance, ParticleOptions particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, Operation<Void> original) {
        if ((Object) this instanceof Swet) return;

        original.call(instance, particleData, x, y, z, xSpeed, ySpeed, zSpeed);
    }
}
