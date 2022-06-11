plugins {
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
    id("fabric-loom") version "0.12-SNAPSHOT"
    java
}

group = "me.obsilabor"
version = "1.1.0"

repositories {
    mavenCentral()
    maven("https://maven.shedaniel.me/")
    maven("https://maven.terraformersmc.com")
}

dependencies {
    implementation(kotlin("stdlib"))
    minecraft("com.mojang:minecraft:1.19")
    mappings("net.fabricmc:yarn:1.19+build.2")
    modImplementation("net.fabricmc:fabric-loader:0.14.7")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.8.0+kotlin.1.7.0")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    modApi("com.terraformersmc:modmenu:4.0.0")
    modApi("me.shedaniel.cloth:cloth-config-fabric:7.0.72") {
        exclude("net.fabricmc.fabric-api")
    }

}
tasks {
    processResources {
        val properties = mapOf(
            "version" to project.version,
        )
        inputs.properties(properties)
        filesMatching("fabric.mod.json") {
            expand(properties)
        }
    }
}
