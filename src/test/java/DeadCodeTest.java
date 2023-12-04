import detecting.BaseDetectAction;
import detecting.DeadCode;

/**
 * @author: Hyeonbeen Park
 * @author: Chanho Song
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
        assertEquals("DeadCode", deadCode.storyName());
    }

    public void testDescription() {
        DeadCode deadCode = new DeadCode();
        String expectedDescription = "<html>When variable or method is not used<br/>" +
            " ,detect it as code smell deadcode.</html>";
        assertEquals(expectedDescription, deadCode.description());
    }
    
    public void testPrecondition() {
        DeadCode deadCode = new DeadCode();
        String expectedPrecondition = "<html>There are variables and method that is declared but not used</html>";
        assertEquals(expectedPrecondition, deadCode.precondition());
    }

    public void testDeadCode1() {
        doDetectSmellTest(1, 1);
    }

    public void testDeadCode2() {
        doDetectSmellTest(2, 1);
    }

    public void testDeadCode3() {
        doDetectSmellTest(3, 1);

    }

    public void testDeadCode4() {
        doDetectSmellTest(4, 0);
    }
}
