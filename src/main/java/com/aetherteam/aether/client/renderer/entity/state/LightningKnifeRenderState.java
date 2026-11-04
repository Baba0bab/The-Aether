package com.aetherteam.aether.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class LightningKnifeRenderState extends EntityRenderState {
    public final ItemStackRenderState item = new ItemStackRenderState();
    public float xRot;
    public float yRot;
}
