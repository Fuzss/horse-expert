package fuzs.horseexpert.world.item;

import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FabricMonocleItem extends TrinketItem {

    public FabricMonocleItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        tooltip.add(MonocleItem.MONOCLE_TOOLTIP_COMPONENT);
    }
}
