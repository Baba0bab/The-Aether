package com.aetherteam.aether.client.gui.screen.inventory;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.AetherKeys;
import com.aetherteam.aether.client.gui.screen.perks.AetherCustomizationsScreen;
import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import com.aetherteam.aether.inventory.menu.AetherAccessoriesMenu;
import com.aetherteam.aether.network.packet.serverbound.ClearItemPacket;
import com.aetherteam.aether.perk.PerkUtil;
import com.aetherteam.nitrogen.fabric.network.PacketDistributor;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.api.users.UserData;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.CraftingRecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

/**
 * [CODE COPY] - {@link InventoryScreen}.<br><br>
 * Modified to register slots for Aether accessories.
 */
public class AetherAccessoriesScreen extends AbstractRecipeBookScreen<AetherAccessoriesMenu> {
    public static final WidgetSprites ACCESSORIES_BUTTON = new WidgetSprites(Identifier.fromNamespaceAndPath(Aether.MODID, "inventory/accessories_button"), Identifier.fromNamespaceAndPath(Aether.MODID, "inventory/accessories_button_highlighted"));
    public static final WidgetSprites SKINS_BUTTON = new WidgetSprites(Identifier.fromNamespaceAndPath(Aether.MODID, "skins/skins_button"), Identifier.fromNamespaceAndPath(Aether.MODID, "skins/skins_button_highlighted"));
    public static final WidgetSprites CUSTOMIZATION_BUTTON = new WidgetSprites(Identifier.fromNamespaceAndPath(Aether.MODID, "customization/customization_button"), Identifier.fromNamespaceAndPath(Aether.MODID, "customization/customization_button_highlighted"));
    private static final WidgetSprites RENDER_TOGGLE_BUTTON = new WidgetSprites(Identifier.withDefaultNamespace("recipe_book/filter_enabled"), Identifier.withDefaultNamespace("recipe_book/filter_enabled_highlighted"));

    private static final Identifier ACCESSORIES_INVENTORY = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/gui/inventory/accessories.png");
    private static final Identifier ACCESSORIES_INVENTORY_CREATIVE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/gui/inventory/accessories_creative.png");
    private static final Identifier CURIO_INVENTORY = Identifier.fromNamespaceAndPath("curios", "textures/gui/curios/inventory.png");

    private static final SimpleContainer DESTROY_ITEM_CONTAINER = new SimpleContainer(1);
    private final RecipeBookComponent<?> recipeBookComponent;
    private final List<AbstractWidget> renderButtons = new ArrayList<>();
    private boolean buttonClicked;
    private boolean isRenderButtonHovered;
    @Nullable
    private Slot destroyItemSlot;
    private int nukeCoolDown = 0;

    public AetherAccessoriesScreen(AetherAccessoriesMenu accessoriesMenu, Inventory playerInventory, Component title) {
        this(accessoriesMenu, new CraftingRecipeBookComponent(accessoriesMenu), playerInventory, title);
    }

    private AetherAccessoriesScreen(AetherAccessoriesMenu accessoriesMenu, RecipeBookComponent<?> recipeBookComponent, Inventory playerInventory, Component title) {
        super(accessoriesMenu, recipeBookComponent, playerInventory, title);
        this.recipeBookComponent = recipeBookComponent;
    }

    @Override
    public void init() {
        super.init();
        this.updateRenderButtons();

        // Create perk-related buttons.
        User user = UserData.Client.getClientUser();
        if (user != null) {
            if (!AetherConfig.CLIENT.disable_skins_button.get() || PerkUtil.hasAnyMoaSkins().test(user)) { // Add the skins button if the config is enabled. If not, only display for players with access.
                this.addRenderableWidget(this.createPerkButton(SKINS_BUTTON, 2, (pressed) -> Minecraft.getInstance().gui.setScreen(new MoaSkinsScreen(this)), "gui.aether.accessories.skins_button"));
            }
            if (PerkUtil.hasDeveloperGlow().test(user) || PerkUtil.hasHalo().test(user)) {
                this.addRenderableWidget(this.createPerkButton(CUSTOMIZATION_BUTTON, 24, (pressed) -> Minecraft.getInstance().gui.setScreen(new AetherCustomizationsScreen(this)), "gui.aether.accessories.customization_button"));
            }
        }
    }

    @Override
    protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + 142, this.height / 2 - 22);
    }

    @Override
    protected void onRecipeBookButtonClick() {
        this.buttonClicked = true;
        this.updateRenderButtons();
    }

    @Override
    public void containerTick() {
        super.containerTick();
        if (this.nukeCoolDown > 0) {
            this.nukeCoolDown--;
        }
    }

    /**
     * Creates a button for one of the perk screens, which moves above the inventory when the recipe book is open.
     *
     * @param sprites The {@link WidgetSprites} for the button.
     * @param offset  The {@link Integer} offset along the side of the inventory.
     * @param onPress The {@link net.minecraft.client.gui.components.Button.OnPress} behavior.
     * @param tooltip The {@link String} translation key for the tooltip.
     * @return The {@link ImageButton}.
     */
    private ImageButton createPerkButton(WidgetSprites sprites, int offset, Button.OnPress onPress, String tooltip) {
        ImageButton button = new ImageButton(this.nitrogen_fabric$getGuiLeft() - 22, this.nitrogen_fabric$getGuiTop() + offset, 20, 20, sprites, onPress, Component.translatable(tooltip)) {
            @Override
            public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
                if (!AetherAccessoriesScreen.this.recipeBookComponent.isVisible()) {
                    this.setX(AetherAccessoriesScreen.this.nitrogen_fabric$getGuiLeft() - 22);
                    this.setY(AetherAccessoriesScreen.this.nitrogen_fabric$getGuiTop() + offset);
                } else {
                    this.setX(AetherAccessoriesScreen.this.nitrogen_fabric$getGuiLeft() + offset);
                    this.setY(AetherAccessoriesScreen.this.nitrogen_fabric$getGuiTop() - 22);
                }
                super.extractContents(guiGraphics, mouseX, mouseY, partialTick);
            }
        };
        button.setTooltip(Tooltip.create(Component.translatable(tooltip)));
        return button;
    }

    /**
     * Adds the toggle buttons for the visibility of each accessory slot's rendering.
     */
    private void updateRenderButtons() {
        this.renderButtons.forEach(this::removeWidget);
        this.renderButtons.clear();
        // Trinkets handles the visibility of equipped accessories itself, so no toggle buttons are added on Fabric.
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (Minecraft.getInstance().player != null) {
            if (Minecraft.getInstance().player.isCreative() && this.destroyItemSlot == null) {
                this.destroyItemSlot = new Slot(DESTROY_ITEM_CONTAINER, 0, 172, 142);
                this.getMenu().slots.add(this.destroyItemSlot);
            } else if (!Minecraft.getInstance().player.isCreative() && this.destroyItemSlot != null) {
                this.getMenu().slots.remove(this.destroyItemSlot);
                this.destroyItemSlot = null;
            }
        }

        this.isRenderButtonHovered = this.renderButtons.stream().anyMatch(AbstractWidget::isHovered);

        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void extractTooltip(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        LocalPlayer clientPlayer = Minecraft.getInstance().player;
        if (clientPlayer != null && clientPlayer.inventoryMenu.getCarried().isEmpty()) {
            if (this.isRenderButtonHovered) {
                guiGraphics.setTooltipForNextFrame(Component.translatable("gui.curios.toggle"), mouseX, mouseY);
            } else if (this.destroyItemSlot != null && this.isHovering(this.destroyItemSlot.x, this.destroyItemSlot.y, 16, 16, mouseX, mouseY)) {
                guiGraphics.setTooltipForNextFrame(this.font, Component.translatable("inventory.binSlot"), mouseX, mouseY);
            } else {
                super.extractTooltip(guiGraphics, mouseX, mouseY);
            }
        }
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTicks);
        if (Minecraft.getInstance().player != null) {
            int i = this.nitrogen_fabric$getGuiLeft();
            int j = this.nitrogen_fabric$getGuiTop();
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Minecraft.getInstance().player.isCreative() ? ACCESSORIES_INVENTORY_CREATIVE : ACCESSORIES_INVENTORY, i, j, 0.0F, 0.0F, this.nitrogen_fabric$getXSize() + this.creativeXOffset(), this.nitrogen_fabric$getYSize(), 256, 256);
            InventoryScreen.extractEntityInInventoryFollowsMouse(guiGraphics, i + 9, j + 8, i + 58, j + 78, 30, 0.1575F, mouseX, mouseY, Minecraft.getInstance().player);
        }
    }

    /**
     * @return The {@link Integer} x-offset for the GUI.
     */
    private int creativeXOffset() {
        return Minecraft.getInstance().player != null && Minecraft.getInstance().player.isCreative() ? 18 : 0;
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        if (Minecraft.getInstance().player != null) {
            guiGraphics.text(this.font, this.title, 115, 6, 0xFF404040, false);
        }
    }

    @Override
    protected boolean isBiggerResultSlot() {
        return false;
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (super.keyPressed(event)) {
            return true;
        } else if (AetherKeys.OPEN_ACCESSORY_INVENTORY.isActiveAndMatches(InputConstants.getKey(event))) {
            LocalPlayer playerEntity = Minecraft.getInstance().player;
            if (playerEntity != null) {
                playerEntity.closeContainer();
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean isHovering(int rectX, int rectY, int rectWidth, int rectHeight, double pointX, double pointY) {
        if (this.isRenderButtonHovered) {
            return false;
        }
        return super.isHovering(rectX, rectY, rectWidth, rectHeight, pointX, pointY);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int left, int top) {
        return super.hasClickedOutside(mouseX, mouseY, left, top) && !(mouseX >= left + this.nitrogen_fabric$getXSize() && mouseX < left + this.nitrogen_fabric$getXSize() + this.creativeXOffset() && mouseY >= top && mouseY < top + this.nitrogen_fabric$getYSize());
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        if (this.buttonClicked) {
            this.buttonClicked = false;
            return true;
        } else {
            return super.mouseReleased(event);
        }
    }

    /**
     * [CODE COPY] {@link net.minecraft.client.gui.screens.inventory.AbstractContainerScreen}.<br><br>
     * Heavily modified to only have behavior for the item trash slot.
     */
    @Override
    protected void slotClicked(Slot slot, int slotId, int mouseButton, ContainerInput type) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().gameMode != null) {
            if (slot != null && slot == this.destroyItemSlot && slot.mayPickup(Minecraft.getInstance().player)) {
                if (type == ContainerInput.QUICK_MOVE) {
                    for (int j = 0; j < Minecraft.getInstance().player.inventoryMenu.getItems().size(); ++j) {
                        Minecraft.getInstance().gameMode.handleCreativeModeItemAdd(ItemStack.EMPTY, j);
                    }
                } else {
                    this.getMenu().setCarried(ItemStack.EMPTY);
                    PacketDistributor.sendToServer(new ClearItemPacket(Minecraft.getInstance().player.getId()));
                }
                return;
            }
            super.slotClicked(slot, slotId, mouseButton, type);
        }
    }

    /**
     * Offsets the accessories screen button based on what screen is currently open.
     *
     * @param screen The current {@link Screen}.
     * @return A {@link Pair} containing the x and y {@link Integer}s.
     */
    public static Pair<Integer, Integer> getButtonOffset(Screen screen) {
        int x = 0;
        int y = 0;
        if (screen instanceof InventoryScreen) {
            x = AetherConfig.CLIENT.button_inventory_x.get();
            y = AetherConfig.CLIENT.button_inventory_y.get();
        }
        if (screen instanceof CreativeModeInventoryScreen) {
            x = AetherConfig.CLIENT.button_creative_x.get();
            y = AetherConfig.CLIENT.button_creative_y.get();
        }
        if (screen instanceof AetherAccessoriesScreen) {
            x = AetherConfig.CLIENT.button_accessories_x.get();
            y = AetherConfig.CLIENT.button_accessories_y.get();
        }
        return Pair.of(x, y);
    }
}
