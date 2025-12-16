package fuzs.horseexpert.fabric.client;

import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.client.HorseExpertClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class HorseExpertFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(HorseExpert.MOD_ID, HorseExpertClient::new);
    }
}
