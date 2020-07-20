package org.wycliffeassociates.sourceaudio.common.data.model

enum class ContainerExtensions(val ext: String) {
    TR("tr");

    companion object : SupportedExtensions {
        override fun isSupported(extension: String): Boolean = values().any { it.name == extension.toUpperCase() }
    }
}