package com.aetherteam.aether.mixin.mixins.common.aether_fabric;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.util.Prediction;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Inject(method = "drop(Lnet/minecraft/world/item/ItemStack;ZLnet/minecraft/util/Prediction;)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At("RETURN"))
    private void aetherFabric$captureDroppedStack(ItemStack droppedItem, boolean dropAround, Prediction prediction, CallbackInfoReturnable<ItemEntity> cir) {
        ItemEntity itemEntity = cir.getReturnValue();
        if (itemEntity != null) {
            itemEntity.getAttachedOrCreate(AetherDataAttachments.DROPPED_ITEM).setOwner((ServerPlayer) (Object) this);
        }
    }
}
