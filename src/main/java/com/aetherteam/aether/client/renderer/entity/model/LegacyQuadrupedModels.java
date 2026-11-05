package com.aetherteam.aether.client.renderer.entity.model;

import net.minecraft.client.model.animal.pig.PigModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/**
 * Pig and cow layers for the Phyg and the Flying Cow, sized for their 64x32 textures.<br><br>
 * Since 26.x, the vanilla pig and cow models are laid out for 64x64 textures, and the cow has an extra snout cube
 * that reads from below the area covered by the Aether's textures.
 */
public final class LegacyQuadrupedModels extends PigModel {
    private LegacyQuadrupedModels(ModelPart root) {
        super(root);
    }

    /**
     * The vanilla pig mesh (whose UVs are unchanged) with a 64x32 texture size.
     */
    public static LayerDefinition createPigLayer(CubeDeformation deformation) {
        return LayerDefinition.create(createBasePigModel(deformation), 64, 32);
    }

    /**
     * The vanilla cow mesh without its snout, with a 64x32 texture size.
     */
    public static LayerDefinition createCowLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F)
                .texOffs(22, 0).addBox("right_horn", -5.0F, -5.0F, -5.0F, 1.0F, 3.0F, 1.0F)
                .texOffs(22, 0).addBox("left_horn", 4.0F, -5.0F, -5.0F, 1.0F, 3.0F, 1.0F),
            PartPose.offset(0.0F, 4.0F, -8.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(18, 4).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F)
                .texOffs(52, 0).addBox(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F),
            PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, Mth.HALF_PI, 0.0F, 0.0F));
        CubeListBuilder leftLeg = CubeListBuilder.create().mirror().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F);
        CubeListBuilder rightLeg = CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F);
        root.addOrReplaceChild("right_hind_leg", rightLeg, PartPose.offset(-4.0F, 12.0F, 7.0F));
        root.addOrReplaceChild("left_hind_leg", leftLeg, PartPose.offset(4.0F, 12.0F, 7.0F));
        root.addOrReplaceChild("right_front_leg", rightLeg, PartPose.offset(-4.0F, 12.0F, -5.0F));
        root.addOrReplaceChild("left_front_leg", leftLeg, PartPose.offset(4.0F, 12.0F, -5.0F));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }
}
