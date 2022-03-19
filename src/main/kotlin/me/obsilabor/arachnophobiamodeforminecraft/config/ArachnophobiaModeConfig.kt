package me.obsilabor.arachnophobiamodeforminecraft.config

import kotlinx.serialization.Serializable

@Serializable
data class ArachnophobiaModeConfig(
    var isEnabled: Boolean,
    var catImage: Int, //int from 1 - 3
    var replaceSounds: Boolean
)