plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-fabric")
}

repositories {
    exclusiveContent {
        forRepository {
            maven {
                name = "Nucleoid"
                url = uri("https://maven.nucleoid.xyz/releases")
            }
        }
        filter {
            @Suppress("UnstableApiUsage")
            includeGroupAndSubgroups("eu.pb4")
        }
    }
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
