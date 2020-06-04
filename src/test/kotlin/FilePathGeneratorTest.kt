import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.wycliffeassociates.sourceaudio.common.directory.upload.FilePathGenerator
import java.io.File
import java.lang.IllegalArgumentException

class FilePathGeneratorTest {

    @Test
    fun testPathCreationWithValidInput() {
        val testFile = File("./src/test/resources/TestCases.csv")
        val testData = csvReader().readAll(testFile)

        for (i in 1 until testData.size) {
            assertEquals(testData[i][7], executeCreatePathFunction(testData[i]), "row ${i + 1}")
        }
    }

    @Test
    fun testPathCreationWithExceptions() {
        val testFile = File("./src/test/resources/TestCasesException.csv")
        val testData = csvReader().readAll(testFile)

        for (i in 1 until testData.size) {
            assertThrows<IllegalArgumentException> { executeCreatePathFunction(testData[i]) }
        }
    }

    @Throws(IllegalArgumentException::class)
    fun executeCreatePathFunction(testDataRow: List<String>): String {
        if (testDataRow[5].isEmpty()) {
            return FilePathGenerator.createPathFromFile(
                File(testDataRow[0]),
                testDataRow[1],
                testDataRow[2],
                testDataRow[6],
                testDataRow[3],
                testDataRow[4]
            )
        } else {
            return FilePathGenerator.createPathFromFile(
                File(testDataRow[0]),
                testDataRow[1],
                testDataRow[2],
                testDataRow[6],
                testDataRow[3],
                testDataRow[4],
                testDataRow[5]
            )
        }
    }
}