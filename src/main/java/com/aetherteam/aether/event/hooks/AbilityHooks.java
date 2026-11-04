package com.aetherteam.aether.event.hooks;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.EquipmentSlot;
import com.aetherteam.aetherfabric.accessories.AccessoryApi;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;
import net.minecraft.world.entity.EntityTypes;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aether.attachment.LightningTrackerAttachment;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.entity.projectile.PoisonNeedle;
import com.aetherteam.aether.entity.projectile.dart.EnchantedDart;
import com.aetherteam.aether.entity.projectile.dart.GoldenDart;
import com.aetherteam.aether.entity.projectile.dart.PoisonDart;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.accessories.abilities.ZaniteAccessory;
import com.aetherteam.aether.item.tools.abilities.HolystoneTool;
import com.aetherteam.aether.item.tools.abilities.ZaniteTool;
import com.aetherteam.aether.loot.AetherLoot;
import com.aetherteam.aether.network.packet.clientbound.ToolDebuffPacket;
import com.aetherteam.nitrogen.fabric.events.FallHelper;
import com.aetherteam.nitrogen.fabric.network.PacketDistributor;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.AtomicDouble;
import com.aetherteam.aetherfabric.accessories.SlotResult;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

public class AbilityHooks {
    public static class AccessoryHooks {
        /**
         * Damages an entity's Gloves when they hurt another entity.
         *
         * @param player The attacking {@link Player}.
         * @see com.aetherteam.aether.mixin.mixins.common.PlayerMixin#attack(Entity, CallbackInfo)
         */
        public static void damageGloves(Player player) {
            SlotResult slotResult = EquipmentUtil.getGloves(player);
            if (slotResult != null) {
                if (player.level() instanceof ServerLevel serverLevel) {
                    slotResult.stack().hurtAndBreak(1, serverLevel, player, (item) -> AccessoryApi.onBroken(slotResult.slotContext(), slotResult.stack()));
                }
            }
        }

        /**
         * Damages Zanite Rings when a block is broken.
         *
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onBlockBreak(BreakBlockEvent)
         */
        public static void damageZaniteRing(LivingEntity entity, LevelAccessor level, BlockState state, BlockPos pos) {
            List<SlotResult> slotResults = EquipmentUtil.getZaniteRings(entity);
            for (SlotResult slotResult : slotResults) {
                if (slotResult != null) {
                    if (state.getDestroySpeed(level, pos) > 0 && entity.getRandom().nextInt(6) == 0) {
                        if (entity.level() instanceof ServerLevel serverLevel) {
                            slotResult.stack().hurtAndBreak(1, serverLevel, entity, (item) -> AccessoryApi.onBroken(slotResult.slotContext(), slotResult.stack()));
                        }
                    }
                }
            }
        }

        /**
         * Damages Zanite Pendant when a block is broken.
         *
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onBlockBreak(BreakBlockEvent)
         */
        public static void damageZanitePendant(LivingEntity entity, LevelAccessor level, BlockState state, BlockPos pos) {
            SlotResult slotResult = EquipmentUtil.getZanitePendant(entity);
            if (slotResult != null) {
                if (state.getDestroySpeed(level, pos) > 0 && entity.getRandom().nextInt(6) == 0) {
                    if (entity.level() instanceof ServerLevel serverLevel) {
                        slotResult.stack().hurtAndBreak(1, serverLevel, entity, (item) -> AccessoryApi.onBroken(slotResult.slotContext(), slotResult.stack()));
                    }
                }
            }
        }

        /**
         * Handles ability for {@link ZaniteAccessory} for Zanite Rings (accounts for if multiple are equipped).
         * @see ZaniteAccessory#handleMiningSpeed(float, ItemStack)
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onMiningSpeed(PlayerEvent.BreakSpeed)
         */
        public static float handleZaniteRingAbility(LivingEntity entity, float speed) {
            float newSpeed = speed;
            List<SlotResult> slotResults = EquipmentUtil.getZaniteRings(entity);
            for (SlotResult slotResult : slotResults) {
                if (slotResult != null) {
                    newSpeed = ZaniteAccessory.handleMiningSpeed(newSpeed, slotResult.stack());
                }
            }
            return newSpeed;
        }

        /**
         * Handles ability for {@link ZaniteAccessory} for the Zanite Pendant.
         * @see ZaniteAccessory#handleMiningSpeed(float, ItemStack)
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onMiningSpeed(PlayerEvent.BreakSpeed)
         */
        public static float handleZanitePendantAbility(LivingEntity entity, float speed) {
            SlotResult slotResult = EquipmentUtil.getZanitePendant(entity);
            if (slotResult != null) {
                speed = ZaniteAccessory.handleMiningSpeed(speed, slotResult.stack());
            }
            return speed;
        }

        /**
         * Checks whether an entity can be targeted while wearing an Invisibility Cloak.
         *
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onTargetSet(LivingEntity, Entity, MutableDouble)
         */
        public static boolean preventTargeting(LivingEntity target, @Nullable Entity lookingEntity) {
            if (target instanceof Player player) {
                var data = player.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER);
                return lookingEntity != null
                        && !lookingEntity.is(AetherTags.Entities.IGNORE_INVISIBILITY)
                        && data.isWearingInvisibilityCloak()
                        && data.isInvisibilityEnabled()
                        && !data.attackedWithInvisibility();
            } else {
                return lookingEntity != null
                        && !lookingEntity.is(AetherTags.Entities.IGNORE_INVISIBILITY)
                        && EquipmentUtil.hasInvisibilityCloak(target);
            }
        }

        /**
         * Checks if an entity recently attacked while wearing an Invisibility Cloak.
         *
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onTargetSet(LivingEntity, Entity, MutableDouble)
         */
        public static boolean recentlyAttackedWithInvisibility(LivingEntity target, Entity lookingEntity) {
            if (target instanceof Player player) {
                var data = player.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER);
                return !lookingEntity.is(AetherTags.Entities.IGNORE_INVISIBILITY)
                        && data.isWearingInvisibilityCloak()
                        && data.isInvisibilityEnabled()
                        && data.attackedWithInvisibility();
            } else {
                return false;
            }
        }

        /**
         * Sets that the player recently attacked.
         *
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onEntityHurt(LivingEntity, DamageSource)
         */
        public static void setAttack(DamageSource source) {
            if (source.getEntity() instanceof Player player) {
                player.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).setAttackedWithInvisibility(true);
            }
        }

        /**
         * Prevents magma block damage when wearing ice accessories.
         *
         * @see com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener#onEntityHurt(LivingEntity, DamageSource)
         */
        public static boolean preventMagmaDamage(LivingEntity entity, DamageSource source) {
            return source == entity.level().damageSources().hotFloor() && EquipmentUtil.hasFreezingAccessory(entity);
        }
    }

    public static class ArmorHooks {
        /**
         * Cancels fall damage if the wearer either has Sentry Boots, a full Gravitite Armor set, or a full Valkyrie Armor set.
         *
         * @param entity The {@link LivingEntity} wearing the armor.
         * @return Whether the wearer's fall damage should be cancelled, as a {@link Boolean}.
         * @see com.aetherteam.aether.event.listeners.abilities.ArmorAbilityListener#onEntityFall(LivingEntity, FallHelper)
         */
        public static boolean fallCancellation(LivingEntity entity) {
            return EquipmentUtil.hasSentryBoots(entity) || EquipmentUtil.hasFullGravititeSet(entity) || EquipmentUtil.hasFullValkyrieSet(entity);
        }
    }

    public static class ToolHooks {
        /**
         * Blocks able to be stripped with an axe, and the equivalent result block.
         */
        public static final Map<Block, Block> STRIPPABLES = (new ImmutableMap.Builder<Block, Block>())
                .put(AetherBlocks.SKYROOT_LOG.get(), AetherBlocks.STRIPPED_SKYROOT_LOG.get())
                .put(AetherBlocks.GOLDEN_OAK_LOG.get(), AetherBlocks.STRIPPED_SKYROOT_LOG.get())
                .put(AetherBlocks.SKYROOT_WOOD.get(), AetherBlocks.STRIPPED_SKYROOT_WOOD.get())
                .put(AetherBlocks.GOLDEN_OAK_WOOD.get(), AetherBlocks.STRIPPED_SKYROOT_WOOD.get())
                .build();

        /**
         * Blocks able to be flattened with a shovel, and the equivalent result block.
         */
        public static final Map<Block, Block> FLATTENABLES = (new ImmutableMap.Builder<Block, Block>())
                .put(AetherBlocks.AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_DIRT_PATH.get())
                .put(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_DIRT_PATH.get())
                .put(AetherBlocks.AETHER_DIRT.get(), AetherBlocks.AETHER_DIRT_PATH.get())
                .build();

        /**
         * Blocks able to be tilled with a hoe, and the equivalent result block.
         */
        public static final Map<Block, Block> TILLABLES = (new ImmutableMap.Builder<Block, Block>())
                .put(AetherBlocks.AETHER_DIRT.get(), AetherBlocks.AETHER_FARMLAND.get())
                .put(AetherBlocks.AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_FARMLAND.get())
                .put(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_FARMLAND.get())
                .put(AetherBlocks.AETHER_DIRT_PATH.get(), AetherBlocks.AETHER_FARMLAND.get())
                .build();

        public static boolean debuffTools;

        /**
         * Handles ability for {@link com.aetherteam.aether.item.tools.abilities.HolystoneTool}.
         *
         * @see HolystoneTool#dropAmbrosium(Player, Level, BlockPos, ItemStack, BlockState)
         * @see com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#doHolystoneAbility(BreakBlockEvent)
         */
        public static void handleHolystoneToolAbility(Player player, Level level, BlockPos pos, ItemStack stack, BlockState blockState) {
            if (stack.getItem() instanceof HolystoneTool holystoneTool) {
                holystoneTool.dropAmbrosium(player, level, pos, stack, blockState);
            }
        }

        /**
         * Handles ability for {@link com.aetherteam.aether.item.tools.abilities.ZaniteTool}.
         * @see ZaniteTool#increaseSpeed(ItemStack, float)
         * @see com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#modifyBreakSpeed(Player, BlockState, float)
         */
        public static float handleZaniteToolAbility(ItemStack stack, float speed) {
            if (stack.getItem() instanceof ZaniteTool zaniteTool) {
                return zaniteTool.increaseSpeed(stack, speed);
            }
            return speed;
        }

        /**
         * Debuffs the mining speed of blocks if they're Aether blocks (according to the description id, and the tags {@link AetherTags.Blocks#TREATED_AS_AETHER_BLOCK} and {@link AetherTags.Blocks#TREATED_AS_VANILLA_BLOCK}),
         * and if the item is non-Aether (according to the description id, and the tag {@link AetherTags.Items#TREATED_AS_AETHER_ITEM}), as long as it's not an empty item (no item at all) and as long as its detected as the correct tool type for the block.<br><br>
         * The debuffed value is the original value to the power of -0.2.
         *
         * @param state The {@link BlockState} of the block being mined.
         * @param stack The {@link ItemStack} being used for mining.
         * @param speed The mining speed of the stack, as a {@link Float}.
         * @return The debuffed mining speed, as a {@link Float}.
         * @see com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#modifyBreakSpeed(Player, BlockState, float)
         */
        public static float reduceToolEffectiveness(Player player, BlockState state, ItemStack stack, float speed) {
            if (debuffTools) {
                if ((state.getBlock().getDescriptionId().startsWith("block.aether.") || state.is(AetherTags.Blocks.TREATED_AS_AETHER_BLOCK)) && !state.is(AetherTags.Blocks.TREATED_AS_VANILLA_BLOCK)) {
                    if (!stack.isEmpty() && stack.isCorrectToolForDrops(state) && !stack.getItem().getDescriptionId().startsWith("item.aether.") && !stack.is(AetherTags.Items.TREATED_AS_AETHER_ITEM)) {
                        speed = (float) Math.max(Math.pow(speed, speed > 1.0 ? -0.5 : 1.5), 1.0);
                    }
                }
            }
            return speed;
        }

        /**
         * Sets up the debuff tool state based on the current server value and attempts to sync the state to the client if needed
         *
         * @param player Current player logging into the server
         * @see com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#
         */
        public static void setDebuffToolsState(ServerPlayer player) {
            if (debuffTools) {
                PacketDistributor.sendToPlayer(player, new ToolDebuffPacket(true));
            } else if (AetherConfig.SERVER.tools_debuff.get()) {
                debuffTools = true;

                PacketDistributor.sendToAllPlayers(new ToolDebuffPacket(true));
            }
        }

        /**
         * Method used to reset the debuffTools state to false on player logout
         * @see com.aetherteam.aether.client.AetherClient#eventSetup(net.neoforged.bus.api.IEventBus)
         */
        public static void resetDebuffToolsState() {
            debuffTools = false;
        }
    }

    public static class WeaponHooks {
        /**
         * Renders Darts that hit the player as stuck on their model, similar to Arrows.<br><br>
         * This is done through increasing values stored in {@link AetherPlayerAttachment} that track the amount of different darts stuck in the player.
         *
         * @param entity The hurt {@link LivingEntity}.
         * @param source The {@link DamageSource} that hurt the entity.
         * @see com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onDartHurt(LivingEntity, DamageSource)
         */
        public static void stickDart(LivingEntity entity, DamageSource source) {
            if (entity instanceof Player player && !player.level().isClientSide()) {
                Entity sourceEntity = source.getDirectEntity();
                var data = player.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER);
                if (sourceEntity instanceof GoldenDart) {
                    data.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setGoldenDartCount", data.getGoldenDartCount() + 1);
                } else if (sourceEntity instanceof PoisonDart || sourceEntity instanceof PoisonNeedle) {
                    data.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setPoisonDartCount", data.getPoisonDartCount() + 1);
                } else if (sourceEntity instanceof EnchantedDart) {
                    data.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setEnchantedDartCount", data.getEnchantedDartCount() + 1);
                }
            }
        }

        /**
         * Sets the hit entity on fire for the amount of seconds the Phoenix Arrow has stored, as determined by {@link com.aetherteam.aether.item.combat.loot.PhoenixBowItem#customArrow(AbstractArrow, ItemStack, ItemStack)}.
         *
         * @param result     The {@link HitResult} of the projectile.
         * @param projectile The {@link Projectile} that hit something.
         * @see com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onArrowHit(Projectile, HitResult, MutableBoolean)
         */
        public static void phoenixArrowHit(HitResult result, Projectile projectile) {
            if (result instanceof EntityHitResult entityHitResult && projectile instanceof AbstractArrow abstractArrow) {
                Entity impactedEntity = entityHitResult.getEntity();
                if (impactedEntity.getType() == EntityTypes.ENDERMAN) {
                    return;
                }
                if (abstractArrow.hasAttached(AetherDataAttachments.PHOENIX_ARROW)) {
                    var data = abstractArrow.getAttached(AetherDataAttachments.PHOENIX_ARROW);
                    if (data.isPhoenixArrow() && data.getFireTime() > 0) {
                        impactedEntity.igniteForSeconds(data.getFireTime());
                    }
                }
            }
        }

        /**
         * Prevents an entity from being hurt by a lightning strike if {@link LightningTrackerAttachment#getOwner(Level)} finds an owner associated with the lightning, if it was summoned through usage of a weapon.
         *
         * @param entity    The {@link Entity} struck by the lightning bolt.
         * @param lightning The {@link LightningBolt} that struck the entity.
         * @return Whether the entity being hurt by the lightning strike should be prevented, as a {@link Boolean}.
         * @see com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onLightningStrike(Entity, LightningBolt, MutableBoolean)
         */
        public static boolean lightningTracking(Entity entity, LightningBolt lightning) {
            if (entity instanceof LivingEntity livingEntity) {
                if (lightning.hasAttached(AetherDataAttachments.LIGHTNING_TRACKER)) {
                    var tracker = lightning.getAttached(AetherDataAttachments.LIGHTNING_TRACKER);
                    Entity owner = tracker.getOwner(lightning.level());
                    if (owner != null) {
                        return livingEntity == owner || livingEntity == owner.getVehicle() || owner.getPassengers().contains(livingEntity);
                    }
                }
            }
            return false;
        }

        /**
         * Reduces the effectiveness of non-Aether weapons against Aether mobs.
         *
         * @param target The target {@link LivingEntity} being attacked.
         * @param source The attacking {@link Entity}.
         * @param damage The original damage as a {@link Float}.
         * @return The modified damage as a {@link Float}.
         * @see com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onEntityDamage(LivingDamageEvent.Pre)
         */
        public static float reduceWeaponEffectiveness(LivingEntity target, Entity source, float damage) {
            if (AetherConfig.SERVER.tools_debuff.get() && !target.level().isClientSide()) { // Checks if tool debuffs are enabled and if the level is on the server side.
                double pow = Math.max(Math.pow(damage, damage > 1.0 ? 0.6 : 1.6), 1.0);
                if (source instanceof LivingEntity livingEntity) {
                    ItemStack stack = livingEntity.getMainHandItem();
                    if ((target.getType().getDescriptionId().startsWith("entity.aether") || target.is(AetherTags.Entities.TREATED_AS_AETHER_ENTITY)) && !target.is(AetherTags.Entities.TREATED_AS_VANILLA_ENTITY)) { // Checks if the target is an Aether entity.
                        if (!stack.isEmpty()) {
                            AtomicDouble value = new AtomicDouble(); // Used for checking if the attack damage from the item is greater than the attacker's default (fist).
                            stack.forEachModifier(EquipmentSlot.MAINHAND, (attribute, modifier) -> {
                                if (attribute.is(Attributes.ATTACK_DAMAGE)) {
                                    value.set(value.get() + modifier.amount());
                                }
                            });
                            if (livingEntity.getAttribute(Attributes.ATTACK_DAMAGE) != null && value.get() > livingEntity.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) && !stack.getItem().getDescriptionId().startsWith("item.aether.") && !stack.is(AetherTags.Items.TREATED_AS_AETHER_ITEM)) { // Checks if the attacking item is non-Aether.
                                damage = (float) pow;
                            }
                        }
                    }
                } else if (source instanceof Projectile) { // Used for reducing projectile weapon effectiveness.
                    if ((target.getType().getDescriptionId().startsWith("entity.aether") || target.is(AetherTags.Entities.TREATED_AS_AETHER_ENTITY)) && !target.is(AetherTags.Entities.TREATED_AS_VANILLA_ENTITY)) { // Checks if the target is an Aether entity.
                        if ((!source.getType().getDescriptionId().startsWith("entity.aether") && !source.is(AetherTags.Entities.TREATED_AS_AETHER_ENTITY)) // Checks if the projectile is non-Aether.
                                && (!(source instanceof AbstractArrow abstractArrow) || !abstractArrow.hasAttached(AetherDataAttachments.PHOENIX_ARROW) || !abstractArrow.getAttachedOrCreate(AetherDataAttachments.PHOENIX_ARROW).isPhoenixArrow())) { // Special check against Phoenix Arrows.
                            damage = (float) pow;
                        }
                    }
                }
            }
            return damage;
        }

        /**
         * Reduces the effectiveness of non-Aether armor against Aether mobs.
         *
         * @param target The target {@link LivingEntity} wearing the armor.
         * @param source The attacking {@link Entity}.
         * @param damage The original damage as a {@link Float}.
         * @return The modified damage as a {@link Float}.
         * @see com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener#onEntityDamage(LivingDamageEvent.Pre)
         */
        public static float reduceArmorEffectiveness(LivingEntity target, @Nullable Entity source, float damage) {
            if (source != null) {
                if ((source.getType().getDescriptionId().startsWith("entity.aether") || source.is(AetherTags.Entities.TREATED_AS_AETHER_ENTITY) && !source.is(AetherTags.Entities.TREATED_AS_VANILLA_ENTITY))) { // Checks if the attacker is an Aether entity.
                    for (EquipmentSlot slot : EquipmentSlotGroup.ARMOR.slots()) {
                        ItemStack stack = target.getItemBySlot(slot);
                        Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
                        if (equippable != null && equippable.slot() == slot && !stack.getItem().getDescriptionId().startsWith("item.aether.") && !stack.is(AetherTags.Items.TREATED_AS_AETHER_ITEM)) { // Checks if the armor is non-Aether.
                            AtomicDouble value = new AtomicDouble();
                            stack.forEachModifier(slot, (attribute, modifier) -> { // Checks if the armor has an armor modifier attribute.
                                if (attribute.is(Attributes.ARMOR)) {
                                    value.set(value.get() + (modifier.amount() / 15));
                                }
                            });
                            damage += (float) value.get();
                        }
                    }
                }
            }
            return damage;
        }
    }
}
