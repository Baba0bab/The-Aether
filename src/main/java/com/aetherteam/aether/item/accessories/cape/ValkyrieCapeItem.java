package com.aetherteam.aether.item.accessories.cape;

import com.aetherteam.aether.item.accessories.abilities.SlowFallAccessory;
import com.aetherteam.aetherfabric.accessories.SlotContext;
import net.minecraft.world.item.ItemStack;

public class ValkyrieCapeItem extends CapeItem implements SlowFallAccessory {
    public ValkyrieCapeItem(Properties properties) {
        super("valkyrie_cape", properties);
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
