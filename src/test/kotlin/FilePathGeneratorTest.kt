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
        val testModels: List<FilePathTestModel> = getTestModelsFromFile("./src/test/resources/TestCases.json")

        for (model in testModels) {
            if(model.expectedResult.isEmpty()) {
                assertThrows<IllegalArgumentException> { FilePathGenerator.createPathFromFile(model.getFileUploadModel()) }
            } else {
                assertEquals(model.expectedResult, FilePathGenerator.createPathFromFile(model.getFileUploadModel()))
            }
        }
    }

    private fun getTestModelsFromFile(filePath: String): List<FilePathTestModel> {
        val jsonTestFile = File(filePath)
        val mapper = jacksonObjectMapper()

        return mapper.readValue(jsonTestFile.readText())
    }
}