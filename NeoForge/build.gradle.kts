plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-neoforge")
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
