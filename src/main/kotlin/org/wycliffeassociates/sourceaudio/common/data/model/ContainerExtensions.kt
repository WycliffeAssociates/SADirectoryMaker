package org.wycliffeassociates.sourceaudio.common.data.model

enum class ContainerExtensions(ext: String) {
    TR("tr");

    companion object: SupportedExtensions {
        override fun isSupported(ext: String): Boolean = values().any { it.name == ext.toUpperCase() }
    }
}