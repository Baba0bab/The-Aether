package com.aetherteam.aether.item.miscellaneous;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class SliderSpawnEggItem extends SpawnEggItem {
    public SliderSpawnEggItem(Properties properties) {
        super(properties);
    }

    /**
     * [CODE COPY] - {@link SpawnEggItem#useOn(UseOnContext)}.<br><br>
     * Changed to round the X/Z values based on the click location.
     * This makes it so the slider will always spawn in the center of a 2x2 region based on what corner of a block the player is aiming.<br><br>
     * The position still needs to be aligned with Math.floor once the entity is spawned, so the Slider rounds it's X and Z coordinates down after spawning.
     *
     * @param context The {@link UseOnContext} of the usage interaction.
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        ItemStack itemStack = context.getItemInHand();
        EntityType<?> entityType = getType(itemStack);
        BlockPos blockPos = context.getClickedPos();
        if (entityType == null || !entityType.canSpawn(level) || level.getBlockEntity(blockPos) instanceof Spawner) {
            return super.useOn(context);
        } else if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            Direction direction = context.getClickedFace();
            BlockState blockState = level.getBlockState(blockPos);
            BlockPos relativePos;
            if (blockState.getCollisionShape(level, blockPos).isEmpty()) {
                relativePos = blockPos;
            } else {
                relativePos = blockPos.relative(direction);
            }
            Vec3 clickLoc = context.getClickLocation();
            BlockPos roundedPos = new BlockPos((int) Math.round(clickLoc.x()), relativePos.getY(), (int) Math.round(clickLoc.z()));

            if (entityType.spawn(serverLevel, itemStack, context.getPlayer(), roundedPos, EntitySpawnReason.SPAWN_ITEM_USE, false, !Objects.equals(blockPos, relativePos) && direction == Direction.UP) != null) {
                itemStack.consume(1, context.getPlayer());
                level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }
    }
}
