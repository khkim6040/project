import detecting.BaseDetectAction;
import detecting.LongMethod;

/**
 * Test for detecting: 'LongMethod'
 *
 * @author Jinyoung Kim
 */
public class LongMethodTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/LongMethod";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new LongMethod();
    }

    public void testLongMethod1() {
        doDetectSmellTest(1, 1);
    }
}
