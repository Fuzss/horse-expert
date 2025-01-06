package fuzs.horseexpert.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.function.Supplier;

public class MonocleAccessoryRenderer implements AccessoryRenderer {

    public static Supplier<AccessoryRenderer> getFactory() {
        // better have this in here to be safe from class loading issues when accessories is not installed
        return MonocleAccessoryRenderer::new;
    }

    @Override
    public <S extends LivingEntityRenderState> void render(ItemStack itemStack, SlotReference slotReference, PoseStack poseStack, EntityModel<S> model, S renderState, MultiBufferSource bufferSource, int packedLight, float partialTick) {
        EntityRenderer<?, ?> entityRender = Minecraft.getInstance()
                .getEntityRenderDispatcher()
                .getRenderer(slotReference.entity());
        if (entityRender instanceof LivingEntityRenderer<?, ?, ?> livingEntityRenderer) {
            var possibleLayer = livingEntityRenderer.layers.stream().filter(renderLayer -> {
                return renderLayer instanceof MonocleLayer<?, ?>;
            }).findFirst();

            extracted(poseStack, renderState, bufferSource, packedLight, possibleLayer);
        }
    }

    private <S extends HumanoidRenderState, M extends HumanoidModel<S>> void extracted(PoseStack poseStack, LivingEntityRenderState renderState, MultiBufferSource bufferSource, int packedLight, Optional<? extends RenderLayer<?, ?>> possibleLayer) {
        possibleLayer.ifPresent(layer -> {

            ((MonocleLayer<S, M>) layer).renderVisible(poseStack,
                    bufferSource,
                    packedLight,
                    (S) renderState,
                    renderState.yRot,
                    renderState.xRot);
        });
    }
}
