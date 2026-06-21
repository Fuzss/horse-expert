package fuzs.horseexpert.common.data.client;

import fuzs.horseexpert.common.HorseExpert;
import fuzs.horseexpert.common.client.init.ModEnumConstants;
import fuzs.horseexpert.common.init.ModRegistry;
import fuzs.puzzleslib.common.api.client.data.v2.AbstractEquipmentProvider;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.function.BiConsumer;

public class ModEquipmentProvider extends AbstractEquipmentProvider {

    public ModEquipmentProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addEquipmentAssets(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> equipmentAssetConsumer) {
        equipmentAssetConsumer.accept(ModRegistry.MONOCLE_EQUIPMENT_ASSET,
                simple(ModEnumConstants.HUMANOID_HEAD_LAYER_TYPE, HorseExpert.id("monocle")));
    }
}
