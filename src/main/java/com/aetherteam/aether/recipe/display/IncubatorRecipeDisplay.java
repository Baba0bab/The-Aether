package com.aetherteam.aether.recipe.display;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record IncubatorRecipeDisplay(SlotDisplay ingredient, SlotDisplay fuel, SlotDisplay result, SlotDisplay craftingStation, int duration) implements RecipeDisplay {
    public static final MapCodec<IncubatorRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec((p_380853_) -> p_380853_.group(
        SlotDisplay.CODEC.fieldOf("ingredient").forGetter(IncubatorRecipeDisplay::ingredient),
        SlotDisplay.CODEC.fieldOf("fuel").forGetter(IncubatorRecipeDisplay::fuel),
        SlotDisplay.CODEC.fieldOf("result").forGetter(IncubatorRecipeDisplay::result),
        SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(IncubatorRecipeDisplay::craftingStation),
        Codec.INT.fieldOf("duration").forGetter(IncubatorRecipeDisplay::duration)
    ).apply(p_380853_, IncubatorRecipeDisplay::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, IncubatorRecipeDisplay> STREAM_CODEC  = StreamCodec.composite(
        SlotDisplay.STREAM_CODEC, IncubatorRecipeDisplay::ingredient,
        SlotDisplay.STREAM_CODEC, IncubatorRecipeDisplay::fuel,
        SlotDisplay.STREAM_CODEC, IncubatorRecipeDisplay::result,
        SlotDisplay.STREAM_CODEC, IncubatorRecipeDisplay::craftingStation,
        ByteBufCodecs.VAR_INT, IncubatorRecipeDisplay::duration,
        IncubatorRecipeDisplay::new);
    public static final RecipeDisplay.Type<IncubatorRecipeDisplay> TYPE = new RecipeDisplay.Type<>(MAP_CODEC, STREAM_CODEC);

    public RecipeDisplay.Type<IncubatorRecipeDisplay> type() {
        return TYPE;
    }

    public boolean isEnabled(FeatureFlagSet enabledFeatures) {
        return this.ingredient.isEnabled(enabledFeatures) && this.fuel().isEnabled(enabledFeatures) && RecipeDisplay.super.isEnabled(enabledFeatures);
    }
}
