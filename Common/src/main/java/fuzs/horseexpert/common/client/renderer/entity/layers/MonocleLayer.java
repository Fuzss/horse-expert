package fuzs.horseexpert.common.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.horseexpert.common.HorseExpert;
import fuzs.horseexpert.common.client.init.ModEnumConstants;
import fuzs.horseexpert.common.client.model.geom.ModModelLayers;
import fuzs.horseexpert.common.init.ModRegistry;
import fuzs.horseexpert.common.util.ItemEquipmentHelper;
import fuzs.puzzleslib.common.api.client.renderer.v1.RenderStateExtraData;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.Equippable;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * @see net.minecraft.client.renderer.entity.layers.WingsLayer
 */
public class MonocleLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {
    private static final ContextKey<ItemStack> MONOCLE_ITEM_KEY = new ContextKey<>(HorseExpert.id("monocle_item"));

    private final EquipmentLayerRenderer equipmentRenderer;
    private final HumanoidModel<S> model;
    private final HumanoidModel<S> babyModel;

    private MonocleLayer(RenderLayerParent<S, M> renderer, EntityRendererProvider.Context context) {
        super(renderer);
        this.equipmentRenderer = context.getEquipmentRenderer();
        this.model = new HumanoidModel<>(context.bakeLayer(ModModelLayers.PLAYER_MONOCLE_LAYER));
        this.babyModel = new HumanoidModel<>(context.bakeLayer(ModModelLayers.PLAYER_BABY_MONOCLE_LAYER));
    }

    /**
     * Similar to player armor, but with cube deformation from piglin armor to avoid z-fighting when worn at the same
     * time as a helmet.
     *
     * @see LayerDefinitions#createRoots()
     */
    public static LayerDefinition createHeadLayer() {
        return PlayerModel.createArmorMeshSet(LayerDefinitions.INNER_ARMOR_DEFORMATION, new CubeDeformation(1.02F))
                .map((MeshDefinition meshDefinition) -> {
                    return LayerDefinition.create(meshDefinition, 64, 32);
                })
                .head();
    }

    public static void onExtractEntityRenderState(Entity entity, EntityRenderState entityRenderState, float partialTick) {
        if (entity instanceof LivingEntity livingEntity && entityRenderState instanceof AvatarRenderState) {
            ItemStack itemStack = ItemEquipmentHelper.getEquippedItem(livingEntity,
                    ModRegistry.INSPECTION_EQUIPMENT_ITEM_TAG);
            RenderStateExtraData.set(entityRenderState, MONOCLE_ITEM_KEY, itemStack);
        }
    }

    public static void addLivingEntityRenderLayers(EntityType<?> entityType, LivingEntityRenderer<?, ?, ?> entityRenderer, EntityRendererProvider.Context context) {
        if (entityRenderer instanceof AvatarRenderer<?> avatarRenderer) {
            avatarRenderer.addLayer(new MonocleLayer<>(avatarRenderer, context));
        }
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, S state, float yRot, float xRot) {
        ItemStack itemStack = RenderStateExtraData.getOrDefault(state, MONOCLE_ITEM_KEY, ItemStack.EMPTY);
        Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
        if (equippable != null && equippable.assetId().isPresent()) {
            HumanoidModel<S> model = state.isBaby ? this.babyModel : this.model;
            poseStack.pushPose();
            this.renderLayers(ModEnumConstants.HUMANOID_HEAD_LAYER_TYPE,
                    equippable.assetId().get(),
                    model,
                    state,
                    itemStack,
                    poseStack,
                    submitNodeCollector,
                    lightCoords,
                    state.outlineColor);
            poseStack.popPose();
        }
    }

    /**
     * @see EquipmentLayerRenderer#renderLayers(EquipmentClientInfo.LayerType, ResourceKey, Model, Object, ItemStack,
     *         PoseStack, SubmitNodeCollector, int, int)
     */
    public void renderLayers(EquipmentClientInfo.LayerType layerType, ResourceKey<EquipmentAsset> equipmentAssetId, Model<? super S> model, S state, ItemStack itemStack, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int outlineColor) {
        this.renderLayers(layerType,
                equipmentAssetId,
                model,
                state,
                itemStack,
                poseStack,
                submitNodeCollector,
                lightCoords,
                null,
                outlineColor,
                1);
    }

    /**
     * @see EquipmentLayerRenderer#renderLayers(EquipmentClientInfo.LayerType, ResourceKey, Model, Object, ItemStack,
     *         PoseStack, SubmitNodeCollector, int, Identifier, int, int)
     */
    public void renderLayers(EquipmentClientInfo.LayerType layerType, ResourceKey<EquipmentAsset> equipmentAssetId, Model<? super S> model, S state, ItemStack itemStack, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, @Nullable Identifier playerTextureOverride, int outlineColor, int order) {
        List<EquipmentClientInfo.Layer> layers = this.equipmentRenderer.equipmentAssets.get(equipmentAssetId)
                .getLayers(layerType);
        if (!layers.isEmpty()) {
            boolean renderFoil = itemStack.hasFoil();
            int nextOrder = order;
            for (EquipmentClientInfo.Layer layer : layers) {
                Identifier layerTexture =
                        layer.usePlayerTexture() && playerTextureOverride != null ? playerTextureOverride :
                                this.equipmentRenderer.layerTextureLookup.apply(new EquipmentLayerRenderer.LayerTextureKey(
                                        layerType,
                                        layer));
                submitNodeCollector.order(nextOrder++)
                        .submitModel(model,
                                state,
                                poseStack,
                                RenderTypes.armorTranslucent(layerTexture),
                                lightCoords,
                                OverlayTexture.NO_OVERLAY,
                                -1,
                                null,
                                outlineColor,
                                null);
                if (renderFoil) {
                    submitNodeCollector.order(nextOrder++)
                            .submitModel(model,
                                    state,
                                    poseStack,
                                    RenderTypes.armorEntityGlint(),
                                    lightCoords,
                                    OverlayTexture.NO_OVERLAY,
                                    -1,
                                    null,
                                    outlineColor,
                                    null);
                }

                renderFoil = false;
            }
        }
    }
}
