package com.aetherteam.aether.loot.modifiers;

import net.minecraft.core.Holder;
import java.util.Optional;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.combat.abilities.weapon.SkyrootWeapon;
import com.aetherteam.nitrogen.fabric.loot.IGlobalLootModifier;
import com.aetherteam.nitrogen.fabric.loot.LootModifier;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class DoubleDropsModifier extends LootModifier {
    public static final MapCodec<DoubleDropsModifier> CODEC = RecordCodecBuilder.mapCodec((instance) -> LootModifier.codecStart(instance).apply(instance, DoubleDropsModifier::new));

    public DoubleDropsModifier(Optional<Holder<LootItemCondition>> conditions, int priority) {
        super(conditions, priority);
    }

    /**
     * Doubles mob drops if a mob is attacked with full strength with an item that implements {@link SkyrootWeapon} if the mob isn't tagged with {@link AetherTags.Entities#NO_SKYROOT_DOUBLE_DROPS} and the item isn't tagged with {@link AetherTags.Items#NO_SKYROOT_DOUBLE_DROPS}.
     *
     * @param lootStacks Result items from a loot table as an {@link ObjectArrayList} of {@link ItemStack}s.
     * @param context    The {@link LootContext}.
     * @return A new {@link ObjectArrayList} of {@link ItemStack}s that a loot table will give.
     */
    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> lootStacks, LootContext context) {
        Entity entity = context.getOptional(LootContextParams.DIRECT_ATTACKING_ENTITY);
        Entity target = context.getOptional(LootContextParams.THIS_ENTITY);
        ObjectArrayList<ItemStack> newStacks = new ObjectArrayList<>(lootStacks);
        if (entity instanceof LivingEntity livingEntity && target != null) {
            if (EquipmentUtil.isFullStrength(livingEntity) && livingEntity.getMainHandItem().getItem() instanceof SkyrootWeapon && !target.is(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS)) {
                for (ItemStack stack : lootStacks) {
                    if (!stack.is(AetherTags.Items.NO_SKYROOT_DOUBLE_DROPS)) {
                        newStacks.add(stack);
                    }
                }
            }
        }
        return newStacks;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return DoubleDropsModifier.CODEC;
    }
}
