import detecting.BaseDetectAction;
import detecting.DetectLargeClass;

/**
 * Test for detecting large class'
 *
 * @author Jinyoung Kim
 */

public class DetectLargeClassTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/DetectLargeClass";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new DetectLargeClass();
    }

    public void testDetectLargeClass1() {
        doDetectSmellTest(1, 1);
    }
}
