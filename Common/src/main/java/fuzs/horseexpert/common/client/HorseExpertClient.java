package fuzs.horseexpert.common.client;

import fuzs.horseexpert.common.HorseExpert;
import fuzs.horseexpert.common.client.gui.screens.inventory.tooltip.ClientHorseAttributeTooltip;
import fuzs.horseexpert.common.client.handler.AttributeOverlayHandler;
import fuzs.horseexpert.common.client.renderer.entity.layers.MonocleLayer;
import fuzs.horseexpert.common.init.ModRegistry;
import fuzs.horseexpert.common.world.inventory.tooltip.HorseAttributeTooltip;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.common.api.client.core.v1.context.ClientTooltipComponentsContext;
import fuzs.puzzleslib.common.api.client.core.v1.context.GuiLayersContext;
import fuzs.puzzleslib.common.api.client.core.v1.context.LayerDefinitionsContext;
import fuzs.puzzleslib.common.api.client.core.v1.context.ResourcePackReloadListenersContext;
import fuzs.puzzleslib.common.api.client.event.v1.renderer.AddLivingEntityRenderLayersCallback;
import fuzs.puzzleslib.common.api.client.event.v1.renderer.ExtractEntityRenderStateCallback;
import fuzs.puzzleslib.common.api.client.gui.v2.tooltip.ItemTooltipRegistry;
import fuzs.puzzleslib.common.api.core.v1.ModLoaderEnvironment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.network.chat.Component;

public class HorseExpertClient implements ClientModConstructor {
    public static final Component MONOCLE_TOOLTIP_COMPONENT = Component.translatable("item.horseexpert.monocle.tooltip")
            .withStyle(ChatFormatting.GRAY);

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        ExtractEntityRenderStateCallback.EVENT.register(MonocleLayer::onExtractEntityRenderState);
        if (!ModLoaderEnvironment.INSTANCE.isModLoaded("accessories")) {
            AddLivingEntityRenderLayersCallback.EVENT.register(MonocleLayer::addLivingEntityRenderLayers);
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
    public void onRegisterClientTooltipComponents(ClientTooltipComponentsContext context) {
        context.registerClientTooltipComponent(HorseAttributeTooltip.class, ClientHorseAttributeTooltip::new);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        ArmorModelSet<LayerDefinition> armorModelSet = PlayerModel.createArmorMeshSet(LayerDefinitions.INNER_ARMOR_DEFORMATION,
                new CubeDeformation(1.02F)).map((MeshDefinition meshDefinition) -> {
            return LayerDefinition.create(meshDefinition, 64, 32);
        });
        ArmorModelSet<LayerDefinition> armorModelSet2 = armorModelSet.map((LayerDefinition layerDefinition) -> {
            return layerDefinition.apply(HumanoidModel.BABY_TRANSFORMER);
        });
        context.registerLayerDefinition(MonocleLayer.PLAYER_MONOCLE_LOCATION, armorModelSet::head);
        context.registerLayerDefinition(MonocleLayer.PLAYER_BABY_MONOCLE_LOCATION, armorModelSet2::head);
    }

    @Override
    public void onRegisterGuiLayers(GuiLayersContext context) {
        context.registerGuiLayer(GuiLayersContext.HELD_ITEM_TOOLTIP,
                HorseExpert.id("attributes_tooltip"),
                AttributeOverlayHandler::extractRenderState);
    }

    @Override
    public void onAddResourcePackReloadListeners(ResourcePackReloadListenersContext context) {
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("accessories")) {
            MonocleLayer.onAddResourcePackReloadListeners(context::registerReloadListener);
        }
    }
}
