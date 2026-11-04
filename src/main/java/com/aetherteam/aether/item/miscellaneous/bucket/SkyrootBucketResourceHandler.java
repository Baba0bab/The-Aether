package com.aetherteam.aether.item.miscellaneous.bucket;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.transfer.ItemAccessResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.item.ItemResource;

import java.util.Objects;

/**
 * A fluid handler for Skyroot Buckets, which swaps between the empty and filled variants of Skyroot Buckets.
 */
public class SkyrootBucketResourceHandler extends ItemAccessResourceHandler<FluidResource> {
    public SkyrootBucketResourceHandler(ItemAccess itemAccess) {
        super(itemAccess, 1);
    }

    @Override
    protected FluidResource getResourceFrom(ItemResource accessResource, int index) {
        if (accessResource.is(AetherItems.SKYROOT_MILK_BUCKET.get()) && NeoForgeMod.MILK.isBound()) {
            return FluidResource.of(NeoForgeMod.MILK.get());
        } else if (accessResource.getItem() instanceof BucketItem bucketItem) {
            return FluidResource.of(bucketItem.content);
        } else {
            return FluidResource.EMPTY;
        }
    }

    @Override
    protected int getAmountFrom(ItemResource accessResource, int index) {
        return this.getResourceFrom(accessResource, index).isEmpty() ? 0 : FluidType.BUCKET_VOLUME;
    }

    @Override
    protected ItemResource update(ItemResource accessResource, int index, FluidResource newResource, int newAmount) {
        if (newAmount == 0) {
            return ItemResource.of(AetherItems.SKYROOT_BUCKET.get());
        } else if (newAmount != FluidType.BUCKET_VOLUME || !this.canHoldFluid(newResource)) {
            return ItemResource.EMPTY;
        } else {
            var newStack = newResource.toStack(newAmount);
            ItemStack filledBucket = SkyrootBucketItem.swapBucketType(newStack.getFluidType().getBucket(newStack));
            return filledBucket.is(AetherItems.SKYROOT_BUCKET.get()) ? ItemResource.EMPTY : ItemResource.of(filledBucket);
        }
    }

    @Override
    public boolean isValid(int index, FluidResource resource) {
        return this.canHoldFluid(resource) && super.isValid(index, resource);
    }

    @Override
    protected int getCapacity(int index, FluidResource resource) {
        Objects.checkIndex(index, this.size());
        return FluidType.BUCKET_VOLUME;
    }

    private boolean canHoldFluid(FluidResource resource) {
        return resource.is(AetherTags.Fluids.ALLOWED_BUCKET_PICKUP) || (NeoForgeMod.MILK_TYPE.isBound() && resource.is(NeoForgeMod.MILK_TYPE.get()));
    }
}
