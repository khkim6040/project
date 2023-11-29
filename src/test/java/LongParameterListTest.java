import detecting.BaseDetectAction;
import detecting.LongParameterList;

/**
 * Test for detecting: 'Long parameter list'
 *
 * @author Jinyoung Kim
 */
public class LongParameterListTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/LongParameterList";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new LongParameterList();
    }


    public void testLongParameterList1() {
        doDetectSmellTest(1, 1);
    }
}
