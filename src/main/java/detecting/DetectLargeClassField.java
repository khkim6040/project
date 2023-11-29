package detecting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import java.util.ArrayList;
import java.util.List;
import ui.UserProperties;
import utils.LoadPsi;

/**
 * Class to provide detecting: 'LargeClass' based on number of fields
 *
 * @author Jinyoung Kim
 */
public class DetectLargeClassField extends BaseDetectAction {

    public Project project;

    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "DLC_F";
    }

    /* Returns the story name as a string format for message. */
    @Override
    public String storyName() {
        return "Detect Large Class Field";
    }

    /* Returns the description of each story. (in html-style) */
    @Override
    public String description() {
        return "<html>When there are too many fields in the class<br/>" +
            " ,detect it as code smell large class.</html>";
    }

    /* Returns the precondition of each story. (in html-style) */
    @Override
    public String precondition() {
        return "<html>There are more fields in the class than a set standard</html>";
    }

    /**
     * Method that checks whether class is large class due to many fields
     *
     * @param e AnActionEvent
     * @return list of smelly PsiElement
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        List<PsiElement> largeClassesField = new ArrayList<>();
        PsiFile psiFile = LoadPsi.loadPsiFile(e);

        int userDefinedMaxFields = UserProperties.getParam("DLC_F");

        for (PsiElement element : psiFile.getChildren()) {
            if (element instanceof PsiClass) {
                PsiClass psiClass = (PsiClass) element;
                if (detectSmell(psiClass, userDefinedMaxFields)) {
                    largeClassesField.add(psiClass);
                }
            }
        }
        return largeClassesField; // No large class code smell detected
    }

    /**
     * Helper method to check if the class is considered 'large'.
     *
     * @param psiClass  PsiClass
     * @param maxFields Number of maximum fields possible
     * @return true if the class is larger than set thresholds for fields and methods
     */
    private boolean detectSmell(PsiClass psiClass, int maxFields) {
        PsiField[] fields = psiClass.getFields();
        return fields.length > maxFields;
    }

}
