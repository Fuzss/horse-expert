package fuzs.horseexpert.client;

import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.client.gui.screens.inventory.tooltip.ClientHorseAttributeTooltip;
import fuzs.horseexpert.client.handler.AttributeOverlayHandler;
import fuzs.horseexpert.client.renderer.entity.layers.MonocleLayer;
import fuzs.horseexpert.init.ModRegistry;
import fuzs.horseexpert.world.inventory.tooltip.HorseAttributeTooltip;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.ClientTooltipComponentsContext;
import fuzs.puzzleslib.api.client.core.v1.context.GuiLayersContext;
import fuzs.puzzleslib.api.client.core.v1.context.LayerDefinitionsContext;
import fuzs.puzzleslib.api.client.core.v1.context.LivingEntityRenderLayersContext;
import fuzs.puzzleslib.api.client.event.v1.AddResourcePackReloadListenersCallback;
import fuzs.puzzleslib.api.client.event.v1.renderer.ExtractRenderStateCallback;
import fuzs.puzzleslib.api.client.gui.v2.tooltip.ItemTooltipRegistry;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;

public class HorseExpertClient implements ClientModConstructor {
    public static final Component MONOCLE_TOOLTIP_COMPONENT = Component.translatable("item.horseexpert.monocle.tooltip")
            .withStyle(ChatFormatting.GRAY);

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        ExtractRenderStateCallback.EVENT.register(MonocleLayer::onExtractRenderState);
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("accessories")) {
            AddResourcePackReloadListenersCallback.EVENT.register(MonocleLayer::onAddResourcePackReloadListeners);
        }
    }

    @Override
    public void onClientSetup() {
        ItemTooltipRegistry.ITEM.registerItemTooltip(ModRegistry.INSPECTION_EQUIPMENT_ITEM_TAG,
                MONOCLE_TOOLTIP_COMPONENT);
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("accessories")) {
            // TODO enable Accessories again when available
//            AccessoriesRendererRegistry.registerRenderer(ModRegistry.MONOCLE_ITEM.value(),
//                    MonocleAccessoryRenderer.getFactory());
        }
    }

    @Override
    public void onRegisterLivingEntityRenderLayers(LivingEntityRenderLayersContext context) {
        if (!ModLoaderEnvironment.INSTANCE.isModLoaded("accessories")) {
            context.<PlayerRenderState, HumanoidModel<PlayerRenderState>>registerRenderLayer(EntityType.PLAYER,
                    MonocleLayer::new);
        }
    }

    @Override
    public void onRegisterClientTooltipComponents(ClientTooltipComponentsContext context) {
        context.registerClientTooltipComponent(HorseAttributeTooltip.class, ClientHorseAttributeTooltip::new);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(MonocleLayer.PLAYER_MONOCLE_MODEL_LAYER_LOCATION,
                () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(1.02F), 0.0F), 64, 32));
        context.registerLayerDefinition(MonocleLayer.PLAYER_BABY_MONOCLE_MODEL_LAYER_LOCATION,
                () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(1.02F), 0.0F), 64, 32)
                        .apply(HumanoidModel.BABY_TRANSFORMER));
    }

    @Override
    public void onRegisterGuiLayers(GuiLayersContext context) {
        context.registerGuiLayer(GuiLayersContext.HELD_ITEM_TOOLTIP,
                HorseExpert.id("attributes_tooltip"),
                AttributeOverlayHandler::renderAttributesTooltip);
    }
}
