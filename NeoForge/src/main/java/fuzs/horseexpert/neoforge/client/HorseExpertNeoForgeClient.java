package fuzs.horseexpert.neoforge.client;

import fuzs.horseexpert.common.HorseExpert;
import fuzs.horseexpert.common.client.HorseExpertClient;
import fuzs.horseexpert.common.data.client.ModLanguageProvider;
import fuzs.horseexpert.common.data.client.ModModelProvider;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = HorseExpert.MOD_ID, dist = Dist.CLIENT)
public class HorseExpertNeoForgeClient {

    public HorseExpertNeoForgeClient(ModContainer modContainer) {
        ClientModConstructor.construct(HorseExpert.MOD_ID, HorseExpertClient::new);
        DataProviderHelper.registerDataProviders(HorseExpert.MOD_ID, ModLanguageProvider::new, ModModelProvider::new);
    }
}
