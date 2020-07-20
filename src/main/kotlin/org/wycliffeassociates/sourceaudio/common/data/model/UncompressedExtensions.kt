package org.wycliffeassociates.sourceaudio.common.data.model

enum class UncompressedExtensions(val ext: String) {
    WAV("wav");

    companion object : SupportedExtensions {
        override fun isSupported(extension: String): Boolean = values().any { it.name == extension.toUpperCase() }
    }
}