import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.lang.IllegalArgumentException

class FilePathGeneratorTest {

    @Test
    fun testPathCreationWithValidInput() {

        val testFile = File("./src/test/resources/TestCases.csv")
        val testData = csvReader().readAll(testFile)

        for (i in 1 until testData.size) {
            assertEquals(
                testData[i][7],
                FilePathGenerator.createPathFromFile(
                    File(testData[i][0]),
                    testData[i][1],
                    testData[i][2],
                    testData[i][6],
                    testData[i][3],
                    testData[i][4],
                    testData[i][5]
                ), "row $i"
            )
        }
    }

    @Test
    fun testPathCreationWithExceptions() {

        val testFile = File("./src/test/resources/TestCasesException.csv")
        val testData = csvReader().readAll(testFile)

        for (i in 1 until testData.size) {
            val ex = assertThrows<IllegalArgumentException> {
                FilePathGenerator.createPathFromFile(
                        File(testData[i][0]),
                        testData[i][1],
                        testData[i][2],
                        testData[i][6],
                        testData[i][3],
                        testData[i][4],
                        testData[i][5]
                )
            }
        }
    }
}