package me.obsilabor.arachnophobiamodeforminecraft.config

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import me.obsilabor.arachnophobiamodeforminecraft.json
import me.obsilabor.arachnophobiamodeforminecraft.minecraft
import me.shedaniel.clothconfig2.api.ConfigBuilder
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.TranslatableText
import java.io.File

object ClothConfigManager {

    fun buildScreen(): Screen {
        val builder = ConfigBuilder.create()
            .setParentScreen(minecraft.currentScreen)
            .setTitle(TranslatableText("title.arachnophobiamode.config"))
            .setSavingRunnable {
                configFile.writeText(json.encodeToString(config))
            }
        val general = builder.getOrCreateCategory(TranslatableText("category.arachnophobiamode.general"))
        val entryBuilder = builder.entryBuilder()
        general.addEntry(entryBuilder.startBooleanToggle(TranslatableText("option.arachnophobiamode.enabled"), config?.isEnabled ?: true)
            .setSaveConsumer {
                config?.isEnabled = it
            }
            .setDefaultValue(true)
            .setTooltip(TranslatableText("option.arachnophobiamode.enabled.tooltip"))
            .build())
        general.addEntry(entryBuilder.startBooleanToggle(TranslatableText("option.arachnophobiamode.replacesounds"), config?.replaceSounds ?: true)
            .setSaveConsumer {
                config?.replaceSounds = it
            }
            .setDefaultValue(true)
            .setTooltip(TranslatableText("option.arachnophobiamode.replacesounds.tooltip"))
            .build())
        general.addEntry(entryBuilder.startIntSlider(TranslatableText("option.arachnophobiamode.catimage"), config?.catImage ?: 1, 1, 3)
            .setSaveConsumer {
                config?.catImage = it
            }
            .setDefaultValue(1)
            .setTooltip(TranslatableText("option.arachnophobiamode.catimage.tooltip"))
            .build())
        return builder.build()
    }

    private val configFile = File(System.getProperty("user.dir") + "/config", "arachnophobia-mode-for-minecraft.json")
    var config: ArachnophobiaModeConfig? = null

    init {
        if(!configFile.parentFile.exists()) {
            configFile.parentFile.mkdirs()
        }
        if(!configFile.exists()) {
            configFile.createNewFile()
            configFile.writeText(json.encodeToString(ArachnophobiaModeConfig(true, 1, true)))
        }
        kotlin.runCatching {
            config = json.decodeFromString(configFile.readText())
        }
    }

}