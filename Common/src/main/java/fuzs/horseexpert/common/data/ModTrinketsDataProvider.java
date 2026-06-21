package fuzs.horseexpert.common.data;

import eu.pb4.trinkets.api.DefaultTrinketSlots;
import eu.pb4.trinkets.api.datagen.TrinketsDataProvider;
import fuzs.horseexpert.common.HorseExpert;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class ModTrinketsDataProvider extends TrinketsDataProvider {

    public ModTrinketsDataProvider(DataProviderContext context) {
        this(context.getPackOutput(), context.getRegistries());
    }

    public ModTrinketsDataProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void generate(TrinketsOutput output) {
        output.entitySlots(HorseExpert.MOD_ID).addPlayer().addSlot(DefaultTrinketSlots.HEAD_FACE);
    }

    @Override
    public String getName() {
        return "Trinkets";
    }
}
