package com.aetherteam.aether.recipe.serializer;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

/**
 * Describes the codecs of a recipe type, which are used to create its {@link RecipeSerializer}.
 *
 * @param <T> The {@link Recipe} type.
 */
public interface AetherRecipeSerializer<T extends Recipe<?>> {
    MapCodec<T> codec();

    StreamCodec<RegistryFriendlyByteBuf, T> streamCodec();

    default RecipeSerializer<T> build() {
        return new RecipeSerializer<>(this.codec(), this.streamCodec());
    }
}
