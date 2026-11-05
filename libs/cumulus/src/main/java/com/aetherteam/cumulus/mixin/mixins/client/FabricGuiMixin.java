package com.aetherteam.cumulus.mixin.mixins.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import com.aetherteam.cumulus.events.CancellableCallbackImpl;
import com.aetherteam.cumulus.client.event.listeners.WorldPreviewListener;
import com.aetherteam.cumulus.client.OpeningScreenEvents;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Fires {@link OpeningScreenEvents#POST} when a screen is opened. In 26.x the current screen is held by {@link Gui} instead of the Minecraft class.
 */
@Mixin(value = Gui.class, priority = 1100)
public abstract class FabricGuiMixin {
    @Shadow
    @Nullable
    private Screen screen;

    @Definition(id = "screen", field = "Lnet/minecraft/client/gui/Gui;screen:Lnet/minecraft/client/gui/screens/Screen;")
    @Definition(id = "guiScreen", local = @Local(argsOnly = true, type = Screen.class))
    @Expression("this.screen = guiScreen")
    @WrapOperation(method = "setScreen", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private void beforeScreenSetCall(Gui instance, Screen value, Operation<Void> original, @Local(argsOnly = true) LocalRef<Screen> guiScreenRef) {
        if (value != null) {
            var replacementScreen = OpeningScreenEvents.POST.invoker().onOpening(this.screen, value);

            if (replacementScreen != null) {
                value = replacementScreen;

                guiScreenRef.set(replacementScreen);
            }
        }

        original.call(instance, value);
    }

    /**
     * Lets the world preview hide the current screen. In 26.x screens are extracted for rendering by {@link Gui}.
     */
    @WrapOperation(method = "extractRenderState(Lnet/minecraft/client/DeltaTracker;ZZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;extractRenderStateWithTooltipAndSubtitles(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V"))
    private void extractScreen(Screen instance, GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick, Operation<Void> original) {
        var callback = new CancellableCallbackImpl();

        WorldPreviewListener.onScreenRender(instance, callback);

        if (!callback.isCanceled()) original.call(instance, guiGraphics, mouseX, mouseY, partialTick);
    }
}
