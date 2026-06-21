package fuzs.horseexpert.common.client.model.geom;

import fuzs.horseexpert.common.HorseExpert;
import fuzs.puzzleslib.common.api.client.init.v1.ModelLayerFactory;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModModelLayers {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(HorseExpert.MOD_ID);
    public static final ModelLayerLocation PLAYER_BABY_MONOCLE_LAYER = MODEL_LAYERS.registerModelLayer("player_baby",
            "monocle");
    public static final ModelLayerLocation PLAYER_MONOCLE_LAYER = MODEL_LAYERS.registerModelLayer("player", "monocle");
}
