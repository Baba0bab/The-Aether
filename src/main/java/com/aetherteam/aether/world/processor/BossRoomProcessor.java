package com.aetherteam.aether.world.processor;

import com.aetherteam.nitrogen.fabric.level.EntityStructureProcessor;

import net.minecraft.world.phys.Vec3;
import net.minecraft.nbt.NbtOps;
import com.aetherteam.nitrogen.entity.BossRoomTracker;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.ArrayList;

/**
 * This processor will give every boss mob in the template the bounding box of the room to use for its dungeon tracker.
 */
public class BossRoomProcessor implements StructureProcessor, EntityStructureProcessor {
    public static final BossRoomProcessor INSTANCE = new BossRoomProcessor();

    public static final MapCodec<BossRoomProcessor> CODEC = MapCodec.unit(BossRoomProcessor.INSTANCE);

    @Override
    public StructureTemplate.StructureEntityInfo nitrogen_fabric$processEntity(LevelReader level, BlockPos seedPos, StructureTemplate.StructureEntityInfo rawEntityInfo, StructureTemplate.StructureEntityInfo entityInfo, StructurePlaceSettings placementSettings, StructureTemplate template) {
        BoundingBox boundingBox = template.getBoundingBox(placementSettings, seedPos);
        BossRoomTracker tracker = new BossRoomTracker(
                entityInfo.pos,
                new Vec3(boundingBox.minX(), boundingBox.minY(), boundingBox.minZ()),
                new Vec3(boundingBox.maxX() + 1, boundingBox.maxY() + 1, boundingBox.maxZ() + 1),
                new ArrayList<>());
        entityInfo.nbt.put("Dungeon", BossRoomTracker.CODEC.encodeStart(NbtOps.INSTANCE, tracker).getOrThrow());
        return entityInfo;
    }

    @Override
    public MapCodec<BossRoomProcessor> codec() {
        return CODEC;
    }
}
