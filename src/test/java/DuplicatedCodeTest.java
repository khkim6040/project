import detecting.BaseDetectAction;
import detecting.DuplicatedCode;

/**
 * Test for detecting: 'Duplicated code'
 *
 * @author Chanho Song
 */
public class DuplicatedCodeTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/DuplicatedCode";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new DuplicatedCode();
    }

    public void testStoryName() {
        DuplicatedCode duplicatedCode = new DuplicatedCode();
        assertEquals("Duplicated Code", duplicatedCode.storyName());
    }

    public void testDescription() {
        DuplicatedCode duplicatedCode = new DuplicatedCode();
        String expectedDescription = "<html>When there duplicated code. <br/>" +
            "Detect codes where the same code is repeated..</html>";
        assertEquals(expectedDescription, duplicatedCode.description());
    }

    public void testPrecondition() {
        DuplicatedCode duplicatedCode = new DuplicatedCode();
        String expectedPrecondition =
            "<html>Find the parts where identical or very similar code exists in multiple locations. " +
                "Identical or similar code blocks or methods .</html>";
        assertEquals(expectedPrecondition, duplicatedCode.precondition());
    }

    public void testDuplicatedCode1() {
        doDetectSmellTest(1, 2);
    }

    public void testDuplicatedCode2() {
        doDetectSmellTest(2, 2);
    }

    public void testDuplicatedCode3() {
        doDetectSmellTest(3, 3);
    }

    public void testDuplicatedCode4() {
        doDetectSmellTest(4, 0);
    }
}
