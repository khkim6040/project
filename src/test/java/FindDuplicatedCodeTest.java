import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.psi.PsiElement;
import detecting.BaseDetectAction;
import detecting.FindDuplicatedCode;

import java.util.List;

/**
 * Test for detecting: 'Duplicated code'
 *
 * @author Chanho Song
 */
public class FindDuplicatedCodeTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/FindDuplicatedCode";
    }

    protected void doDetectSmellTest(int testNum, int expectedCount) {
        myFixture.configureByFiles(getBasePath() + "/test" + testNum + ".java");
        // Set up the action event with the necessary context
        DataContext dataContext = DataManager.getInstance().getDataContext(myFixture.getEditor().getComponent());
        AnActionEvent event = AnActionEvent.createFromDataContext(String.valueOf(ActionManager.getInstance().getAction("")), null, dataContext);
        // Run the action
        BaseDetectAction action = new FindDuplicatedCode();
        List<PsiElement> result = action.findSmells(event);
        // Check the result
        int detectedCount = result.size();
        assertEquals(expectedCount, detectedCount);
    }

    public void testFindDuplicatedCode1() {
        doDetectSmellTest(1, 1); // Replace '1' with the expected number of duplicated code instances in test1.java
    }

//    public void testFindDuplicatedCode2() {
//        doDetectSmellTest(2, 0); // Assuming test2.java has no duplicated code
//    }
}
