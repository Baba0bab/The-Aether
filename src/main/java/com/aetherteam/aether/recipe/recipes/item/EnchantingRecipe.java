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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.FurnaceRecipeDisplay;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EnchantingRecipe extends AbstractAetherCookingRecipe {
    public EnchantingRecipe(String group, AetherBookCategory category, Ingredient ingredient, ItemStackTemplate result, float experience, int enchantingTime) {
        super(group, category, ingredient, result, experience, enchantingTime);
    }

    @Override
    protected Item furnaceIcon() {
        return AetherBlocks.ALTAR.asItem();
    }

    public List<RecipeDisplay> display() {
        return List.of(new FurnaceRecipeDisplay(
            this.input().display(),
            new SlotDisplay.Composite(BuiltInRegistries.ITEM.nitrogen_fabric$getDataMap(AetherDataMaps.ALTAR_FUEL).keySet().stream().map(BuiltInRegistries.ITEM::getValue).filter(Objects::nonNull).map(SlotDisplay.ItemSlotDisplay::new).collect(Collectors.toList())),
            new SlotDisplay.ItemStackSlotDisplay(this.result()),
            new SlotDisplay.ItemSlotDisplay(this.furnaceIcon()),
            this.cookingTime(),
            this.experience()
        ));
    }

    @Override
    public RecipeSerializer<EnchantingRecipe> getSerializer() {
        return AetherRecipeSerializers.ENCHANTING.get();
    }

    @Override
    public RecipeType<? extends AbstractCookingRecipe> getType() {
        return AetherRecipeTypes.ENCHANTING.get();
    }

    public static class Serializer extends AetherCookingSerializer<EnchantingRecipe> {
        public Serializer() {
            super(EnchantingRecipe::new, 250);
        }
    }
}
