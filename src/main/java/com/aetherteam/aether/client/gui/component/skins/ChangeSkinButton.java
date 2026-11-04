package com.aetherteam.aether.client.gui.component.skins;

import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import com.aetherteam.aether.Aether;
import com.aetherteam.nitrogen.fabric.BuilderMadeButton;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

public class ChangeSkinButton extends Button {
    public static final WidgetSprites APPLY_WIDGET = new WidgetSprites(Identifier.fromNamespaceAndPath(Aether.MODID, "skins/apply_button"), Identifier.fromNamespaceAndPath(Aether.MODID, "skins/apply_button_disabled"), Identifier.fromNamespaceAndPath(Aether.MODID, "skins/apply_button_highlighted"));
    public static final WidgetSprites REMOVE_WIDGET = new WidgetSprites(Identifier.fromNamespaceAndPath(Aether.MODID, "skins/remove_button"), Identifier.fromNamespaceAndPath(Aether.MODID, "skins/remove_button_disabled"), Identifier.fromNamespaceAndPath(Aether.MODID, "skins/remove_button_highlighted"));

    private final ButtonType buttonType;

    public ChangeSkinButton(ButtonType buttonType, Builder builder) {
        super(builder);
        this.buttonType = buttonType;
        this.active = false;
    }

    @Override
    public void onPress(InputWithModifiers input) {
        if (this.isActive()) {
            this.onPress.onPress(this);
        }
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        Identifier location;
        if (this.buttonType == ButtonType.APPLY) {
            location = APPLY_WIDGET.get(this.isActive(), this.isHovered());
        } else {
            location = REMOVE_WIDGET.get(this.isActive(), this.isHovered());
        }
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, location, this.getX(), this.getY(), 7, 7);
    }

    public enum ButtonType {
        APPLY,
        REMOVE
    }
}
