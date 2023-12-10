import detecting.BaseDetectAction;
import detecting.DeadCode;

/**
 * Test for detecting: 'DeadCode'
 *
 * @author: Hyeonbeen Park, Chanho Song, Gwanho Kim, Jinmin Goh
 */
public class DeadCodeTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/DeadCode";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new DeadCode();
    }

    public void testStoryName() {
        DeadCode deadCode = new DeadCode();
        assertEquals("Dead Code", deadCode.storyName());
    }

    public void testDescription() {
        DeadCode deadCode = new DeadCode();
        String expectedDescription = "There are variables and method that is declared but not used. They are code smell 'deadcode'.";
        assertEquals(expectedDescription, deadCode.description());
    }

    public void testUnusedSingleVariableIsSmelly() {
        expectedLocations.add(8);
        doFindSmellTest(1, expectedLocations);
    }

    public void testUnusedVariableInMethodIsSmelly() {
        expectedLocations.add(15);
        doFindSmellTest(2, expectedLocations);
    }

    public void testUnusedMethodIsSmelly() {
        expectedLocations.add(18);
        doFindSmellTest(3, expectedLocations);
    }

    public void testUsedMethodIsClean() {
        doFindSmellTest(4, expectedLocations);
    }

    public void testMixedCase() {
        expectedLocations.add(18);
        expectedLocations.add(22);
        expectedLocations.add(23);
        doFindSmellTest(5, expectedLocations);
    }
}
