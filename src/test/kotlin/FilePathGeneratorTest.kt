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
        val testModels: List<FilePathTestModel> = getTestModelsFromFile("./src/test/resources/TestCases.json")

        for (model in testModels) {
            assertEquals(
                model.expectedResult,
                FilePathGenerator.createPathFromFile(model.getFileUploadModel()),
                "Model at index: ${testModels.indexOf(model)}"
            )
        }
    }

    @Test
    fun testPathCreationWithExceptions() {
        val modelList: List<FilePathTestModel> = getTestModelsFromFile("./src/test/resources/TestCasesException.json")

        for (model in modelList) {
            assertThrows<IllegalArgumentException> {
                FilePathGenerator.createPathFromFile(model.getFileUploadModel())
            }
        }
    }

    private fun getTestModelsFromFile(filePath: String): List<FilePathTestModel> {
        val jsonTestFile = File(filePath)
        val mapper = jacksonObjectMapper()

        return mapper.readValue(jsonTestFile.readText())
    }
}