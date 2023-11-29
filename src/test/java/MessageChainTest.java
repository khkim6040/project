import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.psi.PsiElement;
import detecting.BaseDetectAction;
import detecting.MessageChain;
import java.util.List;

/**
 * Test for detecting: 'Message Chain'
 *
 * @author Jinyoung Kim
 */
public class MessageChainTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/MessageChain";
    }

    protected void doDetectSmellTest(int testNum, int expectedCount) {
        myFixture.configureByFiles(getBasePath() + "/test" + testNum + ".java");
        // Set up the action event with the necessary context
        DataContext dataContext = DataManager.getInstance().getDataContext(myFixture.getEditor().getComponent());
        AnActionEvent event = AnActionEvent.createFromDataContext(
            String.valueOf(ActionManager.getInstance().getAction("")), null, dataContext);
        // Run the action to detect message chains
        BaseDetectAction action = new MessageChain();
        List<PsiElement> result = action.findSmells(event);
        // Check the result to see if the number of detected message chains matches the expectation
        int detectedCount = result.size();
        assertEquals(expectedCount, detectedCount);
    }

    public void testMessageChain1() {
        doDetectSmellTest(1, 1);
    }

//    public void testLongParameterList2() {
//        doDetectSmellTest(2, 0);
//    }
}
