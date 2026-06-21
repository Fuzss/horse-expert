package fuzs.horseexpert.neoforge;

import fuzs.horseexpert.common.HorseExpert;
import fuzs.horseexpert.common.data.ModRecipeProvider;
import fuzs.horseexpert.common.data.ModTrinketsDataProvider;
import fuzs.horseexpert.common.data.tags.ModEntityTypeTagsProvider;
import fuzs.horseexpert.common.data.tags.ModItemTagsProvider;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.fml.common.Mod;

@Mod(HorseExpert.MOD_ID)
public class HorseExpertNeoForge {

    public HorseExpertNeoForge() {
        ModConstructor.construct(HorseExpert.MOD_ID, HorseExpert::new);
        DataProviderHelper.registerDataProviders(HorseExpert.MOD_ID,
                ModEntityTypeTagsProvider::new,
                ModItemTagsProvider::new,
                ModRecipeProvider::new,
                ModTrinketsDataProvider::new);
    }
}
