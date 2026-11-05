package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.FireMinionModel;
import com.aetherteam.aether.entity.monster.dungeon.FireMinion;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;

public class FireMinionRenderer extends MobRenderer<FireMinion, LivingEntityRenderState, FireMinionModel<LivingEntityRenderState>> {
    private static final Identifier SUN_SPIRIT_TEXTURE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/sun_spirit/sun_spirit.png");
    private static final Identifier FROZEN_SPIRIT_TEXTURE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/sun_spirit/frozen_sun_spirit.png");
    private static final RenderStateDataKey<Boolean> FROZEN = RenderStateDataKey.create(() -> "aether:frozen_fire_minion");

    public FireMinionRenderer(EntityRendererProvider.Context context) {
        super(context, new FireMinionModel<>(context.bakeLayer(AetherModelLayers.FIRE_MINION)), 0.8F);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void extractRenderState(FireMinion entity, LivingEntityRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        String name = entity.getCustomName() != null ? entity.getCustomName().getString() : "";
        reusedState.setData(FROZEN, name.equals("JorgeQ") || name.equals("Jorge_SunSpirit"));
    }

    @Override
    protected void scale(LivingEntityRenderState renderState, PoseStack poseStack) {
        poseStack.translate(0.0, 0.35, 0.0);
    }

    /**
     * If the Fire Minion has specific custom names, it will use the frozen texture as an Easter Egg.
     * Otherwise, it uses the normal texture.
     *
     * @param renderState The {@link LivingEntityRenderState} for the entity.
     * @return The texture {@link Identifier}.
     */
    @Override
    public Identifier getTextureLocation(LivingEntityRenderState renderState) {
        return renderState.getDataOrDefault(FROZEN, false) ? FROZEN_SPIRIT_TEXTURE : SUN_SPIRIT_TEXTURE;
    }

    @Override
    protected int getBlockLightLevel(FireMinion fireMinion, BlockPos pos) {
        return 15;
    }

    @Override
    protected int getSkyLightLevel(FireMinion fireMinion, BlockPos pos) {
        return 15;
    }
}
