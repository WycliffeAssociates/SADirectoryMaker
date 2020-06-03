import java.io.File
import java.lang.IllegalArgumentException

enum class SupportedExtensions(val ext: String) {
    WAV("wav"),
    MP3("mp3"),
    JPEG("jpeg"),
    JPG("jpg"),
    TR("tr");

    companion object {
        private fun getSupportedContainers(): Array<SupportedExtensions> = arrayOf(TR)
        private fun getCompressedTypes(): Array<SupportedExtensions> = arrayOf(MP3, JPEG, JPG)

        fun isSupportedExtension(ext: String): Boolean = values().any { it.name == ext.toUpperCase() }

        fun isSupportedContainer(ext: String): Boolean = getSupportedContainers().contains(valueOf(ext.toUpperCase()))
        fun isCompressedType(ext: String): Boolean = getCompressedTypes().contains(valueOf(ext.toUpperCase()))
    }
}

enum class Groupings(val grouping: String) {
    BOOK("book"),
    CHAPTER("chapter"),
    VERSE("verse"),
    CHUNK("chunk");

    companion object {
        fun isSupportedGrouping(grouping: String): Boolean = values().any { it.name == grouping.toUpperCase() }
    }
}

enum class MediaQuality(val quality: String) {
    HI("hi"),
    LOW("low");

    companion object {
        fun isSupportedQuality(quality: String): Boolean = values().any { it.name == quality.toUpperCase() }
    }
}

object FilePathGenerator {

    fun createPathFromFile(
        inputFile: File,
        languageCode: String,
        dublinCoreId: String,
        grouping: String,
        projectId: String = "",
        mediaExtension: String = "",
        mediaQuality: String = ""
    ): String {

        validateInput(inputFile, languageCode, dublinCoreId, grouping, mediaExtension, mediaQuality)

        val projectPath = if (projectId.isBlank()) "" else "$projectId/"
        val compressionQuality = if (mediaQuality.isBlank()) "hi" else mediaQuality

        var path = "$languageCode/$dublinCoreId/${projectPath}CONTENTS/${inputFile.extension}/"

        if(SupportedExtensions.isSupportedContainer(inputFile.extension)) {
            path += if (SupportedExtensions.isCompressedType(mediaExtension)) "$mediaExtension/$compressionQuality/" else "$mediaExtension/"
        } else {
            if (SupportedExtensions.isCompressedType(inputFile.extension)) path += "$compressionQuality/"
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
        if (!Groupings.isSupportedGrouping(grouping)) throw IllegalArgumentException("Group is not supported")

        validateExtensions(inputFile.extension, mediaExtension)

        if (!MediaQuality.isSupportedQuality(mediaQuality) && mediaQuality.isNotBlank()) throw IllegalArgumentException("Media Quality is invalid")

    }

    @Throws(IllegalArgumentException::class)
    private fun validateExtensions(fileExtension: String, mediaExtension: String) {
        if(SupportedExtensions.isSupportedContainer(fileExtension)) {
            if (mediaExtension.isBlank()) throw IllegalArgumentException("Media Extension is empty")
            if (!SupportedExtensions.isSupportedExtension(mediaExtension)) throw IllegalArgumentException("Media Extension is not supported")
        } else if (!SupportedExtensions.isSupportedExtension(fileExtension)) throw IllegalArgumentException(".${fileExtension} file is not supported")
    }

}
