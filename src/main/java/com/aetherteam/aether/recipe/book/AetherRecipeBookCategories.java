package com.aetherteam.aether.recipe.book;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.AetherClient;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterRecipeBookSearchCategoriesEvent;
import com.aetherteam.nitrogen.fabric.registries.DeferredHolder;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;

public class AetherRecipeBookCategories {
    public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = DeferredRegister.create(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Aether.MODID);

    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> ENCHANTING_FOOD = RECIPE_BOOK_CATEGORIES.register("enchanting_food", RecipeBookCategory::new);
    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> ENCHANTING_BLOCKS = RECIPE_BOOK_CATEGORIES.register("enchanting_blocks", RecipeBookCategory::new);
    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> ENCHANTING_MISC = RECIPE_BOOK_CATEGORIES.register("enchanting_misc", RecipeBookCategory::new);
    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> ENCHANTING_REPAIR = RECIPE_BOOK_CATEGORIES.register("enchanting_repair", RecipeBookCategory::new);

    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> FREEZABLE_BLOCKS = RECIPE_BOOK_CATEGORIES.register("freezable_blocks", RecipeBookCategory::new);
    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> FREEZABLE_MISC = RECIPE_BOOK_CATEGORIES.register("freezable_misc", RecipeBookCategory::new);

    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> INCUBATION_MISC = RECIPE_BOOK_CATEGORIES.register("incubation_misc", RecipeBookCategory::new);

    public static final RecipeBookCategory ENCHANTING_SEARCH = new RecipeBookCategory();
    public static final RecipeBookCategory FREEZABLE_SEARCH = new RecipeBookCategory();
    public static final RecipeBookCategory INCUBATION_SEARCH = new RecipeBookCategory();

    /**
     * Registers the mod's categories to be used in-game, along with functions to sort items.
     * To add sub-categories to be used by the search, use addAggregateCategories with the
     * search category as the first parameter.
     *
     * @see AetherClient#eventSetup(IEventBus)
     */
    public static void registerRecipeCategories(RegisterRecipeBookSearchCategoriesEvent event) {
        event.register(ENCHANTING_SEARCH, ENCHANTING_FOOD.get(), ENCHANTING_BLOCKS.get(), ENCHANTING_REPAIR.get(), ENCHANTING_MISC.get());
        event.register(FREEZABLE_SEARCH, FREEZABLE_BLOCKS.get(), FREEZABLE_MISC.get());
        event.register(INCUBATION_SEARCH, INCUBATION_MISC.get());


//        event.registerBookCategories(AetherRecipeBookTypes.ALTAR, ImmutableList.of(ENCHANTING_SEARCH.get(), ENCHANTING_FOOD.get(), ENCHANTING_BLOCKS.get(), ENCHANTING_MISC.get(), ENCHANTING_REPAIR.get()));
//        event.registerAggregateCategory(ENCHANTING_SEARCH.get(), ImmutableList.of(ENCHANTING_FOOD.get(), ENCHANTING_BLOCKS.get(), ENCHANTING_MISC.get(), ENCHANTING_REPAIR.get()));
//        event.registerRecipeCategoryFinder(AetherRecipeTypes.ENCHANTING.get(), recipe -> {
//            if (recipe.value() instanceof AltarRepairRecipe || (recipe.value() instanceof AbstractAetherCookingRecipe abstractAetherCookingRecipe && abstractAetherCookingRecipe.aetherCategory() == AetherBookCategory.ENCHANTING_REPAIR)) {
//                return ENCHANTING_REPAIR.get();
//            } else if (recipe.value() instanceof AbstractAetherCookingRecipe abstractAetherCookingRecipe) {
//                if (abstractAetherCookingRecipe.aetherCategory() == AetherBookCategory.ENCHANTING_FOOD) {
//                    return ENCHANTING_FOOD.get();
//                } else if (abstractAetherCookingRecipe.aetherCategory() == AetherBookCategory.ENCHANTING_BLOCKS) {
//                    return ENCHANTING_BLOCKS.get();
//                }
//            }
//            return ENCHANTING_MISC.get();
//        });
//        // Freezing
//        event.registerBookCategories(AetherRecipeBookTypes.FREEZER, ImmutableList.of(FREEZABLE_SEARCH.get(), FREEZABLE_BLOCKS.get(), FREEZABLE_MISC.get()));
//        event.registerAggregateCategory(FREEZABLE_SEARCH.get(), ImmutableList.of(FREEZABLE_BLOCKS.get(), FREEZABLE_MISC.get()));
//        event.registerRecipeCategoryFinder(AetherRecipeTypes.FREEZING.get(), recipe -> {
//            if (recipe.value() instanceof AbstractAetherCookingRecipe abstractAetherCookingRecipe) {
//                if (abstractAetherCookingRecipe.aetherCategory() == AetherBookCategory.FREEZABLE_BLOCKS) {
//                    return FREEZABLE_BLOCKS.get();
//                }
//            }
//            return FREEZABLE_MISC.get();
//        });
//        // Incubation
//        event.registerBookCategories(AetherRecipeBookTypes.INCUBATOR, ImmutableList.of(INCUBATION_SEARCH.get(), INCUBATION_MISC.get()));
//        event.registerAggregateCategory(INCUBATION_SEARCH.get(), ImmutableList.of(INCUBATION_MISC.get()));
//        event.registerRecipeCategoryFinder(AetherRecipeTypes.INCUBATION.get(), recipe -> INCUBATION_MISC.get());
    }
}
