package com.aetherteam.aether.item.accessories.pendant;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.inventory.AetherAccessorySlots;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.item.accessories.SlotIdentifierHolder;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class PendantItem extends AccessoryItem implements SlotIdentifierHolder {
    protected Identifier PENDANT_LOCATION;

    public PendantItem(String pendantLocation, Holder<SoundEvent> pendantSound, Properties properties) {
        this(Identifier.fromNamespaceAndPath(Aether.MODID, pendantLocation), pendantSound, properties);
    }

    public PendantItem(Identifier pendantLocation, Holder<SoundEvent> pendantSound, Properties properties) {
        super(pendantSound, properties);
        this.setRenderTexture(pendantLocation.getNamespace(), pendantLocation.getPath());
    }

    public void setRenderTexture(String modId, String registryName) {
        this.PENDANT_LOCATION = Identifier.fromNamespaceAndPath(modId, "textures/models/accessory/pendant/" + registryName + "_accessory.png");
    }

    public Identifier getPendantTexture() {
        return this.PENDANT_LOCATION;
    }

    /**
     * @return {@link PendantItem}'s own identifier for its accessory slot,
     * using a static method as it is used in other conditions without access to an instance.
     */
    @Override
    public String getIdentifier() {
        return getStaticIdentifier();
    }

    public static String getStaticIdentifier() {
        return AetherAccessorySlots.getPendantSlotType();
    }
}
