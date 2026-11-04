package com.aetherteam.aether.client.renderer.entity.layers;

import net.minecraft.client.renderer.rendertype.RenderTypes;
import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.entity.model.BipedBirdModel;
import com.aetherteam.aether.client.renderer.entity.state.BipedBirdRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.Identifier;

public class CockatriceMarkingsLayer<T extends BipedBirdRenderState, M extends BipedBirdModel<T>> extends EyesLayer<T, M> {
    private static final RenderType COCKATRICE_MARKINGS = RenderTypes.eyes(Identifier.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/cockatrice/cockatrice_emissive.png"));

    public CockatriceMarkingsLayer(RenderLayerParent<T, M> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public RenderType renderType() {
        return COCKATRICE_MARKINGS;
    }
}
