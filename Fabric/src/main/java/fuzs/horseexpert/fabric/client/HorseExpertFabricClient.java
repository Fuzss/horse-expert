package fuzs.horseexpert.fabric.client;

import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.client.HorseExpertClient;
import fuzs.horseexpert.fabric.integration.trinkets.TrinketsClientIntegration;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import net.fabricmc.api.ClientModInitializer;

public class HorseExpertFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(HorseExpert.MOD_ID, HorseExpertClient::new);
        registerIntegrations();
    }

    private static void registerIntegrations() {
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("trinkets")) {
            TrinketsClientIntegration.registerTrinketsRenderer();
        }
    }
}