package org.wycliffeassociates.sourceaudio.directorymaker.common

import org.wycliffeassociates.sourceaudio.directorymaker.common.data.model.SupportedExtensions
import org.wycliffeassociates.sourceaudio.directorymaker.common.data.model.MediaQuality
import org.wycliffeassociates.sourceaudio.directorymaker.common.data.model.Groupings
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

        validateInput(
            inputFile,
            languageCode,
            dublinCoreId,
            grouping,
            mediaExtension,
            mediaQuality
        )

        val projectPath = if (projectId.isBlank()) "" else "$projectId/"

        var path = "$languageCode/$dublinCoreId/${projectPath}CONTENTS/${inputFile.extension}/"

        if(SupportedExtensions.isSupportedContainer(
                inputFile.extension
            )
        ) {
            path += if (SupportedExtensions.isCompressedType(
                    mediaExtension
                )
            ) "$mediaExtension/$mediaQuality/" else "$mediaExtension/"
        } else {
            if (SupportedExtensions.isCompressedType(
                    inputFile.extension
                )
            ) path += "$mediaQuality/"
        }

        path += "$grouping/${inputFile.name}"

        return path

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
        if (!Groupings.isSupportedGrouping(
                grouping
            )
        ) throw IllegalArgumentException("Group is not supported")

        validateExtensions(
            inputFile.extension,
            mediaExtension
        )

        if (!MediaQuality.isSupportedQuality(
                mediaQuality
            )
        ) throw IllegalArgumentException("Media Quality is invalid")

    }

    @Throws(IllegalArgumentException::class)
    private fun validateExtensions(fileExtension: String, mediaExtension: String) {
        if(SupportedExtensions.isSupportedContainer(
                fileExtension
            )
        ) {
            if (mediaExtension.isBlank()) throw IllegalArgumentException("Media Extension is empty")
            if (!SupportedExtensions.isSupportedExtension(
                    mediaExtension
                )
            ) throw IllegalArgumentException("Media Extension is not supported")
        } else if (!SupportedExtensions.isSupportedExtension(
                fileExtension
            )
        ) throw IllegalArgumentException(".${fileExtension} file is not supported")
    }

}
