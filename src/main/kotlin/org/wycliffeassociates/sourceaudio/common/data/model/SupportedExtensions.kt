package org.wycliffeassociates.sourceaudio.common.data.model

enum class SupportedExtensions(ext: String) {
    WAV("wav"),
    MP3("mp3"),
    JPEG("jpeg"),
    JPG("jpg"),
    TR("tr");

    companion object {
        private fun getContainers(): Array<SupportedExtensions> = arrayOf(TR)
        private fun getCompressedTypes(): Array<SupportedExtensions> = arrayOf(MP3, JPEG, JPG)

        fun isSupported(ext: String): Boolean = values().any { it.name == ext.toUpperCase() }
        fun isContainer(ext: String): Boolean = getContainers().contains(valueOf(ext.toUpperCase()))
        fun isCompressed(ext: String): Boolean = getCompressedTypes().contains(valueOf(ext.toUpperCase()))
    }
}