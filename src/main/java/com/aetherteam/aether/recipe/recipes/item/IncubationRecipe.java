package com.aetherteam.aether.recipe.recipes.item;

import com.aetherteam.aether.recipe.book.AetherRecipeBookCategories;
import net.minecraft.resources.Identifier;
import com.aetherteam.aether.recipe.serializer.AetherRecipeSerializer;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.registries.AetherDataMaps;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.display.IncubatorRecipeDisplay;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class IncubationRecipe implements Recipe<SingleRecipeInput> {
    protected final RecipeType<IncubationRecipe> type;
    protected final String group;
    protected final Ingredient ingredient;
    protected final EntityType<?> entity;
    protected final Optional<CompoundTag> tag;
    protected final int incubationTime;

    public IncubationRecipe(String group, Ingredient ingredient, EntityType<?> entity, Optional<CompoundTag> tag, int incubationTime) {
        this.type = AetherRecipeTypes.INCUBATION.get();
        this.group = group;
        this.ingredient = ingredient;
        this.entity = entity;
        this.tag = tag;
        this.incubationTime = incubationTime;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public boolean matches(SingleRecipeInput menu, Level level) {
        return this.ingredient.test(menu.getItem(0));
    }

    /**
     * @return An empty {@link ItemStack}, as there is no item output.
     */
    @Override
    public ItemStack assemble(SingleRecipeInput menu) {
        return ItemStack.EMPTY;
    }

    public int incubationTime() {
        return this.incubationTime;
    }

    public EntityType<?> entity() {
        return this.entity;
    }

    public Optional<CompoundTag> tag() {
        return this.tag;
    }

    public Ingredient input() {
        return this.ingredient;
    }

    @Override
    public String group() {
        return this.group;
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(
            new IncubatorRecipeDisplay(
                this.ingredient.display(),
                new SlotDisplay.Composite(BuiltInRegistries.ITEM.nitrogen_fabric$getDataMap(AetherDataMaps.INCUBATOR_FUEL).keySet().stream().map(BuiltInRegistries.ITEM::getValue).filter(Objects::nonNull).map(SlotDisplay.ItemSlotDisplay::new).collect(Collectors.toList())),
                this.ingredient.display(),
                new SlotDisplay.ItemSlotDisplay(AetherBlocks.INCUBATOR.get().asItem()),
                this.incubationTime
            )
        );
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(this.ingredient);
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return AetherRecipeBookCategories.INCUBATION_MISC.get();
    }

    @Override
    public RecipeSerializer<IncubationRecipe> getSerializer() {
        return AetherRecipeSerializers.INCUBATION.get();
    }

    @Override
    public RecipeType<IncubationRecipe> getType() {
        return this.type;
    }

    public static class Serializer implements AetherRecipeSerializer<IncubationRecipe> {
        @Override
        public MapCodec<IncubationRecipe> codec() {
            return RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(p_300832_ -> p_300832_.group),
                Ingredient.CODEC.fieldOf("ingredient").forGetter((recipe) -> recipe.ingredient),
                BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity").forGetter((recipe) -> recipe.entity),
                CompoundTag.CODEC.optionalFieldOf("tag").forGetter((recipe) -> recipe.tag),
                Codec.INT.fieldOf("incubationtime").orElse(500).forGetter((recipe) -> recipe.incubationTime)
            ).apply(instance, IncubationRecipe::new));
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, IncubationRecipe> streamCodec() {
            return StreamCodec.of(this::toNetwork, this::fromNetwork);
        }

        @Nullable
        public IncubationRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.getOptional(Identifier.parse(buffer.readUtf())).orElseThrow(() -> new JsonSyntaxException("Entity type cannot be found"));
            Optional<CompoundTag> tag = buffer.readOptional(RegistryFriendlyByteBuf::readNbt);
            int incubationTime = buffer.readVarInt();
            return new IncubationRecipe(group, ingredient, entityType, tag, incubationTime);
        }

        public void toNetwork(RegistryFriendlyByteBuf buffer, IncubationRecipe recipe) {
            buffer.writeUtf(recipe.group);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
            buffer.writeUtf(EntityType.getKey(recipe.entity()).toString());
            buffer.writeOptional(recipe.tag, RegistryFriendlyByteBuf::writeNbt);
            buffer.writeVarInt(recipe.incubationTime());
        }
    }
}
