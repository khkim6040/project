import detecting.BaseDetectAction;
import detecting.LargeClassMethod;

/**
 * Test for detecting large class due to methods
 *
 * @author Jinyoung Kim
 */

public class LargeClassMethodTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/LargeClassMethod";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new LargeClassMethod();
    }

    public void testLargeClassMethod1() {
        doDetectSmellTest(1, 1);
    }
}

