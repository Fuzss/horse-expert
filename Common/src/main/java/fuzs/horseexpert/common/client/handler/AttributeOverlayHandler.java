package fuzs.horseexpert.common.client.handler;

import fuzs.horseexpert.common.HorseExpert;
import fuzs.horseexpert.common.config.ClientConfig;
import fuzs.horseexpert.common.init.ModRegistry;
import fuzs.horseexpert.common.util.ItemEquipmentHelper;
import fuzs.horseexpert.common.world.inventory.tooltip.HorseAttributeTooltip;
import fuzs.puzzleslib.common.api.client.gui.v2.tooltip.TooltipRenderHelper;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.animal.equine.Llama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AttributeOverlayHandler {

    public static void extractRenderState(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        LivingEntity entity = getInspectableEntity();
        if (entity != null) {
            extractTooltipComponents(guiGraphics, guiGraphics.guiWidth(), guiGraphics.guiHeight(), entity);
        }
    }

    private static @Nullable LivingEntity getInspectableEntity() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.options.hideGui) {
            return null;
        } else if (minecraft.options.getCameraType().isFirstPerson()
                && minecraft.crosshairPickEntity instanceof LivingEntity entity && minecraft.crosshairPickEntity.is(
                ModRegistry.INSPECTABLE_ENTITY_TYPE_TAG)) {
            if (minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR
                    && minecraft.getCameraEntity() instanceof Player player && !ItemEquipmentHelper.getEquippedItem(
                    player,
                    ModRegistry.INSPECTION_EQUIPMENT_ITEM_TAG).isEmpty() && (
                    !HorseExpert.CONFIG.get(ClientConfig.class).requiresSneaking || player.isShiftKeyDown())) {
                if (player.getVehicle() != entity && (!(entity instanceof AbstractHorse abstractHorse)
                        || !HorseExpert.CONFIG.get(ClientConfig.class).mustBeTamed || abstractHorse.isTamed())) {
                    return entity;
                }
            }
        }

        return null;
    }

    private static void extractTooltipComponents(GuiGraphicsExtractor guiGraphics, int screenWidth, int screenHeight, LivingEntity entity) {
        List<HorseAttributeTooltip> tooltipComponents = collectTooltipComponents(entity);
        int posX = screenWidth / 2 - 12 + 22 + HorseExpert.CONFIG.get(ClientConfig.class).offsetX;
        int posY = screenHeight / 2 + 15 - (tooltipComponents.size() * 29 - 3) / 2
                + HorseExpert.CONFIG.get(ClientConfig.class).offsetY;
        for (int i = 0; i < tooltipComponents.size(); i++) {
            TooltipRenderHelper.renderTooltip(guiGraphics,
                    posX,
                    posY + 29 * i,
                    Component.empty(),
                    tooltipComponents.get(i));
        }
    }

    private static List<HorseAttributeTooltip> collectTooltipComponents(LivingEntity entity) {
        List<HorseAttributeTooltip> tooltipComponents = new ArrayList<>();
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
