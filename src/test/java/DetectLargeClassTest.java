import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import detecting.BaseDetectAction;
import detecting.DetectLargeClass;

public class DetectLargeClassTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/DetectLargeClass";
    }

    protected void doDetectSmellTest(int testNum, boolean expected) {
        myFixture.configureByFiles(getBasePath() + "/test" + testNum + ".java");
        // Set up the action event with the necessary context
        DataContext dataContext = DataManager.getInstance().getDataContext(myFixture.getEditor().getComponent());
        AnActionEvent event = AnActionEvent.createFromDataContext(String.valueOf(ActionManager.getInstance().getAction("")), null, dataContext);
        // Run the action
        BaseDetectAction action = new DetectLargeClass();
        boolean result = action.detectSmell(event);
        // Check the result
        assertEquals(expected, result);
    }

    public void testDetectLargeClass1() {
        doDetectSmellTest(1, true);
    }

    public void testDetectLargeClass2() {

    }
}
