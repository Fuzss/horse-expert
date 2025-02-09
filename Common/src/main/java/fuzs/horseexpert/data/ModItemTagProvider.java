package fuzs.horseexpert.data;

import fuzs.horseexpert.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTagProvider extends AbstractTagProvider<Item> {

    public ModItemTagProvider(DataProviderContext context) {
        super(Registries.ITEM, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.add(ModRegistry.INSPECTION_EQUIPMENT_ITEM_TAG).add(ModRegistry.MONOCLE_ITEM.value());
        TagKey<Item> tagKey = TagFactory.make("accessories").registerItemTag("face");
        this.add(tagKey).add(ModRegistry.MONOCLE_ITEM.value());
    }
}
