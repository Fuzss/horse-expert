package fuzs.horseexpert.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;
import net.minecraft.ChatFormatting;

public class ClientConfig implements ConfigCore {
    @Config(description = "Offset on x-axis for statistics tooltips from original position.")
    public int offsetX = 0;
    @Config(description = "Offset on y-axis for statistics tooltips from original position.")
    public int offsetY = 0;
    @Config(description = "Color used for highlighting low attribute values on statistics tooltips.")
    @Config.AllowedValues(values = {"BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED", "DARK_PURPLE", "GOLD", "GRAY", "DARK_GRAY", "BLUE", "GREEN", "AQUA", "RED", "LIGHT_PURPLE", "YELLOW", "WHITE"})
    public ChatFormatting lowValueColor = ChatFormatting.RED;
    @Config(description = "Color used for highlighting medium attribute values on statistics tooltips.")
    @Config.AllowedValues(values = {"BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED", "DARK_PURPLE", "GOLD", "GRAY", "DARK_GRAY", "BLUE", "GREEN", "AQUA", "RED", "LIGHT_PURPLE", "YELLOW", "WHITE"})
    public ChatFormatting mediumValueColor = ChatFormatting.GOLD;
    @Config(description = "Color used for highlighting high attribute values on statistics tooltips.")
    @Config.AllowedValues(values = {"BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED", "DARK_PURPLE", "GOLD", "GRAY", "DARK_GRAY", "BLUE", "GREEN", "AQUA", "RED", "LIGHT_PURPLE", "YELLOW", "WHITE"})
    public ChatFormatting highValueColor = ChatFormatting.GREEN;
    @Config(description = "Percentage value below which a value is considered to be low when shown on statistics tooltips.")
    @Config.DoubleRange(min = 0.0, max = 1.0)
    public double lowValuePercentage = 0.25;
    @Config(description = "Percentage value above which a value is considered to be high when shown on statistics tooltips.")
    @Config.DoubleRange(min = 0.0, max = 1.0)
    public double highValuePercentage = 0.75;
    @Config(description = "Must the mount be tamed for any statistics to show.")
    public boolean mustBeTamed = true;
    @Config(description = "Show speed and jump height for llamas. They have those attributes as they inherit all functionality from horses, but don't really use them for anything.")
    public boolean allLlamaAttributes = false;
    @Config(description = "Only show statistics tooltips while sneaking (still requires a monocle to be worn).")
    public boolean requiresSneaking = false;
}
