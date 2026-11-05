package com.aetherteam.aether.recipe.recipes.item;

import net.minecraft.world.item.ItemStackTemplate;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.registries.AetherDataMaps;
import com.aetherteam.aether.recipe.book.AetherBookCategory;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.serializer.AetherCookingSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.FurnaceRecipeDisplay;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FreezingRecipe extends AbstractAetherCookingRecipe {
    public FreezingRecipe(String group, AetherBookCategory category, Ingredient ingredient, ItemStackTemplate result, float experience, int freezingTime) {
        super(group, category, ingredient, result, experience, freezingTime);
    }

    @Override
    protected Item furnaceIcon() {
        return AetherBlocks.FREEZER.asItem();
    }

    public List<RecipeDisplay> display() {
        return List.of(new FurnaceRecipeDisplay(
            this.input().display(),
            new SlotDisplay.Composite(BuiltInRegistries.ITEM.nitrogen_fabric$getDataMap(AetherDataMaps.FREEZER_FUEL).keySet().stream().map(BuiltInRegistries.ITEM::getValue).filter(Objects::nonNull).map(SlotDisplay.ItemSlotDisplay::new).collect(Collectors.toList())),
            new SlotDisplay.ItemStackSlotDisplay(this.result()),
            new SlotDisplay.ItemSlotDisplay(this.furnaceIcon()),
            this.cookingTime(),
            this.experience()
        ));
    }

    @Override
    public RecipeSerializer<FreezingRecipe> getSerializer() {
        return AetherRecipeSerializers.FREEZING.get();
    }

    @Override
    public RecipeType<? extends AbstractCookingRecipe> getType() {
        return AetherRecipeTypes.FREEZING.get();
    }

    public static class Serializer extends AetherCookingSerializer<FreezingRecipe> {
        public Serializer() {
            super(FreezingRecipe::new, 800);
        }
    }
}
