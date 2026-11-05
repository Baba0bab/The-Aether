package com.aetherteam.aether.client.renderer;

import net.minecraft.world.level.block.entity.BlockEntityType;

import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;

import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

import net.minecraft.world.entity.EntityTypes;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.blockentity.AetherBlockEntityTypes;
import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.renderer.accessory.GlovesRenderer;
import eu.pb4.trinkets.api.client.TrinketRendererRegistry;
import com.aetherteam.aether.client.renderer.accessory.PendantRenderer;
import com.aetherteam.aether.client.renderer.accessory.ShieldOfRepulsionRenderer;
import com.aetherteam.aether.client.renderer.accessory.layer.ArmorStandCapeLayer;
import com.aetherteam.aether.client.renderer.accessory.model.CapeModel;
import com.aetherteam.aether.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aether.client.renderer.accessory.model.PendantModel;
import com.aetherteam.aether.client.renderer.blockentity.ChestMimicRenderer;
import com.aetherteam.aether.client.renderer.blockentity.TreasureChestRenderer;
import com.aetherteam.aether.client.renderer.entity.*;
import com.aetherteam.aether.client.renderer.entity.model.*;
import com.aetherteam.aether.client.renderer.player.layer.DartLayer;
import com.aetherteam.aether.client.renderer.player.layer.DeveloperGlowLayer;
import com.aetherteam.aether.client.renderer.player.layer.PlayerHaloLayer;
import com.aetherteam.aether.client.renderer.player.layer.PlayerWingsLayer;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.client.model.*;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.model.animal.pig.PigModel;
import net.minecraft.client.model.monster.slime.SlimeModel;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.model.object.armorstand.ArmorStandModel;
import net.minecraft.client.model.object.chest.ChestModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.StandingSignRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;
import net.minecraft.world.entity.decoration.ArmorStand;

public class AetherRenderers {
    /**
     * @see AetherClient#eventSetup()
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void registerEntityRenderers() {
        BlockEntityRendererRegistry.register((BlockEntityType) AetherBlockEntityTypes.SKYROOT_SIGN.get(), StandingSignRenderer::new);
        BlockEntityRendererRegistry.register((BlockEntityType) AetherBlockEntityTypes.SKYROOT_HANGING_SIGN.get(), HangingSignRenderer::new);
        BlockEntityRendererRegistry.register(AetherBlockEntityTypes.CHEST_MIMIC.get(), ChestMimicRenderer::new);
        BlockEntityRendererRegistry.register(AetherBlockEntityTypes.TREASURE_CHEST.get(), TreasureChestRenderer::new);

        EntityRendererRegistry.register(AetherEntityTypes.PHYG.get(), PhygRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.FLYING_COW.get(), FlyingCowRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.SHEEPUFF.get(), SheepuffRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.AERBUNNY.get(), AerbunnyRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.MOA.get(), MoaRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.AERWHALE.get(), AerwhaleRenderer::new);

        EntityRendererRegistry.register(AetherEntityTypes.BLUE_SWET.get(), BlueSwetRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.GOLDEN_SWET.get(), GoldenSwetRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.WHIRLWIND.get(), WhirlwindRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.EVIL_WHIRLWIND.get(), WhirlwindRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.AECHOR_PLANT.get(), AechorPlantRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.COCKATRICE.get(), CockatriceRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.ZEPHYR.get(), ZephyrRenderer::new);

        EntityRendererRegistry.register(AetherEntityTypes.MIMIC.get(), MimicRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.SENTRY.get(), SentryRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.VALKYRIE.get(), ValkyrieRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.FIRE_MINION.get(), FireMinionRenderer::new);

        EntityRendererRegistry.register(AetherEntityTypes.SLIDER.get(), SliderRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.VALKYRIE_QUEEN.get(), ValkyrieQueenRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.SUN_SPIRIT.get(), SunSpiritRenderer::new);

        EntityRendererRegistry.register(AetherEntityTypes.SKYROOT_BOAT.get(), (context) -> new SkyrootBoatRenderer(context, AetherModelLayers.SKYROOT_BOAT, SkyrootBoatRenderer.SKYROOT_BOAT));
        EntityRendererRegistry.register(AetherEntityTypes.SKYROOT_CHEST_BOAT.get(), (context) -> new SkyrootBoatRenderer(context, AetherModelLayers.SKYROOT_CHEST_BOAT, SkyrootBoatRenderer.SKYROOT_CHEST_BOAT));
        EntityRendererRegistry.register(AetherEntityTypes.CLOUD_MINION.get(), CloudMinionRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.COLD_PARACHUTE.get(), (context) -> new ParachuteRenderer(context, AetherBlocks.COLD_AERCLOUD));
        EntityRendererRegistry.register(AetherEntityTypes.GOLDEN_PARACHUTE.get(), (context) -> new ParachuteRenderer(context, AetherBlocks.GOLDEN_AERCLOUD));
        EntityRendererRegistry.register(AetherEntityTypes.FLOATING_BLOCK.get(), FloatingBlockRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.TNT_PRESENT.get(), TntPresentRenderer::new);

        EntityRendererRegistry.register(AetherEntityTypes.ZEPHYR_SNOWBALL.get(), (context) -> new ThrownItemRenderer<>(context, 3.0F, true));
        EntityRendererRegistry.register(AetherEntityTypes.CLOUD_CRYSTAL.get(), CloudCrystalRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.FIRE_CRYSTAL.get(), FireCrystalRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.ICE_CRYSTAL.get(), IceCrystalRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.THUNDER_CRYSTAL.get(), ThunderCrystalRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.GOLDEN_DART.get(), GoldenDartRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.POISON_DART.get(), PoisonDartRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.ENCHANTED_DART.get(), EnchantedDartRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.POISON_NEEDLE.get(), PoisonNeedleRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.LIGHTNING_KNIFE.get(), LightningKnifeRenderer::new);
        EntityRendererRegistry.register(AetherEntityTypes.HAMMER_PROJECTILE.get(), HammerProjectileRenderer::new);
    }

    /**
     * @see AetherClient#eventSetup()
     */
    public static void registerLayerDefinitions() {
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.CHEST_MIMIC, ChestModel::createSingleBodyLayer);

        ModelLayerRegistry.registerModelLayer(AetherModelLayers.PHYG, () -> LegacyQuadrupedModels.createPigLayer(CubeDeformation.NONE));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.PHYG_WINGS, () -> QuadrupedWingsModel.createMainLayer(10.0F));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.PHYG_SADDLE, () -> PigModel.createBodyLayer(new CubeDeformation(0.5F)));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.PHYG_BABY, () -> LegacyQuadrupedModels.createPigLayer(CubeDeformation.NONE).apply(PigModel.BABY_TRANSFORMER));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.PHYG_BABY_SADDLE, () -> PigModel.createBodyLayer(new CubeDeformation(0.5F)).apply(PigModel.BABY_TRANSFORMER));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.PHYG_HALO, () -> HaloModel.createLayer(3.0F, -4.0F, 12.0F, -6.0F));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.FLYING_COW, LegacyQuadrupedModels::createCowLayer);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.FLYING_COW_WINGS, () -> QuadrupedWingsModel.createMainLayer(0.0F));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.FLYING_COW_SADDLE, LegacyQuadrupedModels::createCowLayer);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.FLYING_COW_BABY, () -> LegacyQuadrupedModels.createCowLayer().apply(CowModel.BABY_TRANSFORMER));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.FLYING_COW_BABY_SADDLE, () -> LegacyQuadrupedModels.createCowLayer().apply(CowModel.BABY_TRANSFORMER));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.SHEEPUFF, SheepuffModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.SHEEPUFF_WOOL, () -> SheepuffWoolModel.createFurLayer(new CubeDeformation(1.75F), 0.0F));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.SHEEPUFF_WOOL_PUFFED, () -> SheepuffWoolModel.createFurLayer(new CubeDeformation(3.75F), 2.0F));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.SHEEPUFF_BABY, () -> SheepuffModel.createBodyLayer().apply(SheepuffModel.BABY_TRANSFORMER));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.SHEEPUFF_BABY_WOOL, () -> SheepuffWoolModel.createFurLayer(new CubeDeformation(1.75F), 0.0F).apply(SheepuffModel.BABY_TRANSFORMER));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.SHEEPUFF_BABY_WOOL_PUFFED, () -> SheepuffWoolModel.createFurLayer(new CubeDeformation(3.75F), 2.0F).apply(SheepuffModel.BABY_TRANSFORMER));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.AERBUNNY, AerbunnyModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.MOA, () -> MoaModel.createBodyLayer(CubeDeformation.NONE));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.MOA_HAT, () -> MoaModel.createBodyLayer(new CubeDeformation(0.23F)));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.MOA_SADDLE, () -> MoaModel.createBodyLayer(new CubeDeformation(0.27F)));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.AERWHALE, AerwhaleModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.AERWHALE_CLASSIC, ClassicAerwhaleModel::createBodyLayer);

        ModelLayerRegistry.registerModelLayer(AetherModelLayers.SWET, SlimeModel::createInnerBodyLayer);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.SWET_OUTER, SlimeModel::createOuterBodyLayer);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.AECHOR_PLANT, AechorPlantModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.COCKATRICE, () -> CockatriceModel.createBodyLayer(CubeDeformation.NONE));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.ZEPHYR, ZephyrModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.ZEPHYR_TRANSPARENCY, ZephyrModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.ZEPHYR_CLASSIC, ClassicZephyrModel::createBodyLayer);

        ModelLayerRegistry.registerModelLayer(AetherModelLayers.MIMIC, MimicModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.SENTRY, SlimeModel::createOuterBodyLayer);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.VALKYRIE, ValkyrieModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.VALKYRIE_WINGS, () -> ValkyrieWingsModel.createMainLayer(4.5F, 2.5F));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.FIRE_MINION, FireMinionModel::createBodyLayer);

        ModelLayerRegistry.registerModelLayer(AetherModelLayers.SLIDER, SliderModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.VALKYRIE_QUEEN, ValkyrieModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.VALKYRIE_QUEEN_WINGS, () -> ValkyrieWingsModel.createMainLayer(4.5F, 2.5F));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.SUN_SPIRIT, SunSpiritModel::createBodyLayer);

        ModelLayerRegistry.registerModelLayer(AetherModelLayers.SKYROOT_BOAT, BoatModel::createBoatModel);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.SKYROOT_CHEST_BOAT, BoatModel::createChestBoatModel);

        ModelLayerRegistry.registerModelLayer(AetherModelLayers.CLOUD_MINION, CloudMinionModel::createBodyLayer);

        ModelLayerRegistry.registerModelLayer(AetherModelLayers.CLOUD_CRYSTAL, CrystalModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.THUNDER_CRYSTAL, CrystalModel::createBodyLayer);

        ModelLayerRegistry.registerModelLayer(AetherModelLayers.VALKYRIE_ARMOR_WINGS, () -> ValkyrieWingsModel.createMainLayer(3.5F, 3.375F));

        ModelLayerRegistry.registerModelLayer(AetherModelLayers.PENDANT, PendantModel::createLayer);
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.GLOVES, () -> GlovesModel.createLayer(new CubeDeformation(0.5F), false, false));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.GLOVES_TRIM, () -> GlovesModel.createLayer(new CubeDeformation(0.5F), false, true));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.GLOVES_SLIM, () -> GlovesModel.createLayer(new CubeDeformation(0.5F), true, false));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.GLOVES_TRIM_SLIM, () -> GlovesModel.createLayer(new CubeDeformation(0.5F), true, true));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.GLOVES_FIRST_PERSON, () -> GlovesModel.createLayer(new CubeDeformation(0.25F), false, false));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.GLOVES_TRIM_FIRST_PERSON, () -> GlovesModel.createLayer(new CubeDeformation(0.25F), false, true));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.SHIELD_OF_REPULSION, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1.1F), false), 64, 64));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.SHIELD_OF_REPULSION_SLIM, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1.15F), true), 64, 64));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.SHIELD_OF_REPULSION_ARM, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(0.4F), false), 64, 64));
        ModelLayerRegistry.registerModelLayer(AetherModelLayers.CAPE, CapeModel::createLayer);

        ModelLayerRegistry.registerModelLayer(AetherModelLayers.PLAYER_HALO, () -> HaloModel.createLayer(0.0F, 0.0F, 0.0F, 0.0F));
    }

    /**
     * @see com.aetherteam.aether.client.AetherClient#clientSetup()
     */
    public static void registerAccessoryRenderers() {
        TrinketRendererRegistry.registerRenderer(AetherItems.IRON_PENDANT.get(), (context) -> new PendantRenderer());
        TrinketRendererRegistry.registerRenderer(AetherItems.GOLDEN_PENDANT.get(), (context) -> new PendantRenderer());
        TrinketRendererRegistry.registerRenderer(AetherItems.ZANITE_PENDANT.get(), (context) -> new PendantRenderer());
        TrinketRendererRegistry.registerRenderer(AetherItems.ICE_PENDANT.get(), (context) -> new PendantRenderer());

        TrinketRendererRegistry.registerRenderer(AetherItems.LEATHER_GLOVES.get(), (context) -> new GlovesRenderer());
        TrinketRendererRegistry.registerRenderer(AetherItems.CHAINMAIL_GLOVES.get(), (context) -> new GlovesRenderer());
        TrinketRendererRegistry.registerRenderer(AetherItems.IRON_GLOVES.get(), (context) -> new GlovesRenderer());
        TrinketRendererRegistry.registerRenderer(AetherItems.GOLDEN_GLOVES.get(), (context) -> new GlovesRenderer());
        TrinketRendererRegistry.registerRenderer(AetherItems.DIAMOND_GLOVES.get(), (context) -> new GlovesRenderer());
        TrinketRendererRegistry.registerRenderer(AetherItems.NETHERITE_GLOVES.get(), (context) -> new GlovesRenderer());
        TrinketRendererRegistry.registerRenderer(AetherItems.ZANITE_GLOVES.get(), (context) -> new GlovesRenderer());
        TrinketRendererRegistry.registerRenderer(AetherItems.GRAVITITE_GLOVES.get(), (context) -> new GlovesRenderer());
        TrinketRendererRegistry.registerRenderer(AetherItems.NEPTUNE_GLOVES.get(), (context) -> new GlovesRenderer());
        TrinketRendererRegistry.registerRenderer(AetherItems.PHOENIX_GLOVES.get(), (context) -> new GlovesRenderer());
        TrinketRendererRegistry.registerRenderer(AetherItems.OBSIDIAN_GLOVES.get(), (context) -> new GlovesRenderer());
        TrinketRendererRegistry.registerRenderer(AetherItems.VALKYRIE_GLOVES.get(), (context) -> new GlovesRenderer());
        TrinketRendererRegistry.registerRenderer(AetherItems.SHIELD_OF_REPULSION.get(), (context) -> new ShieldOfRepulsionRenderer());
    }

    /**
     * @see AetherClient#eventSetup()
     */
    @SuppressWarnings("unchecked")
    public static void addEntityLayers() {
        LivingEntityRenderLayerRegistrationCallback.EVENT.register((entityType, entityRenderer, helper, context) -> {
            EntityModelSet modelSet = context.getModelSet();
            if (entityRenderer instanceof AvatarRenderer<?>) {
                AvatarRenderer<AbstractClientPlayer> playerRenderer = (AvatarRenderer<AbstractClientPlayer>) entityRenderer;
                helper.register(new DeveloperGlowLayer<>(playerRenderer));
                helper.register(new DartLayer<>(playerRenderer, modelSet, AetherRenderStateModifiers.GOLDEN_DART_COUNT, GoldenDartRenderer.GOLDEN_DART_TEXTURE, 1.0F));
                helper.register(new DartLayer<>(playerRenderer, modelSet, AetherRenderStateModifiers.ENCHANTED_DART_COUNT, GoldenDartRenderer.GOLDEN_DART_TEXTURE, 2.0F));
                helper.register(new DartLayer<>(playerRenderer, modelSet, AetherRenderStateModifiers.GOLDEN_DART_COUNT, GoldenDartRenderer.GOLDEN_DART_TEXTURE, 3.0F));
                helper.register(new PlayerHaloLayer<>(playerRenderer, modelSet));
                helper.register(new PlayerWingsLayer<>(playerRenderer, modelSet));
            }
            if (entityType == EntityTypes.ARMOR_STAND) {
                helper.register(new ArmorStandCapeLayer((LivingEntityRenderer<ArmorStand, ArmorStandRenderState, ArmorStandModel>) entityRenderer));
            }
        });
    }
}
