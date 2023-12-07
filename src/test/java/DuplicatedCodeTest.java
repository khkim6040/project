import detecting.BaseDetectAction;
import detecting.DuplicatedCode;

/**
 * Test for detecting: 'DuplicatedCode'
 *
 * @author Chanho Song, Gwanho Kim, Jinmin Goh
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

    public void testSimilarMethodsAreSmelly() {
        expectedLocations.add(13);
        expectedLocations.add(20);
        doFindSmellTest(1, expectedLocations);
    }


    public void testSameMethodsAreSmelly() {
        expectedLocations.add(13);
        expectedLocations.add(18);
        doFindSmellTest(2, expectedLocations);
    }

    public void testSimilarThreeMethodsAreSmelly() {
        expectedLocations.add(13);
        expectedLocations.add(18);
        expectedLocations.add(24);
        doFindSmellTest(3, expectedLocations);
    }

    public void testCleanCase() {
        doFindSmellTest(4, expectedLocations);
    }
}
