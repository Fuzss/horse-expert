package fuzs.horseexpert.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.client.renderer.ModRenderType;
import fuzs.horseexpert.client.renderer.entity.layers.MonocleRenderer;
import fuzs.horseexpert.init.ModRegistry;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class HorseExpertFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(HorseExpert.MOD_ID, HorseExpertClient::new);
        registerIntegration();
    }

    private static void registerIntegration() {
        TrinketRendererRegistry.registerRenderer(ModRegistry.MONOCLE_ITEM.get(), (ItemStack itemStack, SlotReference slotReference, EntityModel<? extends LivingEntity> entityModel, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, LivingEntity livingEntity, float v, float v1, float v2, float v3, float v4, float v5) -> {
            MonocleRenderer.get().render(itemStack, poseStack, entityModel, multiBufferSource, i, ModRenderType::armorCutoutTranslucentNoCull);
        });
    }
}