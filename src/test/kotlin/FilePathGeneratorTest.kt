import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.wycliffeassociates.sourceaudio.common.directory.upload.FilePathGenerator
import java.io.File
import java.lang.IllegalArgumentException

class FilePathGeneratorTest {

    @Test
    fun testPathCreationWithValidInput() {
        val modelList: List<FilePathTestModel> = getModelList("./src/test/resources/TestCases.json")

        for (element in modelList) {
            assertEquals(
                element.expectedResult,
                executeCreatePathFunction(element),
                "Model at index: ${modelList.indexOf(element)}"
            )
        }
    }

    @Test
    fun testPathCreationWithExceptions() {
        val modelList: List<FilePathTestModel> = getModelList("./src/test/resources/TestCasesException.json")

        for (element in modelList) {
            assertThrows<IllegalArgumentException> { executeCreatePathFunction(element) }
        }
    }

    private fun getModelList(filePath: String): List<FilePathTestModel> {
        val jsonTestFile = File(filePath)
        val mapper = jacksonObjectMapper()

        return mapper.readValue(jsonTestFile.readText())
    }

    @Throws(IllegalArgumentException::class)
    private fun executeCreatePathFunction(testModel: FilePathTestModel): String {
        return FilePathGenerator.createPathFromFile(testModel.getFileUploadModel())
    }
}