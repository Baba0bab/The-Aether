//package com.aetherteam.aether.data.providers;
//
//import com.aetherteam.aether.Aether;
//import com.aetherteam.aether.block.construction.AetherFarmBlock;
//import com.aetherteam.aether.block.dungeon.DoorwayBlock;
//import com.aetherteam.aether.block.miscellaneous.AetherFrostedIceBlock;
//import com.aetherteam.aether.block.miscellaneous.FacingPillarBlock;
//import com.aetherteam.aether.block.miscellaneous.UnstableObsidianBlock;
//import net.minecraft.client.data.models.BlockModelGenerators;
//import net.minecraft.client.data.models.ItemModelGenerators;
//import net.minecraft.client.data.models.MultiVariant;
//import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
//import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
//import net.minecraft.client.data.models.blockstates.PropertyDispatch;
//import net.minecraft.client.data.models.model.ItemModelUtils;
//import net.minecraft.client.data.models.model.ModelLocationUtils;
//import net.minecraft.client.data.models.model.ModelTemplate;
//import net.minecraft.client.data.models.model.ModelTemplates;
//import net.minecraft.client.data.models.model.TextureMapping;
//import net.minecraft.client.data.models.model.TextureSlot;
//import net.minecraft.client.renderer.special.ChestSpecialRenderer;
//import net.minecraft.client.resources.model.sprite.Material;
//import net.minecraft.core.Direction;
//import net.minecraft.core.registries.BuiltInRegistries;
//import net.minecraft.resources.Identifier;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.state.properties.BedPart;
//import net.minecraft.world.level.block.state.properties.BlockStateProperties;
//import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;
//import net.neoforged.neoforge.client.model.generators.template.FaceBuilder;
//import com.mojang.math.Transformation;
//import org.joml.Vector3f;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Set;
//import java.util.function.Consumer;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * Helpers for generating the Aether's blockstates and block models, built around vanilla's {@link BlockModelGenerators}.
// */
//public abstract class AetherBlockModelProvider {
//    protected static final TextureSlot PORTAL = TextureSlot.create("portal");
//    protected static final TextureSlot BUSH = TextureSlot.create("bush");
//    protected static final TextureSlot FLOWERPOT = TextureSlot.create("flowerpot");
//    protected static final Map<String, TextureSlot> AERCLOUD_SLOTS = Arrays.stream(new String[]{"up", "down", "north", "south", "west", "east"})
//            .flatMap((face) -> Stream.of(face + "_inside", face + "_outside"))
//            .collect(Collectors.toMap((name) -> name, TextureSlot::create));
//
//    protected static final ModelTemplate PORTAL_EW = block("minecraft", "nether_portal_ew", "_ew", TextureSlot.PARTICLE, PORTAL);
//    protected static final ModelTemplate PORTAL_NS = block("minecraft", "nether_portal_ns", "_ns", TextureSlot.PARTICLE, PORTAL);
//    protected static final ModelTemplate DIRT_PATH = block("minecraft", "dirt_path", null, TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM);
//    protected static final ModelTemplate AERCLOUD = aercloudTemplate();
//    protected static final ModelTemplate BERRY_BUSH = berryBushTemplate();
//    protected static final ModelTemplate POTTED_STEM = pottedStemTemplate(false);
//    protected static final ModelTemplate POTTED_BUSH = pottedStemTemplate(true);
//
//    protected final BlockModelGenerators blockModels;
//    protected final ItemModelGenerators itemModels;
//    private final Set<Identifier> createdModels = new HashSet<>();
//
//    public AetherBlockModelProvider(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
//        this.blockModels = blockModels;
//        this.itemModels = itemModels;
//    }
//
//    public abstract void run();
//
//    // Utility methods.
//
//    public String name(Block block) {
//        return BuiltInRegistries.BLOCK.getKey(block).getPath();
//    }
//
//    public Material texture(String name, String location) {
//        return new Material(Identifier.fromNamespaceAndPath(Aether.MODID, "block/" + location + name));
//    }
//
//    public Material texture(String name, String location, String suffix) {
//        return this.texture(name + suffix, location);
//    }
//
//    public Material mcTexture(String name) {
//        return new Material(Identifier.withDefaultNamespace("block/" + name));
//    }
//
//    public Identifier modelLocation(String name) {
//        return Identifier.fromNamespaceAndPath(Aether.MODID, "block/" + name);
//    }
//
//    /**
//     * Creates a model, skipping it if a model with the same location has already been created.
//     */
//    public Identifier model(Identifier location, ModelTemplate template, TextureMapping textures) {
//        if (this.createdModels.add(location)) {
//            template.create(location, textures, this.blockModels.modelOutput);
//        }
//        return location;
//    }
//
//    public Identifier model(String name, ModelTemplate template, TextureMapping textures) {
//        return this.model(this.modelLocation(name), template, textures);
//    }
//
//    public Identifier cubeAll(String name, Material texture) {
//        return this.model(name, ModelTemplates.CUBE_ALL, TextureMapping.cube(texture));
//    }
//
//    public Identifier cubeBottomTop(String name, Material side, Material bottom, Material top) {
//        return this.model(name, ModelTemplates.CUBE_BOTTOM_TOP, new TextureMapping().put(TextureSlot.SIDE, side).put(TextureSlot.BOTTOM, bottom).put(TextureSlot.TOP, top));
//    }
//
//    public void simpleBlock(Block block, Identifier model) {
//        this.blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, BlockModelGenerators.plainVariant(model)));
//    }
//
//    public void randomRotationBlock(Block block, Identifier model) {
//        this.blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(model))));
//    }
//
//    public void blockItem(Block block, Identifier model) {
//        this.blockModels.registerSimpleItemModel(block, model);
//    }
//
//    public void blockItem(Block block) {
//        this.blockItem(block, ModelLocationUtils.getModelLocation(block));
//    }
//
//    public void flatBlockItem(Block block, Material texture) {
//        Identifier model = ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(block.asItem()), TextureMapping.layer0(texture), this.blockModels.modelOutput);
//        this.blockItem(block, model);
//    }
//
//    // Generic blocks.
//
//    public void block(Block block, String location) {
//        this.simpleBlock(block, this.cubeAll(this.name(block), this.texture(this.name(block), location)));
//    }
//
//    public void blockDoubleDrops(Block block, String location) {
//        this.block(block, location);
//    }
//
//    public void randomBlockDoubleDrops(Block block, String location) {
//        this.randomRotationBlock(block, this.cubeAll(this.name(block), this.texture(this.name(block), location)));
//    }
//
//    public void portal(Block block) {
//        TextureMapping textures = new TextureMapping()
//                .put(TextureSlot.PARTICLE, this.texture(this.name(block), "miscellaneous/"))
//                .put(PORTAL, this.texture(this.name(block), "miscellaneous/"));
//        MultiVariant ew = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_ew", PORTAL_EW, textures));
//        MultiVariant ns = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_ns", PORTAL_NS, textures));
//        this.blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
//                .with(PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_AXIS).select(Direction.Axis.X, ns).select(Direction.Axis.Z, ew)));
//    }
//
//    public void grass(Block block, Block dirtBlock) {
//        this.grassBlock(block, block, dirtBlock);
//    }
//
//    public void enchantedGrass(Block block, Block grassBlock, Block dirtBlock) {
//        this.grassBlock(block, grassBlock, dirtBlock);
//    }
//
//    public void grassBlock(Block baseBlock, Block blockForSnow, Block blockForDirt) {
//        Identifier grass = this.cubeBottomTop(this.name(baseBlock),
//                this.texture(this.name(baseBlock), "natural/", "_side"),
//                this.texture(this.name(blockForDirt), "natural/"),
//                this.texture(this.name(baseBlock), "natural/", "_top"));
//        Identifier grassSnowed = this.cubeBottomTop(this.name(blockForSnow) + "_snow",
//                this.texture(this.name(blockForSnow), "natural/", "_snow"),
//                this.texture(this.name(blockForDirt), "natural/"),
//                this.texture(this.name(baseBlock), "natural/", "_top"));
//        this.blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(baseBlock).with(PropertyDispatch.initial(BlockStateProperties.SNOWY)
//                .select(false, BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(grass)))
//                .select(true, BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(grassSnowed)))));
//    }
//
//    public void dirtPath(Block block, Block dirtBlock) {
//        Identifier path = this.model(this.name(block), DIRT_PATH, new TextureMapping()
//                .put(TextureSlot.PARTICLE, this.texture(this.name(dirtBlock), "natural/"))
//                .put(TextureSlot.TOP, this.texture(this.name(block), "construction/", "_top"))
//                .put(TextureSlot.SIDE, this.texture(this.name(block), "construction/", "_side"))
//                .put(TextureSlot.BOTTOM, this.texture(this.name(dirtBlock), "natural/")));
//        this.randomRotationBlock(block, path);
//    }
//
//    public void farmland(Block block, Block dirtBlock) {
//        Material dirt = this.texture(this.name(dirtBlock), "natural/");
//        Identifier farmland = this.model(this.name(block), ModelTemplates.CUBE_BOTTOM_TOP_INDENTED, new TextureMapping()
//                .put(TextureSlot.SIDE, dirt).put(TextureSlot.BOTTOM, dirt)
//                .put(TextureSlot.TOP, this.texture(this.name(block), "construction/")));
//        Identifier moist = this.model(this.name(block) + "_moist", ModelTemplates.CUBE_BOTTOM_TOP_INDENTED, new TextureMapping()
//                .put(TextureSlot.SIDE, dirt).put(TextureSlot.BOTTOM, dirt)
//                .put(TextureSlot.TOP, this.texture(this.name(block), "construction/", "_moist")));
//        this.blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(AetherFarmBlock.MOISTURE)
//                .generate((moisture) -> BlockModelGenerators.plainVariant(moisture < AetherFarmBlock.MAX_MOISTURE ? farmland : moist))));
//    }
//
//    public void aercloudAll(Block block, String location) {
//        Material texture = this.texture(this.name(block), location);
//        TextureMapping textures = new TextureMapping().put(TextureSlot.PARTICLE, texture);
//        AERCLOUD_SLOTS.values().forEach((slot) -> textures.put(slot, texture));
//        this.simpleBlock(block, this.model(this.name(block), AERCLOUD, textures));
//        Identifier item = ModelTemplates.CUBE_ALL.create(ModelLocationUtils.getModelLocation(block.asItem()), TextureMapping.cube(texture), this.blockModels.modelOutput);
//        this.blockItem(block, item);
//    }
//
//    public void translucentBlock(Block block, String location) {
//        this.block(block, location);
//    }
//
//    public void log(Block block) {
//        this.axisBlock(block, this.name(block), this.texture(this.name(block), "natural/"), this.texture(this.name(block), "natural/", "_top"));
//    }
//
//    public void enchantedLog(Block block, Block baseBlock) {
//        this.axisBlock(block, this.name(block), this.texture(this.name(block), "natural/"), this.texture(this.name(baseBlock), "natural/", "_top"));
//    }
//
//    public void wood(Block block, Block baseBlock) {
//        Material texture = this.texture(this.name(baseBlock), "natural/");
//        this.axisBlock(block, this.name(block), texture, texture);
//    }
//
//    public void axisBlock(Block block, String name, Material side, Material end) {
//        TextureMapping textures = TextureMapping.column(side, end);
//        MultiVariant vertical = BlockModelGenerators.plainVariant(this.model(name, ModelTemplates.CUBE_COLUMN, textures));
//        MultiVariant horizontal = BlockModelGenerators.plainVariant(this.model(name + "_horizontal", ModelTemplates.CUBE_COLUMN_HORIZONTAL, textures));
//        this.blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(block, vertical, horizontal));
//    }
//
//    public void pane(Block block, Block glass, String location) {
//        TextureMapping textures = new TextureMapping()
//                .put(TextureSlot.PANE, this.texture(this.name(glass), location))
//                .put(TextureSlot.EDGE, this.texture(this.name(block), location, "_top"));
//        MultiVariant post = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_post", ModelTemplates.STAINED_GLASS_PANE_POST, textures));
//        MultiVariant side = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_side", ModelTemplates.STAINED_GLASS_PANE_SIDE, textures));
//        MultiVariant sideAlt = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_side_alt", ModelTemplates.STAINED_GLASS_PANE_SIDE_ALT, textures));
//        MultiVariant noSide = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_noside", ModelTemplates.STAINED_GLASS_PANE_NOSIDE, textures));
//        MultiVariant noSideAlt = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_noside_alt", ModelTemplates.STAINED_GLASS_PANE_NOSIDE_ALT, textures));
//        this.blockModels.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
//                .with(post)
//                .with(BlockModelGenerators.condition().term(BlockStateProperties.NORTH, true), side)
//                .with(BlockModelGenerators.condition().term(BlockStateProperties.EAST, true), side.with(BlockModelGenerators.Y_ROT_90))
//                .with(BlockModelGenerators.condition().term(BlockStateProperties.SOUTH, true), sideAlt)
//                .with(BlockModelGenerators.condition().term(BlockStateProperties.WEST, true), sideAlt.with(BlockModelGenerators.Y_ROT_90))
//                .with(BlockModelGenerators.condition().term(BlockStateProperties.NORTH, false), noSide)
//                .with(BlockModelGenerators.condition().term(BlockStateProperties.EAST, false), noSideAlt)
//                .with(BlockModelGenerators.condition().term(BlockStateProperties.SOUTH, false), noSideAlt.with(BlockModelGenerators.Y_ROT_90))
//                .with(BlockModelGenerators.condition().term(BlockStateProperties.WEST, false), noSide.with(BlockModelGenerators.Y_ROT_270)));
//        this.flatBlockItem(block, this.texture(this.name(glass), location));
//    }
//
//    // Utility blocks.
//
//    public void altar(Block block) {
//        this.simpleBlock(block, this.cubeBottomTop(this.name(block),
//                this.texture(this.name(block), "utility/", "_side"),
//                this.texture(this.name(block), "utility/", "_bottom"),
//                this.texture(this.name(block), "utility/", "_bottom")));
//    }
//
//    public void freezer(Block block) {
//        this.simpleBlock(block, this.utility(block));
//    }
//
//    public void incubator(Block block) {
//        this.simpleBlock(block, this.utility(block));
//    }
//
//    public Identifier utility(Block block) {
//        return this.cubeBottomTop(this.name(block),
//                this.texture(this.name(block), "utility/", "_side"),
//                this.texture("altar", "utility/", "_bottom"),
//                this.texture(this.name(block), "utility/", "_top"));
//    }
//
//    public void torchBlock(Block block, Block wall) {
//        TextureMapping textures = TextureMapping.torch(this.texture(this.name(block), "utility/"));
//        this.simpleBlock(block, this.model(this.name(block), ModelTemplates.TORCH, textures));
//        this.blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(wall, BlockModelGenerators.plainVariant(this.model(this.name(wall), ModelTemplates.WALL_TORCH, textures)))
//                .with(BlockModelGenerators.ROTATION_TORCH));
//        this.flatBlockItem(block, this.texture(this.name(block), "utility/"));
//    }
//
//    public void signBlock(Block sign, Block wallSign, Material particle) {
//        TextureMapping textures = new TextureMapping().put(TextureSlot.ALL, this.texture(this.name(sign), "construction/")).put(TextureSlot.PARTICLE, particle);
//        this.blockModels.blockStateOutput.accept(BlockModelGenerators.createSign(sign,
//                BlockModelGenerators.plainVariant(this.model(this.name(sign) + "_rot_0", ModelTemplates.SIGN_ROT_0, textures)),
//                BlockModelGenerators.plainVariant(this.model(this.name(sign) + "_rot_1", ModelTemplates.SIGN_ROT_1, textures)),
//                BlockModelGenerators.plainVariant(this.model(this.name(sign) + "_rot_2", ModelTemplates.SIGN_ROT_2, textures)),
//                BlockModelGenerators.plainVariant(this.model(this.name(sign) + "_rot_3", ModelTemplates.SIGN_ROT_3, textures))));
//        this.blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(wallSign, BlockModelGenerators.plainVariant(this.model(this.name(wallSign), ModelTemplates.WALL_SIGN, textures)))
//                .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING_ALT));
//    }
//
//    public void hangingSignBlock(Block sign, Block wallSign, Material particle) {
//        TextureMapping textures = new TextureMapping().put(TextureSlot.ALL, this.texture(this.name(sign), "construction/")).put(TextureSlot.PARTICLE, particle);
//        this.blockModels.blockStateOutput.accept(BlockModelGenerators.createHangingSign(sign,
//                BlockModelGenerators.plainVariant(this.model(this.name(sign) + "_rot_0", ModelTemplates.HANGING_SIGN_ROT_0, textures)),
//                BlockModelGenerators.plainVariant(this.model(this.name(sign) + "_rot_1", ModelTemplates.HANGING_SIGN_ROT_1, textures)),
//                BlockModelGenerators.plainVariant(this.model(this.name(sign) + "_rot_2", ModelTemplates.HANGING_SIGN_ROT_2, textures)),
//                BlockModelGenerators.plainVariant(this.model(this.name(sign) + "_rot_3", ModelTemplates.HANGING_SIGN_ROT_3, textures)),
//                BlockModelGenerators.plainVariant(this.model(this.name(sign) + "_attached_rot_0", ModelTemplates.ATTACHED_HANGING_SIGN_ROT_0, textures)),
//                BlockModelGenerators.plainVariant(this.model(this.name(sign) + "_attached_rot_1", ModelTemplates.ATTACHED_HANGING_SIGN_ROT_1, textures)),
//                BlockModelGenerators.plainVariant(this.model(this.name(sign) + "_attached_rot_2", ModelTemplates.ATTACHED_HANGING_SIGN_ROT_2, textures)),
//                BlockModelGenerators.plainVariant(this.model(this.name(sign) + "_attached_rot_3", ModelTemplates.ATTACHED_HANGING_SIGN_ROT_3, textures))));
//        this.blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(wallSign, BlockModelGenerators.plainVariant(this.model(this.name(wallSign), ModelTemplates.WALL_HANGING_SIGN, textures)))
//                .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
//    }
//
//    // Plants.
//
//    public void crossBlock(Block block, String location) {
//        this.simpleBlock(block, this.model(this.name(block), ModelTemplates.CROSS, TextureMapping.cross(this.texture(this.name(block), location))));
//        this.flatBlockItem(block, this.texture(this.name(block), location));
//    }
//
//    public void saplingBlock(Block block, String location) {
//        this.crossBlock(block, location);
//    }
//
//    public void pottedPlant(Block block, Block flower, String location) {
//        this.simpleBlock(block, this.model(this.name(block), ModelTemplates.FLOWER_POT_CROSS, TextureMapping.plant(this.texture(this.name(flower), location))));
//    }
//
//    public void berryBush(Block block, Block stem) {
//        Identifier bush = this.model(this.name(block), BERRY_BUSH, new TextureMapping()
//                .put(TextureSlot.PARTICLE, this.texture(this.name(block), "natural/"))
//                .put(BUSH, this.texture(this.name(block), "natural/"))
//                .put(TextureSlot.STEM, this.texture(this.name(stem), "natural/")));
//        this.simpleBlock(block, bush);
//        this.blockItem(block, bush);
//    }
//
//    public void pottedStem(Block block, Block stem, String location) {
//        this.simpleBlock(block, this.model(this.name(block), POTTED_STEM, this.pottedStemTextures(stem, location)));
//    }
//
//    public void pottedBush(Block block, Block bush, Block stem, String location) {
//        this.simpleBlock(block, this.model(this.name(block), POTTED_BUSH, this.pottedStemTextures(stem, location).put(BUSH, this.texture(this.name(bush), location))));
//    }
//
//    private TextureMapping pottedStemTextures(Block stem, String location) {
//        return new TextureMapping()
//                .put(TextureSlot.PARTICLE, this.mcTexture("flower_pot"))
//                .put(TextureSlot.STEM, this.texture(this.name(stem), location))
//                .put(TextureSlot.DIRT, this.mcTexture("dirt"))
//                .put(FLOWERPOT, this.mcTexture("flower_pot"));
//    }
//
//    // Dungeon blocks.
//
//    public void dungeonBlock(Block block, Block baseBlock) {
//        this.simpleBlock(block, this.cubeAll(this.name(baseBlock), this.texture(this.name(baseBlock), "dungeon/")));
//    }
//
//    public void invisibleBlock(Block block, Block baseBlock) {
//        MultiVariant visible = BlockModelGenerators.plainVariant(this.cubeAll(this.name(baseBlock), this.texture(this.name(baseBlock), "dungeon/")));
//        MultiVariant invisible = BlockModelGenerators.plainVariant(this.model(this.name(block), ModelTemplates.PARTICLE_ONLY, TextureMapping.particle(this.texture(this.name(baseBlock), "dungeon/"))));
//        this.blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(DoorwayBlock.INVISIBLE).select(false, visible).select(true, invisible)));
//    }
//
//    public void chestMimic(Block block, Block dummyBlock) {
//        this.chest(block, this.mcTexture(this.name(dummyBlock)), ChestSpecialRenderer.REGULAR.single(), true);
//    }
//
//    public void treasureChest(Block block, Block dummyBlock) {
//        this.chest(block, this.texture(this.name(dummyBlock), "dungeon/"), Identifier.fromNamespaceAndPath(Aether.MODID, this.name(block)), false);
//    }
//
//    /**
//     * [CODE COPY] - {@link BlockModelGenerators#createChest(Block, Block, Identifier, boolean)}.<br><br>
//     * Modified to take a particle texture.
//     */
//    public void chest(Block block, Material particle, Identifier chestTexture, boolean hasGiftVariant) {
//        this.simpleBlock(block, this.model(this.name(block), ModelTemplates.PARTICLE_ONLY, TextureMapping.particle(particle)));
//        Identifier itemModelBase = ModelTemplates.CHEST_INVENTORY.create(block.asItem(), TextureMapping.particle(particle), this.blockModels.modelOutput);
//        var plainModel = ItemModelUtils.specialModel(itemModelBase, new ChestSpecialRenderer.Unbaked(chestTexture));
//        if (hasGiftVariant) {
//            var giftModel = ItemModelUtils.specialModel(itemModelBase, new ChestSpecialRenderer.Unbaked(ChestSpecialRenderer.CHRISTMAS.single()));
//            this.blockModels.itemModelOutput.accept(block.asItem(), ItemModelUtils.isXmas(giftModel, plainModel));
//        } else {
//            this.blockModels.itemModelOutput.accept(block.asItem(), plainModel);
//        }
//    }
//
//    public void pillar(Block block) {
//        this.axisBlock(block, this.name(block), this.texture(this.name(block), "dungeon/", "_side"), this.texture(this.name(block), "dungeon/", "_top"));
//    }
//
//    public void pillarTop(Block block) {
//        TextureMapping textures = TextureMapping.column(this.texture("pillar_carved", "dungeon/"), this.texture(this.name(block), "dungeon/"));
//        MultiVariant vertical = BlockModelGenerators.plainVariant(this.model(this.name(block), ModelTemplates.CUBE_COLUMN, textures));
//        MultiVariant horizontal = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_horizontal", ModelTemplates.CUBE_COLUMN_HORIZONTAL, textures));
//        this.blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(FacingPillarBlock.FACING)
//                .select(Direction.DOWN, vertical.with(BlockModelGenerators.X_ROT_180))
//                .select(Direction.UP, vertical)
//                .select(Direction.NORTH, horizontal.with(BlockModelGenerators.X_ROT_90))
//                .select(Direction.EAST, horizontal.with(BlockModelGenerators.X_ROT_90).with(BlockModelGenerators.Y_ROT_90))
//                .select(Direction.SOUTH, horizontal.with(BlockModelGenerators.X_ROT_90).with(BlockModelGenerators.Y_ROT_180))
//                .select(Direction.WEST, horizontal.with(BlockModelGenerators.X_ROT_90).with(BlockModelGenerators.Y_ROT_270))));
//    }
//
//    // Miscellaneous blocks.
//
//    public void present(Block block) {
//        this.simpleBlock(block, this.cubeBottomTop(this.name(block),
//                this.texture(this.name(block), "miscellaneous/", "_side"),
//                this.texture(this.name(block), "miscellaneous/", "_top"),
//                this.texture(this.name(block), "miscellaneous/", "_top")));
//    }
//
//    public void sunAltar(Block block) {
//        this.simpleBlock(block, this.cubeBottomTop(this.name(block),
//                this.texture(this.name(block), "utility/", "_side"),
//                this.texture("hellfire_stone", "dungeon/"),
//                this.texture(this.name(block), "utility/", "_top")));
//    }
//
//    public void bookshelf(Block block, Block endBlock) {
//        this.simpleBlock(block, this.model(this.name(block), ModelTemplates.CUBE_COLUMN, TextureMapping.column(this.texture(this.name(block), "construction/"), this.texture(this.name(endBlock), "construction/"))));
//    }
//
//    /**
//     * [CODE COPY] - {@link BlockModelGenerators#createBed(net.minecraft.world.item.DyeColor)}.<br><br>
//     * Modified to use the Aether's texture locations.
//     */
//    public void bed(Block block) {
//        Identifier head = this.model(this.name(block) + "_" + BedPart.HEAD, ModelTemplates.BED_HEAD, this.bedTextures(block, BedPart.HEAD));
//        Identifier foot = this.model(this.name(block) + "_" + BedPart.FOOT, ModelTemplates.BED_FOOT, this.bedTextures(block, BedPart.FOOT));
//        this.blockModels.blockStateOutput.accept(BlockModelGenerators.createBed(block, BlockModelGenerators.plainVariant(head), BlockModelGenerators.plainVariant(foot)));
//        Transformation footTransformation = new Transformation(new Vector3f(0.0F, 0.0F, 1.0F), null, null, null);
//        this.blockModels.itemModelOutput.accept(block.asItem(), ItemModelUtils.composite(ItemModelUtils.plainModel(head), ItemModelUtils.plainModel(foot, footTransformation)));
//    }
//
//    private TextureMapping bedTextures(Block block, BedPart part) {
//        String prefix = "_" + part + "_";
//        TextureMapping textures = new TextureMapping()
//                .put(TextureSlot.UP, this.texture(this.name(block), "construction/", prefix + "up"))
//                .put(TextureSlot.EAST, this.texture(this.name(block), "construction/", prefix + "east"))
//                .put(TextureSlot.WEST, this.texture(this.name(block), "construction/", prefix + "west"))
//                .putForced(TextureSlot.DOWN, this.texture(this.name(block), "construction/", "_down"));
//        if (part == BedPart.FOOT) {
//            textures.put(TextureSlot.SOUTH, this.texture(this.name(block), "construction/", prefix + "south"));
//        }
//        return textures;
//    }
//
//    public void frostedIce(Block block, Block base) {
//        this.blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(AetherFrostedIceBlock.AGE)
//                .generate((age) -> BlockModelGenerators.plainVariant(this.cubeAll(this.name(block) + "_" + age, this.mcTexture(this.name(base) + "_" + age))))));
//    }
//
//    public void unstableObsidian(Block block) {
//        this.blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(UnstableObsidianBlock.AGE)
//                .generate((age) -> BlockModelGenerators.plainVariant(this.cubeAll(this.name(block) + "_" + age, this.texture(this.name(block) + "_" + age, "miscellaneous/"))))));
//    }
//
//    // Building blocks.
//
//    public void fence(Block block, Block baseBlock, String location) {
//        TextureMapping textures = TextureMapping.defaultTexture(this.texture(this.name(baseBlock), location));
//        MultiVariant post = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_post", ModelTemplates.FENCE_POST, textures));
//        MultiVariant side = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_side", ModelTemplates.FENCE_SIDE, textures));
//        this.blockModels.blockStateOutput.accept(BlockModelGenerators.createFence(block, post, side));
//        this.blockItem(block, this.model(this.name(block) + "_inventory", ModelTemplates.FENCE_INVENTORY, textures));
//    }
//
//    public void fenceGateBlock(Block block, Block baseBlock, String location) {
//        TextureMapping textures = TextureMapping.defaultTexture(this.texture(this.name(baseBlock), location));
//        MultiVariant closed = BlockModelGenerators.plainVariant(this.model(this.name(block), ModelTemplates.FENCE_GATE_CLOSED, textures));
//        MultiVariant open = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_open", ModelTemplates.FENCE_GATE_OPEN, textures));
//        MultiVariant wall = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_wall", ModelTemplates.FENCE_GATE_WALL_CLOSED, textures));
//        MultiVariant wallOpen = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_wall_open", ModelTemplates.FENCE_GATE_WALL_OPEN, textures));
//        this.blockModels.blockStateOutput.accept(BlockModelGenerators.createFenceGate(block, open, closed, wallOpen, wall, true));
//        this.blockItem(block);
//    }
//
//    public void doorBlock(Block block, String location) {
//        TextureMapping textures = TextureMapping.door(this.texture(this.name(block), location, "_top"), this.texture(this.name(block), location, "_bottom"));
//        this.blockModels.blockStateOutput.accept(BlockModelGenerators.createDoor(block,
//                BlockModelGenerators.plainVariant(this.model(this.name(block) + "_bottom_left", ModelTemplates.DOOR_BOTTOM_LEFT, textures)),
//                BlockModelGenerators.plainVariant(this.model(this.name(block) + "_bottom_left_open", ModelTemplates.DOOR_BOTTOM_LEFT_OPEN, textures)),
//                BlockModelGenerators.plainVariant(this.model(this.name(block) + "_bottom_right", ModelTemplates.DOOR_BOTTOM_RIGHT, textures)),
//                BlockModelGenerators.plainVariant(this.model(this.name(block) + "_bottom_right_open", ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN, textures)),
//                BlockModelGenerators.plainVariant(this.model(this.name(block) + "_top_left", ModelTemplates.DOOR_TOP_LEFT, textures)),
//                BlockModelGenerators.plainVariant(this.model(this.name(block) + "_top_left_open", ModelTemplates.DOOR_TOP_LEFT_OPEN, textures)),
//                BlockModelGenerators.plainVariant(this.model(this.name(block) + "_top_right", ModelTemplates.DOOR_TOP_RIGHT, textures)),
//                BlockModelGenerators.plainVariant(this.model(this.name(block) + "_top_right_open", ModelTemplates.DOOR_TOP_RIGHT_OPEN, textures))));
//    }
//
//    public void trapdoorBlock(Block block, String location) {
//        TextureMapping textures = TextureMapping.defaultTexture(this.texture(this.name(block), location));
//        MultiVariant top = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_top", ModelTemplates.TRAPDOOR_TOP, textures));
//        Identifier bottom = this.model(this.name(block) + "_bottom", ModelTemplates.TRAPDOOR_BOTTOM, textures);
//        MultiVariant open = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_open", ModelTemplates.TRAPDOOR_OPEN, textures));
//        this.blockModels.blockStateOutput.accept(BlockModelGenerators.createTrapdoor(block, top, BlockModelGenerators.plainVariant(bottom), open));
//        this.blockItem(block, bottom);
//    }
//
//    public void buttonBlock(Block block, Material texture) {
//        TextureMapping textures = TextureMapping.defaultTexture(texture);
//        MultiVariant normal = BlockModelGenerators.plainVariant(this.model(this.name(block), ModelTemplates.BUTTON, textures));
//        MultiVariant pressed = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_pressed", ModelTemplates.BUTTON_PRESSED, textures));
//        this.blockModels.blockStateOutput.accept(BlockModelGenerators.createButton(block, normal, pressed));
//        this.blockItem(block, this.model(this.name(block) + "_inventory", ModelTemplates.BUTTON_INVENTORY, textures));
//    }
//
//    public void pressurePlateBlock(Block block, Material texture) {
//        TextureMapping textures = TextureMapping.defaultTexture(texture);
//        MultiVariant up = BlockModelGenerators.plainVariant(this.model(this.name(block), ModelTemplates.PRESSURE_PLATE_UP, textures));
//        MultiVariant down = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_down", ModelTemplates.PRESSURE_PLATE_DOWN, textures));
//        this.blockModels.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(block, up, down));
//        this.blockItem(block);
//    }
//
//    public void wallBlock(Block block, Block baseBlock, String location) {
//        TextureMapping textures = new TextureMapping().put(TextureSlot.WALL, this.texture(this.name(baseBlock), location));
//        MultiVariant post = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_post", ModelTemplates.WALL_POST, textures));
//        MultiVariant side = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_side", ModelTemplates.WALL_LOW_SIDE, textures));
//        MultiVariant sideTall = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_side_tall", ModelTemplates.WALL_TALL_SIDE, textures));
//        this.blockModels.blockStateOutput.accept(BlockModelGenerators.createWall(block, post, side, sideTall));
//        this.blockItem(block, this.model(this.name(block) + "_inventory", ModelTemplates.WALL_INVENTORY, textures));
//    }
//
//    public void stairs(Block block, Block baseBlock, String location) {
//        TextureMapping textures = TextureMapping.cube(this.texture(this.name(baseBlock), location));
//        textures.put(TextureSlot.BOTTOM, this.texture(this.name(baseBlock), location)).put(TextureSlot.TOP, this.texture(this.name(baseBlock), location)).put(TextureSlot.SIDE, this.texture(this.name(baseBlock), location));
//        MultiVariant inner = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_inner", ModelTemplates.STAIRS_INNER, textures));
//        Identifier straight = this.model(this.name(block), ModelTemplates.STAIRS_STRAIGHT, textures);
//        MultiVariant outer = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_outer", ModelTemplates.STAIRS_OUTER, textures));
//        this.blockModels.blockStateOutput.accept(BlockModelGenerators.createStairs(block, inner, BlockModelGenerators.plainVariant(straight), outer));
//        this.blockItem(block, straight);
//    }
//
//    public void slab(Block block, Block baseBlock, String location) {
//        Material texture = this.texture(this.name(baseBlock), location);
//        TextureMapping textures = new TextureMapping().put(TextureSlot.BOTTOM, texture).put(TextureSlot.TOP, texture).put(TextureSlot.SIDE, texture);
//        Identifier bottom = this.model(this.name(block), ModelTemplates.SLAB_BOTTOM, textures);
//        MultiVariant top = BlockModelGenerators.plainVariant(this.model(this.name(block) + "_top", ModelTemplates.SLAB_TOP, textures));
//        MultiVariant full = BlockModelGenerators.plainVariant(this.modelLocation(this.name(baseBlock)));
//        this.blockModels.blockStateOutput.accept(BlockModelGenerators.createSlab(block, BlockModelGenerators.plainVariant(bottom), top, full));
//        this.blockItem(block, bottom);
//    }
//
//    public void translucentSlab(Block block, Block baseBlock, String location) {
//        this.slab(block, baseBlock, location);
//    }
//
//    // Templates.
//
//    private static ModelTemplate block(String namespace, String parent, String suffix, TextureSlot... slots) {
//        return new ModelTemplate(Optional.of(Identifier.fromNamespaceAndPath(namespace, "block/" + parent)), Optional.ofNullable(suffix), slots);
//    }
//
//    private static Consumer<FaceBuilder> face(TextureSlot texture, float u1, float v1, float u2, float v2, Direction cullface) {
//        return (face) -> face.texture(texture).uvs(u1, v1, u2, v2).cullface(cullface);
//    }
//
//    private static ModelTemplate aercloudTemplate() {
//        TextureSlot upInside = AERCLOUD_SLOTS.get("up_inside");
//        TextureSlot upOutside = AERCLOUD_SLOTS.get("up_outside");
//        TextureSlot downInside = AERCLOUD_SLOTS.get("down_inside");
//        TextureSlot downOutside = AERCLOUD_SLOTS.get("down_outside");
//        TextureSlot northInside = AERCLOUD_SLOTS.get("north_inside");
//        TextureSlot northOutside = AERCLOUD_SLOTS.get("north_outside");
//        TextureSlot southInside = AERCLOUD_SLOTS.get("south_inside");
//        TextureSlot southOutside = AERCLOUD_SLOTS.get("south_outside");
//        TextureSlot westInside = AERCLOUD_SLOTS.get("west_inside");
//        TextureSlot westOutside = AERCLOUD_SLOTS.get("west_outside");
//        TextureSlot eastInside = AERCLOUD_SLOTS.get("east_inside");
//        TextureSlot eastOutside = AERCLOUD_SLOTS.get("east_outside");
//        return ExtendedModelTemplateBuilder.builder()
//                .parent(Identifier.withDefaultNamespace("block/block"))
//                .requiredTextureSlot(TextureSlot.PARTICLE)
//                .requiredTextureSlot(upInside).requiredTextureSlot(upOutside)
//                .requiredTextureSlot(downInside).requiredTextureSlot(downOutside)
//                .requiredTextureSlot(northInside).requiredTextureSlot(northOutside)
//                .requiredTextureSlot(southInside).requiredTextureSlot(southOutside)
//                .requiredTextureSlot(westInside).requiredTextureSlot(westOutside)
//                .requiredTextureSlot(eastInside).requiredTextureSlot(eastOutside)
//                .element((element) -> element.from(0.0F, 15.998F, 0.0F).to(16.0F, 16.0F, 16.0F)
//                        .face(Direction.DOWN, face(upInside, 0, 16, 16, 0, Direction.UP))
//                        .face(Direction.UP, face(upOutside, 0, 0, 16, 16, Direction.UP)))
//                .element((element) -> element.from(0.0F, 0.0F, 0.0F).to(16.0F, 0.002F, 16.0F)
//                        .face(Direction.DOWN, face(downOutside, 0, 0, 16, 16, Direction.DOWN))
//                        .face(Direction.UP, face(downInside, 0, 16, 16, 0, Direction.DOWN)))
//                .element((element) -> element.from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 0.002F)
//                        .face(Direction.NORTH, face(northOutside, 0, 0, 16, 16, Direction.NORTH))
//                        .face(Direction.SOUTH, face(northInside, 16, 0, 0, 16, Direction.NORTH)))
//                .element((element) -> element.from(0.0F, 0.0F, 15.998F).to(16.0F, 16.0F, 16.0F)
//                        .face(Direction.NORTH, face(southInside, 16, 0, 0, 16, Direction.SOUTH))
//                        .face(Direction.SOUTH, face(southOutside, 0, 0, 16, 16, Direction.SOUTH)))
//                .element((element) -> element.from(0.0F, 0.0F, 0.0F).to(0.002F, 16.0F, 16.0F)
//                        .face(Direction.WEST, face(westOutside, 0, 0, 16, 16, Direction.WEST))
//                        .face(Direction.EAST, face(westInside, 16, 0, 0, 16, Direction.WEST)))
//                .element((element) -> element.from(15.998F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
//                        .face(Direction.WEST, face(eastInside, 16, 0, 0, 16, Direction.EAST))
//                        .face(Direction.EAST, face(eastOutside, 0, 0, 16, 16, Direction.EAST)))
//                .build();
//    }
//
//    private static ModelTemplate berryBushTemplate() {
//        return ExtendedModelTemplateBuilder.builder()
//                .parent(Identifier.withDefaultNamespace("block/block"))
//                .requiredTextureSlot(TextureSlot.PARTICLE).requiredTextureSlot(BUSH).requiredTextureSlot(TextureSlot.STEM)
//                .element((element) -> element.from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F).allFaces((direction, face) -> face.texture(BUSH)))
//                .element((element) -> element.from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F)
//                        .rotation((rotation) -> rotation.origin(8.0F, 8.0F, 8.0F).singleAxis(Direction.Axis.Y, 45.0F).rescale(true))
//                        .face(Direction.NORTH, (face) -> face.texture(TextureSlot.STEM))
//                        .face(Direction.SOUTH, (face) -> face.texture(TextureSlot.STEM)))
//                .element((element) -> element.from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F)
//                        .rotation((rotation) -> rotation.origin(8.0F, 8.0F, 8.0F).singleAxis(Direction.Axis.Y, 45.0F).rescale(true))
//                        .face(Direction.WEST, (face) -> face.texture(TextureSlot.STEM))
//                        .face(Direction.EAST, (face) -> face.texture(TextureSlot.STEM)))
//                .build();
//    }
//
//    private static ModelTemplate pottedStemTemplate(boolean withBush) {
//        ExtendedModelTemplateBuilder builder = ExtendedModelTemplateBuilder.builder()
//                .parent(Identifier.withDefaultNamespace("block/block"))
//                .requiredTextureSlot(TextureSlot.PARTICLE).requiredTextureSlot(TextureSlot.STEM).requiredTextureSlot(TextureSlot.DIRT).requiredTextureSlot(FLOWERPOT)
//                .element((element) -> element.from(5.0F, 0.0F, 5.0F).to(6.0F, 6.0F, 11.0F)
//                        .face(Direction.NORTH, face(FLOWERPOT, 10, 10, 11, 16, null))
//                        .face(Direction.EAST, face(FLOWERPOT, 5, 10, 11, 16, null))
//                        .face(Direction.SOUTH, face(FLOWERPOT, 5, 10, 6, 16, null))
//                        .face(Direction.WEST, face(FLOWERPOT, 5, 10, 11, 16, null))
//                        .face(Direction.UP, face(FLOWERPOT, 5, 5, 6, 11, null))
//                        .face(Direction.DOWN, face(FLOWERPOT, 5, 5, 6, 11, Direction.DOWN)))
//                .element((element) -> element.from(10.0F, 0.0F, 5.0F).to(11.0F, 6.0F, 11.0F)
//                        .face(Direction.NORTH, face(FLOWERPOT, 5, 10, 6, 16, null))
//                        .face(Direction.EAST, face(FLOWERPOT, 5, 10, 11, 16, null))
//                        .face(Direction.SOUTH, face(FLOWERPOT, 10, 10, 11, 16, null))
//                        .face(Direction.WEST, face(FLOWERPOT, 5, 10, 11, 16, null))
//                        .face(Direction.UP, face(FLOWERPOT, 10, 5, 11, 11, null))
//                        .face(Direction.DOWN, face(FLOWERPOT, 10, 5, 11, 11, Direction.DOWN)))
//                .element((element) -> element.from(6.0F, 0.0F, 5.0F).to(10.0F, 6.0F, 6.0F)
//                        .face(Direction.NORTH, face(FLOWERPOT, 6, 10, 10, 16, null))
//                        .face(Direction.SOUTH, face(FLOWERPOT, 6, 10, 10, 16, null))
//                        .face(Direction.UP, face(FLOWERPOT, 6, 5, 10, 6, null))
//                        .face(Direction.DOWN, face(FLOWERPOT, 6, 10, 10, 11, Direction.DOWN)))
//                .element((element) -> element.from(6.0F, 0.0F, 10.0F).to(10.0F, 6.0F, 11.0F)
//                        .face(Direction.NORTH, face(FLOWERPOT, 6, 10, 10, 16, null))
//                        .face(Direction.SOUTH, face(FLOWERPOT, 6, 10, 10, 16, null))
//                        .face(Direction.UP, face(FLOWERPOT, 6, 10, 10, 11, null))
//                        .face(Direction.DOWN, face(FLOWERPOT, 6, 5, 10, 6, Direction.DOWN)))
//                .element((element) -> element.from(6.0F, 0.0F, 6.0F).to(10.0F, 4.0F, 10.0F)
//                        .face(Direction.UP, face(TextureSlot.DIRT, 6, 6, 10, 10, null))
//                        .face(Direction.DOWN, face(FLOWERPOT, 6, 12, 10, 16, Direction.DOWN)));
//        stemElements(builder);
//        if (withBush) {
//            builder.requiredTextureSlot(BUSH).element((element) -> element.from(3.0F, 6.0F, 3.0F).to(13.0F, 16.0F, 13.0F).allFaces((direction, face) -> face.texture(BUSH).uvs(3, 3, 13, 13)));
//        }
//        return builder.build();
//    }
//
//    /**
//     * Adds the stem elements of a potted Berry Bush Stem, placed in the Flower Pot.
//     */
//    private static void stemElements(ExtendedModelTemplateBuilder builder) {
//        builder
//            .element((element) -> element.from(7.0F, 4.0F, 8.0F).to(9.0F, 6.0F, 8.0F)
//                    .rotation((rotation) -> rotation.origin(8.0F, 8.0F, 8.0F).singleAxis(Direction.Axis.Y, 45.0F))
//                    .face(Direction.NORTH, face(TextureSlot.STEM, 7, 14, 9, 16, null))
//                    .face(Direction.SOUTH, face(TextureSlot.STEM, 7, 14, 9, 16, null)))
//            .element((element) -> element.from(1.0F, 6.0F, 8.0F).to(15.0F, 16.0F, 8.0F)
//                    .rotation((rotation) -> rotation.origin(8.0F, 8.0F, 8.0F).singleAxis(Direction.Axis.Y, 45.0F))
//                    .face(Direction.NORTH, face(TextureSlot.STEM, 1, 4, 15, 14, null))
//                    .face(Direction.SOUTH, face(TextureSlot.STEM, 1, 4, 15, 14, null)))
//            .element((element) -> element.from(8.0F, 4.0F, 7.0F).to(8.0F, 6.0F, 9.0F)
//                    .rotation((rotation) -> rotation.origin(8.0F, 8.0F, 8.0F).singleAxis(Direction.Axis.Y, 45.0F))
//                    .face(Direction.WEST, face(TextureSlot.STEM, 7, 14, 9, 16, null))
//                    .face(Direction.EAST, face(TextureSlot.STEM, 7, 14, 9, 16, null)))
//            .element((element) -> element.from(8.0F, 6.0F, 1.0F).to(8.0F, 16.0F, 15.0F)
//                    .rotation((rotation) -> rotation.origin(8.0F, 8.0F, 8.0F).singleAxis(Direction.Axis.Y, 45.0F))
//                    .face(Direction.WEST, face(TextureSlot.STEM, 1, 4, 15, 14, null))
//                    .face(Direction.EAST, face(TextureSlot.STEM, 1, 4, 15, 14, null)));
//    }
//}
