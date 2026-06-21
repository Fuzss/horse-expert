package fuzs.horseexpert.common.util;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.TrinketsApi;
import fuzs.puzzleslib.common.api.core.v1.ModLoaderEnvironment;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public final class ItemEquipmentHelper {

    private ItemEquipmentHelper() {
        // NO-OP
    }

    public static ItemStack getEquippedItem(LivingEntity livingEntity, TagKey<Item> tag) {
        return getTrinket(livingEntity, tag).orElseGet(() -> {
            return getEquipment(livingEntity, tag);
        });
    }

    private static ItemStack getEquipment(LivingEntity livingEntity, TagKey<Item> tag) {
        for (EquipmentSlot equipmentSlot : EquipmentSlotGroup.ARMOR) {
            ItemStack item = livingEntity.getItemBySlot(equipmentSlot);
            if (item.is(tag) && livingEntity.getEquipmentSlotForItem(item) == equipmentSlot) {
                return item;
            }
        }

        return ItemStack.EMPTY;
    }

    private static Optional<ItemStack> getTrinket(LivingEntity livingEntity, TagKey<Item> tag) {
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("trinkets_updated")) {
            return TrinketsApi.getAttachment(livingEntity).findFirst(tag, true).map(TrinketSlotAccess::get);
        } else {
            return Optional.empty();
        }
    }
}
