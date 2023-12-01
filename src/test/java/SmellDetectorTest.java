import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import detecting.BaseDetectAction;
import java.util.List;

/**
 * Abstract class to implement light test through files.
 *
 * @author Gwanho Kim
 */
public abstract class SmellDetectorTest extends LightJavaCodeInsightFixtureTestCase {

    @Override
    protected String getTestDataPath() {
        return "src/test/";
    }

    @Override
    protected String getBasePath() {
        return "testData";
    }

    protected abstract BaseDetectAction getDetectAction();

    /**
     * Test detectSmell() and check the result
     *
     * @param testNum       the number of test cases
     * @param expectedCount the number of expected smells
     */
    protected void doDetectSmellTest(int testNum, int expectedSmellCount) {
        myFixture.configureByFiles(getBasePath() + "/test" + testNum + ".java");
        // Set up the action event with the necessary context
        DataContext dataContext = DataManager.getInstance().getDataContext(myFixture.getEditor().getComponent());
        AnActionEvent event = AnActionEvent.createFromDataContext(
            String.valueOf(ActionManager.getInstance().getAction("")), null, dataContext);
        // Run the action
        BaseDetectAction action = getDetectAction();
        List<PsiElement> result = action.findSmells(event);
        // Check the result
        int detectedCount = result.size();
        assertEquals(expectedSmellCount, detectedCount);
    }
}