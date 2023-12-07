import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import detecting.BaseDetectAction;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class to implement light test through files.
 *
 * @author Gwanho Kim, Jinmin Goh
 * @author CSED332 2020 Team Wanted
 */
public abstract class SmellDetectorTest extends LightJavaCodeInsightFixtureTestCase {

    // List of expected locations of smelly codes
    public List<Integer> expectedLocations = new ArrayList<>();

    @Override
    protected String getTestDataPath() {
        return "src/test/";
    }

    @Override
    protected String getBasePath() {
        return "testData";
    }

    /**
     * Get the BaseDetectAction object in detecting package
     *
     * @return the BaseDetectAction object
     */
    protected abstract BaseDetectAction getDetectAction();

    /**
     * Test detectSmell() and check the result
     *
     * @param testNum           the number of test cases
     * @param expectedLocations the list of expected locations of smelly codes
     */
    protected void doFindSmellTest(int testNum, List<Integer> expectedLocations) {
        myFixture.configureByFiles(getBasePath() + "/test" + testNum + ".java");
        // Set up the action event with the necessary context
        DataContext dataContext = DataManager.getInstance().getDataContext(myFixture.getEditor().getComponent());
        AnActionEvent event = AnActionEvent.createFromDataContext(
            String.valueOf(ActionManager.getInstance().getAction("")), null, dataContext);
        // Run the action
        BaseDetectAction action = getDetectAction();
        List<PsiElement> result = action.findSmells(event);
        List<Integer> actualLocations = new ArrayList<>();
        // Check the result of findSmells()
        for (PsiElement element : result) {
            int offset = element.getTextOffset();
            int lineNumber = myFixture.getEditor().getDocument().getLineNumber(offset);
            actualLocations.add(lineNumber + 1);
        }
        // Sort the lists and compare them
        expectedLocations.sort(Integer::compareTo);
        actualLocations.sort(Integer::compareTo);
        assertThat(actualLocations, is(expectedLocations));
    }
}