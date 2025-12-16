package fuzs.horseexpert;

import fuzs.horseexpert.config.ClientConfig;
import fuzs.horseexpert.init.ModRegistry;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import fuzs.puzzleslib.api.event.v1.BuildCreativeModeTabContentsCallback;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HorseExpert implements ModConstructor {
    public static final String MOD_ID = "horseexpert";
    public static final String MOD_NAME = "Horse Expert";
    public static final Logger LOGGER = LogManager.getLogger(HorseExpert.MOD_NAME);

    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).client(ClientConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.bootstrap();
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        BuildCreativeModeTabContentsCallback.buildCreativeModeTabContents(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register((CreativeModeTab creativeModeTab, CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) -> {
                    output.accept(ModRegistry.MONOCLE_ITEM.value());
                });
    }

    @Override
    public void onCommonSetup() {
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("accessories")) {
            // TODO enable Accessories again when available
//            AccessoryRegistry.register(ModRegistry.MONOCLE_ITEM.value(), AccessoryRegistry.defaultAccessory());
        }
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
