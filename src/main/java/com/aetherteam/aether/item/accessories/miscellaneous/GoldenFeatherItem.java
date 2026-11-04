package com.aetherteam.aether.item.accessories.miscellaneous;

import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.item.accessories.abilities.SlowFallAccessory;
import com.aetherteam.aetherfabric.accessories.SlotContext;
import net.minecraft.world.item.ItemStack;

public class GoldenFeatherItem extends AccessoryItem implements SlowFallAccessory {
    public GoldenFeatherItem(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext reference, ItemStack stack) {
        this.handleSlowFall(reference.entity());
    }

    @Override
    public boolean canWalkOnPowderedSnow(SlotContext reference, ItemStack stack) {
        return true;
    }
}
