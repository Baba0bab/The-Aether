package com.aetherteam.nitrogen.fabric.mixin;

import com.aetherteam.nitrogen.fabric.events.LivingEntityEvents;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.cubemob.MagmaCube;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {LivingEntity.class, MagmaCube.class})
public abstract class LivingEntitiesJumpMixin {
    @Definition(id = "needsSync", field = {
        "Lnet/minecraft/world/entity/LivingEntity;needsSync:Z",
        "Lnet/minecraft/world/entity/monster/cubemob/MagmaCube;needsSync:Z"
    })
    @Expression("this.needsSync = true")
    @Inject(method = "jumpFromGround", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER))
    private void nitrogen_fabric$fireJumpEvent(CallbackInfo ci){
        LivingEntityEvents.ON_JUMP.invoker().onJump((LivingEntity) (Object) this);
    }
}
