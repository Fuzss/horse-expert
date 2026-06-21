plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-fabric")
}

dependencies {
    modApi(sharedLibs.fabricapi.fabric)
    modApi(sharedLibs.puzzleslib.fabric)
    api(sharedLibs.trinkets.fabric)
}

multiloader {
    mixins {
        clientMixin("EquipmentClientInfo\u0024LayerTypeFabricMixin")
    }
}
