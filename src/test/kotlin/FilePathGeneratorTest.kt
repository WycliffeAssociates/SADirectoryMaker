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
                FilePathGenerator.createPathFromFile(element.getFileUploadModel()),
                "Model at index: ${modelList.indexOf(element)}"
            )
        }
    }

    @Test
    fun testPathCreationWithExceptions() {
        val modelList: List<FilePathTestModel> = getModelList("./src/test/resources/TestCasesException.json")

        for (element in modelList) {
            assertThrows<IllegalArgumentException> {
                FilePathGenerator.createPathFromFile(element.getFileUploadModel())
            }
        }
    }

    private fun getModelList(filePath: String): List<FilePathTestModel> {
        val jsonTestFile = File(filePath)
        val mapper = jacksonObjectMapper()

        return mapper.readValue(jsonTestFile.readText())
    }
}