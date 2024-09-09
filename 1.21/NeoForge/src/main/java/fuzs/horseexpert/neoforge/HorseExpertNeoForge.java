package fuzs.horseexpert.neoforge;

import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.data.ModEntityTypeTagProvider;
import fuzs.horseexpert.data.ModItemTagProvider;
import fuzs.horseexpert.data.ModRecipeProvider;
import fuzs.horseexpert.data.client.ModLanguageProvider;
import fuzs.horseexpert.data.client.ModModelProvider;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod(HorseExpert.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class HorseExpertNeoForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(HorseExpert.MOD_ID, HorseExpert::new);
        DataProviderHelper.registerDataProviders(HorseExpert.MOD_ID, ModEntityTypeTagProvider::new, ModItemTagProvider::new, ModRecipeProvider::new, ModLanguageProvider::new, ModModelProvider::new);
    }
}
