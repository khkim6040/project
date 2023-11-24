import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;

/**
 * Abstract class to implement light test through files.
 *
 * @author Gwanho Kim
 */
public abstract class SmellDetectorTest extends LightJavaCodeInsightFixtureTestCase {

    @Override
    protected String getTestDataPath() {
        return "src/test/";
    }

    @Override
    protected String getBasePath() {
        return "testData";
    }

    /**
     * Test detectSmell() and check the result
     *
     * @param testNum       the number of test cases
     * @param expectedCount the number of expected smells
     */
    protected abstract void doDetectSmellTest(int testNum, int expectedCount);
}