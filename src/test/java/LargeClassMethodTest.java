import detecting.BaseDetectAction;
import detecting.LargeClassMethod;

/**
 * Test for detecting: 'LargeClassMethod'
 *
 * @author Jinyoung Kim, Gwanho Kim, Jinmin Goh
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
        String expectedDescription = "There are more methods in the class than a set standard. When there are too many methods in the class, detect it as code smell large class.";
        assertEquals(expectedDescription, largeClassMethod.description());
    }

    public void testClassHasMoreThanConfigurationMethodIsSmelly() {
        expectedLocations.add(12);
        doFindSmellTest(1, expectedLocations);
    }

    public void testClassHasLessOrEqualThanConfigurationMethodIsClean() {
        doFindSmellTest(2, expectedLocations);
    }

    public void testMixedCase() {
        expectedLocations.add(13);
        expectedLocations.add(40);
        expectedLocations.add(69);
        doFindSmellTest(3, expectedLocations);
    }
}

