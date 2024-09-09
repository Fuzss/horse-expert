package fuzs.horseexpert.data.client;

import fuzs.horseexpert.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractLanguageProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations(TranslationBuilder builder) {
        builder.add(ModRegistry.MONOCLE_ITEM.value(), "Monocle");
        builder.add("item.horseexpert.monocle.tooltip", "Wear builder and look at a mount to see its statistics!");
        builder.add("horse.tooltip.min", "Min: %s");
        builder.add("horse.tooltip.max", "Max: %s");
        builder.add("horse.tooltip.health", "Health: %s");
        builder.add("horse.tooltip.health.unit", "%s hearts");
        builder.add("horse.tooltip.speed", "Speed: %s");
        builder.add("horse.tooltip.speed.unit", "%s blocks/second");
        builder.add("horse.tooltip.jump_height", "Jump Height: %s");
        builder.add("horse.tooltip.jump_height.unit", "%s blocks");
        builder.add("horse.tooltip.strength", "Storage: %s");
        builder.add("horse.tooltip.strength.unit", "%s slots");
    }
}
