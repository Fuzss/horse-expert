package fuzs.horseexpert.init;

import fuzs.horseexpert.world.item.FabricMonocleItem;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import net.minecraft.world.item.Item;

public class FabricModRegistry {
    public static final RegistryReference<Item> MONOCLE_ITEM = ModRegistry.REGISTRY.registerItem("monocle", () -> new FabricMonocleItem(new Item.Properties().stacksTo(1)));

    public static void touch() {

    }
}
