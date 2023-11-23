import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import detecting.BaseDetectAction;
import detecting.IdentifyLongMethod;

public class IdentifyLongMethodTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/IdentifyLongMethod";
    }

    protected void doDetectSmellTest(int testNum, boolean expected) {
        myFixture.configureByFiles(getBasePath() + "/test" + testNum + ".java");
        // Set up the action event with the necessary context
        DataContext dataContext = DataManager.getInstance().getDataContext(myFixture.getEditor().getComponent());
        AnActionEvent event = AnActionEvent.createFromDataContext(String.valueOf(ActionManager.getInstance().getAction("")), null, dataContext);
        // Run the action
        BaseDetectAction action = new IdentifyLongMethod();
        boolean result = action.detectSmell(event);
        // Check the result
        assertEquals(expected, result);
    }

    public void testIdentifyLongMethod1() {
        doDetectSmellTest(1, true);
    }

    public void testIdentifyLongMethod2() {
        doDetectSmellTest(1, false);
    }
}
