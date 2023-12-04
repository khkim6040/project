import detecting.BaseDetectAction;
import detecting.LongMethod;

/**
 * Test for detecting: 'LongMethod'
 *
 * @author Jinyoung Kim
 */
public class LongMethodTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/LongMethod";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new LongMethod();
    }

    public void testStoryName() {
        LongMethod longMethod = new LongMethod();
        assertEquals("Long Method", longMethod.storyName());
    }

    public void testDescription() {
        LongMethod longMethod = new LongMethod();
        String expectedDescription = "<html>When there are too many lines in the method<br/>" +
            " ,detect it as code smell long method.</html>";
        assertEquals(expectedDescription, longMethod.description());
    }

    public void testPrecondition() {
        LongMethod longMethod = new LongMethod();
        String expectedPrecondition = "<html>There are more lines in the method than a set standard</html>";
        assertEquals(expectedPrecondition, longMethod.precondition());
    }

    public void testLongMethod1() {
        doDetectSmellTest(1, 1);
    }

    public void testLongMethod2() {
        doDetectSmellTest(2, 0);
    }

    public void testLongMethod3() {
        doDetectSmellTest(3, 3);
    }

}
