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

    public void testComments1() {
        doDetectSmellTest(1, 3);
    }
}
