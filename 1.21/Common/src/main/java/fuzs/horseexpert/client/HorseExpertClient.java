package fuzs.horseexpert.client;

import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.client.gui.screens.inventory.tooltip.ClientHorseAttributeTooltip;
import fuzs.horseexpert.client.handler.AttributeOverlayHandler;
import fuzs.horseexpert.client.handler.MonocleTooltipHandler;
import fuzs.horseexpert.client.renderer.entity.layers.MonocleRenderer;
import fuzs.horseexpert.world.inventory.tooltip.HorseAttributeTooltip;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.ClientTooltipComponentsContext;
import fuzs.puzzleslib.api.client.core.v1.context.LayerDefinitionsContext;
import fuzs.puzzleslib.api.client.event.v1.AddResourcePackReloadListenersCallback;
import fuzs.puzzleslib.api.client.event.v1.gui.ItemTooltipCallback;
import fuzs.puzzleslib.api.client.event.v1.gui.RenderGuiCallback;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import java.util.function.BiConsumer;

public class HorseExpertClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerEventHandlers();
        registerModIntegrations();
    }

    private static void registerEventHandlers() {
        RenderGuiCallback.EVENT.register(AttributeOverlayHandler::renderAttributeOverlay);
        ItemTooltipCallback.EVENT.register(MonocleTooltipHandler::onItemTooltip);
    }

    private static void registerModIntegrations() {
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("curios") || ModLoaderEnvironment.INSTANCE.isModLoaded(
                "trinkets")) {
            AddResourcePackReloadListenersCallback.EVENT.register(
                    (BiConsumer<ResourceLocation, PreparableReloadListener> consumer) -> {
                        consumer.accept(HorseExpert.id("monocle_model"),
                                (ResourceManagerReloadListener) (ResourceManager resourceManager) -> {
                                    EntityModelSet entityModels = Minecraft.getInstance().getEntityModels();
                                    MonocleRenderer.bakeModel(entityModels);
                                }
                        );
                    });
        }
    }

    @Override
    public void onRegisterClientTooltipComponents(ClientTooltipComponentsContext context) {
        context.registerClientTooltipComponent(HorseAttributeTooltip.class, ClientHorseAttributeTooltip::new);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(MonocleRenderer.PLAYER_MONOCLE_LAYER,
                () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(1.02F), 0.0F), 64, 32)
        );
    }
}
