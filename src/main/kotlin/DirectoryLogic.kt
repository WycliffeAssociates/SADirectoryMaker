import java.lang.IllegalArgumentException

class DirectoryLogic {

    private val supportedExtensions = arrayOf("wav", "mp3", "jpeg", "tr")
    private val supportedContainers = arrayOf("tr")
    private val compressedTypes = arrayOf("mp3", "jpeg")
    private val groupings = arrayOf("book", "chapter", "verse", "chunk")

    @Throws(IllegalArgumentException::class)
    fun buildFullFilePath(
        inputFilePath: String,
        languageCode: String,
        dublinCoreId: String,
        group: String,
        projectId: String = "",
        mediaExtension: String = "",
        mediaQuality: String = ""
    ): String {

        val fileExt = inputFilePath.split("/").last().split('.').last()

        var path = ""

        if(languageCode.isBlank()) throw IllegalArgumentException("Language code is empty")
        path += "$languageCode/"

        if(dublinCoreId.isBlank()) throw IllegalArgumentException("Dublin Code ID is empty")
        path += "$dublinCoreId/"

        if(!projectId.isBlank()) path += "$projectId/"
        path += "CONTENTS/"

        path += "$fileExt/"
        if(supportedContainers.contains(fileExt)) {
            if(mediaExtension.isBlank()) throw IllegalArgumentException("Media Extension is empty")
            if(!supportedExtensions.contains(mediaExtension)) throw IllegalArgumentException("Media Extension is not supported")
            path += "$mediaExtension/"
        }

        // if the file is a compressed type
        if(compressedTypes.contains(fileExt) || compressedTypes.contains(mediaExtension)) {
            if(mediaQuality.isBlank()) path += "hi/"
            else if(mediaQuality == "hi" || mediaQuality == "low") path += "$mediaQuality/"
            else throw IllegalArgumentException("Media Quality is invalid")
        }

        if(!groupings.contains(group)) throw IllegalArgumentException("Group is not supported")
        path += "$group/"

        return path

    }

    @Throws(IllegalArgumentException::class)
    fun getFileNameFromFullPath(fullPath: String): String {

        val extensionsString = supportedExtensions.joinToString(separator = "|", prefix = "(", postfix = ")")
        val pattern = Regex("[a-z,0-9,_,~,\\.,\\-,/,\\\\]*.${extensionsString}", RegexOption.IGNORE_CASE)

        if (fullPath.isBlank() || !fullPath.matches(pattern)) throw IllegalArgumentException("$fullPath is invalid")

        return fullPath.split('/').last()

    }

}
