import detecting.BaseDetectAction;
import detecting.DetectLargeClassField;

/**
 * Test for detecting large class due to fields
 *
 * @author Jinyoung Kim
 */

public class DetectLargeClassFieldTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/DetectLargeClassField";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new DetectLargeClassField();
    }

    public void testDetectLargeClassField1() {
        doDetectSmellTest(1, 1);
    }
}

