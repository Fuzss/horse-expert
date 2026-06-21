package fuzs.horseexpert.common.client;

import fuzs.horseexpert.common.HorseExpert;
import fuzs.horseexpert.common.client.gui.screens.inventory.tooltip.ClientHorseAttributeTooltip;
import fuzs.horseexpert.common.client.handler.AttributeOverlayHandler;
import fuzs.horseexpert.common.client.init.ModEnumConstants;
import fuzs.horseexpert.common.client.model.geom.ModModelLayers;
import fuzs.horseexpert.common.client.renderer.entity.layers.MonocleLayer;
import fuzs.horseexpert.common.init.ModRegistry;
import fuzs.horseexpert.common.world.inventory.tooltip.HorseAttributeTooltip;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.common.api.client.core.v1.context.ClientTooltipComponentsContext;
import fuzs.puzzleslib.common.api.client.core.v1.context.GuiLayersContext;
import fuzs.puzzleslib.common.api.client.core.v1.context.LayerDefinitionsContext;
import fuzs.puzzleslib.common.api.client.event.v1.renderer.AddLivingEntityRenderLayersCallback;
import fuzs.puzzleslib.common.api.client.event.v1.renderer.ExtractEntityRenderStateCallback;
import fuzs.puzzleslib.common.api.client.gui.v2.tooltip.ItemTooltipRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;

public class HorseExpertClient implements ClientModConstructor {
    public static final Component MONOCLE_TOOLTIP_COMPONENT = Component.translatable("item.horseexpert.monocle.tooltip")
            .withStyle(ChatFormatting.GOLD);

    @Override
    public void onConstructMod() {
        ModEnumConstants.bootstrap();
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        ExtractEntityRenderStateCallback.EVENT.register(MonocleLayer::onExtractEntityRenderState);
        AddLivingEntityRenderLayersCallback.EVENT.register(MonocleLayer::addLivingEntityRenderLayers);
    }

    @Override
    public void onClientSetup() {
        ItemTooltipRegistry.ITEM.registerItemTooltip(ModRegistry.INSPECTION_EQUIPMENT_ITEM_TAG,
                MONOCLE_TOOLTIP_COMPONENT);
    }

    @Override
    public void onRegisterClientTooltipComponents(ClientTooltipComponentsContext context) {
        context.registerClientTooltipComponent(HorseAttributeTooltip.class, ClientHorseAttributeTooltip::new);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(ModModelLayers.PLAYER_MONOCLE_LAYER, MonocleLayer::createHeadLayer);
        context.registerLayerDefinition(ModModelLayers.PLAYER_BABY_MONOCLE_LAYER, () -> {
            return MonocleLayer.createHeadLayer().apply(HumanoidModel.BABY_TRANSFORMER);
        });
    }

    @Override
    public void onRegisterGuiLayers(GuiLayersContext context) {
        context.registerGuiLayer(GuiLayersContext.HELD_ITEM_TOOLTIP,
                HorseExpert.id("tooltip"),
                AttributeOverlayHandler::extractRenderState);
    }
}
