package com.aetherteam.aether.recipe.book;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeBookCategory;
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

    // TODO: [Fabric 26.3] The search tabs of the Aether's recipe books need their sub-categories registered with the client recipe book,
    // which NeoForge does through RegisterRecipeBookSearchCategoriesEvent and Fabric has no API for yet.
}
