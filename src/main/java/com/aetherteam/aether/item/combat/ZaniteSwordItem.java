package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.combat.abilities.weapon.ZaniteWeapon;
import com.aetherteam.nitrogen.fabric.events.ItemAttributeModifierHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class ZaniteSwordItem extends Item implements ZaniteWeapon {
    public ZaniteSwordItem(Properties properties) {
        super(properties.sword(AetherItemTiers.ZANITE, 3.0F, -2.4F));
    }

    /**
     *
     * @see Aether#eventSetup()
     * @see ZaniteWeapon#increaseDamage(ItemAttributeModifiers, ItemStack)
     */
    public static void onModifyAttributes(ItemStack itemStack, ItemAttributeModifierHelper event) {
        ItemAttributeModifiers modifiers = event.getDefaultModifiers();
        if (itemStack.getItem() instanceof ZaniteWeapon zaniteWeapon) {
            ItemAttributeModifiers.Entry attributeEntry = zaniteWeapon.increaseDamage(modifiers, itemStack);
            event.replaceModifier(attributeEntry.attribute(), attributeEntry.modifier(), attributeEntry.slot());
        }
    }
}
