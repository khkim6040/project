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
        doDetectSmellTest(1, 3);
    }
}
