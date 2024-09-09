package fuzs.horseexpert.init;

import fuzs.horseexpert.HorseExpert;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.api.init.v3.tags.BoundTagFactory;
import fuzs.puzzleslib.api.item.v2.ItemEquipmentFactories;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class ModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(HorseExpert.MOD_ID);
    public static final Holder.Reference<ArmorMaterial> MONOCLE_ARMOR_MATERIAL;
    public static final Holder.Reference<Item> MONOCLE_ITEM;

    static final BoundTagFactory TAGS = BoundTagFactory.make(HorseExpert.MOD_ID);
    public static final TagKey<Item> INSPECTION_EQUIPMENT_ITEM_TAG = TAGS.registerItemTag("inspection_equipment");
    public static final TagKey<EntityType<?>> INSPECTABLE_ENTITY_TYPE_TAG = TAGS.registerEntityTypeTag("inspectable");

    static {
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("curios") || ModLoaderEnvironment.INSTANCE.isModLoaded("trinkets")) {
            MONOCLE_ARMOR_MATERIAL = REGISTRIES.registerLazily(Registries.ARMOR_MATERIAL, "monocle");
            MONOCLE_ITEM = REGISTRIES.registerItem("monocle", () -> new Item(new Item.Properties().stacksTo(1)));
        } else {
            MONOCLE_ARMOR_MATERIAL = REGISTRIES.registerArmorMaterial("monocle", Items.GOLD_NUGGET.builtInRegistryHolder());
            MONOCLE_ITEM = REGISTRIES.registerItem("monocle", () -> new ArmorItem(MONOCLE_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1)));
        }
    }

    public static void touch() {
        // NO-OP
    }
}
