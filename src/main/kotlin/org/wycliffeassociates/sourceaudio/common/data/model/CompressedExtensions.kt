package org.wycliffeassociates.sourceaudio.common.data.model

enum class CompressedExtensions(ext: String) {
    MP3("mp3"),
    JPEG("jpeg"),
    JPG("jpg");

    companion object: SupportedExtensions {
        override fun isSupported(ext: String): Boolean = values().any { it.name == ext.toUpperCase() }
    }
}