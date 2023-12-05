import detecting.BaseDetectAction;
import detecting.MessageChain;

/**
 * Test for detecting: 'Message chain'
 *
 * @author Jinyoung Kim
 */
public class MessageChainTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/MessageChain";
    }

    @Override
    protected BaseDetectAction getDetectAction() {
        return new MessageChain();
    }

    public void testStoryName() {
        MessageChain messageChain = new MessageChain();
        assertEquals("Message Chain", messageChain.storyName());
    }

    public void testDescription() {
        MessageChain messageChain = new MessageChain();
        String expectedDescription = "<html>When a sequence of method calls is chained together<br/>" +
            " ,detect it as code smell message chain.</html>";
        assertEquals(expectedDescription, messageChain.description());
    }

    public void testPrecondition() {
        MessageChain messageChain = new MessageChain();
        String expectedPrecondition = "<html>Message chain whose length is longer than a set standard</html>";
        assertEquals(expectedPrecondition, messageChain.precondition());
    }

    public void testMessageChain1() {
        doDetectSmellTest(1, 1);
    }

    public void testMessageChain2() {
        doDetectSmellTest(2, 0);
    }

    public void testMessageChain3() {
        doDetectSmellTest(3, 3);
    }

    public void testMessageChain4() {
        doDetectSmellTest(4, 11);
    }

    public void testMessageChain5() {
        doDetectSmellTest(5, 6);
    }
}
