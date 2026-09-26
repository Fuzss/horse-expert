package fuzs.horseexpert.common.data.client;

import fuzs.horseexpert.common.init.ModRegistry;
import fuzs.puzzleslib.common.api.client.data.v3.language.AbstractLanguageProvider;
import fuzs.puzzleslib.common.api.data.v3.core.DataProviderContext;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations() {
        this.add(ModRegistry.MONOCLE_ITEM.value(), "Monocle");
        this.add("item.horseexpert.monocle.tooltip", "Wear it and look at a mount to see all statistics!");
        this.add("horse.tooltip.min", "Min: %s");
        this.add("horse.tooltip.max", "Max: %s");
        this.add("horse.tooltip.health", "Health: %s");
        this.add("horse.tooltip.health.unit", "%s hearts");
        this.add("horse.tooltip.speed", "Speed: %s");
        this.add("horse.tooltip.speed.unit", "%s blocks/second");
        this.add("horse.tooltip.jump_height", "Jump Height: %s");
        this.add("horse.tooltip.jump_height.unit", "%s blocks");
        this.add("horse.tooltip.strength", "Storage: %s");
        this.add("horse.tooltip.strength.unit", "%s slots");
    }
}
