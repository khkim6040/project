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
