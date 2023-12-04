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
        doDetectSmellTest(1, 1);
    }

    public void testComments2() {
        doDetectSmellTest(2, 1);
    }

    public void testComments3() {
        doDetectSmellTest(3, 1);
    }

    public void testComments4() {
        doDetectSmellTest(4, 0);
    }
}
