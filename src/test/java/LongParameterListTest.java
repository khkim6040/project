import detecting.BaseDetectAction;
import detecting.LongParameterList;

/**
 * Test for detecting: 'LongParameterList'
 *
 * @author Jinyoung Kim, Gwanho Kim, Jinmin Goh
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
        String expectedDescription = "There are more parameters in a method than a set standard. When there are too many parameters in the method, detect it as code smell 'long parameter list'.";
        assertEquals(expectedDescription, longParameterList.description());
    }

    public void testMethodHasMoreThanConfigurationParameterIsSmelly() {
        expectedLocations.add(21);
        doFindSmellTest(1, expectedLocations);
    }

    public void testMethodHasLessOrEqualThanConfigurationParameterIsSmelly() {
        doFindSmellTest(2, expectedLocations);
    }

    public void testMixedCase() {
        expectedLocations.add(27);
        expectedLocations.add(32);
        expectedLocations.add(37);
        doFindSmellTest(3, expectedLocations);
    }
}
