//package com.aetherteam.aether.data.providers;
//
//import com.aetherteam.aether.Aether;
//import com.aetherteam.aether.client.renderer.item.NamedItemProperty;
//import com.aetherteam.aether.item.miscellaneous.MoaEggItem;
//import net.minecraft.client.data.models.BlockModelGenerators;
//import net.minecraft.client.data.models.ItemModelGenerators;
//import net.minecraft.client.data.models.model.ItemModelUtils;
//import net.minecraft.client.data.models.model.ModelLocationUtils;
//import net.minecraft.client.data.models.model.ModelTemplate;
//import net.minecraft.client.data.models.model.ModelTemplates;
//import net.minecraft.client.data.models.model.TextureMapping;
//import net.minecraft.client.data.models.model.TextureSlot;
//import net.minecraft.client.renderer.item.properties.numeric.UseDuration;
//import net.minecraft.client.resources.model.sprite.Material;
//import net.minecraft.core.Direction;
//import net.minecraft.core.registries.BuiltInRegistries;
//import net.minecraft.resources.Identifier;
//import net.minecraft.util.ARGB;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemDisplayContext;
//import net.minecraft.world.level.block.Block;
//import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;
//
//import java.util.Optional;
//
///**
// * Helpers for generating the Aether's item models and item model definitions, built around vanilla's {@link ItemModelGenerators}.
// */
//public abstract class AetherItemModelProvider {
//    protected static final Identifier TRIM_PREFIX_GLOVES = Identifier.fromNamespaceAndPath(Aether.MODID, "trims/items/gloves_trim");
//    protected static final TextureSlot OVERLAY = TextureSlot.create("overlay");
//    protected static final TextureSlot FACE = TextureSlot.create("face");
//
//    protected static final ModelTemplate LANCE = ExtendedModelTemplateBuilder.of(ModelTemplates.FLAT_HANDHELD_ITEM)
//            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, (transform) -> transform.rotation(0.0F, -90.0F, 45.0F).translation(0.0F, 1.0F, -5.0F).scale(0.85F))
//            .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND, (transform) -> transform.rotation(0.0F, 90.0F, -45.0F).translation(0.0F, 1.0F, -5.0F).scale(0.85F))
//            .build();
//    protected static final ModelTemplate DART_SHOOTER = ExtendedModelTemplateBuilder.of(ModelTemplates.FLAT_HANDHELD_ITEM)
//            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, (transform) -> transform.rotation(0.0F, -90.0F, 45.0F).translation(0.0F, 1.5F, -1.0F).scale(0.85F))
//            .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND, (transform) -> transform.rotation(0.0F, 90.0F, -45.0F).translation(0.0F, 1.5F, -1.0F).scale(0.85F))
//            .build();
//    protected static final ModelTemplate ROTATED_ITEM = ExtendedModelTemplateBuilder.of(ModelTemplates.FLAT_ITEM)
//            .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, (transform) -> transform.rotation(90.0F, -90.0F, 25.0F).translation(1.13F, 3.2F, 1.13F).scale(0.68F))
//            .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND, (transform) -> transform.rotation(90.0F, 90.0F, -25.0F).translation(1.13F, 3.2F, 1.13F).scale(0.68F))
//            .build();
//    protected static final ModelTemplate OVERLAY_DUNGEON_BLOCK = ExtendedModelTemplateBuilder.builder()
//            .parent(Identifier.withDefaultNamespace("block/cube"))
//            .requiredTextureSlot(OVERLAY).requiredTextureSlot(FACE)
//            .element((element) -> element.from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F).allFaces((direction, face) -> face.texture(FACE).cullface(direction)))
//            .element((element) -> element.from(0.0F, 0.0F, -0.1F).to(16.0F, 16.0F, -0.1F)
//                    .rotation((rotation) -> rotation.origin(8.0F, 8.0F, 6.9F).singleAxis(Direction.Axis.Y, 0.0F))
//                    .face(Direction.NORTH, (face) -> face.texture(OVERLAY).lightEmission(15)))
//            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, (transform) -> transform.rotation(75.0F, 45.0F, 0.0F).translation(0.0F, 2.5F, 0.0F).scale(0.375F))
//            .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND, (transform) -> transform.rotation(75.0F, 45.0F, 0.0F).translation(0.0F, 2.5F, 0.0F).scale(0.375F))
//            .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, (transform) -> transform.rotation(-90.0F, -180.0F, -45.0F).scale(0.4F))
//            .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND, (transform) -> transform.rotation(-90.0F, -180.0F, -45.0F).scale(0.4F))
//            .transform(ItemDisplayContext.GROUND, (transform) -> transform.rotation(90.0F, 0.0F, 0.0F).translation(0.0F, 3.0F, 0.0F).scale(0.25F))
//            .transform(ItemDisplayContext.GUI, (transform) -> transform.rotation(30.0F, 135.0F, 0.0F).scale(0.625F))
//            .transform(ItemDisplayContext.FIXED, (transform) -> transform.scale(0.5F))
//            .build();
//    protected static final ModelTemplate TRANSLUCENT_WALL_INVENTORY = new ModelTemplate(Optional.of(Identifier.fromNamespaceAndPath("nitrogen_internals", "block/template_translucent_wall_inventory")), Optional.empty(), TextureSlot.WALL);
//
//    protected final ItemModelGenerators itemModels;
//    protected final BlockModelGenerators blockModels;
//
//    public AetherItemModelProvider(ItemModelGenerators itemModels, BlockModelGenerators blockModels) {
//        this.itemModels = itemModels;
//        this.blockModels = blockModels;
//    }
//
//    public abstract void run();
//
//    // Utility methods.
//
//    public String itemName(Item item) {
//        return BuiltInRegistries.ITEM.getKey(item).getPath();
//    }
//
//    public String blockName(Block block) {
//        return BuiltInRegistries.BLOCK.getKey(block).getPath();
//    }
//
//    public Material itemTexture(Item item, String location) {
//        return this.itemTexture(this.itemName(item), location);
//    }
//
//    public Material itemTexture(String name, String location) {
//        return new Material(Identifier.fromNamespaceAndPath(Aether.MODID, "item/" + location + name));
//    }
//
//    public Material blockTexture(String name, String location) {
//        return new Material(Identifier.fromNamespaceAndPath(Aether.MODID, "block/" + location + name));
//    }
//
//    public Identifier model(Item item, ModelTemplate template, TextureMapping textures) {
//        return template.create(ModelLocationUtils.getModelLocation(item), textures, this.itemModels.modelOutput);
//    }
//
//    public Identifier model(Item item, String suffix, ModelTemplate template, TextureMapping textures) {
//        return template.create(ModelLocationUtils.getModelLocation(item, suffix), textures, this.itemModels.modelOutput);
//    }
//
//    public void plain(Item item, Identifier model) {
//        this.itemModels.itemModelOutput.accept(item, ItemModelUtils.plainModel(model));
//    }
//
//    // Items.
//
//    public void item(Item item, String location) {
//        this.plain(item, this.model(item, ModelTemplates.FLAT_ITEM, TextureMapping.layer0(this.itemTexture(item, location))));
//    }
//
//    public void handheldItem(Item item, String location) {
//        this.plain(item, this.model(item, ModelTemplates.FLAT_HANDHELD_ITEM, TextureMapping.layer0(this.itemTexture(item, location))));
//    }
//
//    public void lanceItem(Item item, String location) {
//        this.plain(item, this.model(item, LANCE, TextureMapping.layer0(this.itemTexture(item, location))));
//    }
//
//    public void dartShooterItem(Item item, String location) {
//        this.plain(item, this.model(item, DART_SHOOTER, TextureMapping.layer0(this.itemTexture(item, location))));
//    }
//
//    public void rotatedItem(Item item, String location) {
//        this.plain(item, this.model(item, ROTATED_ITEM, TextureMapping.layer0(this.itemTexture(item, location))));
//    }
//
//    /**
//     * Switches to a different model when the item is renamed to a specific name.
//     */
//    public void nameableWeapon(Item item, String location, String renamedVariant) {
//        Identifier normal = this.model(item, ModelTemplates.FLAT_HANDHELD_ITEM, TextureMapping.layer0(this.itemTexture(item, location)));
//        Identifier renamed = ModelTemplates.FLAT_HANDHELD_ITEM.create(Identifier.fromNamespaceAndPath(Aether.MODID, "item/" + renamedVariant), TextureMapping.layer0(this.itemTexture(renamedVariant, location)), this.itemModels.modelOutput);
//        String name = renamedVariant.replace('_', ' ');
//        this.itemModels.itemModelOutput.accept(item, ItemModelUtils.conditional(new NamedItemProperty(name), ItemModelUtils.plainModel(renamed), ItemModelUtils.plainModel(normal)));
//    }
//
//    /**
//     * [CODE COPY] - {@link ItemModelGenerators#generateBow(Item)}.<br><br>
//     * Modified to use the Aether's texture locations.
//     */
//    public void bowItem(Item item, String location) {
//        Identifier base = this.model(item, ModelTemplates.BOW, TextureMapping.layer0(this.itemTexture(item, location)));
//        Identifier pulling0 = this.model(item, "_pulling_0", ModelTemplates.BOW, TextureMapping.layer0(this.itemTexture(this.itemName(item) + "_pulling_0", location)));
//        Identifier pulling1 = this.model(item, "_pulling_1", ModelTemplates.BOW, TextureMapping.layer0(this.itemTexture(this.itemName(item) + "_pulling_1", location)));
//        Identifier pulling2 = this.model(item, "_pulling_2", ModelTemplates.BOW, TextureMapping.layer0(this.itemTexture(this.itemName(item) + "_pulling_2", location)));
//        this.itemModels.itemModelOutput.accept(item, ItemModelUtils.conditional(ItemModelUtils.isUsingItem(),
//                ItemModelUtils.rangeSelect(new UseDuration(false), 0.05F, ItemModelUtils.plainModel(pulling0),
//                        ItemModelUtils.override(ItemModelUtils.plainModel(pulling1), 0.65F),
//                        ItemModelUtils.override(ItemModelUtils.plainModel(pulling2), 0.9F)),
//                ItemModelUtils.plainModel(base)));
//    }
//
//    public void helmetItem(Item item, String location) {
//        this.armorItem(item, location, ItemModelGenerators.TRIM_PREFIX_HELMET);
//    }
//
//    public void chestplateItem(Item item, String location) {
//        this.armorItem(item, location, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE);
//    }
//
//    public void leggingsItem(Item item, String location) {
//        this.armorItem(item, location, ItemModelGenerators.TRIM_PREFIX_LEGGINGS);
//    }
//
//    public void bootsItem(Item item, String location) {
//        this.armorItem(item, location, ItemModelGenerators.TRIM_PREFIX_BOOTS);
//    }
//
//    public void armorItem(Item item, String location, Identifier trimPrefix) {
//        Identifier base = this.model(item, ModelTemplates.FLAT_ITEM, TextureMapping.layer0(this.itemTexture(item, location)));
//        this.itemModels.generateDynamicTrimmableItem(item, base, trimPrefix, null);
//    }
//
//    public void glovesItem(Item item, String location) {
//        this.armorItem(item, location, TRIM_PREFIX_GLOVES);
//    }
//
//    public void dyedGlovesItem(Item item, String location, int defaultColor) {
//        Identifier base = this.model(item, ModelTemplates.TWO_LAYERED_ITEM, TextureMapping.layered(this.itemTexture(item, location), this.itemTexture(this.itemName(item) + "_overlay", location)));
//        this.itemModels.generateDynamicTrimmableItem(item, base, TRIM_PREFIX_GLOVES, defaultColor, null);
//    }
//
//    public void moaEggItem(Item item, String location) {
//        Identifier model = this.model(item, ModelTemplates.TWO_LAYERED_ITEM, TextureMapping.layered(this.itemTexture("moa_egg", location), this.itemTexture("moa_egg_spot", location)));
//        int color = ARGB.opaque(((MoaEggItem) item).getColor());
//        this.itemModels.itemModelOutput.accept(item, ItemModelUtils.tintedModel(model, ItemModelUtils.constantTint(color), ItemModelUtils.constantTint(color)));
//    }
//
//    public void portalItem(Item item, String location) {
//        this.plain(item, this.model(item, ModelTemplates.TWO_LAYERED_ITEM, TextureMapping.layered(this.itemTexture(item, location), this.itemTexture(this.itemName(item) + "_inside", location))));
//    }
//
//    public void eggItem(Item item) {
//        this.item(item, "spawn_eggs/");
//    }
//
//    // Block items.
//
//    public void itemBlock(Block block) {
//        this.blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
//    }
//
//    public void itemLockedDungeonBlock(Block block, Block baseBlock) {
//        this.itemOverlayDungeonBlock(block, baseBlock, "lock");
//    }
//
//    public void itemTrappedDungeonBlock(Block block, Block baseBlock) {
//        this.itemOverlayDungeonBlock(block, baseBlock, "exclamation");
//    }
//
//    public void itemBossDoorwayDungeonBlock(Block block, Block baseBlock) {
//        this.itemOverlayDungeonBlock(block, baseBlock, "door");
//    }
//
//    public void itemTreasureDoorwayDungeonBlock(Block block, Block baseBlock) {
//        this.itemOverlayDungeonBlock(block, baseBlock, "treasure");
//    }
//
//    public void itemOverlayDungeonBlock(Block block, Block baseBlock, String overlay) {
//        TextureMapping textures = new TextureMapping()
//                .put(OVERLAY, this.blockTexture(overlay, "dungeon/"))
//                .put(FACE, this.blockTexture(this.blockName(baseBlock), "dungeon/"))
//                .putForced(TextureSlot.PARTICLE, this.blockTexture(this.blockName(baseBlock), "dungeon/"));
//        this.plain(block.asItem(), this.model(block.asItem(), OVERLAY_DUNGEON_BLOCK, textures));
//    }
//
//    public void translucentItemWallBlock(Block block, Block baseBlock, String location) {
//        this.plain(block.asItem(), this.model(block.asItem(), TRANSLUCENT_WALL_INVENTORY, new TextureMapping().put(TextureSlot.WALL, this.blockTexture(this.blockName(baseBlock), location))));
//    }
//
//}
