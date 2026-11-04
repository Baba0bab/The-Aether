package com.aetherteam.aether.item.accessories.gloves;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.inventory.AetherAccessorySlots;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.item.accessories.SlotIdentifierHolder;
import java.util.function.BiConsumer;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import com.aetherteam.aetherfabric.accessories.SlotContext;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;

public class GlovesItem extends AccessoryItem implements SlotIdentifierHolder {
    public static final Identifier BASE_PUNCH_DAMAGE_ID = Identifier.fromNamespaceAndPath(Aether.MODID, "base_punch_damage");
    protected final ArmorMaterial material;
    protected final double damage;
    protected Identifier GLOVES_TEXTURE;

    public GlovesItem(ArmorMaterial material, double punchDamage, String glovesName, Holder<SoundEvent> glovesSound, Properties properties) {
        this(material, punchDamage, Identifier.fromNamespaceAndPath(Aether.MODID, glovesName), glovesSound, properties.enchantable(material.enchantmentValue()).repairable(material.repairIngredient()));
    }

    public GlovesItem(ArmorMaterial material, double punchDamage, Identifier glovesName, Holder<SoundEvent> glovesSound, Properties properties) {
        super(glovesSound, properties);
        this.material = material;
        this.damage = punchDamage;
        this.setRenderTexture(glovesName.getNamespace(), glovesName.getPath());
    }

    @Override
    public void addAttributeModifiers(ItemStack stack, BiConsumer<Holder<Attribute>, AttributeModifier> consumer) {
        consumer.accept(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_PUNCH_DAMAGE_ID, this.damage, AttributeModifier.Operation.ADD_VALUE));
    }

    public ArmorMaterial getMaterial() {
        return this.material;
    }

    public double getDamage() {
        return this.damage;
    }

    public void setRenderTexture(String modId, String registryName) {
        this.GLOVES_TEXTURE = Identifier.fromNamespaceAndPath(modId, "textures/models/accessory/gloves/" + registryName + "_accessory.png");
    }

    public Identifier getGlovesTexture() {
        return this.GLOVES_TEXTURE;
    }

    /**
     * @return {@link GlovesItem}'s own identifier for its accessory slot,
     * using a static method as it is used in other conditions without access to an instance.
     */
    @Override
    public String getIdentifier() {
        return getStaticIdentifier();
    }

    public static String getStaticIdentifier() {
        return AetherAccessorySlots.getGlovesSlotType();
    }
}
