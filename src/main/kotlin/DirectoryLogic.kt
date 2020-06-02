import java.lang.IllegalArgumentException

class DirectoryLogic {

    private val supportedExtensions = arrayOf("wav", "mp3", "jpeg", "tr")

    @Throws(IllegalArgumentException::class)
    fun buildFullFilePath(
        inputFilePath: String,
        languageCode: String,
        dublinCoreId: String,
        group: String,
        projectId: String = "",
        mediaExtension: String = "",
        mediaQuality: String = "hi"
    ): String {

        var path = ""

        if(languageCode.isBlank()) throw IllegalArgumentException("Language code is empty")
        path += "$languageCode/"

        if(dublinCoreId.isBlank()) throw IllegalArgumentException("Dublin Code ID is empty")
        path += "$dublinCoreId/"

        

        return ""

    }

    @Throws(IllegalArgumentException::class)
    fun getFileNameFromFullPath(fullPath: String): String {

        val extensionsString = supportedExtensions.joinToString(separator = "|", prefix = "(", postfix = ")")
        val pattern = Regex("[a-z,0-9,_,~,\\.,\\-,/,\\\\]*.${extensionsString}", RegexOption.IGNORE_CASE)

        if (fullPath.isBlank() || !fullPath.matches(pattern)) throw IllegalArgumentException("$fullPath is invalid")

        return fullPath.split('/').last()

    }

}
