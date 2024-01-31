package fuzs.horseexpert.data;

import fuzs.horseexpert.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractTagProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class ModEntityTypeTagsProvider extends AbstractTagProvider.EntityTypes {

    public ModEntityTypeTagsProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void addTags() {
        this.tag(ModRegistry.INSPECTABLE_ENTITY_TYPE_TAG).add(EntityType.HORSE, EntityType.DONKEY, EntityType.MULE, EntityType.ZOMBIE_HORSE, EntityType.SKELETON_HORSE, EntityType.LLAMA, EntityType.TRADER_LLAMA);
    }
}
