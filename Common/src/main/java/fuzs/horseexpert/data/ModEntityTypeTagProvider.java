package fuzs.horseexpert.data;

import fuzs.horseexpert.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;

public class ModEntityTypeTagProvider extends AbstractTagProvider<EntityType<?>> {

    public ModEntityTypeTagProvider(DataProviderContext context) {
        super(Registries.ENTITY_TYPE, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(ModRegistry.INSPECTABLE_ENTITY_TYPE_TAG)
                .add(EntityType.HORSE,
                        EntityType.DONKEY,
                        EntityType.MULE,
                        EntityType.ZOMBIE_HORSE,
                        EntityType.SKELETON_HORSE,
                        EntityType.LLAMA,
                        EntityType.TRADER_LLAMA);
    }
}
