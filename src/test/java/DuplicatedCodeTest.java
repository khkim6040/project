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
        String expectedDescription = "There are identical or very similar methods. Repeated methods are 'duplicated code' code smell.";
        assertEquals(expectedDescription, duplicatedCode.description());
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
        expectedLocations.add(15);
        expectedLocations.add(21);
        expectedLocations.add(27);
        doFindSmellTest(3, expectedLocations);
    }

    public void testCleanCase() {
        doFindSmellTest(4, expectedLocations);
    }

    public void testEmptyMethodBody() {
        doFindSmellTest(5, expectedLocations);
    }
}
