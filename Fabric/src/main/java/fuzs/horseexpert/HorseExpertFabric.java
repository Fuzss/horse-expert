package fuzs.horseexpert;

import fuzs.horseexpert.init.FabricModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class HorseExpertFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        FabricModRegistry.touch();
        ModConstructor.construct(HorseExpert.MOD_ID, HorseExpert::new);
    }
}
