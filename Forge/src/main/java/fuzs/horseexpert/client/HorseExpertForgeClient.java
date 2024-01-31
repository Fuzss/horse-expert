package fuzs.horseexpert.client;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.client.renderer.ModRenderType;
import fuzs.horseexpert.client.renderer.entity.layers.MonocleRenderer;
import fuzs.horseexpert.init.ModRegistry;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

@Mod.EventBusSubscriber(modid = HorseExpert.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HorseExpertForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(HorseExpert.MOD_ID, HorseExpertClient::new);
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent evt) {
        CuriosRendererRegistry.register(ModRegistry.MONOCLE_ITEM.get(), () -> new ICurioRenderer() {

            @Override
            public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                MonocleRenderer.get().render(stack, matrixStack, renderLayerParent.getModel(), renderTypeBuffer, light, ModRenderType::armorCutoutTranslucentNoCull);
            }
        });
    }
}
