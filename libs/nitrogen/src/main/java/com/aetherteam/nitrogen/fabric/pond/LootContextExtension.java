package com.aetherteam.nitrogen.fabric.pond;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

public interface LootContextExtension {

    @Nullable
    Identifier nitrogen_fabric$getTableId();

    void nitrogen_fabric$pushTableId(Identifier tableId);

    void nitrogen_fabric$popTableId();
}
