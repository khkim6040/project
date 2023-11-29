import detecting.BaseDetectAction;
import detecting.DetectLargeClassMethod;

/**
 * Test for detecting large class due to methods
 *
 * @author Jinyoung Kim
 */

public class DetectLargeClassMethodTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/DetectLargeClassMethod";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new DetectLargeClassMethod();
    }

    public void testDetectLargeClassMethod1() {
        doDetectSmellTest(1, 1);
    }
}

