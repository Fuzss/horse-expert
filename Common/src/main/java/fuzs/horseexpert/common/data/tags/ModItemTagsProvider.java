package fuzs.horseexpert.common.data.tags;

import eu.pb4.trinkets.api.DefaultTrinketSlotTags;
import fuzs.horseexpert.common.init.ModRegistry;
import fuzs.puzzleslib.common.api.core.v1.ModLoaderEnvironment;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.common.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class ModItemTagsProvider extends AbstractTagProvider<Item> {

    public ModItemTagsProvider(DataProviderContext context) {
        super(Registries.ITEM, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(ModRegistry.INSPECTION_EQUIPMENT_ITEM_TAG).add(ModRegistry.MONOCLE_ITEM);
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("trinkets_updated")) {
            this.tag(DefaultTrinketSlotTags.HEAD_FACE).addTag(ModRegistry.INSPECTION_EQUIPMENT_ITEM_TAG);
        }
    }
}
