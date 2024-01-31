package fuzs.horseexpert.client.renderer.entity.layers;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.horseexpert.HorseExpert;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class MonocleRenderer {
    private static final ResourceLocation MONOCLE_LOCATION = new ResourceLocation(HorseExpert.MOD_ID, "textures/entity/monocle.png");
    private static final ModelLayerFactory LAYER_REGISTRY = ModelLayerFactory.from(HorseExpert.MOD_ID);
    public static final ModelLayerLocation PLAYER_MONOCLE = LAYER_REGISTRY.register("player", "monocle");

    private static MonocleRenderer instance;

    private final HumanoidModel<LivingEntity> monocleModel;

    private MonocleRenderer(EntityModelSet entityModels) {
        // nice trick from artifacts mod for overriding those two methods and therefore being able to use the vanilla class
        this.monocleModel = new HumanoidModel<>(entityModels.bakeLayer(PLAYER_MONOCLE)) {

            @Override
            protected Iterable<ModelPart> headParts() {
                return ImmutableList.of(this.head);
            }

            @Override
            protected Iterable<ModelPart> bodyParts() {
                return ImmutableList.of();
            }
        };
    }

    @SuppressWarnings("unchecked")
    public <T extends LivingEntity> void render(ItemStack stack, PoseStack matrixStack, EntityModel<? extends LivingEntity> entityModel, MultiBufferSource multiBufferSource, int light, Function<ResourceLocation, RenderType> renderType) {
        ((HumanoidModel<T>) entityModel).copyPropertiesTo((HumanoidModel<T>) this.monocleModel);
        VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, renderType.apply(MONOCLE_LOCATION), false, stack.hasFoil());
        this.monocleModel.renderToBuffer(matrixStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static MonocleRenderer get() {
        return instance;
    }

    public static void bakeModel(EntityModelSet entityModels) {
        instance = new MonocleRenderer(entityModels);
    }
}
