package org.wycliffeassociates.sourceaudio.common.directory.upload

import org.wycliffeassociates.sourceaudio.common.data.model.*
import java.lang.IllegalArgumentException

object FilePathGenerator {

    @Throws(IllegalArgumentException::class)
    fun createPathFromFile(fileModel: FileUploadModel): String {
        val pathPrefix = getPathPrefix(
            fileModel.languageCode,
            fileModel.dublinCoreId,
            fileModel.projectId,
            fileModel.extension
        )

        val isContainer = ContainerExtensions.isSupported(fileModel.extension)
        val isContainerAndCompressed = isContainer && CompressedExtensions.isSupported(fileModel.mediaExtension)
        val isFileAndCompressed = !isContainer && CompressedExtensions.isSupported(fileModel.extension)

        return when {
            isContainerAndCompressed -> {
                "$pathPrefix/${fileModel.mediaExtension}/${fileModel.mediaQuality}/${fileModel.grouping}/${fileModel.name}"
            }
            isContainer -> {
                "$pathPrefix/${fileModel.mediaExtension}/${fileModel.grouping}/${fileModel.name}"
            }
            isFileAndCompressed -> {
                "$pathPrefix/${fileModel.mediaQuality}/${fileModel.grouping}/${fileModel.name}"
            }
            else -> {
                "$pathPrefix/${fileModel.grouping}/${fileModel.name}"
            }
        }
    }

    private fun getPathPrefix(
        languageCode: String,
        dublinCoreId: String,
        projectId: String,
        inputFileExtension: String
    ): String {
        return when {
            projectId.isBlank() -> "$languageCode/$dublinCoreId/CONTENTS/$inputFileExtension"
            else -> "$languageCode/$dublinCoreId/${projectId}/CONTENTS/$inputFileExtension"
        }
    }
}
