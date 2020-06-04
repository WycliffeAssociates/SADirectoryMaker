package org.wycliffeassociates.sourceaudio.common.data.model

enum class SupportedExtensions(ext: String) {
    WAV("wav"),
    MP3("mp3"),
    JPEG("jpeg"),
    JPG("jpg"),
    TR("tr");

    companion object {
        private fun getSupportedContainers(): Array<SupportedExtensions> = arrayOf(
            TR
        )
        private fun getCompressedTypes(): Array<SupportedExtensions> = arrayOf(
            MP3,
            JPEG,
            JPG
        )

        fun isSupportedExtension(ext: String): Boolean = values().any { it.name == ext.toUpperCase() }

        fun isSupportedContainer(ext: String): Boolean = getSupportedContainers()
            .contains(valueOf(ext.toUpperCase()))
        fun isCompressedType(ext: String): Boolean = getCompressedTypes()
            .contains(valueOf(ext.toUpperCase()))
    }
}