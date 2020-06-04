package org.wycliffeassociates.sourceaudio.common.data.model

enum class MediaQuality(val quality: String) {
    HI("hi"),
    LOW("low");

    companion object {
        fun isSupportedQuality(quality: String): Boolean = values().any { it.name == quality.toUpperCase() }
    }
}