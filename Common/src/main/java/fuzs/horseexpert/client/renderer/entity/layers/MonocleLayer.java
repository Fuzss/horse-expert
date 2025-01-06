package fuzs.horseexpert.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.client.renderer.ModRenderType;
import fuzs.horseexpert.init.ModRegistry;
import fuzs.horseexpert.util.ItemEquipmentHelper;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import fuzs.puzzleslib.api.client.util.v1.RenderPropertyKey;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MonocleLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(HorseExpert.MOD_ID);
    public static final ModelLayerLocation PLAYER_MONOCLE_MODEL_LAYER_LOCATION = MODEL_LAYERS.register("player",
            "monocle");
    public static final ModelLayerLocation PLAYER_BABY_MONOCLE_MODEL_LAYER_LOCATION = MODEL_LAYERS.register(
            "player_baby",
            "monocle");
    public static final RenderPropertyKey<ItemStack> MONOCLE_ITEM_RENDER_PROPERTY_KEY = new RenderPropertyKey<>(
            HorseExpert.id("monocle_item"));
    private static final ResourceLocation TEXTURE_LOCATION = HorseExpert.id(
            "textures/entity/equipment/humanoid/monocle.png");

    private final HumanoidModel<S> model;
    private final HumanoidModel<S> babyModel;
    private boolean visible = !ModLoaderEnvironment.INSTANCE.isModLoaded("accessories");

    public MonocleLayer(RenderLayerParent<S, M> renderer, EntityRendererProvider.Context context) {
        super(renderer);
        this.model = new HumanoidModel<>(context.bakeLayer(PLAYER_MONOCLE_MODEL_LAYER_LOCATION));
        this.babyModel = new HumanoidModel<>(context.bakeLayer(PLAYER_BABY_MONOCLE_MODEL_LAYER_LOCATION));
    }

    public static void onExtractRenderState(Entity entity, EntityRenderState entityRenderState, float partialTick) {
        if (entity instanceof Player player && entityRenderState instanceof PlayerRenderState) {
            ItemStack itemStack = ItemEquipmentHelper.getEquippedItem(player,
                    ModRegistry.INSPECTION_EQUIPMENT_ITEM_TAG);
            RenderPropertyKey.setRenderProperty(entityRenderState, MONOCLE_ITEM_RENDER_PROPERTY_KEY, itemStack);
        }
    }

    public void renderVisible(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, S renderState, float yRot, float xRot) {
        // allows for the accessories renderer to simply fish for the registered render layer,
        // without the layer already being rendered by the living entity renderer
        this.visible = true;
        this.render(poseStack, bufferSource, packedLight, renderState, yRot, xRot);
        this.visible = false;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, S renderState, float yRot, float xRot) {
        if (this.visible) {
            ItemStack itemStack = RenderPropertyKey.getRenderProperty(renderState, MONOCLE_ITEM_RENDER_PROPERTY_KEY);
            if (!itemStack.isEmpty()) {
                HumanoidModel<S> model = renderState.isBaby ? this.babyModel : this.model;
                model.setupAnim(renderState);
                model.setAllVisible(false);
                model.head.visible = true;
                model.hat.visible = true;
                VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(bufferSource,
                        ModRenderType.armorCutoutTranslucentNoCull(TEXTURE_LOCATION),
                        itemStack.hasFoil());
                model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
            }
        }
    }
}
