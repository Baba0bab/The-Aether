package com.aetherteam.aether.recipe.recipes.item;

import net.minecraft.world.item.ItemStackTemplate;
import com.aetherteam.aether.recipe.serializer.AetherRecipeSerializer;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.recipe.book.AetherBookCategory;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class AltarRepairRecipe extends AbstractAetherCookingRecipe {
    public final Ingredient ingredient;

    public AltarRepairRecipe(String group, Ingredient ingredient, int repairTime) {
        super(group, AetherBookCategory.ENCHANTING_REPAIR, ingredient, new ItemStackTemplate(ingredient.items().findFirst().orElseThrow().value()), 0.0F, repairTime);
        this.ingredient = ingredient;
    }

    /**
     * @param inventory The crafting {@link SingleRecipeInput}.
     * @return The original {@link ItemStack} ingredient, because repairing always outputs the same item as the input.
     */
    @Override
    public ItemStack assemble(SingleRecipeInput inventory) {
        return this.ingredient.items().findFirst().orElseThrow().value().getDefaultInstance();
    }

    @Override
    protected Item furnaceIcon() {
        return AetherBlocks.ALTAR.get().asItem();
    }

    @Override
    public RecipeSerializer<AltarRepairRecipe> getSerializer() {
        return AetherRecipeSerializers.REPAIRING.get();
    }

    @Override
    public RecipeType<? extends AbstractCookingRecipe> getType() {
        return AetherRecipeTypes.ENCHANTING.get();
    }

    public static class Serializer implements AetherRecipeSerializer<AltarRepairRecipe> {
        private static final MapCodec<AltarRepairRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(AbstractCookingRecipe::group),
                Ingredient.CODEC.fieldOf("ingredient").forGetter(SingleItemRecipe::input),
                Codec.INT.fieldOf("repairTime").orElse(500).forGetter(AbstractCookingRecipe::cookingTime)
        ).apply(instance, AltarRepairRecipe::new));

        @Override
        public MapCodec<AltarRepairRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AltarRepairRecipe> streamCodec() {
            return StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, AbstractCookingRecipe::group,
                Ingredient.CONTENTS_STREAM_CODEC, AbstractCookingRecipe::input,
                ByteBufCodecs.INT, AbstractCookingRecipe::cookingTime,
                AltarRepairRecipe::new
            );
        }
    }
}
