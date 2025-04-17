package fuzs.horseexpert.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.init.ModRegistry;
import fuzs.horseexpert.util.ItemEquipmentHelper;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import fuzs.puzzleslib.api.client.renderer.v1.RenderPropertyKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiConsumer;

public class MonocleLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(HorseExpert.MOD_ID);
    public static final ModelLayerLocation PLAYER_MONOCLE_MODEL_LAYER_LOCATION = MODEL_LAYERS.registerModelLayer(
            "player",
            "monocle");
    public static final ModelLayerLocation PLAYER_BABY_MONOCLE_MODEL_LAYER_LOCATION = MODEL_LAYERS.registerModelLayer(
            "player_baby",
            "monocle");
    public static final RenderPropertyKey<ItemStack> MONOCLE_ITEM_RENDER_PROPERTY_KEY = new RenderPropertyKey<>(
            HorseExpert.id("monocle_item"));
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
        this.model = new HumanoidModel<>(entityModelSet.bakeLayer(PLAYER_MONOCLE_MODEL_LAYER_LOCATION));
        this.babyModel = new HumanoidModel<>(entityModelSet.bakeLayer(PLAYER_BABY_MONOCLE_MODEL_LAYER_LOCATION));
    }

    public static void onExtractRenderState(Entity entity, EntityRenderState entityRenderState, float partialTick) {
        if (entity instanceof LivingEntity livingEntity && entityRenderState instanceof PlayerRenderState) {
            ItemStack itemStack = ItemEquipmentHelper.getEquippedItem(livingEntity,
                    ModRegistry.INSPECTION_EQUIPMENT_ITEM_TAG);
            RenderPropertyKey.set(entityRenderState, MONOCLE_ITEM_RENDER_PROPERTY_KEY, itemStack);
        }
    }

    public static void onAddResourcePackReloadListeners(BiConsumer<ResourceLocation, PreparableReloadListener> consumer) {
        consumer.accept(HorseExpert.id("monocle_layer"),
                (ResourceManagerReloadListener) (ResourceManager resourceManager) -> {
                    MonocleLayer.monocleLayer = new MonocleLayer<>(null, Minecraft.getInstance().getEntityModels());
                });
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

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, S renderState, float yRot, float xRot) {
        ItemStack itemStack = RenderPropertyKey.getOrDefault(renderState,
                MONOCLE_ITEM_RENDER_PROPERTY_KEY,
                ItemStack.EMPTY);
        if (!itemStack.isEmpty()) {
            HumanoidModel<S> model = renderState.isBaby ? this.babyModel : this.model;
            model.setupAnim(renderState);
            model.setAllVisible(false);
            model.head.visible = true;
            model.hat.visible = true;
            // custom armor foil buffer allowing for parts of the texture to be slightly transparent
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(bufferSource,
                    RenderType.armorTranslucent(TEXTURE_LOCATION),
                    itemStack.hasFoil());
            model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        }
    }
}
