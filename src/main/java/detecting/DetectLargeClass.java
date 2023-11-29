package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import java.util.ArrayList;
import java.util.List;
import ui.UserProperties;
import utils.LoadPsi;

/**
 * Class to provide detecting: 'LargeClass'
 *
 * @author Jinyoung Kim
 */
public class DetectLargeClass extends BaseDetectAction {

    public Project project;
    //private PsiMethod focusMethod;

    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "DLC";
    }

    /* Returns the story name as a string format for message. */
    @Override
    public String storyName() {
        return "Detect Large Class";
    }

    /* Returns the description of each story. (in html-style) */
    @Override
    public String description() {
        return "<html>When there are too many methods or fields in the class<br/>" +
            " ,detect it as code smell large class.</html>";
    }

    /* Returns the precondition of each story. (in html-style) */
    @Override
    public String precondition() {
        return "<html>There are more methods or fields in the class than a set standard</html>";
    }

    /**
     * Method that checks whether class is large class
     *
     * @param e AnActionEvent
     * @return list of smelly PsiElement
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        List<PsiElement> largeClasses = new ArrayList<>();
        PsiFile psiFile = LoadPsi.loadPsiFile(e);

        int userDefinedMaxFields = UserProperties.getParam("DLC_F");
        int userDefinedMaxMethods = UserProperties.getParam("DLC_M");

        for (PsiElement element : psiFile.getChildren()) {
            if (element instanceof PsiClass) {
                PsiClass psiClass = (PsiClass) element;
                if (detectSmell(psiClass, userDefinedMaxFields, userDefinedMaxMethods)) {
                    largeClasses.add(psiClass);
                }
            }
        }
        return largeClasses; // No large class code smell detected
    }

    /**
     * Helper method to check if the class is considered 'large'.
     *
     * @param psiClass PsiClass
     * @return true if the class is larger than set thresholds for fields and methods
     */
    private boolean detectSmell(PsiClass psiClass, int maxFields, int maxMethods) {
        PsiField[] fields = psiClass.getFields();
        PsiMethod[] methods = psiClass.getMethods();
        return fields.length > maxFields || methods.length > maxMethods;
    }

}
