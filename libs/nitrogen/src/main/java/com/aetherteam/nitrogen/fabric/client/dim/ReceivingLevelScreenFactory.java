package com.aetherteam.nitrogen.fabric.client.dim;

import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.multiplayer.LevelLoadTracker;

public interface ReceivingLevelScreenFactory {
    LevelLoadingScreen create(LevelLoadTracker loadTracker, LevelLoadingScreen.Reason reason);
}
