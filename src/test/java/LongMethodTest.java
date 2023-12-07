import detecting.BaseDetectAction;
import detecting.LongMethod;

/**
 * Test for detecting: 'LongMethod'
 *
 * @author Jinyoung Kim, Gwanho Kim, Jinmin Goh
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

    public void testMethodHasMoreThanConfigurationLineIsSmelly() {
        expectedLocations.add(14);
        doFindSmellTest(1, expectedLocations);
    }

    public void testMethodHasLessOrEqualThanConfigurationLineIsClean() {
        doFindSmellTest(2, expectedLocations);
    }

    public void testMixedCase() {
        expectedLocations.add(14);
        expectedLocations.add(51);
        expectedLocations.add(95);
        doFindSmellTest(3, expectedLocations);
    }

}
