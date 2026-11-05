package com.aetherteam.aether.entity;

import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityTypes;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.mixin.mixins.common.accessor.EntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public final class EntityUtil {
    /**
     * Copies rotation values from one entity to another.
     *
     * @param entity The {@link Entity} to copy rotations to.
     * @param source The {@link Entity} to copy rotations from.
     */
    public static void copyRotations(Entity entity, Entity source) {
        entity.setYRot((float) Mth.rotLerp(1.0 / 3.0, source.getYRot(), source.yRotO));
        entity.setXRot((float) Mth.rotLerp(1.0 / 3.0, source.getXRot(), source.xRotO));
        entity.setYBodyRot((float) Mth.rotLerp(1.0 / 3.0, source.getYRot(), source.yRotO));
        entity.setYHeadRot((float) Mth.rotLerp(1.0 / 3.0, source.getYRot(), source.yRotO));
    }

    /**
     * Spawns explosion particles used for various entity movement effects.
     *
     * @param entity The {@link Entity} to spawn the particles for.
     */
    public static void spawnMovementExplosionParticles(Entity entity) {
        RandomSource random = ((EntityAccessor) entity).aether$getRandom();
        double d0 = random.nextGaussian() * 0.02;
        double d1 = random.nextGaussian() * 0.02;
        double d2 = random.nextGaussian() * 0.02;
        double d3 = 10.0;
        double x = entity.getX() + ((double) random.nextFloat() * entity.getBbWidth() * 2.0) - entity.getBbWidth() - d0 * d3;
        double y = entity.getY() + ((double) random.nextFloat() * entity.getBbHeight()) - d1 * d3;
        double z = entity.getZ() + ((double) random.nextFloat() * entity.getBbWidth() * 2.0) - entity.getBbWidth() - d2 * d3;
        entity.level().addParticle(ParticleTypes.POOF, x, y, z, d0, d1, d2);
    }

    /**
     * Spawns explosion particles used for various entity summoning effects.
     *
     * @param entity The {@link Entity} to spawn the particles for.
     */
    public static void spawnSummoningExplosionParticles(Entity entity) {
        RandomSource random = ((EntityAccessor) entity).aether$getRandom();
        for (int i = 0; i < 20; ++i) {
            double d0 = random.nextGaussian() * 0.02;
            double d1 = random.nextGaussian() * 0.02;
            double d2 = random.nextGaussian() * 0.02;
            double d3 = 10.0;
            double x = entity.getX(0.0) - d0 * d3;
            double y = entity.getRandomY() - d1 * d3;
            double z = entity.getRandomZ(1.0) - d2 * d3;
            entity.level().addParticle(ParticleTypes.POOF, x, y, z, d0, d1, d2);
        }
    }

    /**
     * Spawns particles for block removal interactions.
     *
     * @param level The {@link Level} to spawn the particles in.
     * @param pos   The {@link BlockPos} to spawn the particles at.
     */
    public static void spawnRemovalParticles(Level level, BlockPos pos) {
        double a = pos.getX() + 0.5 + (double) (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.375;
        double b = pos.getY() + 0.5 + (double) (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.375;
        double c = pos.getZ() + 0.5 + (double) (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.375;
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.POOF, a, b, c, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    /**
     * Summons a {@link LightningBolt}.
     *
     * @param projectile The {@link Projectile} that is summoning lightning.
     */
    public static void summonLightningFromProjectile(Projectile projectile) {
        LightningBolt lightningBolt = EntityTypes.LIGHTNING_BOLT.create(projectile.level(), EntitySpawnReason.MOB_SUMMONED);
        if (lightningBolt != null) {
            lightningBolt.getAttachedOrCreate(AetherDataAttachments.LIGHTNING_TRACKER).setOwner(projectile.getOwner());
            lightningBolt.setPos(projectile.getX(), projectile.getY(), projectile.getZ());
            projectile.level().addFreshEntity(lightningBolt);
        }
    }

    /**
     * Checks whether a square surface area with a given radius is exposed to sky light.
     *
     * @param level The {@link LevelAccessor} to check in.
     * @param pos The starting {@link BlockPos}.
     * @param hitboxRadius The {@link Integer} radius of the area, correlating to the entity's hitbox.
     * @return Whether the area is exposed to sky, as a {@link Boolean}.
     */
    public static boolean wholeHitboxCanSeeSky(LevelAccessor level, BlockPos pos, int hitboxRadius) {
        boolean flag = true;
        for (int xOffset = -hitboxRadius; xOffset <= hitboxRadius; xOffset++) {
            for (int zOffset = -hitboxRadius; zOffset <= hitboxRadius; zOffset++) {
                flag = flag && level.canSeeSky(pos.offset(xOffset, 0, zOffset));
            }
        }
        return flag;
    }

    /**
     * Checks whether a mob should be swimming upwards in a fluid.
     *
     * @param mob The {@link Mob}.
     * @return Whether the mob should swim, as a {@link Boolean}.
     * @see net.minecraft.world.entity.ai.goal.FloatGoal#canUse()
     */
    public static boolean shouldSwim(Mob mob) {
        return mob.isInWater() && mob.getFluidHeight(FluidTags.WATER) > mob.getFluidJumpThreshold() || mob.isInLava();
    }

    /**
     * Damages the item that a {@link LivingEntity} is blocking with, such as a shield.
     *
     * @param entity The blocking {@link LivingEntity}.
     * @param damage The {@link Float} amount of damage to deal to the item.
     */
    public static void hurtBlockingItem(LivingEntity entity, float damage) {
        ItemStack blockingItem = entity.getItemBlockingWith();
        if (blockingItem != null) {
            BlocksAttacks blocksAttacks = blockingItem.get(DataComponents.BLOCKS_ATTACKS);
            if (blocksAttacks != null) {
                blocksAttacks.hurtBlockingItem(entity.level(), blockingItem, entity, entity.getUsedItemHand(), damage);
            }
        }
    }
}
