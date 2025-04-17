package fuzs.horseexpert.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * Better have this in here to be safe from class loading issues when accessories is not installed.
 */
public class MonocleAccessoryRenderer implements AccessoryRenderer {

    public static Supplier<AccessoryRenderer> getFactory() {
        return MonocleAccessoryRenderer::new;
    }

    @Override
    public <S extends LivingEntityRenderState> void render(ItemStack itemStack, SlotReference slotReference, PoseStack poseStack, EntityModel<S> model, S renderState, MultiBufferSource bufferSource, int packedLight, float partialTick) {
        MonocleLayer.getLayer()
                .render(poseStack, bufferSource, packedLight, renderState, renderState.yRot, renderState.xRot);
    }
}
