package fuzs.horseexpert.client.handler;

import fuzs.horseexpert.init.ModRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MonocleTooltipHandler {
    public static final Component MONOCLE_TOOLTIP_COMPONENT = Component.translatable("item.horseexpert.monocle.tooltip").withStyle(ChatFormatting.GRAY);

    public static void onItemTooltip(ItemStack stack, @Nullable Player player, List<Component> lines, TooltipFlag context) {
        if (stack.is(ModRegistry.INSPECTION_EQUIPMENT_ITEM_TAG)) {
            if (context.isAdvanced()) {
                lines.add(lines.size() - (stack.hasTag() ? 2 : 1), MONOCLE_TOOLTIP_COMPONENT);
            } else {
                lines.add(MONOCLE_TOOLTIP_COMPONENT);
            }
        }
    }
}
