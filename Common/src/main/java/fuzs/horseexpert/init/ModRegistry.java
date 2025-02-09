package fuzs.horseexpert.init;

import fuzs.horseexpert.HorseExpert;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;

public class ModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(HorseExpert.MOD_ID);
    public static final Holder.Reference<Item> MONOCLE_ITEM = REGISTRIES.registerItem("monocle", () -> {
        Item.Properties properties = new Item.Properties().stacksTo(1);
        if (!ModLoaderEnvironment.INSTANCE.isModLoaded("accessories")) {
            // the model must be present, so the default item renderer is not used
            // the equipment definition leads nowhere, that is ok though
            // rendering is done via a separate render layer
            properties.component(DataComponents.EQUIPPABLE,
                    Equippable.builder(EquipmentSlot.HEAD)
                            .setEquipSound(SoundEvents.ARMOR_EQUIP_GOLD)
                            .setAsset(ResourceKey.create(EquipmentAssets.ROOT_ID, HorseExpert.id("monocle")))
                            .setDamageOnHurt(false)
                            .build());
        }
        return properties;
    });

    static final TagFactory TAGS = TagFactory.make(HorseExpert.MOD_ID);
    public static final TagKey<Item> INSPECTION_EQUIPMENT_ITEM_TAG = TAGS.registerItemTag("inspection_equipment");
    public static final TagKey<EntityType<?>> INSPECTABLE_ENTITY_TYPE_TAG = TAGS.registerEntityTypeTag("inspectable");

    public static void bootstrap() {
        // NO-OP
    }
}
