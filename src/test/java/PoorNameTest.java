import detecting.BaseDetectAction;
import detecting.PoorName;

/**
 * Test for detecting: 'Poor name'
 *
 * @author Chanho Song
 */
public class PoorNameTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/PoorName";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new PoorName();
    }

    public void testPoorName1() {
        doDetectSmellTest(1, 1);
    }

    public void testPoorName2() {
        doDetectSmellTest(2, 1);
    }

    public void testPoorName3() {
        doDetectSmellTest(3, 1);
    }

    public void testPoorName4() {
        doDetectSmellTest(4, 0);
    }
}
