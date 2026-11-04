package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.api.AetherAdvancementSoundOverrides;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AdvancementToast.class)
public class AdvancementToastMixin {
    @Final
    @Shadow
    private AdvancementHolder advancement;
    /**
     * Uses the Aether's advancement sounds when the player gets an Aether advancement.
     *
     * @param cir The {@link SoundEvent} {@link CallbackInfoReturnable} used for the method's return value.
     */
    @Inject(at = @At(value = "HEAD"), method = "getSoundEvent()Lnet/minecraft/sounds/SoundEvent;", cancellable = true)
    private void getSoundEvent(CallbackInfoReturnable<SoundEvent> cir) {
        if (this.advancement != null) {
            SoundEvent soundOverride = AetherAdvancementSoundOverrides.retrieveOverride(this.advancement);
            if (soundOverride != null) {
                cir.setReturnValue(soundOverride != SoundEvents.EMPTY ? soundOverride : null);
            }
        }
    }
}
