package fuzs.horseexpert.core;

import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class FabricAbstractions implements CommonAbstractions {

    @Override
    public Optional<ItemStack> findEquippedItem(LivingEntity entity, Item item) {
        return TrinketsApi.getTrinketComponent(entity)
                .flatMap(component -> component.getEquipped(item).stream()
                        .findFirst()
                        .map(Tuple::getB));
    }
}
