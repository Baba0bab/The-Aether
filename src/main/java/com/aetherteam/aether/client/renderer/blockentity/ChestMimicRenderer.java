package com.aetherteam.aether.client.renderer.blockentity;

import com.aetherteam.aether.block.dungeon.ChestMimicBlock;
import com.aetherteam.aether.blockentity.ChestMimicBlockEntity;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.model.object.chest.ChestModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import noobanidus.mods.lootr.fabric.config.ConfigManager;
import net.minecraft.world.phys.Vec3;
import noobanidus.mods.lootr.common.api.LootrAPI;

import org.jetbrains.annotations.Nullable;

/**
 * [CODE COPY] - {@link ChestRenderer}.<br><br>
 * Stripped down to only use what is necessary, as the mimic chest never opens.
 */
public class ChestMimicRenderer implements BlockEntityRenderer<ChestMimicBlockEntity, ChestRenderState> {
    private static final SpriteId LOOTR_MATERIAL = new SpriteId(Sheets.CHEST_SHEET, Identifier.fromNamespaceAndPath("lootr", "chest"));
    private final SpriteGetter sprites;
    private final ChestModel model;
    private final boolean xmasTextures;

    public ChestMimicRenderer(BlockEntityRendererProvider.Context context) {
        this.sprites = context.sprites();
        this.model = new ChestModel(context.bakeLayer(AetherModelLayers.CHEST_MIMIC));
        this.xmasTextures = ChestRenderer.xmasTextures();
    }

    @Override
    public ChestRenderState createRenderState() {
        return new ChestRenderState();
    }

    @Override
    public void extractRenderState(ChestMimicBlockEntity blockEntity, ChestRenderState state, float partialTicks, Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        BlockState blockState = blockEntity.getLevel() != null ? blockEntity.getBlockState() : null;
        state.type = ChestType.SINGLE;
        state.facing = blockState != null && blockState.getBlock() instanceof ChestMimicBlock ? blockState.getValue(ChestBlock.FACING) : Direction.SOUTH;
        state.material = this.xmasTextures ? ChestRenderState.ChestMaterialType.CHRISTMAS : ChestRenderState.ChestMaterialType.REGULAR;
        state.customSprite = FabricLoader.getInstance().isModLoaded("lootr") && !LootrAPI.isVanillaTextures() ? LOOTR_MATERIAL : null;
    }

    @Override
    public void submit(ChestRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.mulPose(ChestRenderer.modelTransformation(state.facing));
        SpriteId spriteId = state.customSprite != null ? state.customSprite : Sheets.chooseSprite(state.material, state.type);
        submitNodeCollector.submitModel(this.model, 0.0F, poseStack, state.lightCoords, OverlayTexture.NO_OVERLAY, -1, spriteId, this.sprites, 0);
        if (state.breakProgress != null) {
            submitNodeCollector.order(1).submitCrumblingOverlay(this.model, 0.0F, poseStack, spriteId.renderType(this.model.renderType()), state.lightCoords, OverlayTexture.NO_OVERLAY, -1, state.breakProgress);
        }
        poseStack.popPose();
    }
}
