package org.wycliffeassociates.sourceaudio.common.directory.upload

import org.wycliffeassociates.sourceaudio.common.data.model.SupportedExtensions
import org.wycliffeassociates.sourceaudio.common.data.model.MediaQuality
import org.wycliffeassociates.sourceaudio.common.data.model.Groupings
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

        val isContainer = SupportedExtensions.isSupportedContainer(inputFile.extension)
        val isContainerAndCompressed = isContainer && SupportedExtensions.isCompressedType(mediaExtension)
        val isFileAndCompressed = !isContainer && SupportedExtensions.isCompressedType(inputFile.extension)

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

        if (!Groupings.isSupportedGrouping(grouping)) throw IllegalArgumentException("Group is not supported")
        if (!MediaQuality.isSupportedQuality(mediaQuality)) throw IllegalArgumentException("Media Quality is invalid")

        validateExtensions(inputFile.extension, mediaExtension)
    }

    @Throws(IllegalArgumentException::class)
    private fun validateExtensions(fileExtension: String, mediaExtension: String) {
        if (SupportedExtensions.isSupportedContainer(fileExtension)) {
            if (mediaExtension.isBlank()) throw IllegalArgumentException("Media Extension is empty")
            if (!SupportedExtensions.isSupportedExtension(mediaExtension)) throw IllegalArgumentException("Media Extension is not supported")
        } else if (!SupportedExtensions.isSupportedExtension(fileExtension)) throw IllegalArgumentException(".${fileExtension} file is not supported")
    }

}
