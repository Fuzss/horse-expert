plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-common")
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
    modCompileOnlyApi(sharedLibs.puzzleslib.common)
    compileOnlyApi(sharedLibs.trinkets.common)
}
