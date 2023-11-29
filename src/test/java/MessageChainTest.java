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

    public void testMessageChain1() {
        doDetectSmellTest(1, 3);
    }
}
