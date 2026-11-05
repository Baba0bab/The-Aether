package com.aetherteam.aether.integration.jei.categories;

import mezz.jei.api.gui.builder.ITooltipBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;


public interface BiomeTooltip {
    /**
     * Adds tooltip information about which biomes a recipe applies in.
     *
     * @param biomeKey The {@link Biome} {@link ResourceKey} for the recipe, if there is one.
     * @param biomeTag The {@link Biome} {@link TagKey} for the recipe, if there is one.
     * @param tooltip  The {@link ITooltipBuilder} for the tooltip.
     */
    default void populateBiomeInformation(@Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, ITooltipBuilder tooltip) {
        if (Minecraft.getInstance().level != null) {
            if (biomeKey != null || biomeTag != null) {
                tooltip.add(Component.translatable("gui.aether.jei.biome.tooltip").withStyle(ChatFormatting.GRAY));
                if (biomeKey != null) {
                    tooltip.add(Component.translatable("gui.aether.jei.biome.tooltip.biome").withStyle(ChatFormatting.DARK_GRAY));
                    tooltip.add(Component.literal(biomeKey.identifier().toString()).withStyle(ChatFormatting.DARK_GRAY));
                } else {
                    tooltip.add(Component.translatable("gui.aether.jei.biome.tooltip.tag").withStyle(ChatFormatting.DARK_GRAY));
                    tooltip.add(Component.literal("#" + biomeTag.location()).withStyle(ChatFormatting.DARK_GRAY));
                    tooltip.add(Component.translatable("gui.aether.jei.biome.tooltip.biomes").withStyle(ChatFormatting.DARK_GRAY));
                    Minecraft.getInstance().level.registryAccess().lookupOrThrow(Registries.BIOME).getTagOrEmpty(biomeTag).forEach((biomeHolder) -> biomeHolder.unwrapKey().ifPresent((key) -> tooltip.add(Component.literal(key.identifier().toString()).withStyle(ChatFormatting.DARK_GRAY))));
                }
            }
        }
    }
}
