package fuzs.horseexpert.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.init.ModRegistry;
import fuzs.horseexpert.util.ItemEquipmentHelper;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import fuzs.puzzleslib.api.client.renderer.v1.RenderStateExtraData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiConsumer;

public class MonocleLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(HorseExpert.MOD_ID);
    public static final ModelLayerLocation PLAYER_MONOCLE_LOCATION = MODEL_LAYERS.registerModelLayer("player",
            "monocle");
    public static final ModelLayerLocation PLAYER_BABY_MONOCLE_LOCATION = MODEL_LAYERS.registerModelLayer("player_baby",
            "monocle");
    public static final ContextKey<ItemStack> MONOCLE_ITEM_CONTEXT_KEY = new ContextKey<>(HorseExpert.id("monocle_item"));
    private static final ResourceLocation TEXTURE_LOCATION = HorseExpert.id(
            "textures/entity/equipment/humanoid/monocle.png");

    @Nullable
    private static RenderLayer<?, ?> monocleLayer;

    private final HumanoidModel<S> model;
    private final HumanoidModel<S> babyModel;

    public MonocleLayer(RenderLayerParent<S, M> renderer, EntityRendererProvider.Context context) {
        this(renderer, context.getModelSet());
    }

    private MonocleLayer(@Nullable RenderLayerParent<S, M> renderer, EntityModelSet entityModelSet) {
        super(renderer);
        this.model = new HumanoidModel<>(entityModelSet.bakeLayer(PLAYER_MONOCLE_LOCATION));
        this.babyModel = new HumanoidModel<>(entityModelSet.bakeLayer(PLAYER_BABY_MONOCLE_LOCATION));
    }

    public static void onExtractRenderState(Entity entity, EntityRenderState entityRenderState, float partialTick) {
        if (entity instanceof LivingEntity livingEntity && entityRenderState instanceof AvatarRenderState) {
            ItemStack itemStack = ItemEquipmentHelper.getEquippedItem(livingEntity,
                    ModRegistry.INSPECTION_EQUIPMENT_ITEM_TAG);
            RenderStateExtraData.set(entityRenderState, MONOCLE_ITEM_CONTEXT_KEY, itemStack);
        }
    }

    public static void onAddResourcePackReloadListeners(BiConsumer<ResourceLocation, PreparableReloadListener> consumer) {
        consumer.accept(HorseExpert.id("monocle_layer"),
                (ResourceManagerReloadListener) (ResourceManager resourceManager) -> {
                    MonocleLayer.monocleLayer = new MonocleLayer<>(null, Minecraft.getInstance().getEntityModels());
                });
    }

    public static void addLivingEntityRenderLayers(EntityType<?> entityType, LivingEntityRenderer<?, ?, ?> entityRenderer, EntityRendererProvider.Context context) {
        if (entityRenderer instanceof AvatarRenderer<?> avatarRenderer) {
            avatarRenderer.addLayer(new MonocleLayer<>(avatarRenderer, context));
        }
    }

    public static <S extends LivingEntityRenderState> RenderLayer<S, ?> getLayer() {
        RenderLayer<?, ?> monocleLayer = MonocleLayer.monocleLayer;
        Objects.requireNonNull(monocleLayer, "monocle layer is null");
        return (RenderLayer<S, ?>) monocleLayer;
    }

    @Nullable
    @Override
    public M getParentModel() {
        try {
            return super.getParentModel();
        } catch (NullPointerException exception) {
            return null;
        }
    }

    /**
     * @see net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer#renderLayers(EquipmentClientInfo.LayerType,
     *         ResourceKey, Model, Object, ItemStack, PoseStack, SubmitNodeCollector, int, ResourceLocation, int, int)
     */
    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, S renderState, float yRot, float xRot) {
        ItemStack itemStack = RenderStateExtraData.getOrDefault(renderState, MONOCLE_ITEM_CONTEXT_KEY, ItemStack.EMPTY);
        if (!itemStack.isEmpty()) {
            HumanoidModel<S> model = renderState.isBaby ? this.babyModel : this.model;
            // the armor foil buffer allows for parts of the texture to be slightly transparent
            submitNodeCollector.order(1)
                    .submitModel(model,
                            renderState,
                            poseStack,
                            RenderType.armorTranslucent(TEXTURE_LOCATION),
                            packedLight,
                            OverlayTexture.NO_OVERLAY,
                            renderState.outlineColor,
                            null);
            if (itemStack.hasFoil()) {
                submitNodeCollector.order(2)
                        .submitModel(model,
                                renderState,
                                poseStack,
                                RenderType.armorEntityGlint(),
                                packedLight,
                                OverlayTexture.NO_OVERLAY,
                                renderState.outlineColor,
                                null);
            }
        }
    }
}
