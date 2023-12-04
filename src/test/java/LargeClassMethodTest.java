import detecting.BaseDetectAction;
import detecting.LargeClassMethod;

/**
 * Test for detecting large class due to methods
 *
 * @author Jinyoung Kim
 */

public class LargeClassMethodTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/LargeClassMethod";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new LargeClassMethod();
    }

    public void testStoryName() {
        LargeClassMethod largeClassMethod = new LargeClassMethod();
        assertEquals("Large Class based on number of methods", largeClassMethod.storyName());
    }

    public void testDescription() {
        LargeClassMethod largeClassMethod = new LargeClassMethod();
        String expectedDescription = "<html>When there are too many methods in the class<br/>" +
            " ,detect it as code smell large class.</html>";
        assertEquals(expectedDescription, largeClassMethod.description());
    }

    public void testPrecondition() {
        LargeClassMethod largeClassMethod = new LargeClassMethod();
        String expectedPrecondition = "<html>There are more methods in the class than a set standard</html>";
        assertEquals(expectedPrecondition, largeClassMethod.precondition());
    }

    public void testLargeClassMethod1() {
        doDetectSmellTest(1, 1);
    }

    public void testLargeClassMethod2() {
        doDetectSmellTest(2, 0);
    }

    public void testLargeClassMethod3() {
        doDetectSmellTest(3, 3);
    }
}

