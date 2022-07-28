plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("fabric-loom") version "0.12-SNAPSHOT"
    id("com.modrinth.minotaur") version "2.+"
    java
}

val mcVersion = "1.19.1"
group = "me.obsilabor"
version = "1.1.0"

repositories {
    mavenCentral()
    maven("https://maven.shedaniel.me/")
    maven("https://maven.terraformersmc.com")
}

dependencies {
    implementation(kotlin("stdlib"))
    minecraft("com.mojang:minecraft:$mcVersion")
    mappings("net.fabricmc:yarn:$mcVersion+build.1")
    modImplementation("net.fabricmc:fabric-loader:0.14.8")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.8.2+kotlin.1.7.10")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0-RC")
    modApi("com.terraformersmc:modmenu:4.0.5")
    modApi("me.shedaniel.cloth:cloth-config-fabric:7.0.73") {
        exclude("net.fabricmc.fabric-api")
    }
}

tasks {
    processResources {
        val properties = mapOf(
            "version" to project.version,
            "mcVersion" to mcVersion
        )
        inputs.properties(properties)
        filesMatching("fabric.mod.json") {
            expand(properties)
        }
    }
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))
    projectId.set("Lybg3a9j")
    versionNumber.set(project.version.toString())
    versionType.set("release")
    gameVersions.addAll(listOf(mcVersion))
    loaders.add("fabric")
    loaders.add("quilt")
    dependencies {
        required.project("Ha28R6CL")
        optional.project("mOgUt4GM")
        required.project("9s6osm5g")
    }

    uploadFile.set(tasks.remapJar.get())
}