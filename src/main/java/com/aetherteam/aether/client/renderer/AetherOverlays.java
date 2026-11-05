package com.aetherteam.aether.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;

import net.minecraft.client.renderer.RenderPipelines;
import org.joml.Matrix3x2fStack;
import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.entity.ai.attribute.AetherAttributes;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.mixin.mixins.client.accessor.GuiAccessor;
import com.aetherteam.aether.mixin.mixins.client.accessor.HeartTypeAccessor;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.util.Util;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AetherOverlays {
    private static final Identifier TEXTURE_INEBRIATION_VIGNETTE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/blur/inebriation_vignette.png");
    private static final Identifier TEXTURE_REMEDY_VIGNETTE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/blur/remedy_vignette.png");
    private static final Identifier TEXTURE_SHIELD_OF_REPULSION_VIGNETTE = Identifier.fromNamespaceAndPath(Aether.MODID, "textures/blur/shield_of_repulsion_vignette.png");

    private static final Identifier TEXTURE_COOLDOWN_BAR = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/cooldown");
    private static final Identifier TEXTURE_COOLDOWN_BAR_BACKGROUND = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/cooldown_background");

    public static final Identifier TEXTURE_DEFAULT_JUMPS = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/jumps");

    private static final Identifier TEXTURE_LIFE_SHARD_FULL = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_full");
    private static final Identifier TEXTURE_LIFE_SHARD_HALF = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_half");
    private static final Identifier TEXTURE_LIFE_SHARD_FULL_BLINKING = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_full_blinking");
    private static final Identifier TEXTURE_LIFE_SHARD_HALF_BLINKING = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_half_blinkin");
    private static final Identifier TEXTURE_LIFE_SHARD_POISONED_FULL = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_poisoned_full");
    private static final Identifier TEXTURE_LIFE_SHARD_POISONED_HALF = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_poisoned_half");
    private static final Identifier TEXTURE_LIFE_SHARD_POISONED_FULL_BLINKING = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_poisoned_full_blinking");
    private static final Identifier TEXTURE_LIFE_SHARD_POISONED_HALF_BLINKING = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_poisoned_half_blinking");
    private static final Identifier TEXTURE_LIFE_SHARD_WITHERED_FULL = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_withered_full");
    private static final Identifier TEXTURE_LIFE_SHARD_WITHERED_HALF = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_withered_half");
    private static final Identifier TEXTURE_LIFE_SHARD_WITHERED_FULL_BLINKING = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_withered_full_blinking");
    private static final Identifier TEXTURE_LIFE_SHARD_WITHERED_HALF_BLINKING = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_withered_half_blinking");
    private static final Identifier TEXTURE_LIFE_SHARD_ABSORBING_FULL = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_absorbing_full");
    private static final Identifier TEXTURE_LIFE_SHARD_ABSORBING_HALF = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_absorbing_half");
    private static final Identifier TEXTURE_LIFE_SHARD_FROZEN_FULL = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_frozen_full");
    private static final Identifier TEXTURE_LIFE_SHARD_FROZEN_HALF = Identifier.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_frozen_half");

    /**
     * @see AetherClient#eventSetup()
     */
    public static void registerOverlays() {
        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(Aether.MODID, "aether_portal_overlay"), (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderAetherPortalOverlay(guiGraphics, minecraft, player.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER), partialTicks);
            }
        });
        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(Aether.MODID, "inebriation_vignette"), (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderInebriationOverlay(guiGraphics, minecraft, window, player);
            }
        });
        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(Aether.MODID, "remedy_vignette"), (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderRemedyOverlay(guiGraphics, minecraft, window, player);
            }
        });
        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(Aether.MODID, "shield_of_repulsion_vignette"), (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderRepulsionOverlay(guiGraphics, minecraft, window, player);
            }
        });
        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(Aether.MODID, "hammer_cooldown"), (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderHammerCooldownOverlay(guiGraphics, minecraft, window, player);
            }
        });
        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(Aether.MODID, "moa_jumps"), (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderMoaJumps(guiGraphics, window, player);
            }
        });
        HudElementRegistry.attachElementAfter(VanillaHudElements.HEALTH_BAR, Identifier.fromNamespaceAndPath(Aether.MODID, "silver_life_shard_hearts"), (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            Hud gui = minecraft.gui.hud;
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderSilverLifeShardHearts(guiGraphics, minecraft, window, gui, player);
            }
        });
    }


    /**
     * [CODE COPY] - Hud#extractPortalOverlay.<br><br>
     * Warning for "deprecation" is suppressed because vanilla calls {@link net.minecraft.client.renderer.block.BlockModelShaper#getParticleIcon(BlockState)} just fine.
     */
    private static void renderAetherPortalOverlay(GuiGraphicsExtractor guiGraphics, Minecraft minecraft, AetherPlayerAttachment handler, DeltaTracker partialTicks) {
        if (minecraft.gui.hud.isHidden()) return;
        float timeInPortal = Mth.lerp(partialTicks.getGameTimeDeltaPartialTick(false), handler.getOldPortalIntensity(), handler.getPortalIntensity());
        if (timeInPortal > 0.0F) {
            if (timeInPortal < 1.0F) {
                timeInPortal *= timeInPortal;
                timeInPortal *= timeInPortal;
                timeInPortal = timeInPortal * 0.8F + 0.2F;
            }

            int i = ARGB.white(timeInPortal);
            TextureAtlasSprite textureatlassprite = minecraft.getModelManager().getBlockStateModelSet().getParticleMaterial(AetherBlocks.AETHER_PORTAL.get().defaultBlockState()).sprite();
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, textureatlassprite, 0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight(), i);
        }
    }

    /**
     * Renders a purple vignette with pulsing opacity.
     *
     * @param guiGraphics The {@link GuiGraphicsExtractor} for rendering.
     * @param minecraft   The {@link Minecraft} instance.
     * @param window      The game {@link Window}.
     * @param player      The player that has the effect.
     */
    private static void renderInebriationOverlay(GuiGraphicsExtractor guiGraphics, Minecraft minecraft, Window window, Player player) {
        if (minecraft.gui.hud.isHidden()) return;
        MobEffectInstance inebriation = player.getEffect(AetherEffects.INEBRIATION);
        double effectScale = minecraft.options.screenEffectScale().get();
        if (inebriation != null) {
            float inebriationDuration = (float) (inebriation.getDuration() % 50) / 50;
            float alpha = (inebriationDuration * inebriationDuration) / 5.0F + 0.4F;
            renderVignette(guiGraphics, window, effectScale, alpha, TEXTURE_INEBRIATION_VIGNETTE);
        }
    }

    /**
     * Renders a green vignette with a gradually fading opacity.
     *
     * @param guiGraphics The {@link GuiGraphicsExtractor} for rendering.
     * @param minecraft   The {@link Minecraft} instance.
     * @param window      The game {@link Window}.
     * @param player      The player that has the effect.
     */
    private static void renderRemedyOverlay(GuiGraphicsExtractor guiGraphics, Minecraft minecraft, Window window, Player player) {
        if (minecraft.gui.hud.isHidden()) return;
        MobEffectInstance remedy = player.getEffect(AetherEffects.REMEDY);
        double effectScale = minecraft.options.screenEffectScale().get();
        if (remedy != null) {
            int remedyStartDuration = player.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).getRemedyStartDuration();
            int remedyDuration = remedy.getDuration();
            if (remedyStartDuration > 0 && remedyDuration > 0) {
                float alpha = ((float) remedyDuration / remedyStartDuration) / 1.5F;
                renderVignette(guiGraphics, window, effectScale, alpha, TEXTURE_REMEDY_VIGNETTE);
            }
        }
    }

    /**
     * Renders a blue vignette with a gradually fading opacity.
     *
     * @param guiGraphics The {@link GuiGraphicsExtractor} for rendering.
     * @param minecraft   The {@link Minecraft} instance.
     * @param window      The game {@link Window}.
     * @param player      The player.
     */
    private static void renderRepulsionOverlay(GuiGraphicsExtractor guiGraphics, Minecraft minecraft, Window window, Player player) {
        if (minecraft.gui.hud.isHidden()) return;
        var handler = player.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER);
        int projectileImpactedMaximum = handler.getProjectileImpactedMaximum();
        int projectileImpactedTimer = handler.getProjectileImpactedTimer();
        double effectScale = minecraft.options.screenEffectScale().get();
        if (projectileImpactedTimer > 0) {
            float alpha = (float) projectileImpactedTimer / projectileImpactedMaximum;
            renderVignette(guiGraphics, window, effectScale, alpha, TEXTURE_SHIELD_OF_REPULSION_VIGNETTE);
        }
    }

    private static void renderVignette(GuiGraphicsExtractor guiGraphics, Window window, double effectScale, float alpha, Identifier resource) {
        Matrix3x2fStack poseStack = guiGraphics.pose();
        poseStack.pushMatrix();
        alpha *= (float) Math.sqrt(effectScale);
        int i = ARGB.white(alpha);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, resource, 0, 0, 0.0F, 0.0F, window.getGuiScaledWidth(), window.getGuiScaledHeight(), window.getGuiScaledWidth(), window.getGuiScaledHeight(), i);
        poseStack.popMatrix();
    }

    /**
     * Renders a boss-esque bar at the top of the screen for the Hammer of Kingbdogz' item cooldown.
     *
     * @param guiGraphics The {@link GuiGraphicsExtractor} for rendering.
     * @param minecraft   The {@link Minecraft} instance.
     * @param window      The game {@link Window}.
     * @param player      The {@link LocalPlayer}.
     */
    private static void renderHammerCooldownOverlay(GuiGraphicsExtractor guiGraphics, Minecraft minecraft, Window window, LocalPlayer player) {
        if (AetherConfig.CLIENT.enable_hammer_cooldown_overlay.get() && !minecraft.gui.hud.isHidden()) {
            Inventory inventory = player.getInventory();
            if (inventory.contains((itemStack) -> itemStack.is(AetherItems.HAMMER_OF_KINGBDOGZ.get()))) {
                List<ItemStack> items = new ArrayList<>(inventory.getNonEquipmentItems());
                items.add(inventory.getItem(Inventory.SLOT_OFFHAND));
                for (ItemStack itemStack : items) {
                    Item item = itemStack.getItem();
                    if (item == AetherItems.HAMMER_OF_KINGBDOGZ.get()) {
                        float cooldownPercent = player.getCooldowns().getCooldownPercent(itemStack, 0.0F);
                        if (cooldownPercent > 0.0F) {
                            if (player.getMainHandItem().getItem() == item) {
                                itemStack = player.getMainHandItem();
                            } else if (player.getOffhandItem().getItem() == item) {
                                itemStack = player.getOffhandItem();
                            }
                            String text = itemStack.getHoverName().getString().concat(" ").concat(Component.translatable("aether.hammer_of_kingbdogz_cooldown").getString());
                            guiGraphics.text(minecraft.font, text, (int) ((window.getGuiScaledWidth() / 2.0F) - (minecraft.font.width(text) / 2.0F)), 32, 0xFFFFFFFF);
                            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, TEXTURE_COOLDOWN_BAR_BACKGROUND, 128, 8, 0, 0, window.getGuiScaledWidth() / 2 - 64, 42, 128, 8);
                            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, TEXTURE_COOLDOWN_BAR, 128, 8, 0, 0, window.getGuiScaledWidth() / 2 - 64, 42, (int) (cooldownPercent * 128), 8);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Renders feathers at the top of the screen corresponding to the amount of jumps the currently mounted Moa has.
     *
     * @param guiGraphics The {@link GuiGraphicsExtractor} for rendering.
     * @param window      The game {@link Window}.
     * @param player      The {@link LocalPlayer}.
     */
    private static void renderMoaJumps(GuiGraphicsExtractor guiGraphics, Window window, LocalPlayer player) {
        if (player.getVehicle() instanceof Moa moa && !Minecraft.getInstance().gui.hud.isHidden()) {
            for (int jumpCount = 0; jumpCount < moa.getMaxJumps(); jumpCount++) {
                int xPos = ((window.getGuiScaledWidth() / 2) + (jumpCount * 8)) - (moa.getMaxJumps() * 8) / 2;
                int yPos = 18;
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, appendBackground(jumpCount >= moa.getRemainingJumps(), getMoaJumpTexture(moa, jumpCount)), xPos, yPos, 9, 11);
            }
        }
    }

    /**
     * Gets the texture used to render the feather on top of the screen.
     *
     * @param moa        The {@link Moa} being ridden
     * @param count      The current feather being rendered
     * @return           The {@link Identifier} of the feather that should be rendered
     */
    private static Identifier getMoaJumpTexture(Moa moa, double count) {
        AttributeInstance instance = moa.getAttribute(AetherAttributes.MOA_MAX_JUMPS);
        if (instance != null) {
            if (count < instance.getBaseValue()) {
                return getDefaultJumpsTexture(moa.getMoaType());
            }
            else {
                Set<AttributeModifier> modifiers = instance.getModifiers();
                double currentCount = instance.getBaseValue();

                for(AttributeModifier modifier : modifiers) {
                    if(modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE) {
                        currentCount += (instance.getBaseValue() * modifier.amount());
                    }
                    else {
                        currentCount += modifier.amount();
                    }

                    if(currentCount >= count) {
                        return moa.getOverlayTexture(modifier.id());
                    }
                }
            }
        }
        return TEXTURE_DEFAULT_JUMPS;
    }

    /**
     * @param type The {@link MoaType} being rendered.
     * @return The {@link Identifier} of the texture that should be rendered on top of the screen.
     * Uses {@link AetherOverlays#TEXTURE_DEFAULT_JUMPS} as a fallback if no other texture has been specified inside the {@link MoaType}
     */
    public static Identifier getDefaultJumpsTexture(@Nullable MoaType type) {
        if (type == null)
            return TEXTURE_DEFAULT_JUMPS;
        else return type.jumpsTexture().isPresent() ? type.jumpsTexture().get() : TEXTURE_DEFAULT_JUMPS;
    }

    /**
     * @param background The {@link Boolean} determines the state of the feather being rendered
     * @param location   The {@link Identifier} of the jump texture being rendered
     * @return           A {@link Identifier} of the jump texture, with the correct suffix applied.
     */
    private static Identifier appendBackground(boolean background, Identifier location) {
        if (background) {
            return location.withSuffix("_background");
        }
        else return location;
    }

    /**
     * [CODE COPY] - Hud#extractHealthLevel.<br><br>
     * Stripped down to only use what is necessary.<br>
     * Renders silver heart textures over the extra hearts given by Life Shards.
     *
     * @param guiGraphics The {@link GuiGraphicsExtractor} for rendering.
     * @param minecraft   The {@link Minecraft} instance.
     * @param window      The {@link Window} of the screen.
     * @param gui         The {@link Hud} included in rendering.
     * @param player      The {@link LocalPlayer}.
     */
    private static void renderSilverLifeShardHearts(GuiGraphicsExtractor guiGraphics, Minecraft minecraft, Window window, Hud gui, LocalPlayer player) {
        GuiAccessor guiAccessor = (GuiAccessor) gui;
        if (AetherConfig.CLIENT.enable_silver_hearts.get() && minecraft.gameMode.canHurtPlayer() && !minecraft.gui.hud.isHidden()) {
            var aetherPlayer = player.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER);
            if (aetherPlayer.getLifeShardCount() > 0) {
                AttributeInstance attributeInstance = player.getAttribute(Attributes.MAX_HEALTH);
                if (attributeInstance != null) {
                    int lastLifeShardHealth = 0;
                    int lastOverallHealth = 0;

                    double overallHealth = attributeInstance.getValue();
                    double maxLifeShardHealth = aetherPlayer.getLifeShardHealthAttributeModifier().amount();

                    int maxDefaultHealth = Mth.ceil(overallHealth - maxLifeShardHealth);

                    int currentOverallHealth = Mth.ceil(player.getHealth());
                    int currentLifeShardHealth = Mth.ceil(maxDefaultHealth > 20 ? Mth.clamp(currentOverallHealth - 20, 0, maxLifeShardHealth) : Math.min(player.getHealth(), currentOverallHealth - maxDefaultHealth));

                    boolean highlight = guiAccessor.aether$getHealthBlinkTime() > (long) gui.getGuiTicks() && (guiAccessor.aether$getHealthBlinkTime() - (long) gui.getGuiTicks()) / 3L % 2L == 1L;
                    if (Util.getMillis() - guiAccessor.aether$getLastHealthTime() > 1000L) {
                        lastOverallHealth = currentOverallHealth;
                        lastLifeShardHealth = currentLifeShardHealth;
                    }

                    //do NOT cast this to long. This is the only way the hearts will properly shake when health is low
                    //the only time the shaking will be off is if the player's max health attribute base is below 0. This probably can't be fixed.
                    guiAccessor.aether$getRandom().setSeed(gui.getGuiTicks() * 312871L);

                    float displayOverallHealth = Math.max((float) overallHealth, Math.max(lastOverallHealth, currentOverallHealth));
                    float displayLifeShardHealth = Math.max((float) maxLifeShardHealth, Math.max(lastLifeShardHealth, currentLifeShardHealth));
                    int absorption = Mth.ceil(player.getAbsorptionAmount());

                    int healthRows = Mth.ceil((displayOverallHealth + absorption) / 2.0F / 10.0F);
                    int rowHeight = Math.max(10 - (healthRows - 2), 3);

                    int left = window.getGuiScaledWidth() / 2 - 91;
                    int top = window.getGuiScaledHeight() - 39;

                    int regen = Integer.MIN_VALUE;
                    if (player.hasEffect(MobEffects.REGENERATION)) {
                        regen = gui.getGuiTicks() % Mth.ceil(displayOverallHealth + 5.0F);
                    }

                    renderHearts(guiGraphics, player, gui, left, top, regen, displayOverallHealth, displayLifeShardHealth, maxDefaultHealth, currentLifeShardHealth, rowHeight, absorption, highlight);
                }
            }
        }
    }

    /**
     * [CODE COPY] - Hud#extractHearts.<br><br>
     * Stripped down to only use what is necessary.
     */
    private static void renderHearts(GuiGraphicsExtractor guiGraphics, Player player, Hud gui, int left, int top, int regen, float displayOverallHealth, float displayLifeShardHealth, int maxDefaultHealth, int lifeShardHealth, int rowHeight, int absorption, boolean highlight) {
        GuiAccessor guiAccessor = (GuiAccessor) gui;
        Hud.HeartType heartType = HeartTypeAccessor.callForPlayer(player);
        int overallHearts = Mth.ceil((double) displayOverallHealth / 2.0);
        int lifeShardHearts = Mth.ceil((double) displayLifeShardHealth / 2.0);
        int maxDefaultHearts = Mth.ceil((double) maxDefaultHealth / 2.0);
        boolean tooManyHearts = overallHearts > 50;
        boolean tooLittleHearts = maxDefaultHearts < 10 && maxDefaultHearts > 0;
        for (int currentHeart = Math.min(overallHearts, lifeShardHearts - 1); currentHeart >= 0; --currentHeart) {
            int x = left + (currentHeart + (tooLittleHearts ? overallHearts - lifeShardHearts : 0)) % 10 * 8;
            int y = top - (currentHeart + (tooManyHearts ? 0 : maxDefaultHearts + currentHeart < 10 ? 0 : 10)) / 10 * rowHeight;

            if (Mth.ceil(player.getHealth()) + absorption <= 4) {
                y += guiAccessor.aether$getRandom().nextInt(2);
            }
            if (currentHeart + (maxDefaultHearts > 10 ? overallHearts - 10 : maxDefaultHearts) < overallHearts && currentHeart + Math.min(maxDefaultHearts, 10) - (tooManyHearts ? overallHearts : 0) == regen) {
                y -= 2;
            }
            int selectedContainer = currentHeart * 2;
            if (highlight && selectedContainer < displayLifeShardHealth) {
                boolean halfHeart = selectedContainer + 1 == displayLifeShardHealth;
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, AetherOverlays.getSprite(heartType, halfHeart, true), x, y, 9, 9);
            }
            if (selectedContainer < lifeShardHealth) {
                boolean halfHeart = selectedContainer + 1 == lifeShardHealth;
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, AetherOverlays.getSprite(heartType, halfHeart, false), x, y, 9, 9);
            }
        }
    }

    private static Identifier getSprite(Hud.HeartType heartType, boolean halfHeart, boolean blinking) {
        if (heartType == Hud.HeartType.NORMAL) {
            if (!halfHeart) {
                return !blinking ? TEXTURE_LIFE_SHARD_FULL : TEXTURE_LIFE_SHARD_FULL_BLINKING;
            } else {
                return !blinking ? TEXTURE_LIFE_SHARD_HALF : TEXTURE_LIFE_SHARD_HALF_BLINKING;
            }
        } else if (heartType == Hud.HeartType.POISIONED) {
            if (!halfHeart) {
                return !blinking ? TEXTURE_LIFE_SHARD_POISONED_FULL : TEXTURE_LIFE_SHARD_POISONED_FULL_BLINKING;
            } else {
                return !blinking ? TEXTURE_LIFE_SHARD_POISONED_HALF : TEXTURE_LIFE_SHARD_POISONED_HALF_BLINKING;
            }
        } else if (heartType == Hud.HeartType.WITHERED) {
            if (!halfHeart) {
                return !blinking ? TEXTURE_LIFE_SHARD_WITHERED_FULL : TEXTURE_LIFE_SHARD_WITHERED_FULL_BLINKING;
            } else {
                return !blinking ? TEXTURE_LIFE_SHARD_WITHERED_HALF : TEXTURE_LIFE_SHARD_WITHERED_HALF_BLINKING;
            }
        } else if (heartType == Hud.HeartType.ABSORBING) {
            return !halfHeart ? TEXTURE_LIFE_SHARD_ABSORBING_FULL : TEXTURE_LIFE_SHARD_ABSORBING_HALF;
        } else if (heartType == Hud.HeartType.FROZEN) {
            return !halfHeart ? TEXTURE_LIFE_SHARD_FROZEN_FULL : TEXTURE_LIFE_SHARD_FROZEN_HALF;
        }
        return Hud.HeartType.CONTAINER.getSprite(false, halfHeart, blinking);
    }
}
