package org.wycliffeassociates.sourceaudio.common.data.model

interface SupportedExtensions {

    fun isSupported(ext: String): Boolean

}