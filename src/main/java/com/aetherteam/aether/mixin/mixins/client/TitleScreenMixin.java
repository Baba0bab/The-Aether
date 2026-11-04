package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.gui.screen.menu.CustomBranding;
import net.minecraft.client.gui.Font;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @WrapOperation(method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/internal/BrandingControl;forEachLine(ZZLjava/util/function/BiConsumer;)V"))
    private void forEachLine(boolean includeMC, boolean reverse, BiConsumer<Integer, String> lineConsumer, Operation<Void> original, @Local(argsOnly = true) GuiGraphicsExtractor guiGraphics, @Local(ordinal = 2) int i) {
        TitleScreen titleScreen = (TitleScreen) (Object) this;
        if (!(titleScreen instanceof CustomBranding customBranding) || !customBranding.forEachLineBranding(includeMC, reverse, lineConsumer, guiGraphics, i)) {
            original.call(includeMC, reverse, lineConsumer);
        }
    }

    @WrapOperation(method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/internal/BrandingControl;forEachAboveCopyrightLine(Ljava/util/function/BiConsumer;)V"))
    private void forEachLine(BiConsumer<Integer, String> lineConsumer, Operation<Void> original, @Local(argsOnly = true) GuiGraphicsExtractor guiGraphics, @Local(ordinal = 2) int i) {
        TitleScreen titleScreen = (TitleScreen) (Object) this;
        if (titleScreen instanceof CustomBranding customPosition) {
            Font font = args.get(0);
            String text = args.get(1);
            int x = args.get(2);
            int y = args.get(3);
            args.set(2, customPosition.getBrandingTextX(font, text, x));
            args.set(3, customPosition.getBrandingTextY(font, text, y));
        }
    }
}
