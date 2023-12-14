import detecting.BaseDetectAction;
import detecting.SwitchStatement;

/**
 * Test for detecting: 'SwitchStatement
 *
 * @author Hyeonbeen Park, Gwanho Kim, Jinmin Goh
 */
public class SwitchStatementTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/SwitchStatement";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new SwitchStatement();
    }

    public void testStoryName() {
        SwitchStatement switchStatement = new SwitchStatement();
        assertEquals("Switch Statement", switchStatement.storyName());
    }

    public void testDescription() {
        SwitchStatement switchStatement = new SwitchStatement();
        String expectedDescription =
            "There are multiple type casting in a conditional statement. If type casting occurs multiple times for one object in conditional statement, detect it as a 'switch statement' code smell.";
        assertEquals(expectedDescription, switchStatement.description());
    }

    public void testTypeCastingInIfStatementIsSmelly() {
        expectedLocations.add(9);
        doFindSmellTest(1, expectedLocations);
    }

    public void testTypeCastingInContinuedIfStatementIsSmelly() {
        expectedLocations.add(30);
        doFindSmellTest(2, expectedLocations);
    }

    public void testTypeCastingInTwoIfStatementIsSmelly() {
        expectedLocations.add(30);
        expectedLocations.add(45);
        doFindSmellTest(3, expectedLocations);
    }

    public void testTypeCastingInSwitchStatementIsSmelly() {
        expectedLocations.add(52);
        doFindSmellTest(4, expectedLocations);
    }

    public void testCleanSwitchStatement() {
        doFindSmellTest(5, expectedLocations);
    }

    public void testTypeCastingButNotMultiCasting() {
        doFindSmellTest(6, expectedLocations);
    }

}
