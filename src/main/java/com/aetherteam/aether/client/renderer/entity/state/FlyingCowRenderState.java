package com.aetherteam.aether.client.renderer.entity.state;

import com.aetherteam.aether.client.renderer.entity.state.SaddleableRenderState;

public class FlyingCowRenderState extends WingEntityRenderState implements SaddleableRenderState {
    public boolean saddle;

    @Override
    public boolean isSaddled() {
        return saddle;
    }
}
