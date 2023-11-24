import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import detecting.BaseDetectAction;
import detecting.DetectLargeClass;
import com.intellij.psi.PsiElement;

import java.util.List;
/**
 * Test for detecting large class'
 *
 * @author Jinyoung Kim
 */

public class DetectLargeClassTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/DetectLargeClass";
    }

    protected void doDetectSmellTest(int testNum, int expectedCount) {
        myFixture.configureByFiles(getBasePath() + "/test" + testNum + ".java");
        // Set up the action event with the necessary context
        DataContext dataContext = DataManager.getInstance().getDataContext(myFixture.getEditor().getComponent());
        AnActionEvent event = AnActionEvent.createFromDataContext(String.valueOf(ActionManager.getInstance().getAction("")), null, dataContext);
        // Run the action
        BaseDetectAction action = new DetectLargeClass();
        List<PsiElement> result = action.findSmells(event);
        // Check the result
        int detectedCount = result.size();
        assertEquals(expectedCount, detectedCount);
    }

    public void testDetectLargeClass1() {
        doDetectSmellTest(1, 1);
        // Replace right number with the expected number of large classes in test1.java
    }

//    public void testDetectLargeClass2() {
//        doDetectSmellTest(2, 0);
//        // Assume test2.java has 0 large classes
//    }
}
