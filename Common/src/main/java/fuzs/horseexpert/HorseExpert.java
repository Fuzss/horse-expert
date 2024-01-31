package fuzs.horseexpert;

import fuzs.horseexpert.config.ClientConfig;
import fuzs.horseexpert.init.ModRegistry;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.BuildCreativeModeTabContentsContext;
import net.minecraft.world.item.CreativeModeTab;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HorseExpert implements ModConstructor {
    public static final String MOD_ID = "horseexpert";
    public static final String MOD_NAME = "Horse Expert";
    public static final Logger LOGGER = LogManager.getLogger(HorseExpert.MOD_NAME);

    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).client(ClientConfig.class);

    @Override
    public void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsContext context) {
        context.registerBuildListener(CreativeModeTab.TAB_TOOLS, (output) -> {
            output.accept(ModRegistry.MONOCLE_ITEM.get());
        });
    }

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
    }
}
