package fuzs.horseexpert.client;

import fuzs.horseexpert.client.gui.screens.inventory.tooltip.ClientHorseAttributeTooltip;
import fuzs.horseexpert.client.handler.AttributeOverlayHandler;
import fuzs.horseexpert.client.handler.MonocleTooltipHandler;
import fuzs.horseexpert.client.renderer.entity.layers.MonocleAccessoryRenderer;
import fuzs.horseexpert.client.renderer.entity.layers.MonocleLayer;
import fuzs.horseexpert.init.ModRegistry;
import fuzs.horseexpert.world.inventory.tooltip.HorseAttributeTooltip;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.ClientTooltipComponentsContext;
import fuzs.puzzleslib.api.client.core.v1.context.LayerDefinitionsContext;
import fuzs.puzzleslib.api.client.core.v1.context.LivingEntityRenderLayersContext;
import fuzs.puzzleslib.api.client.event.v1.AddResourcePackReloadListenersCallback;
import fuzs.puzzleslib.api.client.event.v1.gui.ItemTooltipCallback;
import fuzs.puzzleslib.api.client.event.v1.gui.RenderGuiEvents;
import fuzs.puzzleslib.api.client.event.v1.renderer.ExtractRenderStateCallbackV2;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.world.entity.EntityType;

public class HorseExpertClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        RenderGuiEvents.AFTER.register(AttributeOverlayHandler::onAfterRenderGui);
        ItemTooltipCallback.EVENT.register(MonocleTooltipHandler::onItemTooltip);
        ExtractRenderStateCallbackV2.EVENT.register(MonocleLayer::onExtractRenderState);
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("accessories")) {
            AddResourcePackReloadListenersCallback.EVENT.register(MonocleLayer::onAddResourcePackReloadListeners);
        }
    }

    @Override
    public void onClientSetup() {
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("accessories")) {
            AccessoriesRendererRegistry.registerRenderer(ModRegistry.MONOCLE_ITEM.value(),
                    MonocleAccessoryRenderer.getFactory());
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
}
