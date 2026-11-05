package com.aetherteam.aether.recipe.serializer;

import net.minecraft.world.item.ItemStackTemplate;
import com.aetherteam.aether.recipe.book.AetherBookCategory;
import com.aetherteam.aether.recipe.recipes.item.AbstractAetherCookingRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.*;

/**
 * [CODE COPY] - {@link AbstractCookingRecipe.Serializer}.<br><br>
 * Cleaned up.
 */
public class AetherCookingSerializer<T extends AbstractAetherCookingRecipe> implements AetherRecipeSerializer<T> {
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public AetherCookingSerializer(AetherCookingSerializer.CookieBaker<T> factory, int defaultCookingTime) {
        this.codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(AbstractCookingRecipe::group),
                AetherBookCategory.CODEC.fieldOf("category").forGetter(AbstractAetherCookingRecipe::aetherCategory),
                Ingredient.CODEC.fieldOf("ingredient").forGetter(SingleItemRecipe::input),
                ItemStackTemplate.CODEC.fieldOf("result").forGetter(AbstractAetherCookingRecipe::result),
                Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(AbstractCookingRecipe::experience),
                Codec.INT.fieldOf("cookingtime").orElse(defaultCookingTime).forGetter(AbstractCookingRecipe::cookingTime)
        ).apply(instance, factory::create));
        this.streamCodec = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, AbstractCookingRecipe::group,
            AetherBookCategory.STREAM_CODEC, AbstractAetherCookingRecipe::aetherCategory,
            Ingredient.CONTENTS_STREAM_CODEC, SingleItemRecipe::input,
            ItemStackTemplate.STREAM_CODEC, AbstractAetherCookingRecipe::result,
            ByteBufCodecs.FLOAT, AbstractCookingRecipe::experience,
            ByteBufCodecs.INT, AbstractCookingRecipe::cookingTime,
            factory::create
        );
    }

    @Override
    public MapCodec<T> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return this.streamCodec;
    }

    public interface CookieBaker<T extends AbstractAetherCookingRecipe> {
        T create(String group, AetherBookCategory category, Ingredient ingredient, ItemStackTemplate result, float experience, int cookingTime);
    }
}
