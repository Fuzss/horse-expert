package fuzs.horseexpert.data;

import fuzs.horseexpert.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractModelProvider;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void registerStatesAndModels() {
        this.basicItem(ModRegistry.MONOCLE_ITEM.get());
    }
}
