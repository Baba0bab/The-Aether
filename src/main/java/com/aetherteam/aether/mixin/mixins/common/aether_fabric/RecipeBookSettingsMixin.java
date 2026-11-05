package com.aetherteam.aether.mixin.mixins.common.aether_fabric;

import com.aetherteam.aether.inventory.AetherRecipeBookTypes;
import net.minecraft.stats.RecipeBookSettings;
import net.minecraft.world.inventory.RecipeBookType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * Vanilla only has fields for its own recipe book types and throws for any other enum value,
 * so the settings for the Aether's types are kept here. They are not saved or synced.
 */
@Mixin(RecipeBookSettings.class)
public abstract class RecipeBookSettingsMixin {
    @Unique
    private final Map<RecipeBookType, RecipeBookSettings.TypeSettings> aetherFabric$aetherSettings = new HashMap<>();

    @Inject(method = "getSettings", at = @At("HEAD"), cancellable = true)
    private void aetherFabric$getSettings(RecipeBookType type, CallbackInfoReturnable<RecipeBookSettings.TypeSettings> cir) {
        if (AetherRecipeBookTypes.isAetherType(type)) {
            cir.setReturnValue(this.aetherFabric$aetherSettings.getOrDefault(type, RecipeBookSettings.TypeSettings.DEFAULT));
        }
    }

    @Inject(method = "updateSettings", at = @At("HEAD"), cancellable = true)
    private void aetherFabric$updateSettings(RecipeBookType type, UnaryOperator<RecipeBookSettings.TypeSettings> operator, CallbackInfo ci) {
        if (AetherRecipeBookTypes.isAetherType(type)) {
            this.aetherFabric$aetherSettings.put(type, operator.apply(this.aetherFabric$aetherSettings.getOrDefault(type, RecipeBookSettings.TypeSettings.DEFAULT)));
            ci.cancel();
        }
    }

    @Inject(method = "copy", at = @At("RETURN"))
    private void aetherFabric$copy(CallbackInfoReturnable<RecipeBookSettings> cir) {
        ((RecipeBookSettingsMixin) (Object) cir.getReturnValue()).aetherFabric$aetherSettings.putAll(this.aetherFabric$aetherSettings);
    }

    @Inject(method = "replaceFrom", at = @At("TAIL"))
    private void aetherFabric$replaceFrom(RecipeBookSettings other, CallbackInfo ci) {
        this.aetherFabric$aetherSettings.clear();
        this.aetherFabric$aetherSettings.putAll(((RecipeBookSettingsMixin) (Object) other).aetherFabric$aetherSettings);
    }
}
