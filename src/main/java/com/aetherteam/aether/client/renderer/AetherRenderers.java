package com.aetherteam.aether.client.renderer;

import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import net.minecraft.world.entity.EntityTypes;
import com.aetherteam.aether.Aether;
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
import com.aetherteam.nitrogen.client.renderer.NitrogenRenderers;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.model.animal.pig.PigModel;
import net.minecraft.client.model.monster.slime.SlimeModel;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.model.object.armorstand.ArmorStandModel;
import net.minecraft.client.model.object.chest.ChestModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.StandingSignRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;

public class AetherRenderers {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(AetherBlockEntityTypes.SKYROOT_SIGN.get(), StandingSignRenderer::new);
        event.registerBlockEntityRenderer(AetherBlockEntityTypes.SKYROOT_HANGING_SIGN.get(), HangingSignRenderer::new);
        event.registerBlockEntityRenderer(AetherBlockEntityTypes.CHEST_MIMIC.get(), ChestMimicRenderer::new);
        event.registerBlockEntityRenderer(AetherBlockEntityTypes.TREASURE_CHEST.get(), TreasureChestRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.PHYG.get(), PhygRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.FLYING_COW.get(), FlyingCowRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.SHEEPUFF.get(), SheepuffRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.AERBUNNY.get(), AerbunnyRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.MOA.get(), MoaRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.AERWHALE.get(), AerwhaleRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.BLUE_SWET.get(), BlueSwetRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.GOLDEN_SWET.get(), GoldenSwetRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.WHIRLWIND.get(), WhirlwindRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.EVIL_WHIRLWIND.get(), WhirlwindRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.AECHOR_PLANT.get(), AechorPlantRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.COCKATRICE.get(), CockatriceRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.ZEPHYR.get(), ZephyrRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.MIMIC.get(), MimicRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.SENTRY.get(), SentryRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.VALKYRIE.get(), ValkyrieRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.FIRE_MINION.get(), FireMinionRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.SLIDER.get(), SliderRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.VALKYRIE_QUEEN.get(), ValkyrieQueenRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.SUN_SPIRIT.get(), SunSpiritRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.SKYROOT_BOAT.get(), (context) -> new SkyrootBoatRenderer(context, AetherModelLayers.SKYROOT_BOAT, SkyrootBoatRenderer.SKYROOT_BOAT));
        event.registerEntityRenderer(AetherEntityTypes.SKYROOT_CHEST_BOAT.get(), (context) -> new SkyrootBoatRenderer(context, AetherModelLayers.SKYROOT_CHEST_BOAT, SkyrootBoatRenderer.SKYROOT_CHEST_BOAT));
        event.registerEntityRenderer(AetherEntityTypes.CLOUD_MINION.get(), CloudMinionRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.COLD_PARACHUTE.get(), (context) -> new ParachuteRenderer(context, AetherBlocks.COLD_AERCLOUD));
        event.registerEntityRenderer(AetherEntityTypes.GOLDEN_PARACHUTE.get(), (context) -> new ParachuteRenderer(context, AetherBlocks.GOLDEN_AERCLOUD));
        event.registerEntityRenderer(AetherEntityTypes.FLOATING_BLOCK.get(), FloatingBlockRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.TNT_PRESENT.get(), TntPresentRenderer::new);

        event.registerEntityRenderer(AetherEntityTypes.ZEPHYR_SNOWBALL.get(), (context) -> new ThrownItemRenderer<>(context, 3.0F, true));
        event.registerEntityRenderer(AetherEntityTypes.CLOUD_CRYSTAL.get(), CloudCrystalRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.FIRE_CRYSTAL.get(), FireCrystalRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.ICE_CRYSTAL.get(), IceCrystalRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.THUNDER_CRYSTAL.get(), ThunderCrystalRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.GOLDEN_DART.get(), GoldenDartRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.POISON_DART.get(), PoisonDartRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.ENCHANTED_DART.get(), EnchantedDartRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.POISON_NEEDLE.get(), PoisonNeedleRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.LIGHTNING_KNIFE.get(), LightningKnifeRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.HAMMER_PROJECTILE.get(), HammerProjectileRenderer::new);
    }

    /**
     * @see AetherClient#eventSetup()
     */
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AetherModelLayers.CHEST_MIMIC, ChestModel::createSingleBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.PHYG, () -> PigModel.createBodyLayer(CubeDeformation.NONE));
        event.registerLayerDefinition(AetherModelLayers.PHYG_WINGS, () -> QuadrupedWingsModel.createMainLayer(10.0F));
        event.registerLayerDefinition(AetherModelLayers.PHYG_SADDLE, () -> PigModel.createBodyLayer(new CubeDeformation(0.5F)));
        event.registerLayerDefinition(AetherModelLayers.PHYG_BABY, () -> PigModel.createBodyLayer(CubeDeformation.NONE).apply(PigModel.BABY_TRANSFORMER));
        event.registerLayerDefinition(AetherModelLayers.PHYG_BABY_SADDLE, () -> PigModel.createBodyLayer(new CubeDeformation(0.5F)).apply(PigModel.BABY_TRANSFORMER));
        event.registerLayerDefinition(AetherModelLayers.PHYG_HALO, () -> HaloModel.createLayer(3.0F, -4.0F, 12.0F, -6.0F));
        event.registerLayerDefinition(AetherModelLayers.FLYING_COW, CowModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.FLYING_COW_WINGS, () -> QuadrupedWingsModel.createMainLayer(0.0F));
        event.registerLayerDefinition(AetherModelLayers.FLYING_COW_SADDLE, CowModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.FLYING_COW_BABY, () -> CowModel.createBodyLayer().apply(CowModel.BABY_TRANSFORMER));
        event.registerLayerDefinition(AetherModelLayers.FLYING_COW_BABY_SADDLE, () -> CowModel.createBodyLayer().apply(CowModel.BABY_TRANSFORMER));
        event.registerLayerDefinition(AetherModelLayers.SHEEPUFF, SheepuffModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.SHEEPUFF_WOOL, () -> SheepuffWoolModel.createFurLayer(new CubeDeformation(1.75F), 0.0F));
        event.registerLayerDefinition(AetherModelLayers.SHEEPUFF_WOOL_PUFFED, () -> SheepuffWoolModel.createFurLayer(new CubeDeformation(3.75F), 2.0F));
        event.registerLayerDefinition(AetherModelLayers.SHEEPUFF_BABY, () -> SheepuffModel.createBodyLayer().apply(SheepuffModel.BABY_TRANSFORMER));
        event.registerLayerDefinition(AetherModelLayers.SHEEPUFF_BABY_WOOL, () -> SheepuffWoolModel.createFurLayer(new CubeDeformation(1.75F), 0.0F).apply(SheepuffModel.BABY_TRANSFORMER));
        event.registerLayerDefinition(AetherModelLayers.SHEEPUFF_BABY_WOOL_PUFFED, () -> SheepuffWoolModel.createFurLayer(new CubeDeformation(3.75F), 2.0F).apply(SheepuffModel.BABY_TRANSFORMER));
        event.registerLayerDefinition(AetherModelLayers.AERBUNNY, AerbunnyModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.MOA, () -> MoaModel.createBodyLayer(CubeDeformation.NONE));
        event.registerLayerDefinition(AetherModelLayers.MOA_HAT, () -> MoaModel.createBodyLayer(new CubeDeformation(0.23F)));
        event.registerLayerDefinition(AetherModelLayers.MOA_SADDLE, () -> MoaModel.createBodyLayer(new CubeDeformation(0.27F)));
        event.registerLayerDefinition(AetherModelLayers.AERWHALE, AerwhaleModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.AERWHALE_CLASSIC, ClassicAerwhaleModel::createBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.SWET, SlimeModel::createInnerBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.SWET_OUTER, SlimeModel::createOuterBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.AECHOR_PLANT, AechorPlantModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.COCKATRICE, () -> CockatriceModel.createBodyLayer(CubeDeformation.NONE));
        event.registerLayerDefinition(AetherModelLayers.ZEPHYR, ZephyrModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.ZEPHYR_TRANSPARENCY, ZephyrModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.ZEPHYR_CLASSIC, ClassicZephyrModel::createBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.MIMIC, MimicModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.SENTRY, SlimeModel::createOuterBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.VALKYRIE, ValkyrieModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.VALKYRIE_WINGS, () -> ValkyrieWingsModel.createMainLayer(4.5F, 2.5F));
        event.registerLayerDefinition(AetherModelLayers.FIRE_MINION, FireMinionModel::createBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.SLIDER, SliderModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.VALKYRIE_QUEEN, ValkyrieModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.VALKYRIE_QUEEN_WINGS, () -> ValkyrieWingsModel.createMainLayer(4.5F, 2.5F));
        event.registerLayerDefinition(AetherModelLayers.SUN_SPIRIT, SunSpiritModel::createBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.SKYROOT_BOAT, BoatModel::createBoatModel);
        event.registerLayerDefinition(AetherModelLayers.SKYROOT_CHEST_BOAT, BoatModel::createChestBoatModel);

        event.registerLayerDefinition(AetherModelLayers.CLOUD_MINION, CloudMinionModel::createBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.CLOUD_CRYSTAL, CrystalModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.THUNDER_CRYSTAL, CrystalModel::createBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.VALKYRIE_ARMOR_WINGS, () -> ValkyrieWingsModel.createMainLayer(3.5F, 3.375F));

        event.registerLayerDefinition(AetherModelLayers.PENDANT, PendantModel::createLayer);
        event.registerLayerDefinition(AetherModelLayers.GLOVES, () -> GlovesModel.createLayer(new CubeDeformation(0.5F), false, false));
        event.registerLayerDefinition(AetherModelLayers.GLOVES_TRIM, () -> GlovesModel.createLayer(new CubeDeformation(0.5F), false, true));
        event.registerLayerDefinition(AetherModelLayers.GLOVES_SLIM, () -> GlovesModel.createLayer(new CubeDeformation(0.5F), true, false));
        event.registerLayerDefinition(AetherModelLayers.GLOVES_TRIM_SLIM, () -> GlovesModel.createLayer(new CubeDeformation(0.5F), true, true));
        event.registerLayerDefinition(AetherModelLayers.GLOVES_FIRST_PERSON, () -> GlovesModel.createLayer(new CubeDeformation(0.25F), false, false));
        event.registerLayerDefinition(AetherModelLayers.GLOVES_TRIM_FIRST_PERSON, () -> GlovesModel.createLayer(new CubeDeformation(0.25F), false, true));
        event.registerLayerDefinition(AetherModelLayers.SHIELD_OF_REPULSION, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1.1F), false), 64, 64));
        event.registerLayerDefinition(AetherModelLayers.SHIELD_OF_REPULSION_SLIM, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(1.15F), true), 64, 64));
        event.registerLayerDefinition(AetherModelLayers.SHIELD_OF_REPULSION_ARM, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(0.4F), false), 64, 64));
        event.registerLayerDefinition(AetherModelLayers.CAPE, CapeModel::createLayer);

        event.registerLayerDefinition(AetherModelLayers.PLAYER_HALO, () -> HaloModel.createLayer(0.0F, 0.0F, 0.0F, 0.0F));
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
    public static void addEntityLayers(EntityRenderersEvent.AddLayers event) {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        for (PlayerModelType type : event.getSkins()) {
            AvatarRenderer<AbstractClientPlayer> playerRenderer = event.getPlayerRenderer(type);
            if (playerRenderer != null) {
                playerRenderer.addLayer(new DeveloperGlowLayer<>(playerRenderer));
                playerRenderer.addLayer(new DartLayer<>(playerRenderer, modelSet, AetherRenderStateModifiers.GOLDEN_DART_COUNT, GoldenDartRenderer.GOLDEN_DART_TEXTURE, 1.0F));
                playerRenderer.addLayer(new DartLayer<>(playerRenderer, modelSet, AetherRenderStateModifiers.ENCHANTED_DART_COUNT, GoldenDartRenderer.GOLDEN_DART_TEXTURE, 2.0F));
                playerRenderer.addLayer(new DartLayer<>(playerRenderer, modelSet, AetherRenderStateModifiers.GOLDEN_DART_COUNT, GoldenDartRenderer.GOLDEN_DART_TEXTURE, 3.0F));
                playerRenderer.addLayer(new PlayerHaloLayer<>(playerRenderer, modelSet));
                playerRenderer.addLayer(new PlayerWingsLayer<>(playerRenderer, modelSet));
            }
        }
        LivingEntityRenderer<ArmorStand, ArmorStandRenderState, ArmorStandModel> renderer = event.getRenderer(EntityTypes.ARMOR_STAND);
        if (renderer != null) {
            renderer.addLayer(new ArmorStandCapeLayer(renderer));
        }
    }
}
