import detecting.BaseDetectAction;
import detecting.MessageChain;

/**
 * Test for detecting: 'MessageChain'
 *
 * @author Jinyoung Kim, Gwanho Kim, Jinmin Goh
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
        String expectedDescription = "There are message chain whose length is longer than a set standard. When a sequence of method calls is chained together,detect it as code smell 'message chain'.";
        assertEquals(expectedDescription, messageChain.description());
    }

    public void testStatementHasLongerThanConfigurationChainLengthIsSmelly() {
        expectedLocations.add(18);
        doFindSmellTest(1, expectedLocations);
    }

    public void testStatementHasShorterOrEqualThanConfigurationChainLengthIsSmelly() {
        doFindSmellTest(2, expectedLocations);
    }

    public void testMixedCaseForSingleStatement() {
        expectedLocations.add(16);
        expectedLocations.add(19);
        expectedLocations.add(23);
        doFindSmellTest(3, expectedLocations);
    }

    public void testMixedCaseForVariousStatements() {
        expectedLocations.add(15);
        expectedLocations.add(18);
        expectedLocations.add(21);
        expectedLocations.add(23);
        expectedLocations.add(27);
        expectedLocations.add(29);
        expectedLocations.add(33);
        expectedLocations.add(35);
        expectedLocations.add(39);
        expectedLocations.add(41);
        expectedLocations.add(44);
        doFindSmellTest(4, expectedLocations);
    }

    public void testMessageChain5() {
        expectedLocations.add(16);
        expectedLocations.add(19);
        expectedLocations.add(22);
        expectedLocations.add(27);
        expectedLocations.add(32);
        expectedLocations.add(37);
        doFindSmellTest(5, expectedLocations);
    }

}
