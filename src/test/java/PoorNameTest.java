import detecting.BaseDetectAction;
import detecting.PoorName;

/**
 * Test for detecting: 'PoorName'
 *
 * @author Chanho Song, Gwanho Kim, Jinmin Goh
 */
public class PoorNameTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/PoorName";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new PoorName();
    }

    public void testStoryName() {
        PoorName poorName = new PoorName();
        assertEquals("Poor Name", poorName.storyName());
    }

    public void testDescription() {
        PoorName poorName = new PoorName();
        String expectedDescription = "There are variables which have too short name, sequential alphabet name or form of repeated alphabet. Detect the variables as a 'poor name' code smell.";
        assertEquals(expectedDescription, poorName.description());
    }

    public void testVariableNameLengthLessThanFourIsSmelly() {
        expectedLocations.add(13);
        doFindSmellTest(1, expectedLocations);
    }

    public void testVariableNameWithRepeatedAlphabetIsSmelly() {
        expectedLocations.add(13);
        doFindSmellTest(2, expectedLocations);
    }

    public void testVariableNameWithSequentialAlphabetIsSmelly() {
        expectedLocations.add(13);
        doFindSmellTest(3, expectedLocations);
    }

    public void testCleanVariableName() {
        doFindSmellTest(4, expectedLocations);
    }
}
