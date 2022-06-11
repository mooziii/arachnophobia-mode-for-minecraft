package me.obsilabor.arachnophobiamodeforminecraft

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.MinecraftClient
import net.minecraft.util.Identifier

class ArachnophobiaMode : ClientModInitializer {
    override fun onInitializeClient() {

    }
}

@OptIn(ExperimentalSerializationApi::class)
val json = Json {
    prettyPrint = true
    encodeDefaults = true
    @Suppress("EXPERIMENTAL_API_USAGE")
    prettyPrintIndent = "  "
}

val minecraft: MinecraftClient
get() = MinecraftClient.getInstance()