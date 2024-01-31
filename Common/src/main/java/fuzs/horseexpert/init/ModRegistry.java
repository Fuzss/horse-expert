package fuzs.horseexpert.init;

import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.world.item.MonocleItem;
import fuzs.puzzleslib.api.core.v1.ModLoader;
import fuzs.puzzleslib.api.init.v2.RegistryManager;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class ModRegistry {
    static final RegistryManager REGISTRY = RegistryManager.instant(HorseExpert.MOD_ID);
    public static final RegistryReference<Item> MONOCLE_ITEM = ModRegistry.REGISTRY.whenOn(ModLoader.FORGE).registerItem("monocle", () -> new MonocleItem(new Item.Properties().stacksTo(1)));

    public static final TagKey<EntityType<?>> INSPECTABLE_ENTITY_TYPE_TAG = REGISTRY.registerEntityTypeTag("inspectable");

    public static void touch() {

    }
}
