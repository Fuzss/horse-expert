package fuzs.horseexpert.neoforge.client;

import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.client.HorseExpertClient;
import fuzs.horseexpert.data.client.ModLanguageProvider;
import fuzs.horseexpert.data.client.ModModelProvider;
import fuzs.horseexpert.neoforge.integration.curios.CuriosClientIntegration;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = HorseExpert.MOD_ID, dist = Dist.CLIENT)
public class HorseExpertNeoForgeClient {

    public HorseExpertNeoForgeClient(ModContainer modContainer) {
        ClientModConstructor.construct(HorseExpert.MOD_ID, HorseExpertClient::new);
        registerLoadingEvents(modContainer.getEventBus());
        DataProviderHelper.registerDataProviders(HorseExpert.MOD_ID, ModLanguageProvider::new, ModModelProvider::new);
    }

    private static void registerLoadingEvents(IEventBus eventBus) {
        eventBus.addListener((final FMLClientSetupEvent evt) -> {
            registerModIntegrations();
        });
    }

    private static void registerModIntegrations() {
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("curios")) {
            CuriosClientIntegration.registerCuriosRenderer();
        }
    }
}
