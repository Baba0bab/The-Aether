package com.aetherteam.aether.data.resources;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

/**
 * Data map value for the burn time of fuels used by the Aether's furnace-like blocks.
 *
 * @param burnTime The {@link Integer} burn time in ticks.
 */
public record AetherFuel(int burnTime) {
    public static final Codec<AetherFuel> BURN_TIME_CODEC = ExtraCodecs.POSITIVE_INT.xmap(AetherFuel::new, AetherFuel::burnTime);
    public static final Codec<AetherFuel> CODEC = Codec.withAlternative(
        RecordCodecBuilder.create((instance) -> instance.group(ExtraCodecs.POSITIVE_INT.fieldOf("burn_time").forGetter(AetherFuel::burnTime)).apply(instance, AetherFuel::new)),
        BURN_TIME_CODEC);
}
