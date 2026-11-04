package com.aetherteam.aether.mixin.mixins.client.accessor;

import net.minecraft.client.gui.Hud;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Hud.HeartType.class)
public interface HeartTypeAccessor {
    @Invoker
    static Hud.HeartType callForPlayer(Player player) {
        throw new AssertionError();
    }
}