package fuzs.horseexpert.util;

import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.caching.ItemTagPredicate;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class ItemEquipmentHelper {

    private ItemEquipmentHelper() {
        // NO-OP
    }

    public static ItemStack getEquippedItem(LivingEntity livingEntity, TagKey<Item> tagKey) {
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("accessories")) {
            return getAccessoriesItem(livingEntity, tagKey);
        } else {
            return getVanillaItem(livingEntity, tagKey);
        }
    }

    private static ItemStack getVanillaItem(LivingEntity livingEntity, TagKey<Item> tagKey) {
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (equipmentSlot != EquipmentSlot.MAINHAND) {
                ItemStack itemStack = livingEntity.getItemBySlot(equipmentSlot);
                if (itemStack.is(tagKey) && livingEntity.getEquipmentSlotForItem(itemStack) == equipmentSlot) {
                    return itemStack;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private static ItemStack getAccessoriesItem(LivingEntity livingEntity, TagKey<Item> tagKey) {
        return AccessoriesCapability.getOptionally(livingEntity).map((AccessoriesCapability capability) -> {
            ItemTagPredicate itemTagPredicate = new ItemTagPredicate(tagKey.location().getPath() + "_check", tagKey);
            return capability.getFirstEquipped(itemTagPredicate);
        }).map(SlotEntryReference::stack).orElse(ItemStack.EMPTY);
    }
}
