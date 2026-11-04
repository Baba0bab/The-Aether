package com.aetherteam.aether.entity.monster.dungeon.boss.goal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;
import net.minecraft.world.entity.player.Player;


/**
 * A variant of {@link NearestAttackableTargetGoal} that ignores line of sight, as the Slider is often obstructed by its own boss room.
 */
public class SliderNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final TargetingConditions sliderTargetConditions;

    public SliderNearestAttackableTargetGoal(Mob mob, Class<T> targetType, boolean mustSee) {
        this(mob, targetType, 10, mustSee, false, null);
    }

    public SliderNearestAttackableTargetGoal(Mob mob, Class<T> targetType, int randomInterval, boolean mustSee, boolean mustReach, @Nullable TargetingConditions.Selector targetPredicate) {
        super(mob, targetType, randomInterval, mustSee, mustReach, targetPredicate);
        this.sliderTargetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).ignoreLineOfSight().selector(targetPredicate);
    }

    @Override
    protected void findTarget() {
        ServerLevel level = getServerLevel(this.mob);
        TargetingConditions conditions = this.sliderTargetConditions.range(this.getFollowDistance());
        if (this.targetType != Player.class && this.targetType != ServerPlayer.class) {
            this.target = level.getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), (entity) -> true), conditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        } else {
            this.target = level.getNearestPlayer(conditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        }
    }
}
