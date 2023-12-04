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

    public void testStoryName() {
        LongParameterList longParameterList = new LongParameterList();
        assertEquals("Long Parameter List", longParameterList.storyName());
    }

    public void testDescription() {
        LongParameterList longParameterList = new LongParameterList();
        String expectedDescription = "<html>When there are too many parameters in the method<br/>" +
            " ,detect it as code smell long parameter list.</html>";
        assertEquals(expectedDescription, longParameterList.description());
    }

    public void testPrecondition() {
        LongParameterList longParameterList = new LongParameterList();
        String expectedPrecondition = "<html>There are more parameters in the method than a set standard</html>";
        assertEquals(expectedPrecondition, longParameterList.precondition());
    }

    public void testLongParameterList1() {
        doDetectSmellTest(1, 1);
    }

    public void testLongParameterList2() {
        doDetectSmellTest(2, 0);
    }

    public void testLongParameterList3() {
        doDetectSmellTest(3, 3);
    }
}
