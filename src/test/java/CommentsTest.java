import detecting.BaseDetectAction;
import detecting.Comments;

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
        String expectedDescription = "<html>When comments are smelly<br/>" +
            " ,detect it as a code smell.</html>";
        assertEquals(expectedDescription, comments.description());
    }

    public void testPrecondition() {
        Comments comments = new Comments();
        String expectedPrecondition = "<html>There are comments longer than three line or TODO, or Fix.</html>";
        assertEquals(expectedPrecondition, comments.precondition());
    }

    public void testComments1() {
        expectedLocations.add(10);
        doFindSmellTest(1, expectedLocations);
    }

    public void testComments2() {
        expectedLocations.add(11);
        doFindSmellTest(2, expectedLocations);
    }
    //
    //    public void testComments3() {
    //        doDetectSmellTest(3, 1);
    //    }
    //
    //    public void testComments4() {
    //        doDetectSmellTest(4, 0);
    //    }
}
