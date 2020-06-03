import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.lang.IllegalArgumentException

class DirectoryLogicTester {

    private val directoryLogic = DirectoryLogic()

    @Test
    fun testBuildFullFilePath() {

        var testFile: File = File("/home/dj/IdeaProjects/SADirectoryMaker/src/test/resources/TestCases.csv")
        var testData = csvReader().readAll(testFile)

        for (i in 1 until testData.size) {
            assertEquals(
                    testData[i][7],
                    directoryLogic.buildFullFilePath(
                            testData[i][0],
                            testData[i][1],
                            testData[i][2],
                            testData[i][6],
                            testData[i][3],
                            testData[i][4],
                            testData[i][5]
                    ), "row $i"
            )
        }

        testFile = File("/home/dj/IdeaProjects/SADirectoryMaker/src/test/resources/TestCasesException.csv")
        testData = csvReader().readAll(testFile)

        for (i in 1 until testData.size) {
            val ex = assertThrows<IllegalArgumentException> {
                directoryLogic.buildFullFilePath(
                        testData[i][0],
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