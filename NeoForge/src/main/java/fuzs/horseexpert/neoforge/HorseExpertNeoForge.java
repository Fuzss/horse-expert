package fuzs.horseexpert.neoforge;

import fuzs.horseexpert.common.HorseExpert;
import fuzs.horseexpert.common.data.ModRecipeProvider;
import fuzs.horseexpert.common.data.ModTrinketsDataProvider;
import fuzs.horseexpert.common.data.tags.ModEntityTypeTagsProvider;
import fuzs.horseexpert.common.data.tags.ModItemTagsProvider;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.common.api.core.v1.ModLoaderEnvironment;
import fuzs.puzzleslib.neoforge.api.data.v3.core.DataProviderBuilder;
import net.neoforged.fml.common.Mod;

@Mod(HorseExpert.MOD_ID)
public class HorseExpertNeoForge {

    public HorseExpertNeoForge() {
        ModConstructor.construct(HorseExpert.MOD_ID, HorseExpert::new);
        DataProviderBuilder builder = DataProviderBuilder.of(HorseExpert.MOD_ID)
                .addProvider(ModEntityTypeTagsProvider::new, ModItemTagsProvider::new)
                .addRecipeProvider(ModRecipeProvider::new);
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("trinkets_updated")) {
            builder.addProvider(ModTrinketsDataProvider::new);
        }
    }
}
