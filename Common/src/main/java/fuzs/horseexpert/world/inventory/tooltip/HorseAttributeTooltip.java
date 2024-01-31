package fuzs.horseexpert.world.inventory.tooltip;

import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.config.ClientConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.function.DoubleUnaryOperator;

public record HorseAttributeTooltip(@Nullable Item item, @Nullable MobEffect icon, Component line1, @Nullable Component line2) implements TooltipComponent {
    private static final DecimalFormat ATTRIBUTE_VALUE_FORMAT = Util.make(new DecimalFormat("#.##"), (p_41704_) -> {
        p_41704_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    });

    private HorseAttributeTooltip(MobEffect icon, double value, double min, double max, String translationKey) {
        this(null, icon, value, min, max, translationKey, DoubleUnaryOperator.identity());
    }

    private HorseAttributeTooltip(MobEffect icon, double value, double min, double max, String translationKey, DoubleUnaryOperator valueConverter) {
        this(null, icon, value, min, max, translationKey, valueConverter);
    }

    private HorseAttributeTooltip(Item item, double value, double min, double max, String translationKey, DoubleUnaryOperator valueConverter) {
        this(item, null, value, min, max, translationKey, valueConverter);
    }

    private HorseAttributeTooltip(@Nullable Item item, @Nullable MobEffect icon, double value, double min, double max, String translationKey, DoubleUnaryOperator valueConverter) {
        this(item, icon, line1(value, categorizeValue(value, min, max), translationKey, valueConverter), line2(min, max, valueConverter));
    }

    private HorseAttributeTooltip(MobEffect icon, double value, String translationKey) {
        this(null, icon, value, translationKey, DoubleUnaryOperator.identity());
    }

    private HorseAttributeTooltip(MobEffect icon, double value, String translationKey, DoubleUnaryOperator valueConverter) {
        this(null, icon, value, translationKey, valueConverter);
    }

    private HorseAttributeTooltip(Item item, double value, String translationKey, DoubleUnaryOperator valueConverter) {
        this(item, null, value, translationKey, valueConverter);
    }

    private HorseAttributeTooltip(@Nullable Item item, @Nullable MobEffect icon, double value, String translationKey, DoubleUnaryOperator valueConverter) {
        this(item, icon, line1(value, null, translationKey, valueConverter), null);
    }

    private static Component line1(double value, @Nullable ChatFormatting color, String translationKey, DoubleUnaryOperator valueConverter) {
        MutableComponent component1 = new TextComponent(ATTRIBUTE_VALUE_FORMAT.format(valueConverter.applyAsDouble(value)));
        if (color != null) component1 = component1.withStyle(color);
        MutableComponent component2 = new TranslatableComponent(translationKey.concat(".unit"), component1).withStyle(ChatFormatting.GRAY);
        return new TranslatableComponent(translationKey, component2).withStyle(ChatFormatting.WHITE);
    }

    private static Component line2(double min, double max, DoubleUnaryOperator valueConverter) {
        MutableComponent minComponent = new TranslatableComponent("horse.tooltip.min", new TextComponent(ATTRIBUTE_VALUE_FORMAT.format(valueConverter.applyAsDouble(min))).withStyle(ChatFormatting.GRAY)).withStyle(HorseExpert.CONFIG.get(ClientConfig.class).lowValueColor);
        MutableComponent maxComponent = new TranslatableComponent("horse.tooltip.max", new TextComponent(ATTRIBUTE_VALUE_FORMAT.format(valueConverter.applyAsDouble(max))).withStyle(ChatFormatting.GRAY)).withStyle(HorseExpert.CONFIG.get(ClientConfig.class).highValueColor);
        MutableComponent separatorComponent = new TextComponent("   ").withStyle(ChatFormatting.GRAY);
        return minComponent.append(separatorComponent).append(maxComponent);
    }

    private static ChatFormatting categorizeValue(double value, double min, double max) {
        if (value < min + (max - min) * HorseExpert.CONFIG.get(ClientConfig.class).lowValuePercentage) {
            return HorseExpert.CONFIG.get(ClientConfig.class).lowValueColor;
        } else if (value >= min + (max - min) * HorseExpert.CONFIG.get(ClientConfig.class).highValuePercentage) {
            return HorseExpert.CONFIG.get(ClientConfig.class).highValueColor;
        }
        return HorseExpert.CONFIG.get(ClientConfig.class).mediumValueColor;
    }

    public static HorseAttributeTooltip healthTooltip(double value, boolean minMax) {
        // half health values as our translation string says hearts
        if (minMax) {
            return new HorseAttributeTooltip(MobEffects.HEALTH_BOOST, value / 2.0, 7.5, 15.0, "horse.tooltip.health");
        }
        return new HorseAttributeTooltip(MobEffects.HEALTH_BOOST, value / 2.0, "horse.tooltip.health");
    }

    public static HorseAttributeTooltip speedTooltip(double value, boolean minMax) {
        if (minMax) {
            return new HorseAttributeTooltip(MobEffects.MOVEMENT_SPEED, value, 0.1125, 0.3375, "horse.tooltip.speed", d -> d * 43.17);
        }
        return new HorseAttributeTooltip(MobEffects.MOVEMENT_SPEED, value, "horse.tooltip.speed", d -> d * 43.17);
    }

    public static HorseAttributeTooltip jumpHeightTooltip(double value, boolean minMax) {
        if (minMax) {
            return new HorseAttributeTooltip(MobEffects.JUMP, value, 0.4, 1.0, "horse.tooltip.jump_height", d -> Math.pow(d, 1.7) * 5.293);
        }
        return new HorseAttributeTooltip(MobEffects.JUMP, value, "horse.tooltip.jump_height", d -> Math.pow(d, 1.7) * 5.293);
    }

    public static HorseAttributeTooltip strengthTooltip(double value) {
        return new HorseAttributeTooltip(Items.CHEST, value, 1.0, 5.0, "horse.tooltip.strength", d -> d * 3);
    }
}
