import org.wycliffeassociates.sourceaudio.common.data.model.FileUploadModel
import java.io.File

data class FilePathTestModel(
    val fileName: String,
    val languageCode: String,
    val dublinCoreId: String,
    val grouping: String,
    val expectedResult: String,
    val projectId: String = "",
    val mediaExtension: String = "",
    val mediaQuality: String = "hi"
) {
    private val inputFile: File = File(fileName)

    fun getFileUploadModel(): FileUploadModel {
        return if(mediaQuality.isEmpty()) {
            FileUploadModel(inputFile, languageCode, dublinCoreId, grouping, projectId, mediaExtension)
        } else {
            FileUploadModel(inputFile, languageCode, dublinCoreId, grouping, projectId, mediaExtension, mediaQuality)
        }
    }
}