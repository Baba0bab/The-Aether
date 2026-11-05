package com.aetherteam.aether.integration.jei;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.client.AetherClientRecipes;
import com.aetherteam.aether.integration.jei.categories.ban.BlockBanRecipeCategory;
import com.aetherteam.aether.integration.jei.categories.ban.ItemBanRecipeCategory;
import com.aetherteam.aether.integration.jei.categories.block.*;
import com.aetherteam.aether.integration.jei.categories.fuel.AetherFuelCategory;
import com.aetherteam.aether.integration.jei.categories.fuel.AetherFuelRecipeMaker;
import com.aetherteam.aether.integration.jei.categories.item.AltarRepairRecipeCategory;
import com.aetherteam.aether.integration.jei.categories.item.EnchantingRecipeCategory;
import com.aetherteam.aether.integration.jei.categories.item.FreezingRecipeCategory;
import com.aetherteam.aether.integration.jei.categories.item.IncubationRecipeCategory;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.recipes.item.AltarRepairRecipe;
import com.aetherteam.aether.recipe.recipes.item.EnchantingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

@JeiPlugin
public class AetherJEIPlugin implements IModPlugin {
    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(Aether.MODID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        IPlatformFluidHelper<?> fluidHelper = registration.getJeiHelpers().getPlatformFluidHelper();

        // Item
        registration.addRecipeCategories(new EnchantingRecipeCategory(guiHelper));
        registration.addRecipeCategories(new AltarRepairRecipeCategory(guiHelper));
        registration.addRecipeCategories(new FreezingRecipeCategory(guiHelper));
        registration.addRecipeCategories(new IncubationRecipeCategory(guiHelper));

        // Fuel
        registration.addRecipeCategories(new AetherFuelCategory(guiHelper));

        // Block
        registration.addRecipeCategories(new AmbrosiumRecipeCategory(guiHelper, fluidHelper));
        registration.addRecipeCategories(new SwetBallRecipeCategory(guiHelper, fluidHelper));
        registration.addRecipeCategories(new IcestoneFreezableRecipeCategory(guiHelper, fluidHelper));
        registration.addRecipeCategories(new AccessoryFreezableRecipeCategory(guiHelper, fluidHelper));
        registration.addRecipeCategories(new PlacementConversionRecipeCategory(guiHelper, fluidHelper));
        registration.addRecipeCategories(new ItemBanRecipeCategory(guiHelper, fluidHelper));
        registration.addRecipeCategories(new BlockBanRecipeCategory(guiHelper, fluidHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        AetherClientRecipes recipes = AetherClientRecipes.getRecipes();

        // Item
        List<? extends Recipe<?>> altarRecipes = values(recipes, AetherRecipeTypes.ENCHANTING.get());
        registration.addRecipes(EnchantingRecipeCategory.RECIPE_TYPE, altarRecipes.stream().filter((recipe) -> recipe instanceof EnchantingRecipe).map((recipe) -> (EnchantingRecipe) recipe).toList());
        registration.addRecipes(AltarRepairRecipeCategory.RECIPE_TYPE, altarRecipes.stream().filter((recipe) -> recipe instanceof AltarRepairRecipe).map((recipe) -> (AltarRepairRecipe) recipe).toList());
        registration.addRecipes(FreezingRecipeCategory.RECIPE_TYPE, values(recipes, AetherRecipeTypes.FREEZING.get()));
        registration.addRecipes(IncubationRecipeCategory.RECIPE_TYPE, values(recipes, AetherRecipeTypes.INCUBATION.get()));

        // Fuel
        registration.addRecipes(AetherFuelCategory.RECIPE_TYPE, AetherFuelRecipeMaker.getFuelRecipes());

        // Block
        registration.addRecipes(AmbrosiumRecipeCategory.RECIPE_TYPE, values(recipes, AetherRecipeTypes.AMBROSIUM_ENCHANTING.get()));
        registration.addRecipes(SwetBallRecipeCategory.RECIPE_TYPE, values(recipes, AetherRecipeTypes.SWET_BALL_CONVERSION.get()));
        registration.addRecipes(IcestoneFreezableRecipeCategory.RECIPE_TYPE, values(recipes, AetherRecipeTypes.ICESTONE_FREEZABLE.get()));
        registration.addRecipes(AccessoryFreezableRecipeCategory.RECIPE_TYPE, values(recipes, AetherRecipeTypes.ACCESSORY_FREEZABLE.get()));
        registration.addRecipes(PlacementConversionRecipeCategory.RECIPE_TYPE, values(recipes, AetherRecipeTypes.PLACEMENT_CONVERSION.get()));
        registration.addRecipes(ItemBanRecipeCategory.RECIPE_TYPE, values(recipes, AetherRecipeTypes.ITEM_PLACEMENT_BAN.get()));
        registration.addRecipes(BlockBanRecipeCategory.RECIPE_TYPE, values(recipes, AetherRecipeTypes.BLOCK_PLACEMENT_BAN.get()));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T extends Recipe<?>> List<T> values(AetherClientRecipes recipes, RecipeType<? extends T> type) {
        return ((java.util.Collection<RecipeHolder<T>>) (java.util.Collection) recipes.byType((RecipeType) type)).stream().map(RecipeHolder::value).toList();
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        // Item
        registration.addCraftingStation(EnchantingRecipeCategory.RECIPE_TYPE, AetherBlocks.ALTAR.get());
        registration.addCraftingStation(AltarRepairRecipeCategory.RECIPE_TYPE, AetherBlocks.ALTAR.get());
        registration.addCraftingStation(FreezingRecipeCategory.RECIPE_TYPE, AetherBlocks.FREEZER.get());
        registration.addCraftingStation(IncubationRecipeCategory.RECIPE_TYPE, AetherBlocks.INCUBATOR.get());
        registration.addCraftingStation(AetherFuelCategory.RECIPE_TYPE, AetherBlocks.ALTAR.get(), AetherBlocks.FREEZER.get(), AetherBlocks.INCUBATOR.get());

        // Block
        registration.addCraftingStation(AmbrosiumRecipeCategory.RECIPE_TYPE, AetherItems.AMBROSIUM_SHARD.get());
        registration.addCraftingStation(SwetBallRecipeCategory.RECIPE_TYPE, AetherItems.SWET_BALL.get());
        registration.addCraftingStation(IcestoneFreezableRecipeCategory.RECIPE_TYPE, AetherBlocks.ICESTONE.get(), AetherBlocks.ICESTONE_SLAB.get(), AetherBlocks.ICESTONE_STAIRS.get(), AetherBlocks.ICESTONE_WALL.get());
        registration.addCraftingStation(AccessoryFreezableRecipeCategory.RECIPE_TYPE, AetherItems.ICE_RING.get(), AetherItems.ICE_PENDANT.get());
        registration.addCraftingStation(PlacementConversionRecipeCategory.RECIPE_TYPE, AetherItems.AETHER_PORTAL_FRAME.get());
        registration.addCraftingStation(ItemBanRecipeCategory.RECIPE_TYPE, Items.FLINT_AND_STEEL);
        registration.addCraftingStation(BlockBanRecipeCategory.RECIPE_TYPE, Blocks.TORCH);
    }
}
