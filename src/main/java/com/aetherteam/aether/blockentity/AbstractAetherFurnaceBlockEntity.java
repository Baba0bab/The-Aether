package com.aetherteam.aether.blockentity;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Containers;
import com.aetherteam.nitrogen.fabric.registries.datamaps.DataMapType;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Item;
import com.aetherteam.aether.data.resources.AetherFuel;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.mixin.mixins.common.accessor.AbstractFurnaceBlockEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * [CODE COPY] - {@link AbstractFurnaceBlockEntity}.<br><br>
 * Certain static methods are copied with minor noted changes, and use accessors for {@link AbstractFurnaceBlockEntity}.
 */
public abstract class AbstractAetherFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 0};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    protected ItemStack remainderItem = ItemStack.EMPTY;

    public AbstractAetherFurnaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(type, pos, state, recipeType);
    }

    /**
     * Based on {@link AbstractFurnaceBlockEntity#serverTick(ServerLevel, BlockPos, BlockState, AbstractFurnaceBlockEntity)}.<br><br>
     * Modified to preserve item components in recipes and to handle crafting remainders of input items.
     */
    public static void serverTick(ServerLevel level, BlockPos pos, BlockState state, AbstractAetherFurnaceBlockEntity blockEntity) {
        AbstractFurnaceBlockEntityAccessor accessor = (AbstractFurnaceBlockEntityAccessor) blockEntity;
        NonNullList<ItemStack> items = accessor.aether$getItems();
        boolean changed = false;
        boolean isLit;
        boolean wasLit;
        if (accessor.aether$getLitTimeRemaining() > 0) {
            wasLit = true;
            accessor.aether$setLitTimeRemaining(accessor.aether$getLitTimeRemaining() - 1);
            isLit = accessor.aether$getLitTimeRemaining() > 0;
        } else {
            wasLit = false;
            isLit = false;
        }

        ItemStack fuel = items.get(1);
        ItemStack ingredient = items.get(0);
        boolean hasIngredient = !ingredient.isEmpty();
        boolean hasFuel = !fuel.isEmpty();
        if (isLit || hasFuel && hasIngredient) {
            if (hasIngredient) {
                SingleRecipeInput input = new SingleRecipeInput(ingredient);
                RecipeHolder<? extends AbstractCookingRecipe> recipe = accessor.aether$getQuickCheck().getRecipeFor(input, level).orElse(null);
                if (recipe != null) {
                    int maxStackSize = blockEntity.getMaxStackSize();
                    ItemStack burnResult = blockEntity.getBurnResult(recipe, input, ingredient);
                    if (!burnResult.isEmpty() && AbstractFurnaceBlockEntityAccessor.callCanBurn(items, maxStackSize, burnResult)) {
                        if (!isLit) {
                            int newLitTime = blockEntity.getBurnDuration(level, fuel);
                            accessor.aether$setLitTimeRemaining(newLitTime);
                            accessor.aether$setLitTotalTime(newLitTime);
                            accessor.aether$setSpeedMultiplier(blockEntity.getSpeedMultiplier(level, fuel));
                            if (newLitTime > 0) {
                                consumeFuel(level, pos, items, fuel);
                                isLit = true;
                                changed = true;
                            }
                        }
                        if (isLit) {
                            accessor.aether$setCookingTimer(accessor.aether$getCookingTimer() + 1);
                            if (accessor.aether$getCookingTimer() >= accessor.aether$getCookingTotalTime()) {
                                accessor.aether$setCookingTimer(0);
                                accessor.aether$setCookingTotalTime(AbstractFurnaceBlockEntityAccessor.callGetTotalCookTime(recipe, blockEntity));
                                burn(items, ingredient, burnResult);
                                blockEntity.setRecipeUsed(recipe);
                                changed = true;
                            }
                        } else {
                            accessor.aether$setCookingTimer(0);
                        }
                    } else {
                        accessor.aether$setCookingTimer(0);
                    }
                }
            } else {
                accessor.aether$setCookingTimer(0);
            }
        } else if (accessor.aether$getCookingTimer() > 0) {
            accessor.aether$setCookingTimer(Mth.clamp(accessor.aether$getCookingTimer() - 2, 0, accessor.aether$getCookingTotalTime()));
        }

        if (wasLit != isLit) {
            changed = true;
            state = state.setValue(AbstractFurnaceBlock.LIT, isLit);
            level.setBlockAndUpdate(pos, state);
        }

        if (changed) {
            setChanged(level, pos, state);
        }

        if (items.get(0).isEmpty() && items.get(2).isEmpty()) {
            blockEntity.remainderItem = ItemStack.EMPTY; // Resets the remainder item variable used for hopper extraction at the end of the tick loop. This is necessary so that it actually has enough time to get extracted.
        }
    }

    /**
     * Gets the result of a recipe, preserving the components of the input item if the result is the same item or if it is in the {@link AetherTags.Items#SAVE_NBT_IN_RECIPE} tag.
     */
    protected ItemStack getBurnResult(RecipeHolder<? extends AbstractCookingRecipe> recipe, SingleRecipeInput input, ItemStack ingredient) {
        ItemStack resultStack = recipe.value().assemble(input);
        if (ingredient.is(resultStack.getItem()) || resultStack.is(AetherTags.Items.SAVE_NBT_IN_RECIPE)) {
            resultStack = new ItemStack(resultStack.typeHolder(), resultStack.getCount(), ingredient.getComponentsPatch());
        }
        if (ingredient.is(resultStack.getItem())) {
            resultStack.setDamageValue(0);
        }
        return resultStack;
    }

    /**
     * [CODE COPY] - {@link AbstractFurnaceBlockEntity#consumeFuel(ServerLevel, BlockPos, NonNullList, ItemStack)}.
     */
    private static void consumeFuel(ServerLevel level, BlockPos pos, NonNullList<ItemStack> items, ItemStack fuel) {
        ItemStackTemplate remainder = fuel.getCraftingRemainder();
        ItemStack newFuel = fuel;
        fuel.shrink(1);
        if (remainder != null) {
            if (fuel.isEmpty()) {
                newFuel = remainder.create();
            } else {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), remainder.create());
            }
        }
        items.set(1, newFuel);
    }

    /**
     * Based on {@link AbstractFurnaceBlockEntity#burn(NonNullList, ItemStack, ItemStack)}.<br><br>
     * Modified to replace the input with its crafting remainder if it has one.
     */
    private static void burn(NonNullList<ItemStack> items, ItemStack inputItemStack, ItemStack result) {
        ItemStack resultItemStack = items.get(2);
        if (resultItemStack.isEmpty()) {
            items.set(2, result.copy());
        } else {
            resultItemStack.grow(result.getCount());
        }

        ItemStackTemplate inputRemainder = inputItemStack.getCraftingRemainder();
        ItemStackTemplate resultRemainder = result.getCraftingRemainder();
        if (inputRemainder != null && (resultRemainder == null || !inputRemainder.item().equals(resultRemainder.item()))) {
            items.set(0, inputRemainder.create());
        } else {
            inputItemStack.shrink(1);
        }
    }

    /**
     * @return The {@link DataMapType} for the fuels of this block entity.
     */
    protected abstract DataMapType<Item, AetherFuel> getFuelDataMap();

    @Override
    protected int getBurnDuration(ServerLevel level, ItemStack fuelStack) {
        return getFuelBurnTime(fuelStack);
    }

    @Override
    protected float getSpeedMultiplier(ServerLevel level, ItemStack fuelItem) {
        return 1.0F;
    }

    public int getFuelBurnTime(ItemStack fuelStack) {
        if (!fuelStack.isEmpty()) {
            AetherFuel fuel = fuelStack.typeHolder().nitrogen_fabric$getData(this.getFuelDataMap());
            if (fuel != null) {
                return fuel.burnTime();
            }
        }
        return 0;
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return direction == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            return this.getFuelBurnTime(stack) > 0;
        }
    }

    /**
     * Allows the Aether's furnaces to have remaining crafting byproducts extracted (like buckets) alongside product items.
     *
     * @param index     The {@link Integer} for the slot index.
     * @param stack     The {@link ItemStack} trying to be taken from the block.
     * @param direction The {@link Direction} for a face.
     * @return Whether the item can be taken by a hopper through the face, as a {@link Boolean}.
     */
    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        if (this.level instanceof ServerLevel serverLevel) {
            AbstractFurnaceBlockEntityAccessor abstractFurnaceBlockEntityAccessor = (AbstractFurnaceBlockEntityAccessor) this;
            Optional<Ingredient> ingredient = abstractFurnaceBlockEntityAccessor.aether$getQuickCheck().getRecipeFor(new SingleRecipeInput(abstractFurnaceBlockEntityAccessor.aether$getItems().getFirst()), serverLevel).map((recipe) -> recipe.value().input());
            if (this.remainderItem.isEmpty()) {
                ingredient.ifPresent(ing -> {
                    ItemStackTemplate remainder = stack.getCraftingRemainder();
                    this.remainderItem = remainder != null ? remainder.create() : ItemStack.EMPTY;
                }); // Stores the correlating crafting remainder item.
            }
            if (direction == Direction.DOWN && index == 0) {
                if (!this.remainderItem.isEmpty()) {
                    return stack.is(this.remainderItem.getItem()); // An item can be taken as long as it matches the stored crafting remainder item.
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        super.setItem(index, stack);
        if (this.getLevel() != null) {
            this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 1 | 2);
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registry) {
        return this.saveWithoutMetadata(registry);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
