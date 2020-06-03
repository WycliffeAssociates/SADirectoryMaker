import java.lang.IllegalArgumentException

class DirectoryLogic {

    private val supportedExtensions = arrayOf("wav", "mp3", "jpeg", "jpg", "tr")
    private val supportedContainers = arrayOf("tr")
    private val compressedTypes = arrayOf("mp3", "jpeg", "jpg")
    private val groupings = arrayOf("book", "chapter", "verse", "chunk")

    fun buildFullFilePath(
            inputFilePath: String,
            languageCode: String,
            dublinCoreId: String,
            group: String,
            projectId: String = "",
            mediaExtension: String = "",
            mediaQuality: String = ""
    ): String {

        validateInput(inputFilePath, languageCode, dublinCoreId, group, projectId, mediaExtension, mediaQuality)

        val fileExt = inputFilePath.split("/").last().split('.').last()

        var path = ""

        path += "$languageCode/$dublinCoreId"

        if (projectId.isNotBlank()) path += "$projectId/"

        path += "CONTENTS/$fileExt"

        if (supportedContainers.contains(fileExt)) {
            path += "$mediaExtension/"
        }

        // if the file is a compressed type
        if (compressedTypes.contains(fileExt) || compressedTypes.contains(mediaExtension)) {
            path += if (mediaQuality.isBlank()) "hi/"
            else "$mediaQuality/"
        }

        path += "$group/"
        path += getFileNameFromFullPath(inputFilePath)

        return path
    }

    @Throws(IllegalArgumentException::class)
    fun validateInput(
        inputFilePath: String,
        languageCode: String,
        dublinCoreId: String,
        group: String,
        projectId: String = "",
        mediaExtension: String = "",
        mediaQuality: String = ""
    ) {

        val fileExt = inputFilePath.split("/").last().split('.').last()

        if (languageCode.isBlank()) throw IllegalArgumentException("Language code is empty")
        if (dublinCoreId.isBlank()) throw IllegalArgumentException("Dublin Code ID is empty")

        if (group.isBlank()) throw IllegalArgumentException("Group is empty")
        if (!groupings.contains(group)) throw IllegalArgumentException("Group is not supported")

        if (supportedContainers.contains(fileExt)) {
            if (mediaExtension.isBlank()) throw IllegalArgumentException("Media Extension is empty")
            if (!supportedExtensions.contains(mediaExtension)) throw IllegalArgumentException("Media Extension is not supported")
        } else if (!supportedExtensions.contains(fileExt)) throw IllegalArgumentException(".$fileExt file is not supported")

        if(mediaQuality != "hi" && mediaQuality != "low" && !mediaQuality.isBlank()) throw IllegalArgumentException("Media Quality is invalid")

    }

    @Throws(IllegalArgumentException::class)
    fun getFileNameFromFullPath(fullPath: String): String {

        val extensionsString = supportedExtensions.joinToString(separator = "|", prefix = "(", postfix = ")")
        val pattern = Regex("[a-z,0-9,_,~,\\.,\\-,/,\\\\]*.${extensionsString}", RegexOption.IGNORE_CASE)

        if (fullPath.isBlank() || !fullPath.matches(pattern)) throw IllegalArgumentException("$fullPath is invalid")

        return fullPath.split('/').last()

    }

}
