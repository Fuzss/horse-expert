package fuzs.horseexpert.world.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MonocleItem extends Item {
    public static final MutableComponent MONOCLE_TOOLTIP_COMPONENT = new TranslatableComponent("item.horseexpert.monocle.tooltip").withStyle(ChatFormatting.GRAY);

    public MonocleItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        tooltip.add(MONOCLE_TOOLTIP_COMPONENT);
    }
}
