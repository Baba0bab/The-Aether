package com.aetherteam.aether.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.DimensionHooks;
import com.aetherteam.nitrogen.fabric.events.*;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityLevelChangeEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;


public class DimensionListener {
    /**
     * @see Aether#eventSetup()
     */
    public static void listen() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> onPlayerLogin(handler.getPlayer()));
        UseBlockCallback.EVENT.register(DimensionListener::onInteractWithPortalFrame);
        BlockEvents.NEIGHBOR_UPDATE.register((level, pos, state, notifiedSides, forceRedstoneUpdate, isCancelled) -> onWaterExistsInsidePortalFrame(level, pos, isCancelled));
        LevelEvents.AFTER.register(DimensionListener::onWorldTick);
        EntityEvents.BEFORE_DIMENSION_CHANGE.register(DimensionListener::onEntityTravelToDimension);
        ServerEntityLevelChangeEvents.AFTER_PLAYER_CHANGE_LEVEL.register((player, origin, destination) -> DimensionListener.onPlayerChangedDimension(player));
        PlayerTickEvents.AFTER.register(DimensionListener::onPlayerTraveling);
        EntitySleepEvents.ALLOW_SLEEPING.register((player, sleepingPos) -> DimensionListener.onTriedToSleep(player));
//        bus.addListener(DimensionListener::onAlterGround);
    }

    /**
     * @see DimensionHooks#startInAether(Player)
     */
    public static void onPlayerLogin(Player player) {
        DimensionHooks.startInAether(player);
    }

    /**
     * @see DimensionHooks#createPortal(Player, Level, BlockPos, Direction, ItemStack, InteractionHand)
     */
    public static InteractionResult onInteractWithPortalFrame(Player player, Level level, InteractionHand interactionHand, BlockHitResult hitResult) {
        BlockPos blockPos = hitResult.getBlockPos();
        Direction direction = hitResult.getDirection();
        ItemStack itemStack = player.getItemInHand(interactionHand);
        return DimensionHooks.createPortal(player, level, blockPos, direction, itemStack, interactionHand);
    }

    /**
     * @see DimensionHooks#detectWaterInFrame(LevelAccessor, BlockPos, BlockState, FluidState)
     */
    public static void onWaterExistsInsidePortalFrame(LevelAccessor level, BlockPos blockPos, CancellableCallback isCancelled) {
        BlockState blockState = level.getBlockState(blockPos);
        FluidState fluidState = level.getFluidState(blockPos);
        if (DimensionHooks.detectWaterInFrame(level, blockPos, blockState, fluidState)) {
            isCancelled.setCanceled(true);
        }
    }

    /**
     * @see DimensionHooks#tickTime(Level)
     * @see DimensionHooks#checkEternalDayConfig(Level)
     */
    public static void onWorldTick(Level level) {
        if (!level.isClientSide()) {
            DimensionHooks.tickTime(level);
            DimensionHooks.checkEternalDayConfig(level);
        }
    }

    /**
     * @see DimensionHooks#dimensionTravel(Entity, ResourceKey)
     * @see DimensionHooks#removePlayerAerbunny(Entity)
     */
    public static void onEntityTravelToDimension(Entity entity, ResourceKey<Level> dimension) {
        DimensionHooks.dimensionTravel(entity, dimension);
        DimensionHooks.removePlayerAerbunny(entity);
    }

    /**
     * @see DimensionHooks#remountPlayerAerbunny(Player)
     */
    public static void onPlayerChangedDimension(Player player) {
        DimensionHooks.remountPlayerAerbunny(player);
    }

    /**
     * @see DimensionHooks#travelling(Player)
     */
    public static void onPlayerTraveling(Player player) {
        DimensionHooks.travelling(player);
    }



    /**
     * @see DimensionHooks#isEternalDay(Player)
     */
    @Nullable
    public static Player.BedSleepingProblem onTriedToSleep(Player player) {
        if (DimensionHooks.isEternalDay(player)) {
            return Player.BedSleepingProblem.OTHER_PROBLEM;
        }

        return null;
    }

    /**
     * Prevents Aether Dirt from being replaced by Podzol.
     */
//    public static void onAlterGround(AlterGroundEvent event) {
//        TreeDecorator.Context context = event.getContext();
//        AlterGroundEvent.StateProvider provider = event.getStateProvider();
//        event.setStateProvider((rand, pos) -> {
//            MutableObject<BlockState> oldState = new MutableObject<>(); // Ground to replace.
//            BlockState attemptedState = provider.getState(rand, pos); // Ground to maybe replace with.
//            if (context.level().isStateAtPosition(pos, state -> {
//                if (state.is(AetherTags.Blocks.AETHER_DIRT)) {
//                    oldState.setValue(state);
//                    return true;
//                } else {
//                    return false;
//                }
//            })) {
//                return attemptedState.is(Blocks.PODZOL) ? oldState.getValue() : attemptedState; // Ground to actually replace with.
//            } else {
//                return attemptedState;
//            }
//        });
//    }
}
