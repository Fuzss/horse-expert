package fuzs.horseexpert.common.client.init;

import fuzs.horseexpert.common.HorseExpert;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;

import java.util.Locale;
import java.util.function.Function;

public class ModEnumConstants {
    private static final Identifier HUMANOID_HEAD_ID = HorseExpert.id("humanoid_head");
    public static final EquipmentClientInfo.LayerType HUMANOID_HEAD_LAYER_TYPE = getEnumConstant(HUMANOID_HEAD_ID,
            EquipmentClientInfo.LayerType::valueOf);

    public static void bootstrap() {
        // NO-OP
    }

    private static <E extends Enum<E>> E getEnumConstant(Identifier id, Function<String, E> valueOfInvoker) {
        return valueOfInvoker.apply(id.toDebugFileName().toUpperCase(Locale.ROOT));
    }
}
