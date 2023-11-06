package fuzs.horseexpert.data;

import fuzs.horseexpert.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractTagProvider;
import fuzs.puzzleslib.api.init.v3.tags.TypedTagFactory;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends AbstractTagProvider.Items {

    public ModItemTagsProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModRegistry.INSPECTION_EQUIPMENT_ITEM_TAG).add(ModRegistry.MONOCLE_ITEM.get());
        this.tag(TypedTagFactory.ITEM.curios("head")).add(ModRegistry.MONOCLE_ITEM.get());
        this.tag(TypedTagFactory.ITEM.trinkets("head/face")).add(ModRegistry.MONOCLE_ITEM.get());
    }
}
