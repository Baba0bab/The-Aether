package com.aetherteam.aether.block.portal;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Based on {@link net.minecraft.world.level.portal.PortalShape}.
 */
public class AetherPortalShape {
    private static final BlockBehaviour.StatePredicate FRAME = (state, level, pos) -> state.is(AetherTags.Blocks.AETHER_PORTAL_BLOCKS);
    private final Direction.Axis axis;
    private final Direction rightDir;
    private final int numPortalBlocks;
    private final BlockPos bottomLeft;
    private final int height;
    private final int width;

    public static Optional<AetherPortalShape> findEmptyAetherPortalShape(LevelAccessor level, BlockPos bottomLeft, Direction.Axis axis) {
        return findAetherPortalShape(level, bottomLeft, (shape) -> shape.isValid() && shape.numPortalBlocks == 0, axis);
    }

    public static Optional<AetherPortalShape> findAetherPortalShape(LevelAccessor level, BlockPos bottomLeft, Predicate<AetherPortalShape> predicate, Direction.Axis axis) {
        Optional<AetherPortalShape> optional = Optional.of(findAnyShape(level, bottomLeft, axis)).filter(predicate);
        if (optional.isPresent()) {
            return optional;
        } else {
            Direction.Axis directionAxis = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
            return Optional.of(findAnyShape(level, bottomLeft, directionAxis)).filter(predicate);
        }
    }

    public static AetherPortalShape findAnyShape(BlockGetter level, BlockPos pos, Direction.Axis axis) {
        Direction direction = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        BlockPos blockpos = calculateBottomLeft(level, direction, pos);
        if (blockpos == null) {
            return new AetherPortalShape(axis, 0, direction, pos, 0, 0);
        } else {
            int i = calculateWidth(level, blockpos, direction);
            if (i == 0) {
                return new AetherPortalShape(axis, 0, direction, blockpos, 0, 0);
            } else {
                MutableInt mutableint = new MutableInt();
                int j = calculateHeight(level, blockpos, direction, i, mutableint);
                return new AetherPortalShape(axis, mutableint.getValue(), direction, blockpos, i, j);
            }
        }
    }

    @Nullable
    private static BlockPos calculateBottomLeft(BlockGetter level, Direction rightDir, BlockPos pos) {
        for (int i = Math.max(level.getMinY(), pos.getY() - 21); pos.getY() > i && isEmpty(level.getBlockState(pos.below())); pos = pos.below()) { }
        Direction direction = rightDir.getOpposite();
        int j = getDistanceUntilEdgeAboveFrame(level, pos, direction) - 1;
        return j < 0 ? null : pos.relative(direction, j);
    }

    private static int calculateWidth(BlockGetter level, BlockPos bottomLeft, Direction rightDir) {
        int i = getDistanceUntilEdgeAboveFrame(level, bottomLeft, rightDir);
        return i >= 2 && i <= 21 ? i : 0;
    }

    private static int getDistanceUntilEdgeAboveFrame(BlockGetter level, BlockPos pos, Direction direction) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (int i = 0; i <= 21; ++i) {
            mutablePos.set(pos).move(direction, i);
            BlockState blockState = level.getBlockState(mutablePos);
            if (!isEmpty(blockState)) {
                if (FRAME.test(blockState, level, mutablePos)) {
                    return i;
                }
                break;
            }
            BlockState belowState = level.getBlockState(mutablePos.move(Direction.DOWN));
            if (!FRAME.test(belowState, level, mutablePos)) {
                break;
            }
        }
        return 0;
    }

    private static int calculateHeight(BlockGetter level, BlockPos bottomLeft, Direction rightDir, int width, MutableInt numPortalBlocks) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        int i = getDistanceUntilTop(level, mutablePos, rightDir, width, bottomLeft, numPortalBlocks);
        return i >= 3 && i <= 21 && hasTopFrame(level, mutablePos, i, width, bottomLeft, rightDir) ? i : 0;
    }

    private static boolean hasTopFrame(BlockGetter level, BlockPos.MutableBlockPos mutablePos, int amount, int width, BlockPos bottomLeft, Direction rightDir) {
        for (int i = 0; i < width; ++i) {
            BlockPos.MutableBlockPos movedPos = mutablePos.set(bottomLeft).move(Direction.UP, amount).move(rightDir, i);
            if (!FRAME.test(level.getBlockState(movedPos), level, movedPos)) {
                return false;
            }
        }
        return true;
    }

    private static int getDistanceUntilTop(BlockGetter level, BlockPos.MutableBlockPos mutablePos, Direction rightDir, int width, BlockPos bottomLeft, MutableInt numPortalBlocks) {
        for (int i = 0; i < 21; ++i) {
            mutablePos.set(bottomLeft).move(Direction.UP, i).move(rightDir, -1);
            if (!FRAME.test(level.getBlockState(mutablePos), level, mutablePos)) {
                return i;
            }

            mutablePos.set(bottomLeft).move(Direction.UP, i).move(rightDir, width);
            if (!FRAME.test(level.getBlockState(mutablePos), level, mutablePos)) {
                return i;
            }

            for (int j = 0; j < width; ++j) {
                mutablePos.set(bottomLeft).move(Direction.UP, i).move(rightDir, j);
                BlockState blockState = level.getBlockState(mutablePos);
                if (!isEmpty(blockState)) {
                    return i;
                }
                if (blockState.is(AetherBlocks.AETHER_PORTAL.get())) {
                    numPortalBlocks.increment();
                }
            }
        }

        return 21;
    }

    public AetherPortalShape(Direction.Axis axis, int numPortalBlocks, Direction rightDir, BlockPos bottomLeft, int width, int height) {
        this.axis = axis;
        this.numPortalBlocks = numPortalBlocks;
        this.rightDir = rightDir;
        this.bottomLeft = bottomLeft;
        this.width = width;
        this.height = height;
    }

    private static boolean isEmpty(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER) || state.is(AetherBlocks.AETHER_PORTAL.get());
    }

    public boolean isValid() {
        return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
    }

    public void createPortalBlocks(LevelAccessor level) {
        BlockState blockState = AetherBlocks.AETHER_PORTAL.get().defaultBlockState().setValue(NetherPortalBlock.AXIS, this.axis);
        BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1)).forEach((pos) -> level.setBlock(pos, blockState, 2 | 16));
    }

    public boolean isComplete() {
        return this.isValid() && this.numPortalBlocks == this.width * this.height;
    }

    public static Vec3 findCollisionFreePosition(Vec3 pos, ServerLevel level, Entity entity, EntityDimensions dimensions) {
        if (!(dimensions.width() > 4.0F) && !(dimensions.height() > 4.0F)) {
            double y = (double) dimensions.height() / 2.0;
            Vec3 vec3 = pos.add(0.0, y, 0.0);
            VoxelShape shape = Shapes.create(
                AABB.ofSize(vec3, dimensions.width(), 0.0, dimensions.width()).expandTowards(0.0, 1.0, 0.0).inflate(1.0E-6)
            );
            Optional<Vec3> optional = level.findFreePosition(
                entity, shape, vec3, dimensions.width(), dimensions.height(), dimensions.width()
            );
            Optional<Vec3> optionalPos = optional.map(vec -> vec.subtract(0.0, y, 0.0));
            return optionalPos.orElse(pos);
        } else {
            return pos;
        }
    }
}
