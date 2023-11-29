import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.psi.PsiElement;
import detecting.BaseDetectAction;
import detecting.DetectLargeClassMethod;
import java.util.List;

/**
 * Test for detecting large class due to methods
 *
 * @author Jinyoung Kim
 */

public class DetectLargeClassMethodTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/DetectLargeClassMethod";
    }

    protected void doDetectSmellTest(int testNum, int expectedCount) {
        myFixture.configureByFiles(getBasePath() + "/test" + testNum + ".java");
        // Set up the action event with the necessary context
        DataContext dataContext = DataManager.getInstance().getDataContext(myFixture.getEditor().getComponent());
        AnActionEvent event = AnActionEvent.createFromDataContext(
            String.valueOf(ActionManager.getInstance().getAction("")), null, dataContext);
        // Run the action
        BaseDetectAction action = new DetectLargeClassMethod();
        List<PsiElement> result = action.findSmells(event);
        // Check the result
        int detectedCount = result.size();
        assertEquals(expectedCount, detectedCount);
    }

    public void testDetectLargeClassMethod1() {
        doDetectSmellTest(1, 1);
        // Replace right number with the expected number of large classes in test1.java
    }

//    public void testDetectLargeClassMethod2() {
//        doDetectSmellTest(2, 0);
//        // Assume test2.java has 0 large classes
//    }
}
