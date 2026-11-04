package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.GuiHooks;
import com.aetherteam.aether.client.gui.component.inventory.AccessoryButton;
import com.aetherteam.aether.client.gui.screen.inventory.AetherAccessoriesScreen;
import com.aetherteam.nitrogen.fabric.client.events.ScreenKeyboardEventsExtension;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Tuple;
import net.minecraft.world.BossEvent;
import com.mojang.datafixers.util.Pair;

import java.util.UUID;
import java.util.function.Consumer;

public class GuiListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            onGuiInitialize(screen, Screens.getButtons(screen)::add);
            ScreenEvents.afterRender(screen).register((screen1, drawContext, mouseX, mouseY, tickDelta) -> GuiListener.onGuiDraw(screen1, drawContext));
            ScreenKeyboardEvents.afterKeyPress(screen).register((screen1, key, scancode, modifiers) -> {
                if (ScreenKeyboardEventsExtension.getPressedResult(screen1)) return;

                onKeyPress(key, InputConstants.PRESS);
            });
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> onClientTick());
    }

    /**
     * @see AetherAccessoriesScreen#getButtonOffset(Screen)
     * @see GuiHooks#setupAccessoryButton(Screen, Tuple)
     * @see GuiHooks#setupPerksButtons(Screen)
     */
    public static void onGuiInitialize(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();

        Pair<Integer, Integer> offsets = AetherAccessoriesScreen.getButtonOffset(screen);
        AccessoryButton inventoryAccessoryButton = GuiHooks.setupAccessoryButton(screen, offsets);
        if (inventoryAccessoryButton != null) {
            if (GuiHooks.isAccessoryButtonEnabled()) {
                event.addListener(inventoryAccessoryButton);
            }
        } else {
            GridLayout layout = GuiHooks.setupPerksButtons(screen);
            if (layout != null) {
                layout.visitWidgets(abstractWidget -> {
                    if (abstractWidget instanceof AbstractButton abstractButton) consumer.accept(abstractButton);
                });
            }
        }
    }

    /**
     * @see GuiHooks#drawTrivia(Screen, GuiGraphicsExtractor)
     * @see GuiHooks#drawAetherTravelMessage(Screen, GuiGraphicsExtractor)
     */
    public static void onGuiDraw(ScreenEvent.Render.Post event) {
        Screen screen = event.getScreen();
        GuiGraphicsExtractor guiGraphics = event.getGuiGraphics();
        if (!FabricLoader.getInstance().isModLoaded("tipsmod")) {
            GuiHooks.drawTrivia(screen, guiGraphics);
        }
        GuiHooks.drawAetherTravelMessage(screen, guiGraphics);
    }

    /**
     * @see GuiHooks#handlePatreonRefreshRebound()
     */
    public static void onClientTick() {
        GuiHooks.handlePatreonRefreshRebound();
        GuiHooks.openAccessoryMenu();
    }

    /**
     * @see GuiHooks#openAccessoryMenu()
     * @see GuiHooks#closeContainerMenu(int, int)
     */
    public static void onKeyPress(int key, int action) {
        GuiHooks.closeContainerMenu(key, action);
    }

    /**
     * This event is cancelled in BossHealthOverlayMixin. See it for more info.
     *
     * @see com.aetherteam.aether.mixin.mixins.client.BossHealthOverlayMixin#event(CustomizeGuiOverlayEvent.BossEventProgress)
     * @see GuiHooks#drawBossHealthBar(GuiGraphicsExtractor, int, int, LerpingBossEvent)
     */
    public static void onRenderBossBar(CustomizeGuiOverlayEvent.BossEventProgress event) {
        GuiGraphicsExtractor guiGraphics = event.getGuiGraphics();
        LerpingBossEvent bossEvent = event.getBossEvent();
        UUID bossUUID = bossEvent.getId();
        if (bossEvent instanceof LerpingBossEvent lerpingBossEvent && GuiHooks.isAetherBossBar(bossUUID)) {
            GuiHooks.drawBossHealthBar(guiGraphics, k, j, lerpingBossEvent);
            return true;
        }
        return false;
    }
}
