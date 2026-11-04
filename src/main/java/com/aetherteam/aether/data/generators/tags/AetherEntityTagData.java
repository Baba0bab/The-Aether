//package com.aetherteam.aether.data.generators.tags;
//
//import net.minecraft.core.registries.Registries;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.tags.TagKey;
//import net.minecraft.core.registries.BuiltInRegistries;
//import com.aetherteam.nitrogen.data.providers.IntrinsicTagAppender;
//import net.minecraft.world.entity.EntityTypes;
//import com.aetherteam.aether.Aether;
//import com.aetherteam.aether.AetherTags;
//import com.aetherteam.aether.entity.AetherEntityTypes;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.data.PackOutput;
//import net.minecraft.data.tags.EntityTypeTagsProvider;
//import net.minecraft.resources.Identifier;
//import net.minecraft.tags.EntityTypeTags;
//import net.minecraft.world.entity.EntityType;
//import net.neoforged.neoforge.common.Tags;
//
//import java.util.concurrent.CompletableFuture;
//
//public class AetherEntityTagData extends EntityTypeTagsProvider {
//    public AetherEntityTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
//        super(output, registries, Aether.MODID);
//    }
//
//    @Override
//    protected IntrinsicTagAppender<EntityType<?>> tag(TagKey<EntityType<?>> tag) {
//        return new IntrinsicTagAppender<>(super.tag(tag), (element) -> BuiltInRegistries.ENTITY_TYPE.getResourceKey(element).orElseThrow());
//    }
//
//    @Override
//    public void addTags(HolderLookup.Provider provider) {
//        // Aether
//        this.tag(AetherTags.Entities.SWETS).add(
//                AetherEntityTypes.BLUE_SWET.get(),
//                AetherEntityTypes.GOLDEN_SWET.get());
//        this.tag(AetherTags.Entities.WHIRLWIND_UNAFFECTED).addTag(Tags.EntityTypes.BOSSES).add(
//            AetherEntityTypes.AECHOR_PLANT.get(),
//            AetherEntityTypes.WHIRLWIND.get(),
//            AetherEntityTypes.EVIL_WHIRLWIND.get());
//        this.tag(AetherTags.Entities.PIGS).add(
//                EntityTypes.PIG,
//                AetherEntityTypes.PHYG.get(),
//                EntityTypes.PIGLIN,
//                EntityTypes.PIGLIN_BRUTE,
//                EntityTypes.ZOMBIFIED_PIGLIN,
//                EntityTypes.HOGLIN,
//                EntityTypes.ZOGLIN);
//        this.tag(AetherTags.Entities.FIRE_MOB).add(
//                EntityTypes.BLAZE,
//                AetherEntityTypes.FIRE_MINION.get());
//        this.tag(AetherTags.Entities.NO_SKYROOT_DOUBLE_DROPS).add(
//                EntityTypes.PLAYER,
//                EntityTypes.WITHER,
//                EntityTypes.ENDER_DRAGON);
//        this.tag(AetherTags.Entities.NO_AMBROSIUM_DROPS).add(EntityTypes.PLAYER);
//        this.tag(AetherTags.Entities.UNLAUNCHABLE).add(AetherEntityTypes.AECHOR_PLANT.get());
//        this.tag(AetherTags.Entities.NO_CANDY_CANE_DROPS).add(EntityTypes.PLAYER);
//        this.tag(AetherTags.Entities.DEFLECTABLE_PROJECTILES).addTag(EntityTypeTags.ARROWS).add(
//                EntityTypes.EGG,
//                EntityTypes.SMALL_FIREBALL,
//                EntityTypes.FIREBALL,
//                EntityTypes.SNOWBALL,
//                EntityTypes.LLAMA_SPIT,
//                EntityTypes.TRIDENT,
//                EntityTypes.SHULKER_BULLET,
//                AetherEntityTypes.GOLDEN_DART.get(),
//                AetherEntityTypes.POISON_DART.get(),
//                AetherEntityTypes.ENCHANTED_DART.get(),
//                AetherEntityTypes.POISON_NEEDLE.get(),
//                AetherEntityTypes.ZEPHYR_SNOWBALL.get(),
//                AetherEntityTypes.LIGHTNING_KNIFE.get(),
//                AetherEntityTypes.HAMMER_PROJECTILE.get());
//        this.tag(AetherTags.Entities.IGNORE_INVISIBILITY).addTag(Tags.EntityTypes.BOSSES).add(
//                EntityTypes.GUARDIAN,
//                EntityTypes.ELDER_GUARDIAN);
//        this.tag(AetherTags.Entities.UNHOOKABLE).add(
//                AetherEntityTypes.AECHOR_PLANT.get(),
//                AetherEntityTypes.WHIRLWIND.get(),
//                AetherEntityTypes.EVIL_WHIRLWIND.get(),
//                AetherEntityTypes.SLIDER.get(),
//                AetherEntityTypes.SUN_SPIRIT.get());
//        this.tag(AetherTags.Entities.TREATED_AS_AETHER_ENTITY);
//        this.tag(AetherTags.Entities.TREATED_AS_VANILLA_ENTITY);
//        this.tag(AetherTags.Entities.DUNGEON_ENTITIES).add(
//                AetherEntityTypes.SENTRY.get(),
//                AetherEntityTypes.SLIDER.get(),
//                AetherEntityTypes.VALKYRIE.get(),
//                AetherEntityTypes.VALKYRIE_QUEEN.get(),
//                AetherEntityTypes.THUNDER_CRYSTAL.get(),
//                AetherEntityTypes.SUN_SPIRIT.get(),
//                AetherEntityTypes.FIRE_MINION.get(),
//                AetherEntityTypes.FIRE_CRYSTAL.get(),
//                AetherEntityTypes.ICE_CRYSTAL.get());
//        this.tag(AetherTags.Entities.SLIDER_DAMAGING_PROJECTILES)
//            .addOptional(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("quark", "pickarang")))
//            .addOptional(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("quark", "flamerang")));
//
//        // Forge
//        this.tag(Tags.EntityTypes.BOSSES).add(
//                AetherEntityTypes.SLIDER.get(),
//                AetherEntityTypes.VALKYRIE_QUEEN.get(),
//                AetherEntityTypes.SUN_SPIRIT.get()
//        );
//        this.tag(Tags.EntityTypes.CAPTURING_NOT_SUPPORTED).add(
//            AetherEntityTypes.SLIDER.get(),
//            AetherEntityTypes.VALKYRIE_QUEEN.get(),
//            AetherEntityTypes.SUN_SPIRIT.get()
//        );
//        this.tag(Tags.EntityTypes.TELEPORTING_NOT_SUPPORTED).add(
//            AetherEntityTypes.SLIDER.get(),
//            AetherEntityTypes.VALKYRIE_QUEEN.get(),
//            AetherEntityTypes.SUN_SPIRIT.get()
//        );
//
//        // Vanilla
//        this.tag(EntityTypeTags.IMPACT_PROJECTILES).add(
//                AetherEntityTypes.GOLDEN_DART.get(),
//                AetherEntityTypes.POISON_DART.get(),
//                AetherEntityTypes.ENCHANTED_DART.get(),
//                AetherEntityTypes.LIGHTNING_KNIFE.get(),
//                AetherEntityTypes.HAMMER_PROJECTILE.get());
//        this.tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add(AetherEntityTypes.AERBUNNY.get());
//        this.tag(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES).add(AetherEntityTypes.FIRE_MINION.get());
//        this.tag(EntityTypeTags.FROG_FOOD).add(
//                AetherEntityTypes.BLUE_SWET.get(),
//                AetherEntityTypes.GOLDEN_SWET.get(),
//                AetherEntityTypes.SENTRY.get());
//        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(
//                AetherEntityTypes.PHYG.get(),
//                AetherEntityTypes.FLYING_COW.get(),
//                AetherEntityTypes.MOA.get(),
//                AetherEntityTypes.AERWHALE.get(),
//                AetherEntityTypes.AERBUNNY.get(),
//                AetherEntityTypes.WHIRLWIND.get(),
//                AetherEntityTypes.EVIL_WHIRLWIND.get(),
//                AetherEntityTypes.COCKATRICE.get(),
//                AetherEntityTypes.ZEPHYR.get(),
//                AetherEntityTypes.SLIDER.get(),
//                AetherEntityTypes.VALKYRIE.get(),
//                AetherEntityTypes.VALKYRIE_QUEEN.get(),
//                AetherEntityTypes.SUN_SPIRIT.get()
//        );
//        this.tag(EntityTypeTags.DISMOUNTS_UNDERWATER).add(
//                AetherEntityTypes.PHYG.get(),
//                AetherEntityTypes.FLYING_COW.get(),
//                AetherEntityTypes.MOA.get(),
//                AetherEntityTypes.AERBUNNY.get(),
//                AetherEntityTypes.BLUE_SWET.get(),
//                AetherEntityTypes.GOLDEN_SWET.get()
//        );
//        this.tag(EntityTypeTags.REDIRECTABLE_PROJECTILE).add(
//            AetherEntityTypes.ZEPHYR_SNOWBALL.get()
//        );
//    }
//}
