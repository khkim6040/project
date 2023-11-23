import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.psi.PsiElement;
import detecting.BaseDetectAction;
import detecting.FindDuplicatedCode;

import java.util.List;

public class FindDuplicatedCodeTest extends SmellDetectorTest {

    @Override
    protected String getBasePath() {
        return super.getBasePath() + "/FindDuplicatedCode";
    }

    protected void doDetectSmellTest(int testNum, boolean expected) {
        myFixture.configureByFiles(getBasePath() + "/test" + testNum + ".java");
        // Set up the action event with the necessary context
        DataContext dataContext = DataManager.getInstance().getDataContext(myFixture.getEditor().getComponent());
        AnActionEvent event = AnActionEvent.createFromDataContext(String.valueOf(ActionManager.getInstance().getAction("")), null, dataContext);
        // Run the action
        BaseDetectAction action = new FindDuplicatedCode();
        List<PsiElement> result = action.findSmells(event);
        // Check the result
        //Todo : need to fix
        assertEquals(expected, result);
    }

    public void testLongParameterList1() {
        doDetectSmellTest(1, true);
    }

    public void testLongParameterList2() {

    }
}
