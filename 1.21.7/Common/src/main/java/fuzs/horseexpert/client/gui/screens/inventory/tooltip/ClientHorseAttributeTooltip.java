package fuzs.horseexpert.client.gui.screens.inventory.tooltip;

import fuzs.horseexpert.world.inventory.tooltip.HorseAttributeTooltip;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record ClientHorseAttributeTooltip(@Nullable Item item,
                                          @Nullable Holder<MobEffect> icon,
                                          Component line1,
                                          @Nullable Component line2) implements ClientTooltipComponent {
    private static final int TEXT_INDENT = 4;
    private static final int ICON_SIZE = 20;
    // this is needed since we need to always supply an empty text component above this on the tooltip, but we actually want to move up where the empty component would normally be
    private static final int FIRST_LINE_HEIGHT = 12;

    public ClientHorseAttributeTooltip(HorseAttributeTooltip tooltip) {
        this(tooltip.item(), tooltip.icon(), tooltip.line1(), tooltip.line2());
    }

    @Override
    public int getWidth(Font font) {
        return Math.max(font.width(this.line1), (this.line2 != null ? font.width(this.line2) : 0)) + TEXT_INDENT * 2
                + ICON_SIZE;
    }

    @Override
    public int getHeight(Font font) {
        return ICON_SIZE - FIRST_LINE_HEIGHT;
    }

    @Override
    public void renderText(GuiGraphics guiGraphics, Font font, int posX, int posY) {
        int width1 = font.width(this.line1);
        int width2 = this.line2 != null ? font.width(this.line2) : 0;
        int startX1, startX2;
        startX1 = startX2 = TEXT_INDENT;
        if (width2 > width1) {
            startX1 += (width2 - width1) / 2;
        } else {
            startX2 += (width1 - width2) / 2;
        }
        if (this.line2 == null) {
            posY += 5;
        }
        guiGraphics.drawString(font, this.line1, posX + ICON_SIZE + startX1, posY - FIRST_LINE_HEIGHT, -1);
        if (this.line2 != null) {
            guiGraphics.drawString(font, this.line2, posX + ICON_SIZE + startX2, posY + 10 - FIRST_LINE_HEIGHT, -1);
        }
    }

    @Override
    public void renderImage(Font font, int posX, int posY, int width, int height, GuiGraphics guiGraphics) {
        if (this.item != null) {
            guiGraphics.renderItem(new ItemStack(this.item), posX + 2, posY + 1 - FIRST_LINE_HEIGHT);
        }
        if (this.icon != null) {
            ResourceLocation resourceLocation = Gui.getMobEffectSprite(this.icon);
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                    resourceLocation,
                    posX + 1,
                    posY - FIRST_LINE_HEIGHT,
                    18,
                    18);
        }
    }
}