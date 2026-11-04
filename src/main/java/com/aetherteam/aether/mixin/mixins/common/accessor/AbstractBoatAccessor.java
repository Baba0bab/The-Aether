package com.aetherteam.aether.mixin.mixins.common.accessor;

import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBoat.class)
public interface AbstractBoatAccessor {
    @Accessor("deltaRotation")
    float aether$getDeltaRotation();

    @Accessor("deltaRotation")
    void aether$setDeltaRotation(float deltaRotation);
}
