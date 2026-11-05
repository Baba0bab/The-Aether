package com.aetherteam.aether.perk.data;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.perk.types.DeveloperGlow;
import com.aetherteam.aether.perk.types.Halo;
import com.aetherteam.aether.perk.types.MoaData;
import com.aetherteam.aether.perk.types.MoaSkins;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.SavedDataStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PerkSavedData extends SavedData {
    public static final String FILE_NAME = "perks";
    private final Map<UUID, MoaData> storedSkinData = new HashMap<>();
    private final Map<UUID, Halo> storedHaloData = new HashMap<>();
    private final Map<UUID, DeveloperGlow> storedDeveloperGlowData = new HashMap<>();

    private static final Codec<MoaData> MOA_DATA_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        UUIDUtil.CODEC.optionalFieldOf("MoaUUID").forGetter((moaData) -> Optional.ofNullable(moaData.moaUUID())),
        Codec.STRING.optionalFieldOf("MoaSkin").forGetter((moaData) -> Optional.ofNullable(moaData.moaSkin()).map(MoaSkins.MoaSkin::getId))
    ).apply(instance, (moaUUID, moaSkin) -> new MoaData(moaUUID.orElse(null), moaSkin.map((id) -> MoaSkins.getMoaSkins().get(id)).orElse(null))));
    private static final Codec<Halo> HALO_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        Codec.STRING.optionalFieldOf("HexColor").forGetter((halo) -> Optional.ofNullable(halo.hexColor()))
    ).apply(instance, (hexColor) -> new Halo(hexColor.orElse(null))));
    private static final Codec<DeveloperGlow> DEVELOPER_GLOW_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        Codec.STRING.optionalFieldOf("HexColor").forGetter((developerGlow) -> Optional.ofNullable(developerGlow.hexColor()))
    ).apply(instance, (hexColor) -> new DeveloperGlow(hexColor.orElse(null))));

    /**
     * Saves perk data to the world in a file named "perks.dat".
     */
    public static final Codec<PerkSavedData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        Codec.unboundedMap(UUIDUtil.STRING_CODEC, MOA_DATA_CODEC).optionalFieldOf("StoredSkinData", Map.of()).forGetter((data) -> data.storedSkinData),
        Codec.unboundedMap(UUIDUtil.STRING_CODEC, HALO_CODEC).optionalFieldOf("StoredHaloData", Map.of()).forGetter((data) -> data.storedHaloData),
        Codec.unboundedMap(UUIDUtil.STRING_CODEC, DEVELOPER_GLOW_CODEC).optionalFieldOf("StoredDeveloperGlowData", Map.of()).forGetter((data) -> data.storedDeveloperGlowData)
    ).apply(instance, PerkSavedData::new));

    public static final SavedDataType<PerkSavedData> TYPE = new SavedDataType<>(Identifier.fromNamespaceAndPath(Aether.MODID, FILE_NAME), PerkSavedData::new, CODEC, null);

    public PerkSavedData() { }

    private PerkSavedData(Map<UUID, MoaData> storedSkinData, Map<UUID, Halo> storedHaloData, Map<UUID, DeveloperGlow> storedDeveloperGlowData) {
        this.storedSkinData.putAll(storedSkinData);
        this.storedHaloData.putAll(storedHaloData);
        this.storedDeveloperGlowData.putAll(storedDeveloperGlowData);
    }

    /**
     * Loads or creates the "perks.dat" file.
     *
     * @param dataStorage The {@link SavedDataStorage} of the server.
     * @return The {@link PerkSavedData} corresponding to the data file.
     */
    public static PerkSavedData compute(SavedDataStorage dataStorage) {
        return dataStorage.computeIfAbsent(TYPE);
    }

    /**
     * @return A {@link Map} of player {@link UUID}s and {@link MoaData} retrieved from "perks.dat".
     */
    Map<UUID, MoaData> getStoredSkinData() {
        return ImmutableMap.copyOf(this.storedSkinData);
    }

    /**
     * Modifies the {@link MoaData} stored in the world data.
     *
     * @param uuid    The {@link UUID} of the player that the {@link MoaData} belongs to.
     * @param moaData The {@link MoaData}.
     */
    void modifyStoredSkinData(UUID uuid, MoaData moaData) {
        this.storedSkinData.put(uuid, moaData);
        this.setDirty();
    }

    /**
     * Removes an entry for a player's {@link MoaData} from the world data.
     *
     * @param uuid The player's {@link UUID}.
     */
    void removeStoredSkinData(UUID uuid) {
        this.storedSkinData.remove(uuid);
        this.setDirty();
    }

    /**
     * @return A {@link Map} of player {@link UUID}s and {@link Halo} info retrieved from "perks.dat".
     */
    Map<UUID, Halo> getStoredHaloData() {
        return ImmutableMap.copyOf(this.storedHaloData);
    }

    /**
     * Modifies the {@link Halo} info stored in the world data.
     *
     * @param uuid The {@link UUID} of the player that the {@link Halo} belongs to.
     * @param halo The {@link Halo}.
     */
    void modifyStoredHaloData(UUID uuid, Halo halo) {
        this.storedHaloData.put(uuid, halo);
        this.setDirty();
    }

    /**
     * Removes an entry for a player's {@link Halo} from the world data.
     *
     * @param uuid The player's {@link UUID}.
     */
    void removeStoredHaloData(UUID uuid) {
        this.storedHaloData.remove(uuid);
        this.setDirty();
    }

    /**
     * @return A {@link Map} of player {@link UUID}s and {@link DeveloperGlow} info retrieved from "perks.dat".
     */
    Map<UUID, DeveloperGlow> getStoredDeveloperGlowData() {
        return ImmutableMap.copyOf(this.storedDeveloperGlowData);
    }

    /**
     * Modifies the {@link DeveloperGlow} info stored in the world data.
     *
     * @param uuid          The {@link UUID} of the player that the {@link Halo} belongs to.
     * @param developerGlow The {@link DeveloperGlow}.
     */
    void modifyStoredDeveloperGlowData(UUID uuid, DeveloperGlow developerGlow) {
        this.storedDeveloperGlowData.put(uuid, developerGlow);
        this.setDirty();
    }

    /**
     * Removes an entry for a player's {@link DeveloperGlow} from the world data.
     *
     * @param uuid The player's {@link UUID}.
     */
    void removeStoredDeveloperGlowData(UUID uuid) {
        this.storedDeveloperGlowData.remove(uuid);
        this.setDirty();
    }
}
