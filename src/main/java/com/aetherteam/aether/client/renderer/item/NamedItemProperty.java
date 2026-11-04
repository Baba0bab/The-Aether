package com.aetherteam.aether.client.renderer.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.Nullable;

/**
 * Checks whether an item has been renamed to a specific name, ignoring case. Used for Easter Egg textures.
 *
 * @param name The {@link String} name to check for.
 */
public record NamedItemProperty(String name) implements ConditionalItemModelProperty {
    public static final MapCodec<NamedItemProperty> MAP_CODEC = Codec.STRING.fieldOf("name").xmap(NamedItemProperty::new, NamedItemProperty::name);

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext displayContext) {
        return stack.getHoverName().getString().equalsIgnoreCase(this.name);
    }

    @Override
    public MapCodec<NamedItemProperty> type() {
        return MAP_CODEC;
    }
}
