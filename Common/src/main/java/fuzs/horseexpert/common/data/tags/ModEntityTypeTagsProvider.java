package fuzs.horseexpert.common.data.tags;

import fuzs.horseexpert.common.init.ModRegistry;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.common.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypeIds;

public class ModEntityTypeTagsProvider extends AbstractTagProvider<EntityType<?>> {

    public ModEntityTypeTagsProvider(DataProviderContext context) {
        super(Registries.ENTITY_TYPE, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(ModRegistry.INSPECTABLE_ENTITY_TYPE_TAG)
                .add(EntityTypeIds.HORSE,
                        EntityTypeIds.DONKEY,
                        EntityTypeIds.MULE,
                        EntityTypeIds.ZOMBIE_HORSE,
                        EntityTypeIds.SKELETON_HORSE,
                        EntityTypeIds.LLAMA,
                        EntityTypeIds.TRADER_LLAMA);
    }
}
