import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.psi.PsiElement;
import detecting.BaseDetectAction;
import detecting.PoorName;
import java.util.List;

/**
 * Test for detecting: 'Poor name'
 *
 * @author Chanho Song
 */
public class PoorNameTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/PoorName";
    }

    protected void doDetectSmellTest(int testNum, int expectedCount) {
        myFixture.configureByFiles(getBasePath() + "/test" + testNum + ".java");
        // Set up the action event with the necessary context
        DataContext dataContext = DataManager.getInstance().getDataContext(myFixture.getEditor().getComponent());
        AnActionEvent event = AnActionEvent.createFromDataContext(
            String.valueOf(ActionManager.getInstance().getAction("")), null, dataContext);
        // Run the action
        BaseDetectAction action = new PoorName();
        List<PsiElement> result = action.findSmells(event);
        // Check the result
        int detectedCount = result.size();
        assertEquals(expectedCount, detectedCount);
    }

    public void testPoorName1() {
        doDetectSmellTest(1, 3); // Replace '1' with the expected number of instances with poor naming in test1.java
    }

//    public void testPoorName2() {
//        doDetectSmellTest(2, 0); // Assuming test2.java has no instances with poor naming
//    }
}
