package com.aetherteam.aether.attachment;

import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.ServerClockManager;
import net.minecraft.world.clock.ClockInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Holder;
import com.mojang.serialization.MapCodec;
import net.minecraft.SharedConstants;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.network.packet.AetherTimeSyncPacket;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Capability class to store data for the Aether's custom day/night cycle.
 * This capability only has an effect on levels where the dimension type's effects are set to the Aether's.
 *
 * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherTimeHooks
 */
public class AetherTimeAttachment implements INBTSynchable {
    public static final long DAY_LENGTH = SharedConstants.TICKS_PER_GAME_DAY;
    public static final long NOON = DAY_LENGTH / 4;
    private static final long MIGRATED = -2;
    private static int ticksPerDayMultiplier = -1;
    private long dayTime = -1;
    private boolean isEternalDay = true;
    private boolean shouldWait = false;

    /**
     * Stores the following methods as able to be synced between client and server and vice-versa.
     */
    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
        Map.entry("setEternalDay", Triple.of(Type.BOOLEAN, (object) -> this.setEternalDay((boolean) object), this::isEternalDay)),
        Map.entry("setShouldWait", Triple.of(Type.BOOLEAN, (object) -> this.setShouldWait((boolean) object), this::getShouldWait))
    );

    public static final MapCodec<AetherTimeAttachment> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.LONG.fieldOf("day_time").forGetter(AetherTimeAttachment::getDayTime),
            Codec.BOOL.fieldOf("eternal_day").forGetter(AetherTimeAttachment::isEternalDay),
            Codec.BOOL.fieldOf("should_wait").forGetter(AetherTimeAttachment::getShouldWait)
    ).apply(instance, AetherTimeAttachment::new));

    protected AetherTimeAttachment() {

    }

    private AetherTimeAttachment(long time, boolean eternalDay, boolean shouldWait) {
        this.setDayTime(time);
        this.setEternalDay(eternalDay);
        this.setShouldWait(shouldWait);
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    /**
     * Handles the Aether's day/night cycle, which uses the dimension's default {@link WorldClock}.<br><br>
     * The clock is slowed down for the Aether's longer days, kept at noon during eternal day, and kept in sync with the Overworld if configured.
     *
     * @param level The {@link ServerLevel}.
     */
    public void tickTime(ServerLevel level) {
        Holder<WorldClock> clock = level.dimensionType().defaultClock().orElse(null);
        if (clock == null) {
            return;
        }
        ServerClockManager clockManager = level.clockManager();
        ClockInstance instance = clockManager.getInstance(clock);
        long clockTime = instance.totalTicks();

        if (this.getDayTime() != MIGRATED) { // Initializes the clock for new worlds, or converts the time from older worlds.
            if (this.getDayTime() == -1) {
                if (!AetherConfig.SERVER.disable_eternal_day.get()) {
                    clockManager.setTotalTicks(clock, NOON);
                }
            } else {
                clockManager.setTotalTicks(clock, this.getDayTime() * DAY_LENGTH / getTicksPerDay());
            }
            this.setDayTime(MIGRATED);
            clockTime = clockManager.getInstance(clock).totalTicks();
        }

        configsChanged(clockTime);
        if (this.isEternalDay() || this.getShouldWait()) {
            if (!instance.isPaused()) {
                clockManager.setPaused(clock, true);
            }
            long timeOfDay = Math.floorMod(clockTime, DAY_LENGTH);
            if (timeOfDay != NOON) { // Moves the time towards noon.
                long tempTime = timeOfDay > DAY_LENGTH * 0.75 ? timeOfDay - DAY_LENGTH : timeOfDay;
                long target = Mth.clamp(NOON - tempTime, -10, 10);
                clockManager.setTotalTicks(clock, clockTime + target);
            }
            if (this.getShouldWait()) {
                if (AetherConfig.SERVER.sync_aether_time.get()) {
                    if (Math.floorMod(level.getOverworldClockTime(), DAY_LENGTH) == Math.floorMod(clockManager.getInstance(clock).totalTicks(), DAY_LENGTH)) {
                        this.setSynched(-1, Direction.DIMENSION, "setShouldWait", false, level);
                    }
                } else {
                    this.setSynched(-1, Direction.DIMENSION, "setShouldWait", false, level);
                }
            }
        } else {
            if (instance.isPaused()) {
                clockManager.setPaused(clock, false);
            }
            if (this.isTimeSynced()) {
                if (instance.rate() != 1.0F) {
                    clockManager.setRate(clock, 1.0F);
                }
                long overworldTime = level.getOverworldClockTime();
                if (clockTime != overworldTime) {
                    clockManager.setTotalTicks(clock, overworldTime);
                }
            } else {
                float rate = 1.0F / getTicksPerDayMultiplier();
                if (instance.rate() != rate) {
                    clockManager.setRate(clock, rate);
                }
            }
        }
    }

    /**
     * @param level The {@link Level}.
     * @return The {@link Long} time of the dimension's default clock.
     */
    public static long getClockTime(Level level) {
        return level.dimensionType().defaultClock().map((clock) -> level.clockManager().getInstance(clock).totalTicks()).orElse(0L);
    }

    /**
     * Sets the time of the dimension's default clock.
     *
     * @param level The {@link ServerLevel}.
     * @param time  The {@link Long} time to set.
     */
    public static void setClockTime(ServerLevel level, long time) {
        level.dimensionType().defaultClock().ifPresent((clock) -> level.clockManager().setTotalTicks(clock, time));
    }

    private long configsChanged(long time) {
        int multiplier = configMultiplier();
        if (ticksPerDayMultiplier != multiplier) {
            ticksPerDayMultiplier = multiplier;
        }
        return time;
    }

    /**
     * Sends the eternal day value to the client dimension.
     */
    public void updateEternalDay(Level level) {
        this.setSynched(-1, Direction.DIMENSION, "setEternalDay", this.isEternalDay, level);
    }

    /**
     * Sends the eternal day value to the client player.
     */
    public void updateEternalDay(ServerPlayer player) {
        this.setSynched(player.getId(), Direction.PLAYER, "setEternalDay", this.isEternalDay, player);
    }

    public void setDayTime(long time) {
        this.dayTime = time;
    }

    /**
     * @return A {@link Long} for the time in the Aether.
     */
    public long getDayTime() {
        return this.dayTime;
    }

    public void setEternalDay(boolean isEternalDay) {
        this.isEternalDay = isEternalDay;
    }

    /**
     * @return Whether eternal day is active, as a {@link Boolean}.
     */
    public boolean isEternalDay() {
        return this.isEternalDay;
    }

    public void setShouldWait(boolean shouldWait) {
        this.shouldWait = shouldWait;
    }

    /**
     * @return Whether there should be a wait to disable eternal day, as a {@link Boolean}.
     */
    public boolean getShouldWait() {
        return this.shouldWait;
    }

    public boolean isTimeSynced() {
        return AetherConfig.SERVER.sync_aether_time.get() && !this.isEternalDay() && !this.getShouldWait();
    }

    @Override
    public SyncPacket getSyncPacket(int entityID, String key, Type type, Object value) {
        return new AetherTimeSyncPacket(key, type, value);
    }

    public static int getTicksPerDayMultiplier() {
        if (ticksPerDayMultiplier < 0) {
            ticksPerDayMultiplier = AetherConfig.SERVER.normal_length_aether_time.get() || AetherConfig.SERVER.sync_aether_time.get() ? 1 : 3;
        }
        return ticksPerDayMultiplier;
    }

    public static int getTicksPerDay() {
        return SharedConstants.TICKS_PER_GAME_DAY * getTicksPerDayMultiplier(); // Time in ticks of how long a day/night cycle lasts.
    }

    public static int configMultiplier() {
        return AetherConfig.SERVER.normal_length_aether_time.get() || AetherConfig.SERVER.sync_aether_time.get() ? 1 : 3;
    }
}
