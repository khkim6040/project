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
        String expectedDescription = "There are more lines in the method than a set standard. When there are too many lines in the method,detect it as code smell 'long method'.";
        assertEquals(expectedDescription, longMethod.description());
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

    public void testEmptyMethodBody() {
        doFindSmellTest(4, expectedLocations);
    }

}
