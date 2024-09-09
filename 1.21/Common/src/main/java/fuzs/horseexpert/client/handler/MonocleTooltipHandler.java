package fuzs.horseexpert.client.handler;

import fuzs.horseexpert.init.ModRegistry;
import fuzs.puzzleslib.api.core.v1.Proxy;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MonocleTooltipHandler {
    public static final Component MONOCLE_TOOLTIP_COMPONENT = Component.translatable("item.horseexpert.monocle.tooltip").withStyle(ChatFormatting.GRAY);

    public static void onItemTooltip(ItemStack itemStack, List<Component> lines, Item.TooltipContext tooltipContext, @Nullable Player player, TooltipFlag tooltipFlag) {
        if (itemStack.is(ModRegistry.INSPECTION_EQUIPMENT_ITEM_TAG)) {
            List<Component> newLines = Proxy.INSTANCE.splitTooltipLines(MONOCLE_TOOLTIP_COMPONENT);
            if (tooltipFlag.isAdvanced()) {
                int index = lines.size() - (!itemStack.getComponents().isEmpty() ? 2 : 1);
                lines.addAll(index, newLines);
            } else {
                lines.addAll(newLines);
            }
        }
    }
}
