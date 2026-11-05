package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.gui.screen.menu.CustomBranding;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @ModifyArgs(method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;text(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"))
    private void drawString(Args args) {
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
