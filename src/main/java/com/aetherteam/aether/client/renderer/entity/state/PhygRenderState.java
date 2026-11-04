package com.aetherteam.aether.client.renderer.entity.state;

import org.jetbrains.annotations.Nullable;
import com.aetherteam.aether.client.renderer.entity.state.SaddleableRenderState;

public class PhygRenderState extends WingEntityRenderState implements SaddleableRenderState {
    @Nullable
    public String customName;
    public boolean saddle;

    @Override
    public boolean isSaddled() {
        return saddle;
    }
}
