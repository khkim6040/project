import detecting.BaseDetectAction;
import detecting.FindDuplicatedCode;

/**
 * Test for detecting: 'Duplicated code'
 *
 * @author Chanho Song
 */
public class FindDuplicatedCodeTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/FindDuplicatedCode";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new FindDuplicatedCode();
    }

    public void testFindDuplicatedCode1() {
        doDetectSmellTest(1, 1);
    }
}
