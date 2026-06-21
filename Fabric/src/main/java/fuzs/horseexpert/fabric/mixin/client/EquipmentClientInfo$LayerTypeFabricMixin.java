package fuzs.horseexpert.fabric.mixin.client;

import net.minecraft.client.resources.model.EquipmentClientInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EquipmentClientInfo.LayerType.class)
enum EquipmentClientInfo$LayerTypeFabricMixin {
    HORSEEXPERT_HUMANOID_HEAD("horseexpert/humanoid_head");

    @Shadow
    EquipmentClientInfo$LayerTypeFabricMixin(String id) {
        throw new RuntimeException();
    }
}
