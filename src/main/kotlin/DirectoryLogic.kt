import java.io.File
import java.lang.IllegalArgumentException

class DirectoryLogic {

    private val supportedExtensions = arrayOf("wav", "mp3", "jpeg", "jpg", "tr")
    private val supportedContainers = arrayOf("tr")
    private val compressedTypes = arrayOf("mp3", "jpeg", "jpg")
    private val groupings = arrayOf("book", "chapter", "verse", "chunk")

    fun buildFullFilePath(
        inputFile: File,
        languageCode: String,
        dublinCoreId: String,
        group: String,
        projectId: String = "",
        mediaExtension: String = "",
        mediaQuality: String = ""
    ): String {

        validateInput(inputFile, languageCode, dublinCoreId, group, mediaExtension, mediaQuality)

        val projectPath = if (projectId.isBlank()) "" else "$projectId/"
        val compressionQuality = if (mediaQuality.isBlank()) "hi" else mediaQuality

        var path = "$languageCode/$dublinCoreId/${projectPath}CONTENTS/${inputFile.extension}/"

        if (supportedContainers.contains(inputFile.extension)) {
            path += if (compressedTypes.contains(mediaExtension)) "$mediaExtension/$compressionQuality/" else "$mediaExtension/"
        } else {
            if (compressedTypes.contains(inputFile.extension)) path += "$compressionQuality/"
        }

        path += "$group/${inputFile.name}"

        return path

    }

    @Throws(IllegalArgumentException::class)
    fun validateInput(
        inputFile: File,
        languageCode: String,
        dublinCoreId: String,
        group: String,
        mediaExtension: String = "",
        mediaQuality: String = ""
    ) {

        if (languageCode.isBlank()) throw IllegalArgumentException("Language Code is empty")
        if (dublinCoreId.isBlank()) throw IllegalArgumentException("Dublin Core ID is empty")

        if (group.isBlank()) throw IllegalArgumentException("Group is empty")
        if (!groupings.contains(group)) throw IllegalArgumentException("Group is not supported")

        validateExtensions(inputFile.extension, mediaExtension)

        if (mediaQuality != "hi" && mediaQuality != "low" && mediaQuality.isNotBlank()) throw IllegalArgumentException("Media Quality is invalid")

    }

    @Throws(IllegalArgumentException::class)
    private fun validateExtensions(fileExtension: String, mediaExtension: String) {
        if (supportedContainers.contains(fileExtension)) {
            if (mediaExtension.isBlank()) throw IllegalArgumentException("Media Extension is empty")
            if (!supportedExtensions.contains(mediaExtension)) throw IllegalArgumentException("Media Extension is not supported")
        } else if (!supportedExtensions.contains(fileExtension)) throw IllegalArgumentException(".${fileExtension} file is not supported")
    }

}
