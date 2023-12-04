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
        doDetectSmellTest(1, 2);
    }

    public void testDuplicatedCode2() {
        doDetectSmellTest(2, 2);
    }

    public void testDuplicatedCode3() {
        doDetectSmellTest(3, 3);
    }

    public void testDuplicatedCode4() {
        doDetectSmellTest(4, 0);
    }
}
