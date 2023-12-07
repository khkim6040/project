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
        String expectedDescription = "<html>When there are too many parameters in the method<br/>" +
            " ,detect it as code smell long parameter list.</html>";
        assertEquals(expectedDescription, longParameterList.description());
    }

    public void testPrecondition() {
        LongParameterList longParameterList = new LongParameterList();
        String expectedPrecondition = "<html>There are more parameters in the method than a set standard</html>";
        assertEquals(expectedPrecondition, longParameterList.precondition());
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
