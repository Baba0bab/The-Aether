package com.aetherteam.aether.mixin.mixins.common.accessor;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractFurnaceBlockEntity.class)
public interface AbstractFurnaceBlockEntityAccessor {
    @Accessor("quickCheck")
    RecipeManager.CachedCheck<SingleRecipeInput, ? extends AbstractCookingRecipe> aether$getQuickCheck();

    @Invoker
    static boolean callCanBurn(NonNullList<ItemStack> items, int maxStackSize, ItemStack burnResult) {
        throw new AssertionError();
    }

    @Invoker
    static int callGetTotalCookTime(RecipeHolder<? extends AbstractCookingRecipe> recipe, AbstractFurnaceBlockEntity entity) {
        throw new AssertionError();
    }

    @Accessor("litTimeRemaining")
    int aether$getLitTimeRemaining();

    @Accessor("litTimeRemaining")
    void aether$setLitTimeRemaining(int litTimeRemaining);

    @Accessor("litTotalTime")
    void aether$setLitTotalTime(int litTotalTime);

    @Accessor("cookingTimer")
    int aether$getCookingTimer();

    @Accessor("cookingTimer")
    void aether$setCookingTimer(int cookingTimer);

    @Accessor("cookingTotalTime")
    int aether$getCookingTotalTime();

    @Accessor("cookingTotalTime")
    void aether$setCookingTotalTime(int cookingTotalTime);

    @Accessor("speedMultiplier")
    void aether$setSpeedMultiplier(float speedMultiplier);

    @Accessor("items")
    NonNullList<ItemStack> aether$getItems();
}
