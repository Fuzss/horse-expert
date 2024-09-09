package fuzs.horseexpert.neoforge;

import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.data.ModEntityTypeTagProvider;
import fuzs.horseexpert.data.ModItemTagProvider;
import fuzs.horseexpert.data.ModRecipeProvider;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.fml.common.Mod;

@Mod(HorseExpert.MOD_ID)
public class HorseExpertNeoForge {

    public HorseExpertNeoForge() {
        ModConstructor.construct(HorseExpert.MOD_ID, HorseExpert::new);
        DataProviderHelper.registerDataProviders(HorseExpert.MOD_ID, ModEntityTypeTagProvider::new,
                ModItemTagProvider::new, ModRecipeProvider::new
        );
    }
}
