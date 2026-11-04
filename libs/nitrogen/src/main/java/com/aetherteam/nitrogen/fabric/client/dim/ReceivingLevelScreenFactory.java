package com.aetherteam.nitrogen.fabric.client.dim;

import net.minecraft.client.gui.screens.LevelLoadingScreen;

import java.util.function.BooleanSupplier;

public interface ReceivingLevelScreenFactory {
    LevelLoadingScreen create(BooleanSupplier supplier, LevelLoadingScreen.Reason reason);
}
