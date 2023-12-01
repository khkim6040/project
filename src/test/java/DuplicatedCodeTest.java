import detecting.BaseDetectAction;
import detecting.DuplicatedCode;

/**
 * Test for detecting: 'Duplicated code'
 *
 * @author Chanho Song
 */
public class DuplicatedCodeTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/DuplicatedCode";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new DuplicatedCode();
    }

    public void testDuplicatedCode1() {
        doDetectSmellTest(1, 1);
    }
}
