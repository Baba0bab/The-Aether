package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.renderer.AetherRenderStateModifiers;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.Identifier;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.layers.WingsLayer;
import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WingsLayer.class)
public class ElytraLayerMixin {
    /**
     * Used to change the elytra texture on an armor stand based on the equipped cape.
     *
     * @param original The original {@link Identifier} of the texture for the elytra on this armor stand.
     * @param stack  The elytra {@link ItemStack}.
     * @param entity The {@link LivingEntity} wearing the elytra.
     * @return If the armor stand has an equipped cape, the cape texture, else returns the original texture.
     */
    @ModifyReturnValue(at = @At("RETURN"), method = "getPlayerElytraTexture(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)Lnet/minecraft/resources/Identifier;", remap = false)
    private static <S extends HumanoidRenderState, M extends EntityModel<S>> Identifier getPlayerElytraTexture(Identifier original, @Local(ordinal = 0, argsOnly = true) S renderState) {
        if (renderState instanceof ArmorStandRenderState armorStand) {
            ItemStack capeStack = armorStand.getData(AetherRenderStateModifiers.IS_CAPE_VISIBLE);
            if (capeStack != null && !capeStack.isEmpty()) {
                Identifier texture = AetherMixinHooks.getCapeTexture(capeStack);
                if (texture != null) {
                    return texture;
                }
            }
        }
        return original;
    }
}
