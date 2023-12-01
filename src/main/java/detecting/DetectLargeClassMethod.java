package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import java.util.ArrayList;
import java.util.List;
import ui.UserProperties;
import utils.LoadPsi;

/**
 * Class to provide detecting: 'LargeClass' based on number of methods
 *
 * @author Jinyoung Kim
 */
public class DetectLargeClassMethod extends BaseDetectAction {

    public Project project;

    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "DLC_M";
    }

    /* Returns the story name as a string format for message. */
    @Override
    public String storyName() {
        return "Detect Large Class Method";
    }

    /* Returns the description of each story. (in html-style) */
    @Override
    public String description() {
        return "<html>When there are too many methods in the class<br/>" +
            " ,detect it as code smell large class.</html>";
    }

    /* Returns the precondition of each story. (in html-style) */
    @Override
    public String precondition() {
        return "<html>There are more methods in the class than a set standard</html>";
    }

    /**
     * Method that checks whether class is large class due to many methods
     *
     * @param e AnActionEvent
     * @return list of smelly PsiElement
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        List<PsiElement> largeClassesMethod = new ArrayList<>();
        PsiFile psiFile = LoadPsi.loadPsiFile(e);

        int userDefinedMaxMethods = UserProperties.getParam(storyID());

        for (PsiElement element : psiFile.getChildren()) {
            if (element instanceof PsiClass) {
                PsiClass psiClass = (PsiClass) element;
                if (detectSmell(psiClass, userDefinedMaxMethods)) {
                    largeClassesMethod.add(psiClass);
                }
            }
        }
        return largeClassesMethod; // No large class code smell detected
    }

    /**
     * Helper method to check if the class is considered 'large'.
     *
     * @param psiClass   PsiClass
     * @param maxMethods Max number of methods possible
     * @return true if the class is larger than set thresholds for methods
     */
    private boolean detectSmell(PsiClass psiClass, int maxMethods) {
        PsiMethod[] methods = psiClass.getMethods();
        return methods.length > maxMethods;
    }

}
