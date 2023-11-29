import detecting.BaseDetectAction;
import detecting.SwitchStatement;

public class SwitchStatementTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/SwitchStatement";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new SwitchStatement();
    }
    
    public void testSwitchStatement1() {
        doDetectSmellTest(1, 1);
    }
}
