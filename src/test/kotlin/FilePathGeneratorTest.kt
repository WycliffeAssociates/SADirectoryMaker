import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.Test
import org.junit.internal.runners.statements.ExpectException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.wycliffeassociates.sourceaudio.common.data.model.FileUploadModel
import org.wycliffeassociates.sourceaudio.common.directory.upload.FilePathGenerator
import java.io.File
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class FilePathGeneratorTest {

    @Test
    fun testPathCreation() {
        val testModels: List<FilePathTestModel> = getTestModels()

        for (model in testModels) {
            if(model.expectedResult.isEmpty()) {
                testPathCreationWithException(model)
            } else {
                testPathCreationWithValidInput(model)
            }
        }
    }

    private fun testPathCreationWithException(testModel: FilePathTestModel) {
        assertThrows<IllegalArgumentException> {
            FilePathGenerator.createPathFromFile(testModel.getFileUploadModel())
        }
    }

    private fun testPathCreationWithValidInput(testModel: FilePathTestModel) {
        assertEquals(
            testModel.expectedResult,
            FilePathGenerator.createPathFromFile(testModel.getFileUploadModel())
        )
    }

    private fun getTestModels(): List<FilePathTestModel> {
        val jsonTestFile = File("./src/test/resources/TestCases.json")
        val mapper = jacksonObjectMapper()

        return mapper.readValue(jsonTestFile.readText())
    }
}