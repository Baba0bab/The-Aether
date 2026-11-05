package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.PhygHaloLayer;
import com.aetherteam.aether.client.renderer.entity.layers.QuadrupedWingsLayer;
import com.aetherteam.aether.client.renderer.entity.model.HaloModel;
import com.aetherteam.aether.client.renderer.entity.model.QuadrupedWingsModel;
import com.aetherteam.aether.client.renderer.entity.state.PhygRenderState;
import com.aetherteam.aether.entity.passive.Phyg;
import net.minecraft.client.model.animal.pig.PigModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.aetherteam.aether.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.Identifier;

public class PhygRenderer extends AgeableMobRenderer<Phyg, PhygRenderState, PigModel> {
    private static final Identifier PHYG_TEXTURE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/phyg/phyg.png");
    private static final Identifier PHYG_WINGS_TEXTURE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/phyg/phyg_wings.png");
    private static final Identifier PHYG_SADDLE_TEXTURE = Identifier.withDefaultNamespace("textures/entity/equipment/pig_saddle/saddle.png");

    public PhygRenderer(EntityRendererProvider.Context context) {
        super(context, new PigModel(context.bakeLayer(AetherModelLayers.PHYG)), new PigModel(context.bakeLayer(AetherModelLayers.PHYG_BABY)), 0.7F);
        this.addLayer(new QuadrupedWingsLayer<>(this, new QuadrupedWingsModel<>(context.bakeLayer(AetherModelLayers.PHYG_WINGS)), PHYG_WINGS_TEXTURE));
        this.addLayer(new SaddleLayer<>(this, new PigModel(context.bakeLayer(AetherModelLayers.PHYG_SADDLE)), new PigModel(context.bakeLayer(AetherModelLayers.PHYG_BABY_SADDLE)), PHYG_SADDLE_TEXTURE));
        this.addLayer(new PhygHaloLayer(this, new HaloModel<>(context.bakeLayer(AetherModelLayers.PHYG_HALO))));
    }

    @Override
    public PhygRenderState createRenderState() {
        return new PhygRenderState();
    }

    @Override
    public void extractRenderState(Phyg phyg, PhygRenderState reusedState, float partialTick) {
        super.extractRenderState(phyg, reusedState, partialTick);
        reusedState.customName = phyg.getCustomName() != null ? phyg.getCustomName().getString() : null;
        QuadrupedWingsModel.extractWings(phyg, reusedState);
        reusedState.saddle = phyg.isSaddled();
    }

    @Override
    public Identifier getTextureLocation(PhygRenderState renderState) {
        return PHYG_TEXTURE;
    }
}
