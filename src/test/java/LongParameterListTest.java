import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import detecting.BaseDetectAction;
import detecting.LongParameterList;
import com.intellij.psi.PsiElement;
import java.util.List;

/**
 * Test for detecting: 'Long parameter list'
 *
 * @author Jinyoung Kim
 */
public class LongParameterListTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/LongParameterList";
    }

    protected void doDetectSmellTest(int testNum, int expectedCount) {
        myFixture.configureByFiles(getBasePath() + "/test" + testNum + ".java");
        // Set up the action event with the necessary context
        DataContext dataContext = DataManager.getInstance().getDataContext(myFixture.getEditor().getComponent());
        AnActionEvent event = AnActionEvent.createFromDataContext(String.valueOf(ActionManager.getInstance().getAction("")), null, dataContext);
        // Run the action
        BaseDetectAction action = new LongParameterList();
        List<PsiElement> result = action.findSmells(event);
        // Check the result
        int detectedCount = result.size();
        assertEquals(expectedCount, detectedCount);
    }

    public void testLongParameterList1() {
        doDetectSmellTest(1, 1);
    }

//    public void testLongParameterList2() {
//        doDetectSmellTest(2, 0);
//    }
}
