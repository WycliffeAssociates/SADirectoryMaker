package org.wycliffeassociates.sourceaudio.common.data.model

interface SupportedExtensions {

    fun isSupported(extension: String): Boolean

}