import detecting.BaseDetectAction;
import detecting.DeadCode;

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
}
