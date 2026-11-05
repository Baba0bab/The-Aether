package com.aetherteam.aether.network.packet.serverbound;

import net.minecraft.server.permissions.Permissions;
import com.aetherteam.aether.attachment.AetherTimeAttachment;
import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.command.SunAltarWhitelist;
import com.aetherteam.nitrogen.fabric.network.handling.IPayloadContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

/**
 * Updates the time on the server, then updates that time for all players in the Aether.
 */
public record SunAltarUpdatePacket(long dayTime, int timeScale) implements CustomPacketPayload {
    public static final Type<SunAltarUpdatePacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Aether.MODID, "update_sun_altar"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SunAltarUpdatePacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_LONG,
        SunAltarUpdatePacket::dayTime,
        ByteBufCodecs.INT,
        SunAltarUpdatePacket::timeScale,
        SunAltarUpdatePacket::new);

    @Override
    public Type<SunAltarUpdatePacket> type() {
        return TYPE;
    }

    public static void execute(SunAltarUpdatePacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.level() instanceof ServerLevel level && (!AetherConfig.SERVER.sun_altar_whitelist.get() || playerEntity.permissions().hasPermission(Permissions.COMMANDS_OWNER) || SunAltarWhitelist.INSTANCE.isWhiteListed(playerEntity.nameAndId()))) {
            if (AetherConfig.SERVER.sun_altar_dimensions.get().contains(level.dimension().identifier().toString())) {
                // Get how many days have passed in the world first, then add to it.
                long dayBase = AetherTimeAttachment.getClockTime(level) / AetherTimeAttachment.DAY_LENGTH;
                long dayTime = (dayBase * AetherTimeAttachment.DAY_LENGTH) + (payload.dayTime() * AetherTimeAttachment.DAY_LENGTH / payload.timeScale());
                // Set the time.
                AetherTimeAttachment.setClockTime(level, dayTime);
            }
        }
    }
}
