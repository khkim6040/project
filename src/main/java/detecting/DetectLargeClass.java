package detecting;


import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import java.util.ArrayList;
import java.util.List;

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
     * Method that checks whether candidate method has long parameter list
     *
     * @param e AnActionEvent
     * @return true if method has smell code
     */
    @Override
    public List<PsiElement> findSmells(AnActionEvent e) {
        List<PsiElement> largeClasses = new ArrayList<>();
        Project project = e.getProject();
        if (project == null) {
            return largeClasses;
        }

        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return largeClasses;
        }

        Document document = editor.getDocument();
        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
        if (psiFile == null) {
            return largeClasses;
        }

        //int userDefinedMaxFields = getUserDefinedThreshold(project, "Enter the maximum number of fields for a class:", 5);
        //int userDefinedMaxMethods = getUserDefinedThreshold(project, "Enter the maximum number of methods for a class:", 5);

        int userDefinedMaxFields = 5;
        int userDefinedMaxMethods = 5;

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


    /**
     private int getUserDefinedThreshold(Project project, String prompt, int defaultValue) {
     String response = Messages.showInputDialog(
     project,
     prompt,
     "Configure Threshold",
     Messages.getQuestionIcon(),
     Integer.toString(defaultValue),
     new IntegerInputValidator()
     );

     if (response == null) {
     return defaultValue; // User pressed Cancel or closed the dialog
     }

     try {
     return Integer.parseInt(response);
     } catch (NumberFormatException e) {
     return defaultValue;
     }
     }

     private static class IntegerInputValidator implements InputValidator {
    @Override public boolean checkInput(String inputString) {
    try {
    int value = Integer.parseInt(inputString);
    return value > 0;
    } catch (NumberFormatException e) {
    return false;
    }
    }

    @Override public boolean canClose(String inputString) {
    return checkInput(inputString);
    }
    }
     **/
}
