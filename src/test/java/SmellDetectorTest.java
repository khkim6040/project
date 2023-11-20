import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;

public abstract class SmellDetectorTest extends LightJavaCodeInsightFixtureTestCase {

    @Override
    protected String getTestDataPath() {
        return "src/test/";
    }

    @Override
    protected String getBasePath() {
        return "testData";
    }

    protected abstract void doDetectSmellTest(int testNum, boolean expected);
}