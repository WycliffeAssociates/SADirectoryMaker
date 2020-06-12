import org.wycliffeassociates.sourceaudio.common.data.model.FileUploadModel
import java.io.File

data class FilePathTestModel(
    val fileName: String,
    val languageCode: String,
    val resourceType: String,
    val grouping: String,
    val expectedResult: String,
    val projectId: String = "",
    val mediaExtension: String = "",
    val mediaQuality: String = "hi"
) {
    private val inputFile: File = File(fileName)

    fun getFileUploadModel(): FileUploadModel {
        return FileUploadModel(inputFile, languageCode, resourceType, grouping, projectId, mediaExtension, mediaQuality)
    }
}