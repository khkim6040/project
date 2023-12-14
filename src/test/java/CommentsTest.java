import detecting.BaseDetectAction;
import detecting.Comments;

/**
 * Test for detecting: 'Comments'
 *
 * @author Chanho Song, Gwanho Kim, Jinmin Goh
 */
public class CommentsTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/Comments";
    }

    protected BaseDetectAction getDetectAction() {
        return new Comments();
    }

    public void testStoryName() {
        Comments comments = new Comments();
        assertEquals("Comments", comments.storyName());
    }

    public void testDescription() {
        Comments comments = new Comments();
        String expectedDescription = "There are comments longer than user defined length or include TODO, or Fix. These are smelly comments.";
        assertEquals(expectedDescription, comments.description());
    }

    public void testCommentLongerThanConfigurationLineIsSmelly() {
        expectedLocations.add(10);
        doFindSmellTest(1, expectedLocations);
    }

    public void testTODOCommentIsSmelly() {
        expectedLocations.add(11);
        doFindSmellTest(2, expectedLocations);
    }

    public void testFixCommentIsSmelly() {
        expectedLocations.add(10);
        doFindSmellTest(3, expectedLocations);
    }

    public void testCleanComment() {
        doFindSmellTest(4, expectedLocations);
    }

}
