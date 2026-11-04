package com.aetherteam.aether.entity.passive;

import net.minecraft.world.item.Items;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.component.SwingAnimation;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.ai.controller.FallingMoveControl;
import com.aetherteam.aether.entity.ai.goal.EatAetherGrassGoal;
import com.aetherteam.aether.entity.ai.goal.FallingRandomStrollGoal;
import com.aetherteam.aether.entity.ai.navigator.FallPathNavigation;
import com.aetherteam.aether.loot.AetherLoot;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * [CODE COPY] - {@link net.minecraft.world.entity.animal.Sheep}.<br><br>
 * Cleaned up and added additional behavior for puff behavior and slow-falling.<br><br>
 * Warning for "deprecation" is suppressed because we still need to use vanilla shearing behavior from {@link Shearable}.
 */
@SuppressWarnings("deprecation")
public class Sheepuff extends AetherAnimal implements Shearable {
    private static final EntityDataAccessor<Byte> DATA_WOOL_COLOR_ID = SynchedEntityData.defineId(Sheepuff.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> DATA_PUFFED_ID = SynchedEntityData.defineId(Sheepuff.class, EntityDataSerializers.BOOLEAN);

    private static final Map<DyeColor, ItemLike> ITEM_BY_DYE = Util.make(new EnumMap<>(DyeColor.class), (map) -> {
        map.put(DyeColor.WHITE, Blocks.WOOL.pick(DyeColor.WHITE));
        map.put(DyeColor.ORANGE, Blocks.WOOL.pick(DyeColor.ORANGE));
        map.put(DyeColor.MAGENTA, Blocks.WOOL.pick(DyeColor.MAGENTA));
        map.put(DyeColor.LIGHT_BLUE, Blocks.WOOL.pick(DyeColor.LIGHT_BLUE));
        map.put(DyeColor.YELLOW, Blocks.WOOL.pick(DyeColor.YELLOW));
        map.put(DyeColor.LIME, Blocks.WOOL.pick(DyeColor.LIME));
        map.put(DyeColor.PINK, Blocks.WOOL.pick(DyeColor.PINK));
        map.put(DyeColor.GRAY, Blocks.WOOL.pick(DyeColor.GRAY));
        map.put(DyeColor.LIGHT_GRAY, Blocks.WOOL.pick(DyeColor.LIGHT_GRAY));
        map.put(DyeColor.CYAN, Blocks.WOOL.pick(DyeColor.CYAN));
        map.put(DyeColor.PURPLE, Blocks.WOOL.pick(DyeColor.PURPLE));
        map.put(DyeColor.BLUE, Blocks.WOOL.pick(DyeColor.BLUE));
        map.put(DyeColor.BROWN, Blocks.WOOL.pick(DyeColor.BROWN));
        map.put(DyeColor.GREEN, Blocks.WOOL.pick(DyeColor.GREEN));
        map.put(DyeColor.RED, Blocks.WOOL.pick(DyeColor.RED));
        map.put(DyeColor.BLACK, Blocks.WOOL.pick(DyeColor.BLACK));
    });
    private static final Map<DyeColor, Integer> COLOR_BY_DYE = Maps.<DyeColor, Integer>newEnumMap( // Do not remove these explicit type arguments even if your dev environment says they can be removed.
        Arrays.stream(DyeColor.values()).collect(Collectors.toMap(p_29868_ -> p_29868_, Sheepuff::createSheepColor))
    );
    private int eatAnimationTick, amountEaten;
    private EatAetherGrassGoal eatBlockGoal;

    private final FallPathNavigation fallNavigation;
    private final GroundPathNavigation groundNavigation;

    private static int createSheepColor(DyeColor dyeColor) {
        if (dyeColor == DyeColor.WHITE) {
            return -1644826;
        } else {
            int i = dyeColor.getTextureDiffuseColor();
            return ARGB.color(255, Mth.floor((float) ARGB.red(i) * 0.75F), Mth.floor((float) ARGB.green(i) * 0.75F), Mth.floor((float) ARGB.blue(i) * 0.75F));
        }
    }

    public static int getColor(DyeColor dyeColor) {
        return COLOR_BY_DYE.get(dyeColor);
    }

    public Sheepuff(EntityType<? extends Sheepuff> type, Level level) {
        super(type, level);
        this.moveControl = new FallingMoveControl(this);
        this.fallNavigation = new FallPathNavigation(this, level);
        this.groundNavigation = new GroundPathNavigation(this, level);
    }

    @Override
    protected void registerGoals() {
        this.eatBlockGoal = new EatAetherGrassGoal(this);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, (stack) -> stack.is(AetherTags.Items.SHEEPUFF_TEMPTATION_ITEMS), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, this.eatBlockGoal);
        this.goalSelector.addGoal(6, new FallingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 8.0)
                .add(Attributes.MOVEMENT_SPEED, 0.23);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_WOOL_COLOR_ID, (byte) 0);
        builder.define(DATA_PUFFED_ID, false);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData spawnData) {
        this.setColor(getRandomSheepuffColor(level.getRandom()));
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    @Override
    protected void customServerAiStep(ServerLevel serverLevel) {
        this.eatAnimationTick = this.eatBlockGoal.getEatAnimationTick();
        super.customServerAiStep(serverLevel);
    }

    @Override
    public void aiStep() {
        if (this.level().isClientSide()) {
            this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        }
        super.aiStep();
    }

    /**
     * Makes this entity fall slowly when puffed up.
     */
    @Override
    public void tick() {
        super.tick();
        if (this.getPuffed()) {
            this.checkFallDistanceAccumulation();
            AttributeInstance gravity = this.getAttribute(Attributes.GRAVITY);
            if (gravity != null) {
                double fallSpeed = Math.max(gravity.getValue() * -0.625, -0.05);
                if (this.getDeltaMovement().y() < fallSpeed) {
                    this.setDeltaMovement(this.getDeltaMovement().x, fallSpeed, this.getDeltaMovement().z);
                }
            }
            this.navigation = this.fallNavigation;
        } else {
            this.navigation = this.groundNavigation;
        }
    }

    /**
     * Makes this entity jump much higher when puffed up.
     */
    @Override
    public void jumpFromGround() {
        super.jumpFromGround();
        if (this.getPuffed()) {
            this.push(0.0, 1.8, 0.0);
        }
    }

    @Override
    public void ate() {
        ++this.amountEaten;
        if (!this.isSheared()) {
            if (this.amountEaten >= 2) { // Sheepuffs only puff up after eating twice.
                this.setPuffed(true);
                this.amountEaten = 0;
            }
        } else {
            if (this.amountEaten == 1) {
                this.setSheared(false);
                this.amountEaten = 0;
            }
        }
        if (this.isBaby()) {
            this.ageUp(60);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        DyeColor color = itemstack.get(DataComponents.DYE);
        if (color != null && !this.isSheared()) {
            if (this.getColor() != color) {
                if (this.getPuffed() && itemstack.getCount() >= 2) {
                    player.swing(hand, SwingAnimation.DEFAULT, true);
                    if (!player.level().isClientSide()) {
                        this.setColor(color);
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(2);
                        }
                    }
                } else if (!this.getPuffed()) {
                    player.swing(hand, SwingAnimation.DEFAULT, true);
                    if (!player.level().isClientSide()) {
                        this.setColor(color);
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                    }
                }

            }
        } else if ((itemstack.is(Items.SHEARS) || itemstack.is(ConventionalItemTags.SHEAR_TOOLS)) && this.readyForShearing()) {
            this.shear(SoundSource.PLAYERS);
            this.gameEvent(GameEvent.SHEAR, player);
            if (!this.level().isClientSide()) {
                itemstack.hurtAndBreak(1, player, getSlotForHand(hand));
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    /**
     * Vanilla shearing method (needed for dispenser behavior).
     */
    @Override
    public void shear(ServerLevel serverLevel, SoundSource source, ItemStack stack) { //todo update
        this.level().playSound(null, this, AetherSoundEvents.ENTITY_SHEEPUFF_SHEAR.get(), source, 1.0F, 1.0F);
        int i;
        this.amountEaten = 0;
        if (this.getPuffed()) {
            this.setPuffed(false);
            i = 2;
        } else {
            this.setSheared(true);
            i = 1;
        }
        i += this.getRandom().nextInt(3);

        for (int j = 0; j < i; ++j) {
            ItemEntity itementity = this.spawnAtLocation(serverLevel, new ItemStack(ITEM_BY_DYE.get(this.getColor())), 1.0F);
            if (itementity != null) {
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F, this.getRandom().nextFloat() * 0.05F, (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F));
            }
        }
    }

    @Override
    public boolean readyForShearing() {
        return this.isAlive() && !this.isSheared() && !this.isBaby();
    }

    public boolean isSheared() {
        return (this.getEntityData().get(DATA_WOOL_COLOR_ID) & 16) != 0;
    }

    public void setSheared(boolean sheared) {
        byte b0 = this.getEntityData().get(DATA_WOOL_COLOR_ID);
        if (sheared) {
            this.getEntityData().set(DATA_WOOL_COLOR_ID, (byte) (b0 | 16));
        } else {
            this.getEntityData().set(DATA_WOOL_COLOR_ID, (byte) (b0 & -17));
        }
    }

    /**
     * @return Whether the Sheepuff is puffed up, as a {@link Boolean}.
     */
    public boolean getPuffed() {
        return this.getEntityData().get(DATA_PUFFED_ID);
    }

    /**
     * Sets whether the Sheepuff is puffed up.
     *
     * @param flag Whether to set the Sheepuff as puffed, as a {@link Boolean}.
     */
    public void setPuffed(boolean flag) {
        this.getEntityData().set(DATA_PUFFED_ID, flag);
    }

    public DyeColor getColor() {
        return DyeColor.byId(this.getEntityData().get(DATA_WOOL_COLOR_ID) & 15);
    }

    public void setColor(DyeColor dyeColor) {
        byte b0 = this.getEntityData().get(DATA_WOOL_COLOR_ID);
        this.getEntityData().set(DATA_WOOL_COLOR_ID, (byte) (b0 & 240 | dyeColor.getId() & 15));
    }

    @Override
    public <T> @Nullable T get(DataComponentType<? extends T> type) {
        return type == DataComponents.SHEEP_COLOR ? castComponentValue((DataComponentType<T>) type, this.getColor()) : super.get(type);
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter components) {
        this.applyImplicitComponentIfPresent(components, DataComponents.SHEEP_COLOR);
        super.applyImplicitComponents(components);
    }

    @Override
    protected <T> boolean applyImplicitComponent(DataComponentType<T> type, T value) {
        if (type == DataComponents.SHEEP_COLOR) {
            this.setColor(castComponentValue(DataComponents.SHEEP_COLOR, value));
            return true;
        } else {
            return super.applyImplicitComponent(type, value);
        }
    }

    /**
     * Unsheared Sheepuffs also drop wool of their color, from a separate loot table.
     */
    @Override
    protected void dropFromLootTable(ServerLevel level, DamageSource source, boolean playerKilled) {
        super.dropFromLootTable(level, source, playerKilled);
        if (!this.isSheared()) {
            this.dropFromLootTable(level, source, playerKilled, AetherLoot.SHEEPUFF_BY_DYE.get(this.getColor()));
        }
    }

    public static DyeColor getRandomSheepuffColor(RandomSource random) {
        int i = random.nextInt(100);
        if (i < 5) {
            return DyeColor.LIGHT_BLUE;
        } else if (i < 10) {
            return DyeColor.CYAN;
        } else if (i < 15) {
            return DyeColor.LIME;
        } else if (i < 18) {
            return DyeColor.PINK;
        } else {
            return random.nextInt(500) == 0 ? DyeColor.PURPLE : DyeColor.WHITE;
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(AetherTags.Items.SHEEPUFF_TEMPTATION_ITEMS);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_SHEEPUFF_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherSoundEvents.ENTITY_SHEEPUFF_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_SHEEPUFF_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), AetherSoundEvents.ENTITY_SHEEPUFF_STEP.get(), SoundSource.NEUTRAL, 0.15F, 1.0F);
    }

    @Override
    protected int calculateFallDamage(double distance, float damageMultiplier) {
        return this.getPuffed() ? 0 : super.calculateFallDamage(distance, damageMultiplier);
    }

    @Override
    public int getMaxFallDistance() {
        return !this.onGround() && this.getPuffed() ? 20 : super.getMaxFallDistance();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob entity) {
        Sheepuff parent = (Sheepuff) entity;
        Sheepuff baby = AetherEntityTypes.SHEEPUFF.get().create(level, EntitySpawnReason.BREEDING);
        if (baby != null) {
            baby.setColor(this.getOffspringColor(level, this, parent));
        }
        return baby;
    }

    private DyeColor getOffspringColor(ServerLevel level, Animal parent1, Animal parent2) {
        DyeColor dyeColor1 = ((Sheepuff) parent1).getColor();
        DyeColor dyeColor2 = ((Sheepuff) parent2).getColor();
        CraftingInput craftingInput = makeCraftInput(dyeColor1, dyeColor2);
        return level
                .recipeAccess()
                .getRecipeFor(RecipeType.CRAFTING, craftingInput, this.level())
                .map(recipeHolder -> recipeHolder.value().assemble(craftingInput))
                .map(stack -> stack.get(DataComponents.DYE))
                .orElseGet(() -> this.level().getRandom().nextBoolean() ? dyeColor1 : dyeColor2);
    }

    private static CraftingInput makeCraftInput(DyeColor color1, DyeColor color2) {
        return CraftingInput.of(2, 1, List.of(new ItemStack(Items.DYE.pick(color1)), new ItemStack(Items.DYE.pick(color2))));
    }

    public float getHeadEatPositionScale(float pos) {
        if (this.eatAnimationTick <= 0) {
            return 0.0F;
        } else if (this.eatAnimationTick >= 4 && this.eatAnimationTick <= 36) {
            return 1.0F;
        } else {
            return this.eatAnimationTick < 4 ? (this.eatAnimationTick - pos) / 4.0F : -(this.eatAnimationTick - 40 - pos) / 4.0F;
        }
    }

    public float getHeadEatAngleScale(float angle) {
        if (this.eatAnimationTick > 4 && this.eatAnimationTick <= 36) {
            float f = ((float) (this.eatAnimationTick - 4) - angle) / 32.0F;
            return (Mth.PI / 5.0F) + 0.21991149F * Mth.sin(f * 28.7F);
        } else {
            return this.eatAnimationTick > 0 ? (Mth.PI / 5.0F) : this.getXRot() * Mth.DEG_TO_RAD;
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            this.eatAnimationTick = 40;
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Sheared", this.isSheared());
        tag.putBoolean("Puffed", this.getPuffed());
        tag.putByte("Color", (byte) this.getColor().getId());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput tag) {
        super.readAdditionalSaveData(tag);
        if (tag.read("Sheared", Codec.PASSTHROUGH).isPresent()) {
            this.setSheared(tag.getBooleanOr("Sheared", false));
        }
        if (tag.read("Puffed", Codec.PASSTHROUGH).isPresent()) {
            this.setPuffed(tag.getBooleanOr("Puffed", false));
        }
        if (tag.read("Color", Codec.PASSTHROUGH).isPresent()) {
            this.setColor(DyeColor.byId(tag.getByteOr("Color", (byte) 0)));
        }
    }
}
