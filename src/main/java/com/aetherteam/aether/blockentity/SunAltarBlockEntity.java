package com.aetherteam.aether.blockentity;

import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.core.component.DataComponentGetter;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import com.aetherteam.aether.Aether;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SunAltarBlockEntity extends BlockEntity implements Nameable {
    @Nullable
    private Component name;

    public SunAltarBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherBlockEntityTypes.SUN_ALTAR.get(), pos, blockState);
    }

    @Nullable
    @Override
    public Component getName() {
        return this.name != null ? this.name : Component.translatable("menu." + Aether.MODID + ".sun_altar");
    }

    public void setCustomName(@Nullable Component name) {
        this.name = name;
    }

    @Nullable
    @Override
    public Component getCustomName() {
        return this.name;
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter componentInput) {
        super.applyImplicitComponents(componentInput);
        this.name = componentInput.get(DataComponents.CUSTOM_NAME);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(DataComponents.CUSTOM_NAME, this.name);
    }

    /**
     * Warning for "deprecation" is suppressed because the method is fine to override.
     */
    @SuppressWarnings("deprecation")
    @Override
    public void removeComponentsFromTag(ValueOutput tag) {
        tag.discard("CustomName");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
        return this.saveWithoutMetadata(lookupProvider);
    }

    @Override
    public void handleUpdateTag(ValueInput tag) {
        this.loadAdditional(tag);
    }

    @Override
    protected void saveAdditional(ValueOutput tag) {
        super.saveAdditional(tag);
        tag.storeNullable("CustomName", ComponentSerialization.CODEC, this.name);
    }

    @Override
    public void loadAdditional(ValueInput tag) {
        super.loadAdditional(tag);
        this.name = parseCustomNameSafe(tag, "CustomName");
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection connection, ValueInput valueInput) {
        this.handleUpdateTag(valueInput);
    }
}
