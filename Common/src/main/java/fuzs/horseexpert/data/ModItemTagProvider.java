package fuzs.horseexpert.data;

import fuzs.horseexpert.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractTagProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.init.v3.tags.TypedTagFactory;
import net.minecraft.core.HolderLookup;

public class ModItemTagProvider extends AbstractTagProvider.Items {

    public ModItemTagProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(ModRegistry.INSPECTION_EQUIPMENT_ITEM_TAG).add(ModRegistry.MONOCLE_ITEM.value());
        this.tag(TypedTagFactory.ITEM.curios("head")).add(ModRegistry.MONOCLE_ITEM.value());
        this.tag(TypedTagFactory.ITEM.trinkets("head/face")).add(ModRegistry.MONOCLE_ITEM.value());
    }
}
