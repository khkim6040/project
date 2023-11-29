import detecting.BaseDetectAction;
import detecting.IdentifyLongMethod;

/**
 * Test for detecting: 'LongMethod'
 *
 * @author Jinyoung Kim
 */
public class IdentifyLongMethodTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/IdentifyLongMethod";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new IdentifyLongMethod();
    }

    public void testIdentifyLongMethod1() {
        doDetectSmellTest(1, 1);
    }
}
