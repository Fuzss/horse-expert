package fuzs.horseexpert.client;

import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.integration.curios.CuriosClientIntegration;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = HorseExpert.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HorseExpertForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(HorseExpert.MOD_ID, HorseExpertClient::new);
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent evt) {
        registerIntegrations();
    }

    private static void registerIntegrations() {
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("curios")) {
            CuriosClientIntegration.registerCuriosRenderer();
        }
    }
}
