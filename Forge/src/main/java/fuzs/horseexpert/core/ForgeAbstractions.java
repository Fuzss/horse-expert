package fuzs.horseexpert.core;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

public class ForgeAbstractions implements CommonAbstractions {

    @Override
    public Optional<ItemStack> findEquippedItem(LivingEntity entity, Item item) {
        return CuriosApi.getCuriosHelper().findFirstCurio(entity, item).map(SlotResult::stack);
    }
}
