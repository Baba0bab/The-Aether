package com.aetherteam.aether.data.resources.registries;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.material.VanillaMaterialConditions;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.material.MaterialRules;
import net.minecraft.world.level.levelgen.material.condition.MaterialCondition;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;

public class AetherMaterialRules {
    public static final ResourceKey<MaterialRule> SKYLANDS = createKey("skylands");

    private static ResourceKey<MaterialRule> createKey(String name) {
        return ResourceKey.create(Registries.MATERIAL_RULE, Identifier.fromNamespaceAndPath(Aether.MODID, name));
    }

    /**
     * Places Aether Grass on the surface of islands, and Aether Dirt underneath it.
     */
    public static void bootstrap(BootstrapContext<MaterialRule> context) {
        HolderGetter<MaterialCondition> conditions = context.lookup(Registries.MATERIAL_CONDITION);
        MaterialRule grassBlock = MaterialRules.state(AetherBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true));
        MaterialRule dirt = MaterialRules.state(AetherBlocks.AETHER_DIRT.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true));
        MaterialRule surface = MaterialRules.sequence(MaterialRules.ifTrue(MaterialRules.waterBlockCheck(-1, 0), grassBlock), dirt);
        context.register(SKYLANDS, MaterialRules.sequence(
            MaterialRules.ifTrue(MaterialRules.getCondition(conditions, VanillaMaterialConditions.ON_FLOOR), surface),
            MaterialRules.ifTrue(MaterialRules.getCondition(conditions, VanillaMaterialConditions.UNDER_FLOOR), dirt)
        ));
    }
}
