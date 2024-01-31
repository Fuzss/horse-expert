package fuzs.horseexpert.data;

import fuzs.horseexpert.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractTagProvider;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class ModItemTagsProvider extends AbstractTagProvider.Items {

    public ModItemTagsProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void addTags() {
        this.tag("curios:head").add(ModRegistry.MONOCLE_ITEM.get());
        this.tag("trinkets:head/face").add(ModRegistry.MONOCLE_ITEM.get());
    }
}
