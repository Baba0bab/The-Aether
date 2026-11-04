package com.aetherteam.aether.client.renderer.entity.state;

import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class ParachuteRenderState extends EntityRenderState {
    public final BlockModelRenderState blockState = new BlockModelRenderState();
    public float yRot;
}
