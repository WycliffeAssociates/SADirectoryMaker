package org.wycliffeassociates.sourceaudio.common.data.model

enum class UncompressedExtensions(ext: String) {
    WAV("wav");

    companion object: SupportedExtensions {
        override fun isSupported(ext: String): Boolean = values().any { it.name == ext.toUpperCase() }
    }
}