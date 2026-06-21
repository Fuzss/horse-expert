package fuzs.horseexpert.common.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.horseexpert.common.HorseExpert;
import fuzs.horseexpert.common.init.ModRegistry;
import fuzs.horseexpert.common.util.ItemEquipmentHelper;
import fuzs.puzzleslib.common.api.client.init.v1.ModelLayerFactory;
import fuzs.puzzleslib.common.api.client.renderer.v1.RenderStateExtraData;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class MonocleLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(HorseExpert.MOD_ID);
    public static final ModelLayerLocation PLAYER_MONOCLE_LOCATION = MODEL_LAYERS.registerModelLayer("player",
            "monocle");
    public static final ModelLayerLocation PLAYER_BABY_MONOCLE_LOCATION = MODEL_LAYERS.registerModelLayer("player_baby",
            "monocle");
    public static final ContextKey<ItemStack> MONOCLE_ITEM_KEY = new ContextKey<>(HorseExpert.id("monocle_item"));
    private static final Identifier TEXTURE_LOCATION = HorseExpert.id("textures/entity/equipment/humanoid/monocle.png");

    private final HumanoidModel<S> model;
    private final HumanoidModel<S> babyModel;

    private MonocleLayer(RenderLayerParent<S, M> renderer, EntityRendererProvider.Context context) {
        super(renderer);
        this.model = new HumanoidModel<>(context.bakeLayer(PLAYER_MONOCLE_LOCATION));
        this.babyModel = new HumanoidModel<>(context.bakeLayer(PLAYER_BABY_MONOCLE_LOCATION));
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

    /**
     * @see net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer#renderLayers(EquipmentClientInfo.LayerType,
     *         ResourceKey, Model, Object, ItemStack, PoseStack, SubmitNodeCollector, int, Identifier, int, int)
     */
    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, S renderState, float yRot, float xRot) {
        ItemStack itemStack = RenderStateExtraData.getOrDefault(renderState, MONOCLE_ITEM_KEY, ItemStack.EMPTY);
        if (!itemStack.isEmpty()) {
            HumanoidModel<S> model = renderState.isBaby ? this.babyModel : this.model;
            // the armor foil buffer allows for parts of the texture to be slightly transparent
            submitNodeCollector.order(1)
                    .submitModel(model,
                            renderState,
                            poseStack,
                            RenderTypes.armorTranslucent(TEXTURE_LOCATION),
                            packedLight,
                            OverlayTexture.NO_OVERLAY,
                            renderState.outlineColor,
                            null);
            if (itemStack.hasFoil()) {
                submitNodeCollector.order(2)
                        .submitModel(model,
                                renderState,
                                poseStack,
                                RenderTypes.armorEntityGlint(),
                                packedLight,
                                OverlayTexture.NO_OVERLAY,
                                renderState.outlineColor,
                                null);
            }
        }
    }
}
