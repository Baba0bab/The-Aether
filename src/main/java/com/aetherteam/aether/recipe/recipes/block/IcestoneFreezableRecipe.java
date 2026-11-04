package com.aetherteam.aether.recipe.recipes.block;

import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.aetherteam.aether.recipe.serializer.BlockStateRecipeSerializer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.Optional;

public class IcestoneFreezableRecipe extends AbstractBlockStateRecipe {
    public IcestoneFreezableRecipe(BlockStateIngredient ingredient, BlockPropertyPair result, Optional<Identifier> function) {
        super(AetherRecipeTypes.ICESTONE_FREEZABLE.get(), ingredient, result, function);
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    @Override
    public RecipeSerializer<IcestoneFreezableRecipe> getSerializer() {
        return AetherRecipeSerializers.ICESTONE_FREEZABLE.get();
    }

    public static class Serializer extends BlockStateRecipeSerializer<IcestoneFreezableRecipe> {
        public Serializer() {
            super(IcestoneFreezableRecipe::new);
        }
    }
}
