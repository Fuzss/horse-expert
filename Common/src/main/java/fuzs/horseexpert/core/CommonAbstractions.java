package fuzs.horseexpert.core;

import fuzs.puzzleslib.api.core.v1.ServiceProviderHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public interface CommonAbstractions {
    CommonAbstractions INSTANCE = ServiceProviderHelper.load(CommonAbstractions.class);

    Optional<ItemStack> findEquippedItem(LivingEntity entity, Item item);
}
