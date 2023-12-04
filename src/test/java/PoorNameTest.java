import detecting.BaseDetectAction;
import detecting.PoorName;

/**
 * Test for detecting: 'Poor name'
 *
 * @author Chanho Song
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
        String expectedDescription = "<html>When there are variables with poor names. <br/>" +
            "detect names that is hardly reflect its function.</html>";
        assertEquals(expectedDescription, poorName.description());
    }
    
    public void testPrecondition() {
        PoorName poorName = new PoorName();
        String expectedPrecondition = "<html>The variable which is just one alphabet or form of repeated alphabet. " +
            "The variable whose length is less than or equal to 3.</html>";
        assertEquals(expectedPrecondition, poorName.precondition());
    }

    public void testPoorName1() {
        doDetectSmellTest(1, 1);
    }

    public void testPoorName2() {
        doDetectSmellTest(2, 1);
    }

    public void testPoorName3() {
        doDetectSmellTest(3, 1);
    }

    public void testPoorName4() {
        doDetectSmellTest(4, 0);
    }
}
