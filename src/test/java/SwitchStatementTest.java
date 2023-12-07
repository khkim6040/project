import detecting.BaseDetectAction;
import detecting.SwitchStatement;

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
            "<html>There are conditional statements that identify class of object that leads to " +
                "casting of the object to use method of the class</html>";
        assertEquals(expectedDescription, switchStatement.description());
    }

    public void testPrecondition() {
        SwitchStatement switchStatement = new SwitchStatement();
        String expectedPrecondition = "<html>instanceof in if statement and multiple casting of object dependent to condition</html>";
        assertEquals(expectedPrecondition, switchStatement.precondition());
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

}
