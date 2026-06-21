plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-neoforge")
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
    modApi(sharedLibs.puzzleslib.neoforge)
    api(sharedLibs.trinkets.neoforge)
}

multiloader {
    modFile {
        enumExtensions.set("META-INF/enumextensions.json")
    }
}
