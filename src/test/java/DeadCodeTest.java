import detecting.BaseDetectAction;
import detecting.DeadCode;

/**
 * @author: Hyeonbeen Park
 * @author: Chanho Song
 */
public class DeadCodeTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/DeadCode";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new DeadCode();
    }

    public void testDeadCode1() {
        doDetectSmellTest(1, 1);
    }

    public void testDeadCode2() {
        doDetectSmellTest(2, 1);
    }

    public void testDeadCode3() {
        doDetectSmellTest(3, 1);
    }
}
