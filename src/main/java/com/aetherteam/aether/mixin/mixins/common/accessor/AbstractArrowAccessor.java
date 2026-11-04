package com.aetherteam.aether.mixin.mixins.common.accessor;

import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractArrow.class)
public interface AbstractArrowAccessor {
    @Accessor("baseDamage")
    double aether$getBaseDamage();
}
