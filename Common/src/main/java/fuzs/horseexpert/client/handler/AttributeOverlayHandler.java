package fuzs.horseexpert.client.handler;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.config.ClientConfig;
import fuzs.horseexpert.init.ModRegistry;
import fuzs.horseexpert.util.ItemEquipmentHelper;
import fuzs.horseexpert.world.inventory.tooltip.HorseAttributeTooltip;
import fuzs.puzzleslib.api.client.gui.v2.components.TooltipRenderHelper;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;

import java.util.List;
import java.util.Optional;

public class AttributeOverlayHandler {

    public static void onAfterRenderGui(Gui gui, GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        isRenderingTooltipsAllowed(gui.minecraft).ifPresent(abstractHorse -> {
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR,
                    GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR,
                    GlStateManager.SourceFactor.ONE,
                    GlStateManager.DestFactor.ZERO);
            RenderSystem.disableDepthTest();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            actuallyRenderAttributeOverlay(guiGraphics,
                    guiGraphics.guiWidth(),
                    guiGraphics.guiHeight(),
                    abstractHorse,
                    gui.minecraft.font,
                    gui.minecraft.getItemRenderer());
        });
    }

    private static Optional<LivingEntity> isRenderingTooltipsAllowed(Minecraft minecraft) {
        if (minecraft.options.hideGui) return Optional.empty();
        if (minecraft.options.getCameraType().isFirstPerson() &&
                minecraft.crosshairPickEntity instanceof LivingEntity entity &&
                minecraft.crosshairPickEntity.getType().is(ModRegistry.INSPECTABLE_ENTITY_TYPE_TAG)) {
            if (minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR &&
                    minecraft.cameraEntity instanceof Player player &&
                    !ItemEquipmentHelper.getEquippedItem(player, ModRegistry.INSPECTION_EQUIPMENT_ITEM_TAG)
                            .isEmpty() &&
                    (!HorseExpert.CONFIG.get(ClientConfig.class).requiresSneaking || player.isShiftKeyDown())) {
                if (player.getVehicle() != entity && (!(entity instanceof AbstractHorse abstractHorse) ||
                        !HorseExpert.CONFIG.get(ClientConfig.class).mustBeTamed || abstractHorse.isTamed())) {
                    return Optional.of(entity);
                }
            }
        }
        return Optional.empty();
    }

    private static void actuallyRenderAttributeOverlay(GuiGraphics guiGraphics, int screenWidth, int screenHeight, LivingEntity entity, Font font, ItemRenderer itemRenderer) {
        List<HorseAttributeTooltip> tooltipComponents = buildTooltipComponents(entity);
        int posX = screenWidth / 2 - 12 + 22 + HorseExpert.CONFIG.get(ClientConfig.class).offsetX;
        int posY = screenHeight / 2 + 15 - (tooltipComponents.size() * 29 - 3) / 2 +
                HorseExpert.CONFIG.get(ClientConfig.class).offsetY;
        for (int i = 0; i < tooltipComponents.size(); i++) {
            TooltipRenderHelper.renderTooltip(guiGraphics,
                    posX,
                    posY + 29 * i,
                    Component.empty(),
                    tooltipComponents.get(i));
        }
    }

    private static List<HorseAttributeTooltip> buildTooltipComponents(LivingEntity entity) {
        List<HorseAttributeTooltip> tooltipComponents = Lists.newArrayList();
        if (entity.getAttributes().hasAttribute(Attributes.MAX_HEALTH)) {
            tooltipComponents.add(HorseAttributeTooltip.healthTooltip(entity.getAttributeValue(Attributes.MAX_HEALTH),
                    entity instanceof AbstractHorse));
        }
        if (!(entity instanceof Llama) || HorseExpert.CONFIG.get(ClientConfig.class).allLlamaAttributes) {
            if (entity.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED)) {
                tooltipComponents.add(HorseAttributeTooltip.speedTooltip(entity.getAttributeValue(Attributes.MOVEMENT_SPEED),
                        entity instanceof AbstractHorse));
            }
            if (entity.getAttributes().hasAttribute(Attributes.JUMP_STRENGTH)) {
                tooltipComponents.add(HorseAttributeTooltip.jumpHeightTooltip(entity.getAttributeValue(Attributes.JUMP_STRENGTH),
                        entity instanceof AbstractHorse));
            }
        }
        if (entity instanceof Llama llama) {
            tooltipComponents.add(HorseAttributeTooltip.strengthTooltip(llama.getStrength()));
        }
        return tooltipComponents;
    }
}
