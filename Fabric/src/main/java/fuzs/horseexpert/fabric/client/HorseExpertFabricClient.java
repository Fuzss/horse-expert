package fuzs.horseexpert.fabric.client;

import fuzs.horseexpert.common.HorseExpert;
import fuzs.horseexpert.common.client.HorseExpertClient;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class HorseExpertFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(HorseExpert.MOD_ID, HorseExpertClient::new);
    }
}
