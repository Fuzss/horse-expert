package fuzs.horseexpert;

import fuzs.horseexpert.data.ModEntityTypeTagsProvider;
import fuzs.horseexpert.data.ModItemTagsProvider;
import fuzs.horseexpert.data.ModRecipeProvider;
import fuzs.horseexpert.data.client.ModLanguageProvider;
import fuzs.horseexpert.data.client.ModModelProvider;
import fuzs.horseexpert.integration.curios.CuriosIntegration;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import fuzs.puzzleslib.api.data.v2.core.DataProviderHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(HorseExpert.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class HorseExpertForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(HorseExpert.MOD_ID, HorseExpert::new);
        registerIntegrations();
        DataProviderHelper.registerDataProviders(HorseExpert.MOD_ID, ModEntityTypeTagsProvider::new, ModItemTagsProvider::new, ModRecipeProvider::new, ModLanguageProvider::new, ModModelProvider::new);
    }

    private static void registerIntegrations() {
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("curios")) {
            CuriosIntegration.registerHandlers();
        }
    }
}
