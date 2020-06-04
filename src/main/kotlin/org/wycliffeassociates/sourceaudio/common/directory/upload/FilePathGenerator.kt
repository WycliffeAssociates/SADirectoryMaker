package org.wycliffeassociates.sourceaudio.common.directory.upload

import org.wycliffeassociates.sourceaudio.common.data.model.*
import java.io.File
import java.lang.IllegalArgumentException

object FilePathGenerator {

    fun createPathFromFile(
        inputFile: File,
        languageCode: String,
        dublinCoreId: String,
        grouping: String,
        projectId: String = "",
        mediaExtension: String = "",
        mediaQuality: String = "hi"
    ): String {
        validateInput(inputFile, languageCode, dublinCoreId, grouping, mediaExtension, mediaQuality)

        val project = if (projectId.isBlank()) "" else "$projectId/"

        val isContainer = ContainerExtensions.isSupported(inputFile.extension)
        val isContainerAndCompressed = isContainer && CompressedExtensions.isSupported(mediaExtension)
        val isFileAndCompressed = !isContainer && CompressedExtensions.isSupported(inputFile.extension)
        return when {
            isContainerAndCompressed -> "$languageCode/$dublinCoreId/${project}CONTENTS/${inputFile.extension}/$mediaExtension/$mediaQuality/$grouping/${inputFile.name}"
            isContainer -> "$languageCode/$dublinCoreId/${project}CONTENTS/${inputFile.extension}/$mediaExtension/$grouping/${inputFile.name}"
            isFileAndCompressed -> "$languageCode/$dublinCoreId/${project}CONTENTS/${inputFile.extension}/$mediaQuality/$grouping/${inputFile.name}"
            else -> "$languageCode/$dublinCoreId/${project}CONTENTS/${inputFile.extension}/$grouping/${inputFile.name}"
        }
    }

    @Throws(IllegalArgumentException::class)
    fun validateInput(
        inputFile: File,
        languageCode: String,
        dublinCoreId: String,
        grouping: String,
        mediaExtension: String = "",
        mediaQuality: String = ""
    ) {
        if (languageCode.isBlank()) throw IllegalArgumentException("Language Code is empty")
        if (dublinCoreId.isBlank()) throw IllegalArgumentException("Dublin Core ID is empty")
        if (grouping.isBlank()) throw IllegalArgumentException("Group is empty")
        if (!Groupings.isSupported(grouping)) {
            throw IllegalArgumentException("Group is not supported")
        }
        if (!MediaQuality.isSupported(mediaQuality)) {
            throw IllegalArgumentException("Media Quality is invalid")
        }
        validateExtensions(inputFile.extension, mediaExtension)
    }

    @Throws(IllegalArgumentException::class)
    private fun validateExtensions(fileExtension: String, mediaExtension: String) {
        if (ContainerExtensions.isSupported(fileExtension)) {
            if (mediaExtension.isBlank()) {
                throw IllegalArgumentException("Media Extension is empty")
            }
            if (!CompressedExtensions.isSupported(mediaExtension) && !UncompressedExtensions.isSupported(mediaExtension)) {
                throw IllegalArgumentException("Media Extension is not supported")
            }
        } else if (!CompressedExtensions.isSupported(fileExtension) && !UncompressedExtensions.isSupported(fileExtension)) {
            throw IllegalArgumentException(".${fileExtension} file is not supported")
        }
    }
}
