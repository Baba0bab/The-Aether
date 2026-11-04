package com.aetherteam.aether.inventory.menu;

import net.minecraft.util.Prediction;
import eu.pb4.trinkets.api.TrinketInventory;
import eu.pb4.trinkets.api.TrinketSlotUtils;
import com.aetherteam.aetherfabric.accessories.AccessoryApi;
import com.aetherteam.aether.inventory.AetherAccessorySlots;
import com.aetherteam.aether.mixin.mixins.common.accessor.AbstractContainerMenuAccessor;
import com.aetherteam.aether.mixin.mixins.common.accessor.CraftingMenuAccessor;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class AetherAccessoriesMenu extends InventoryMenu {
    private static final Map<EquipmentSlot, Identifier> TEXTURE_EMPTY_SLOTS = Map.of(
        EquipmentSlot.FEET,
        InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS,
        EquipmentSlot.LEGS,
        InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS,
        EquipmentSlot.CHEST,
        InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE,
        EquipmentSlot.HEAD,
        InventoryMenu.EMPTY_ARMOR_SLOT_HELMET
    );
    private static final EquipmentSlot[] SLOT_IDS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    private final CraftingContainer craftSlots = new TransientCraftingContainer(this, 2, 2);
    private final ResultContainer resultSlots = new ResultContainer();
    private final Player owner;
    private final Set<Slot> accessorySlots = new java.util.HashSet<>();

    public final boolean hasButton;

    public AetherAccessoriesMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, true);
    }

    public AetherAccessoriesMenu(int containerId, Inventory playerInventory, boolean hasButton) {
        super(playerInventory, playerInventory.player.level().isClientSide(), playerInventory.player);
        this.owner = playerInventory.player;

        this.slots.clear();

        AbstractContainerMenuAccessor abstractContainerMenuAccessor = (AbstractContainerMenuAccessor) this;
        abstractContainerMenuAccessor.aether$setMenuType(AetherMenuTypes.ACCESSORIES.get());
        abstractContainerMenuAccessor.aether$setContainerId(containerId);
        abstractContainerMenuAccessor.aether$getRemoteSlots().clear();
        abstractContainerMenuAccessor.aether$getLastSlots().clear();

        this.addSlot(new ResultSlot(playerInventory.player, this.craftSlots, this.resultSlots, 0, 154, 28));

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                this.addSlot(new Slot(this.craftSlots, j + i * 2, 116 + j * 18, 18 + i * 18));
            }
        }

        int x = 77, y = 8; // Adjust these values

        this.addAccessorySlots(x, y, true, AetherAccessorySlots.getPendantSlotType(), AetherAccessorySlots.getCapeSlotType(), AetherAccessorySlots.getShieldSlotType());
        this.addAccessorySlots(x + 18, y, true, AetherAccessorySlots.getRingSlotType(), AetherAccessorySlots.getGlovesSlotType());
        this.addAccessorySlots(x, y + (3 * 18), false, AetherAccessorySlots.getAccessorySlotType());

        this.hasButton = hasButton;

        for (int k = 0; k < 4; k++) {
            EquipmentSlot equipmentslot = SLOT_IDS[k];
            Identifier resourcelocation = TEXTURE_EMPTY_SLOTS.get(equipmentslot);
            this.addSlot(new ArmorSlot(playerInventory, this.owner, equipmentslot, 36 + (3 - k), 59, 8 + k * 18, resourcelocation));
        }

        for (int l = 0; l < 3; l++) {
            for (int j1 = 0; j1 < 9; j1++) {
                this.addSlot(new Slot(playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; i1++) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
        }

        this.addSlot(new Slot(playerInventory, 40, 116, 62) {
            @Override
            public void setByPlayer(ItemStack p_270969_, ItemStack p_299918_) {
                AetherAccessoriesMenu.this.owner.onEquipItem(EquipmentSlot.OFFHAND, p_299918_, p_270969_);
                super.setByPlayer(p_270969_, p_299918_);
            }

            @Override
            public Identifier getNoItemIcon() {
                return InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD;
            }
        });
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    @Override
    public void slotsChanged(Container inventory) {
        if (this.owner.level() instanceof ServerLevel serverLevel) {
            CraftingMenuAccessor.callSlotChangedCraftingGrid(this, serverLevel, this.owner, this.craftSlots, this.resultSlots, null);
        }
    }

    /**
     * Called when the container is closed.
     */
    @Override
    public void removed(Player player) {
        super.removed(player);
        this.resultSlots.clearContent();
        if (!player.level().isClientSide()) {
            this.clearContainer(player, this.craftSlots);
        }
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player inventory and the other inventory(s).
     */
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();
            EquipmentSlot equipmentSlot = player.getEquipmentSlotForItem(itemStack);
            Set<Integer> emptyAccessorySlots = this.getEmptyAccessorySlots(itemStack1);
            if (index == 0) {
                if (!this.moveItemStackTo(itemStack1, 17, 53, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack1, itemStack);
            } else if (index < 5) {
                if (!this.moveItemStackTo(itemStack1, 17, 53, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 17) {
                if (!this.moveItemStackTo(itemStack1, 17, 53, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR && !this.slots.get(16 - equipmentSlot.getIndex()).hasItem()) {
                int i = 16 - equipmentSlot.getIndex();
                if (!this.moveItemStackTo(itemStack1, i, i + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 53 && !emptyAccessorySlots.isEmpty()) {
                for (int i : emptyAccessorySlots) {
                    if (!this.moveItemStackTo(itemStack1, i, i + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (equipmentSlot == EquipmentSlot.OFFHAND && !(this.slots.get(53)).hasItem()) {
                if (!this.moveItemStackTo(itemStack1, 53, 54, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 44) {
                if (!this.moveItemStackTo(itemStack1, 44, 53, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 53) {
                if (!this.moveItemStackTo(itemStack1, 17, 44, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack1, 17, 53, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (itemStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, itemStack1);
            if (index == 0) {
                player.drop(itemStack1, false, Prediction.SERVER_ONLY);
            }
        }
        return itemStack;
    }

    /**
     * Adds the slots of Curios slot types to the menu.
     *
     * @param x           The {@link Integer} x-position of the first slot.
     * @param y           The {@link Integer} y-position of the first slot.
     * @param column      Whether the slots are laid out in a column, as a {@link Boolean}. Otherwise, they are laid out in a row.
     * @param identifiers The {@link String} identifiers of the slot types to add.
     */
    private void addAccessorySlots(int x, int y, boolean column, String... identifiers) {
        int offset = 0;
        for (String identifier : identifiers) {
            TrinketInventory inventory = AccessoryApi.getInventory(this.owner, identifier).orElse(null);
            if (inventory != null) {
                for (int i = 0; i < inventory.getContainerSize(); i++) {
                    int slotX = column ? x : x + (offset * 18);
                    int slotY = column ? y + (offset * 18) : y;
                    Slot slot = TrinketSlotUtils.createSlot(inventory.getOrCreateSlotAccess(i), slotX, slotY);
                    this.accessorySlots.add(slot);
                    this.addSlot(slot);
                    offset++;
                }
            }
        }
    }

    /**
     * @return Whether the given {@link Slot} is one of the Trinkets accessory slots of this menu.
     */
    public boolean isAccessorySlot(Slot slot) {
        return this.accessorySlots.contains(slot);
    }

    private Set<Integer> getEmptyAccessorySlots(ItemStack stack) {
        Set<Integer> slots = new LinkedHashSet<>();
        for (int i = 0; i < this.slots.size(); i++) {
            Slot slot = this.slots.get(i);
            if (this.isAccessorySlot(slot) && !slot.hasItem() && slot.mayPlace(stack)) {
                slots.add(i);
            }
        }
        return slots;
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is null for the initial slot that was double-clicked.
     */
    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }
}
