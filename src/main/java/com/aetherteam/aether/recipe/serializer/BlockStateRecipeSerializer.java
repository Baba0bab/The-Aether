package com.aetherteam.aether.recipe.serializer;

import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class BlockStateRecipeSerializer<T extends AbstractBlockStateRecipe> implements AetherRecipeSerializer<T> {
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public BlockStateRecipeSerializer(AbstractBlockStateRecipe.Factory<T> factory) {
        this.codec = AbstractBlockStateRecipe.codec(factory);
        this.streamCodec = AbstractBlockStateRecipe.streamCodec(factory);
    }

    @Override
    public MapCodec<T> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return this.streamCodec;
    }
}
