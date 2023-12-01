import detecting.BaseDetectAction;
import detecting.LargeClassField;

/**
 * Test for detecting large class due to fields
 *
 * @author Jinyoung Kim
 */

public class LargeClassFieldTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/LargeClassField";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new LargeClassField();
    }

    public void testLargeClassField1() {
        doDetectSmellTest(1, 1);
    }
}

